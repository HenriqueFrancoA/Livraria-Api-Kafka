package br.com.henrique.orderservice.resources;

import br.com.henrique.orderservice.models.Order;
import br.com.henrique.orderservice.models.dto.OrderAddressDto;
import br.com.henrique.orderservice.models.dto.OrderDto;
import br.com.henrique.orderservice.models.dto.OrderStatusDto;
import br.com.henrique.orderservice.models.dto.OrderWithUserAddressDto;
import br.com.henrique.orderservice.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Pedidos")
@RestController
@RequestMapping("/api/orders/v1")
public class OrderResource {

    @Autowired
    private OrderService orderService;

    @Operation(summary = "Busca todos pedidos de um Usuário.")
    @GetMapping("/user/{id}")
    public List<Order> findByUser(@PathVariable Long id){
        return orderService.findByUser(id);
    }

    @Operation(summary = "Busca um pedido pelo ID.")
    @GetMapping("{id}")
    public ResponseEntity<Order> findById(@PathVariable Long id){
        return ResponseEntity.ok(orderService.findById(id));
    }

    @Operation(summary = "Busca um pedido pelo ID e caso exista atualiza o status")
    @PutMapping("/status/{id}")
    public ResponseEntity<Order> updateStatus(@PathVariable Long id, @RequestBody OrderStatusDto orderStatus) {
        return ResponseEntity.ok(orderService.updateStatus(id, orderStatus));
    }

    @Operation(summary = "Cria um novo pedido.")
    @PostMapping
    public ResponseEntity<Order> create(@RequestBody OrderWithUserAddressDto orderWithUserDto){
        Order savedPayment = orderService.create(orderWithUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPayment);
    }

}