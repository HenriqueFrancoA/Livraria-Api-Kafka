package br.com.henrique.paymentservice.resources;

import br.com.henrique.paymentservice.models.Payment;
import br.com.henrique.paymentservice.models.dto.PaymentStatusDto;
import br.com.henrique.paymentservice.services.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Pagamentos", description = "Endpoints para o gerenciamento de Pagamentos")
@RestController
@RequestMapping("/api/payments/v1")
public class PaymentResource {

    @Autowired
    private PaymentService paymentService;

    @Operation(summary = "Busca um pagamento pelo ID.", security = @SecurityRequirement(name = "bearerAuth"), description = "Busca um pagamento pelo seu ID", tags = "Pagamentos", responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Payment.class))),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    @GetMapping("{id}")
    public ResponseEntity<Payment> findById(
            @PathVariable @Parameter(description = "O id do pagamento a ser encontrado") Long id) {
        return ResponseEntity.ok(paymentService.findById(id));
    }

    @Operation(summary = "Busca um pagamento pelo ID e caso exista atualiza o mesmo", security = @SecurityRequirement(name = "bearerAuth"), tags = "Pagamentos", responses = {
            @ApiResponse(description = "Updated", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Payment.class))),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    @PutMapping("/status/{id}")
    public ResponseEntity<Payment> updateStatus(
            @PathVariable @Parameter(description = "O id do pagamento a ser atualizado") Long id,
            @RequestBody PaymentStatusDto paymentStatusDto) {
        return ResponseEntity.ok(paymentService.updateStatus(id, paymentStatusDto));
    }

}
