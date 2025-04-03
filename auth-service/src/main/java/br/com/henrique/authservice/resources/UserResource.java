package br.com.henrique.authservice.resources;

import br.com.henrique.authservice.models.dto.UserDto;
import br.com.henrique.authservice.models.dto.UserFullnameDto;
import br.com.henrique.authservice.models.dto.UserToCreateDto;
import br.com.henrique.authservice.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Usuários", description = "Endpoints para o gerenciamento de Usuários")
@RestController
@RequestMapping("/api/user/v1")
public class UserResource {

    @Autowired
    private UserService userService;

    @GetMapping
    @Operation(summary = "Busca todos usuários cadastrados.",
        tags = "Usuários",
        security = @SecurityRequirement(name = "bearerAuth"),
        responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserDto.class))
                    )
            }),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
        }
    )
    public List<UserDto> findAll(){
        return userService.findAll();
    }

    @GetMapping("{id}")
    @Operation(summary = "Busca um usuário",
            security = @SecurityRequirement(name = "bearerAuth"),
            description = "Busca um usuário pelo seu ID",
            tags = "Usuários",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
                    ),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<UserDto> findById(
            @PathVariable
            @Parameter(description = "O id do usuário a ser encontrado")
            Long id){
        return ResponseEntity.ok(userService.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Busca um usuário pelo ID e caso exista atualiza o mesmo",
            security = @SecurityRequirement(name = "bearerAuth"),
            tags = "Usuários",
            responses = {
                    @ApiResponse(description = "Updated", responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody UserFullnameDto userFullname) {
        return ResponseEntity.ok(userService.update(id, userFullname));
    }

    @PostMapping
    @Operation(summary = "Cria um novo usuário.",
            security = @SecurityRequirement(name = "bearerAuth"),
            tags = "Usuários",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<UserDto> create(@RequestBody UserToCreateDto userToCreateDto) {
        UserDto savedUser = userService.save(userToCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

}
