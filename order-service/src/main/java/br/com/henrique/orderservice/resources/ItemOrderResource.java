package br.com.henrique.orderservice.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.henrique.orderservice.models.dto.ItemOrderDto;
import br.com.henrique.orderservice.services.ItemOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Itens Pedidos", description = "Endpoints para o gerenciamento de Itens de Pedidos")
@RestController
@RequestMapping("/api/items/v1")
public class ItemOrderResource {

    @Autowired
    private ItemOrderService itemOrderService;

    @GetMapping("{id}")
    @Operation(summary = "Busca um item pelo ID.", security = @SecurityRequirement(name = "bearerAuth"), description = "Busca um item de pedido pelo seu ID", tags = "Itens Pedidos", responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ItemOrderDto.class))),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    public ResponseEntity<ItemOrderDto> findById(
            @PathVariable @Parameter(description = "O id do item a ser encontrado") Long id) {
        return ResponseEntity.ok(itemOrderService.findById(id));
    }

}
