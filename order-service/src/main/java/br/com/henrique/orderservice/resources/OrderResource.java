package br.com.henrique.orderservice.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.henrique.orderservice.models.Order;
import br.com.henrique.orderservice.models.dto.OrderStatusDto;
import br.com.henrique.orderservice.models.dto.OrderWithUserAddressDto;
import br.com.henrique.orderservice.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Pedidos", description = "Endpoints para o gerenciamento de Pedidos")
@RestController
@RequestMapping("/api/orders/v1")
public class OrderResource {

    @Autowired
    private OrderService orderService;

    @Operation(summary = "Busca todos pedidos de um Usuário.", security = @SecurityRequirement(name = "bearerAuth"), tags = "Pedidos", responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Order.class))),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    @GetMapping("/user/{id}")
    public List<Order> findByUser(
            @PathVariable @Parameter(description = "O id do usuário para buscar os pedidos") Long id) {
        return orderService.findByUser(id);
    }

    @Operation(summary = "Busca um pedido pelo ID.", security = @SecurityRequirement(name = "bearerAuth"), description = "Busca um pedido pelo seu ID", tags = "Pedidos", responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Order.class))),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    @GetMapping("{id}")
    public ResponseEntity<Order> findById(
            @PathVariable @Parameter(description = "O id do pedido a ser encontrado") Long id) {
        return ResponseEntity.ok(orderService.findById(id));
    }

    @Operation(summary = "Busca um pedido pelo ID e caso exista atualiza o status", security = @SecurityRequirement(name = "bearerAuth"), tags = "Pedidos", responses = {
            @ApiResponse(description = "Updated", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Order.class))),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    @PutMapping("/status/{id}")
    public ResponseEntity<Order> updateStatus(
            @PathVariable @Parameter(description = "O id do pedido a ser atualizado") Long id,
            @RequestBody OrderStatusDto orderStatus) {
        return ResponseEntity.ok(orderService.updateStatus(id, orderStatus));
    }

    @Operation(summary = "Cria um novo pedido.", security = @SecurityRequirement(name = "bearerAuth"), tags = "Pedidos", responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Order.class))),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Order> create(@RequestBody OrderWithUserAddressDto orderWithUserDto) {
        Order savedPayment = orderService.create(orderWithUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPayment);
    }

}