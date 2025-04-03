package br.com.henrique.addressservice.resources;

import br.com.henrique.addressservice.models.dto.AddressDto;
import br.com.henrique.addressservice.models.dto.AddressWithUserDto;
import br.com.henrique.addressservice.models.dto.AddressWithoutUserDto;
import br.com.henrique.addressservice.services.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Endereços", description = "Endpoints para o gerenciamento de Endereços")
@RestController
@RequestMapping("/api/address/v1")
public class AddressResource {

    @Autowired
    private AddressService addressService;

    @GetMapping
    @Operation(summary = "Busca todos endereços cadastrados.",
        tags = "Endereços",
        security = @SecurityRequirement(name = "bearerAuth"),
        responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = AddressDto.class))
                    )
            }),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
        }
    )
    public ResponseEntity<PagedModel<EntityModel<AddressDto>>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "15") Integer limit,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
            ) {
        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "id"));
        return ResponseEntity.ok(addressService.findAll(pageable));
    }

    @GetMapping("{id}")
    @Operation(summary = "Busca um endereço",
            security = @SecurityRequirement(name = "bearerAuth"),
            description = "Busca um endereço pelo seu ID",
            tags = "Endereços",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AddressDto.class))
                    ),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<AddressDto> findById(
            @PathVariable
            @Parameter(description = "O id do endereço a ser encontrado")
            Long id){
        return ResponseEntity.ok(addressService.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Busca um endereço pelo ID e caso exista atualiza o mesmo",
            security = @SecurityRequirement(name = "bearerAuth"),
            tags = "Endereços",
            responses = {
                    @ApiResponse(description = "Updated", responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AddressDto.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<AddressDto> update(@PathVariable Long id, @RequestBody AddressWithoutUserDto addressWithoutUserDto) {
        return ResponseEntity.ok(addressService.update(id, addressWithoutUserDto));
    }

    @PostMapping
    @Operation(summary = "Cria um novo endreço.",
            security = @SecurityRequirement(name = "bearerAuth"),
            tags = "Endereços",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AddressDto.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<AddressDto> create(@RequestBody AddressWithUserDto addressWithUserDto) {
        AddressDto savedAddress = addressService.save(addressWithUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAddress);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta o endereço pelo ID",
            security = @SecurityRequirement(name = "bearerAuth"),
            tags = "Endereços",
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<Void> delete(
            @PathVariable
            @Parameter(description = "O id do endereço a ser deletado")
            Long id) {
        addressService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
