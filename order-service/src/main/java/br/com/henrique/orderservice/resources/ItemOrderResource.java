package br.com.henrique.orderservice.resources;

import br.com.henrique.orderservice.models.ItemOrder;
import br.com.henrique.orderservice.models.dto.ItemOrderDto;
import br.com.henrique.orderservice.models.dto.ItemOrderListDto;
import br.com.henrique.orderservice.models.dto.ItemOrderQuantityDto;
import br.com.henrique.orderservice.services.ItemOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Itens Pedidos")
@RestController
@RequestMapping("/api/items/v1")
public class ItemOrderResource  {

    @Autowired
    private ItemOrderService itemOrderService;

    @Operation(summary = "Busca um item pelo ID.")
    @GetMapping("{id}")
    public ResponseEntity<ItemOrderDto> findById(@PathVariable Long id){
        return ResponseEntity.ok(itemOrderService.findById(id));
    }

}
