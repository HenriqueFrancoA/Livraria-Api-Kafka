package br.com.henrique.addressservice.services;

import br.com.henrique.addressservice.enums.ESagaStatus;
import br.com.henrique.addressservice.exceptions.ValidationException;
import br.com.henrique.addressservice.mapper.DozerMapper;
import br.com.henrique.addressservice.models.Address;
import br.com.henrique.addressservice.models.dto.*;
import br.com.henrique.addressservice.producer.KafkaProducer;
import br.com.henrique.addressservice.repositories.AddressRepository;
import br.com.henrique.addressservice.resources.AddressResource;
import br.com.henrique.addressservice.utils.JsonUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.logging.Logger;

import static br.com.henrique.addressservice.enums.ESagaStatus.FAIL;
import static br.com.henrique.addressservice.enums.ESagaStatus.SUCCESS;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class AddressService  {

    private final Logger logger = Logger.getLogger(AddressService.class.getName());

    private static final String CURRENT_SOURCE = "ADDRESS_SERVICE";

    private final JsonUtil jsonUtil;

    private final KafkaProducer kafkaProducer;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PagedResourcesAssembler<AddressDto> assembler;

    public AddressService(JsonUtil jsonUtil, KafkaProducer kafkaProducer) {
        this.jsonUtil = jsonUtil;
        this.kafkaProducer = kafkaProducer;
    }

    public AddressDto update(Long id, AddressWithoutUserDto adressWithoutUserDto) {
        logger.info("Atualizando Endereço, ID: " + id);
        Address adr = addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ID do Endereço não encontrado."));

        adr.setStreet(adressWithoutUserDto.getStreet());
        adr.setNumber(adressWithoutUserDto.getNumber());
        adr.setCity(adressWithoutUserDto.getCity());
        adr.setState(adressWithoutUserDto.getState());
        adr.setZipCode(adressWithoutUserDto.getZipCode());
        adr.setCountry(adressWithoutUserDto.getCountry());

        AddressDto addressDto =  DozerMapper.parseObject( addressRepository.save(adr), AddressDto.class);

        addressDto.add(linkTo(methodOn(AddressResource.class).findById(addressDto.getKey())).withSelfRel());

        return addressDto;
    }

    public AddressDto save(AddressWithUserDto addressWithUserDto) {
        logger.info("Criando Endereço.");
        // implementar eureka
        //User user = userRepository.findById(addressWithUserDto.getUserId())
        //        .orElseThrow(() ->  new EntityNotFoundException("Usuário não encontrado."));

        Address address = new Address(
                addressWithUserDto,
                1L
        );

        AddressDto addressDto = DozerMapper.parseObject( addressRepository.save(address), AddressDto.class);
        addressDto.add(linkTo(methodOn(AddressResource.class).findById(addressDto.getKey())).withSelfRel());

        return addressDto;
    }

    public PagedModel<EntityModel<AddressDto>> findAll(Pageable pageable) {
        logger.info("Retornando todos endereços...");

        Page<Address> addressPage = addressRepository.findAll(pageable);

        Page<AddressDto> addressDtoPage = addressPage.map(a -> DozerMapper.parseObject(a, AddressDto.class));

        addressDtoPage.map(a -> a.add(linkTo(methodOn(AddressResource.class).findById(a.getKey())).withSelfRel()));

        Link link = linkTo(methodOn(AddressResource.class)
                .findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();

        return assembler.toModel(addressDtoPage, link);
    }

    public AddressDto findById(Long id) {
        logger.info("Procurando Endereço do ID: " + id);
        Address address = addressRepository.findById(id)
                .orElseThrow(() ->  new EntityNotFoundException("ID do Endereço não encontrado."));

        AddressDto addressDto = DozerMapper.parseObject(address, AddressDto.class);
        addressDto.add(linkTo(methodOn(AddressResource.class).findById(id)).withSelfRel());

        return addressDto;
    }

    public void delete(Long id) {
        logger.info("Deletando endereço do ID: " + id);
        if (!addressRepository.existsById(id)) {
            throw new EntityNotFoundException("Endereço não encontrado.");
        }
        addressRepository.deleteById(id);
    }

    public void validateAddressSuccess(Event event) {
        try{
            checkAddressValidation(event);
            checkAddressAndUser(event);

            handleSuccess(event);
        } catch (Exception ex) {
            logger.info("Erro ao validar o endereço: " + ex);
            handleFailCurrentNotExecuted(event, ex.getMessage());
        }

        kafkaProducer.sendEvent(jsonUtil.toJson(event));
    }


    private void checkAddressValidation(Event event) {
        if(!addressRepository.existsById(event.getPayload().getAddressId()))
            throw new ValidationException("Endereço não encontrado, ID: " + event.getPayload().getAddressId());
    }

    private void checkAddressAndUser(Event event) {
        Address address = addressRepository.findById(event.getPayload().getAddressId()).orElseThrow();

        if(!address.getUserId().equals(event.getPayload().getUserId()))
            throw new ValidationException("O Endereço não pertence ao Usuário informado.");
    }

    private void handleSuccess(Event event) {
        event.setStatus(SUCCESS);
        event.setSource(CURRENT_SOURCE);

        addHistory(event, "Endereço verificado com sucesso!");
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
        event.setStatus(ESagaStatus.ROLLBACK_PENDING);
        event.setSource(CURRENT_SOURCE);

        addHistory(event, "Falha ao validar endereço: " + message);
    }

    public void validateAddressFail(Event event) {
        event.setStatus(FAIL);
        event.setSource(CURRENT_SOURCE);
        try {
            addHistory(event, "Rollback executado para endereço");
        } catch (Exception ex) {
            addHistory(event, "Rollback não executado para endereço: " + ex.getMessage());
        }

        kafkaProducer.sendEvent(jsonUtil.toJson(event));
    }
}
