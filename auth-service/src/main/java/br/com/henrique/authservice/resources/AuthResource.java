package br.com.henrique.authservice.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.henrique.authservice.models.vo.AccountCredentialsVO;
import br.com.henrique.authservice.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Endpoint de Autenticação")
@RestController
@RequestMapping("/auth")
public class AuthResource {

    @Autowired
    AuthService authService;

    @SuppressWarnings("rawtypes")
    @Operation(summary = "Faz a autentificação do usuário e retorna um token.")
    @PostMapping(value = "/signin")
    public ResponseEntity signin(@RequestBody AccountCredentialsVO data) {
        if (checkIfParamsIsNotNull(data.getUsername()) || checkIfParamsIsNotNull(data.getPassword()))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Requisição inválida.");

        try {
            var token = authService.signin(data);

            if (token == null)
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Requisição inválida.");

            return token;

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário e/ou senha inválidos.");
        }

    }

    @SuppressWarnings("rawtypes")
    @Operation(summary = "Atualiza o token do usuário.")
    @PutMapping("/refresh/{username}")
    public ResponseEntity refreshToken(@PathVariable String username,
            @RequestHeader("Authorization") String refreshToken) {
        System.out.println("Aqui passou... " + username + " com token: " + refreshToken);
        if (checkIfParamsIsNotNull(username) || checkIfParamsIsNotNull(refreshToken))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Requisição inválida.");

        try {
            var token = authService.refreshToken(username, refreshToken);

            if (token == null)
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Requisição inválida.");

            return token;

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário e/ou senha inválidos.");
        }

    }

    private boolean checkIfParamsIsNotNull(String param) {
        return param == null || param.isBlank();
    }
}
