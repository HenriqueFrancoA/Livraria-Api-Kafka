package br.com.henrique.orderservice.repositories;

import br.com.henrique.orderservice.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findAllByOrderByCreatedAtDesc();

    Optional<Event> findFirstByOrderIdOrderByCreatedAtDesc(Long orderId);

    Optional<Event> findFirstByTransactionIdOrderByCreatedAtDesc(String transactionId);
}
