package br.com.henrique.paymentservice.repositories;

import br.com.henrique.paymentservice.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Boolean existsByOrderIdAndTransactionId(Long orderId, String transactionId);

    Optional<Payment> findByOrderIdAndTransactionId(Long orderId, String transactionId);
}
