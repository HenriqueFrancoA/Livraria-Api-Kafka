package br.com.henrique.orderservice.services;


import br.com.henrique.orderservice.exceptions.ValidationException;
import br.com.henrique.orderservice.models.Event;
import br.com.henrique.orderservice.models.dto.EventFilters;
import br.com.henrique.orderservice.repositories.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {

    private static final Logger log = LoggerFactory.getLogger(EventService.class);
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public void notifyEnding(Event event){
        event.setOrderId(event.getOrderId());
        event.setCreatedAt(LocalDateTime.now());
        save(event);
        log.info("Order {} with saga notified! TransactionId: {}", event.getOrderId(), event.getTransactionId());
    }

    public List<Event> findAll() {
        return eventRepository.findAllByOrderByCreatedAtDesc();
    }

    public Event findByFilters(EventFilters eventFilters) {
        validateEmptyFilters(eventFilters);

        if(eventFilters.getOrderId() != null){
            return  findByOrderId(eventFilters.getOrderId());
        }else {
            return  findByTransactionId(eventFilters.getTransactionId());
        }
    }

    private Event findByOrderId(Long orderId){
        return eventRepository.findFirstByOrderIdOrderByCreatedAtDesc(orderId)
                .orElseThrow(() -> new ValidationException("Event not found by orderID."));
    }

    private Event findByTransactionId(String transactionId){
        return eventRepository.findFirstByTransactionIdOrderByCreatedAtDesc(transactionId)
                .orElseThrow(() -> new ValidationException("Event not found by transactionID."));
    }

    private void validateEmptyFilters(EventFilters eventFilters){
        if(eventFilters.getOrderId() != null && eventFilters.getTransactionId().isEmpty() ){
            throw new ValidationException("OrderID or TransactionID must be informed.");
        }
    }

    public Event save(Event event){
        return eventRepository.save(event);
    }

}
