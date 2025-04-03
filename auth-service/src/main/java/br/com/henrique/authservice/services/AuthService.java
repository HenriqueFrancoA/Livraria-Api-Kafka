package br.com.henrique.authservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.henrique.authservice.models.User;
import br.com.henrique.authservice.models.vo.AccountCredentialsVO;
import br.com.henrique.authservice.models.vo.TokenVO;
import br.com.henrique.authservice.repositories.UserRepository;
import br.com.henrique.authservice.security.jwt.JwtTokenProvider;

@Service
public class AuthService {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @SuppressWarnings("rawtypes")
    public ResponseEntity signin(AccountCredentialsVO data) {
        try {
            String username = data.getUsername();
            String password = data.getPassword();

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

            User user = userRepository.findByUsername(username);

            TokenVO tokenResponse = new TokenVO();

            if (user != null) {
                tokenResponse = tokenProvider.createAccessToken(username, user.getRoles());
            } else {
                throw new UsernameNotFoundException("Username " + username + " não encontrado!");
            }

            return ResponseEntity.ok(tokenResponse);
        } catch (Exception e) {
            throw new BadCredentialsException("Usuário e/ou senha inválidos.");
        }
    }

    @SuppressWarnings("rawtypes")
    public ResponseEntity refreshToken(String username, String refreshToken) {
        try {
            User user = userRepository.findByUsername(username);

            TokenVO tokenResponse = new TokenVO();

            if (user != null) {
                tokenResponse = tokenProvider.refreshToken(refreshToken);
            } else {
                throw new UsernameNotFoundException("Username " + username + " não encontrado!");
            }

            return ResponseEntity.ok(tokenResponse);
        } catch (Exception e) {
            throw new BadCredentialsException("Usuário e/ou senha inválidos.");
        }
    }

}
