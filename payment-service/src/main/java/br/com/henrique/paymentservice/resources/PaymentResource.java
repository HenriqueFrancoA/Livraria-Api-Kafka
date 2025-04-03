package br.com.henrique.paymentservice.resources;

import br.com.henrique.paymentservice.models.dto.*;
import br.com.henrique.paymentservice.services.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Pagamentos")
@RestController
@RequestMapping("/api/payments/v1")
public class PaymentResource {

    @Autowired
    private PaymentService paymentService;

    @Operation(summary = "Busca um pagamento pelo ID.")
    @GetMapping("{id}")
    public ResponseEntity<PaymentDto> findById(@PathVariable Long id){
        return ResponseEntity.ok(paymentService.findById(id));
    }

    @Operation(summary = "Busca um pagamento pelo ID e caso exista atualiza o mesmo")
    @PutMapping("/status/{id}")
    public ResponseEntity<PaymentDto> updateStatus(@PathVariable Long id, @RequestBody PaymentStatusDto paymentStatusDto) {
        return ResponseEntity.ok(paymentService.updateStatus(id, paymentStatusDto));
    }

}
