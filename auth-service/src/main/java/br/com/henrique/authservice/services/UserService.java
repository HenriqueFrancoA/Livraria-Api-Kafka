package br.com.henrique.authservice.services;

import br.com.henrique.authservice.exceptions.ListEmptyException;
import br.com.henrique.authservice.exceptions.UsernameAlreadyExistsException;
import br.com.henrique.authservice.exceptions.ValidationException;
import br.com.henrique.authservice.mapper.DozerMapper;
import br.com.henrique.authservice.models.Permission;
import br.com.henrique.authservice.models.User;
import br.com.henrique.authservice.models.UserPermission;
import br.com.henrique.authservice.models.dto.*;
import br.com.henrique.authservice.models.embedd.UserPermissionId;
import br.com.henrique.authservice.producer.KafkaProducer;
import br.com.henrique.authservice.repositories.PermissionRepository;
import br.com.henrique.authservice.repositories.UserPermissionRepository;
import br.com.henrique.authservice.repositories.UserRepository;
import br.com.henrique.authservice.resources.UserResource;
import br.com.henrique.authservice.utils.JsonUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static br.com.henrique.authservice.enums.ESagaStatus.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class UserService implements UserDetailsService{

    private Logger logger = Logger.getLogger(UserService.class.getName());

    private static final String CURRENT_SOURCE = "USER_SERVICE";

    private final JsonUtil jsonUtil;

    private final KafkaProducer kafkaProducer;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private UserPermissionRepository userPermissionRepository;

    public UserService(JsonUtil jsonUtil, KafkaProducer kafkaProducer) {
        this.jsonUtil = jsonUtil;
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Procurando Usuário pelo nome: " + username);

        User user = userRepository.findByUsername(username);

        if (user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException("Usuário :" + username + " não encontrado!");
        }

    }

    public List<UserDto> findAll() {
        logger.info("Retornando todos Usuários...");

        List<UserDto> listUsers = DozerMapper.parseListObjects(userRepository.findAll(), UserDto.class);

        for (UserDto u : listUsers) {
            u.add(linkTo(methodOn(UserResource.class).findById(u.getKey())).withSelfRel());
        }

        return listUsers;
    }

    public UserDto findById(Long id) {
        logger.info("Procurando Usuário do ID: " + id);
        User user = userRepository.findById(id)
                .orElseThrow(() ->  new EntityNotFoundException("ID do Usuário não encontrado."));

        UserDto userDto = DozerMapper.parseObject(user, UserDto.class);
        userDto.add(linkTo(methodOn(UserResource.class).findById(id)).withSelfRel());

        return userDto;
    }

    public UserDto update(Long id, UserFullnameDto userFullname) {
        logger.info("Atualizando Usuário, ID: " + id);

        User user = userRepository.findById(id)
                .orElseThrow(() ->  new EntityNotFoundException("ID do Usuário não encontrado."));

        user.setFullName(userFullname.getFullName());

        UserDto userDto = DozerMapper.parseObject( userRepository.save(user), UserDto.class) ;

        userDto.add(linkTo(methodOn(UserResource.class).findById(id)).withSelfRel());

        return userDto;
    }


    public UserDto save(UserToCreateDto userToCreateDto) {
        logger.info("Criando Usuário.");

        Permission permission;
        List<Permission> listPermission = new ArrayList<>();

        if(userToCreateDto.getPermissionsIds().isEmpty())
            throw new ListEmptyException("Lista de Permissão está vazia.");

        for(long id : userToCreateDto.getPermissionsIds()){
             permission = permissionRepository.findById(id)
                    .orElseThrow(() ->  new EntityNotFoundException("ID da Permissão não encontrado."));
             listPermission.add(permission);
        }

        User userNameInUse = userRepository.findByUsername(userToCreateDto.getUserName());

        if(userNameInUse != null)
            throw new UsernameAlreadyExistsException("O nome de usuário: '" + userToCreateDto.getUserName() + "' já está em uso.");

        String password = encodePassword(userToCreateDto.getPassword());

        User user = new User(
                userToCreateDto.getUserName(),
                userToCreateDto.getFullName(),
                password.substring(8),
                true,
                true,
                true,
                true
        );

        User userCreated = userRepository.save(user);

        UserPermission userPermission;

        UserPermissionId userPermissionId;


        for(Permission perm : listPermission){
            userPermissionId = new UserPermissionId(user.getId(), perm.getId());
            userPermission = new UserPermission(userPermissionId, user, perm);

            userPermissionRepository.save(userPermission);
        }


        UserDto userDto = DozerMapper.parseObject(userCreated, UserDto.class);
        userDto.add(linkTo(methodOn(UserResource.class).findById(userCreated.getId())).withSelfRel());

        return userDto;
    }

    private static String encodePassword(String password) {
        Map<String, PasswordEncoder> encoders = new HashMap<>();

        Pbkdf2PasswordEncoder pbkdf2PasswordEncoder = new Pbkdf2PasswordEncoder("",
        8, 185000,
        SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);

        encoders.put("pbkdf2", pbkdf2PasswordEncoder);

        DelegatingPasswordEncoder passwordEncoder = new
        DelegatingPasswordEncoder("pbkdf2", encoders);

        passwordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2PasswordEncoder);

        return passwordEncoder.encode(password);
    }

    public void validateUserSuccess(Event event) {
        try{
            checkUserValidation(event);

            handleSuccess(event);
        } catch (Exception ex) {
            logger.info("Erro ao validar o usuário: " + ex);
            handleFailCurrentNotExecuted(event, ex.getMessage());
        }

        kafkaProducer.sendEvent(jsonUtil.toJson(event));
    }

    private void checkUserValidation(Event event) {
        if(!userRepository.existsById(event.getPayload().getUserId()))
            throw new ValidationException("Endereço não encontrado, ID: " + event.getPayload().getUserId());
    }

    private void handleSuccess(Event event) {
        event.setStatus(SUCCESS);
        event.setSource(CURRENT_SOURCE);

        addHistory(event, "Usuário verificado com sucesso!");
    }

    private void addHistory(Event event, String message){
        var history = new History(
                event.getSource(),
                event.getStatus(),
                message,
                LocalDateTime.now()
        );
        event.addToHistory(history);
    }

    private void handleFailCurrentNotExecuted(Event event, String message) {
        event.setStatus(ROLLBACK_PENDING);
        event.setSource(CURRENT_SOURCE);

        addHistory(event, "Falha ao validar usuário: " + message);
    }

    public void validateUserFail(Event event) {
        event.setStatus(FAIL);
        event.setSource(CURRENT_SOURCE);
        try {
            addHistory(event, "Rollback executado para usuário");
        } catch (Exception ex) {
            addHistory(event, "Rollback não executado para usuário: " + ex.getMessage());
        }

        kafkaProducer.sendEvent(jsonUtil.toJson(event));
    }
}
