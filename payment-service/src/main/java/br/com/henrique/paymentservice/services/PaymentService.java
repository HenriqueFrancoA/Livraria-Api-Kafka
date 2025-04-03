package br.com.henrique.paymentservice.services;

import br.com.henrique.paymentservice.enums.ESagaStatus;
import br.com.henrique.paymentservice.enums.PaymentStatus;
import br.com.henrique.paymentservice.exceptions.ValidationException;
import br.com.henrique.paymentservice.mapper.DozerMapper;
import br.com.henrique.paymentservice.models.Payment;
import br.com.henrique.paymentservice.models.dto.*;
import br.com.henrique.paymentservice.producer.KafkaProducer;
import br.com.henrique.paymentservice.repositories.PaymentRepository;
import br.com.henrique.paymentservice.utils.JsonUtil;
import br.com.henrique.paymentservice.utils.ValidationUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.logging.Logger;

import static br.com.henrique.paymentservice.enums.ESagaStatus.SUCCESS;
import static br.com.henrique.paymentservice.enums.PaymentStatus.*;

@Service
public class PaymentService {

    private final Logger logger = Logger.getLogger(PaymentService.class.getName());

    private static final String CURRENT_SOURCE = "PAYMENT_SERVICE";
    private static final Double REDUCE_SUM_VALUE = 0.0;
    private static final Double MIN_AMOUNT_VALUE = 0.1;

    private final JsonUtil jsonUtil;

    private final KafkaProducer kafkaProducer;

    @Autowired
    private PaymentRepository paymentRepository;

    public PaymentService(JsonUtil jsonUtil, KafkaProducer kafkaProducer) {
        this.jsonUtil = jsonUtil;
        this.kafkaProducer = kafkaProducer;
    }

    public PaymentDto findById(Long id) {
        logger.info("Procurando Pagamento do ID: " + id);
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() ->  new EntityNotFoundException("ID do Pagamento não encontrado."));

        return DozerMapper.parseObject(payment, PaymentDto.class);
    }

    public PaymentDto updateStatus(Long id, PaymentStatusDto paymentStatusDto) {
        logger.info("Atualizando status do Pagamento, ID: " + id);

        ValidationUtils.verifyPaymentStatus(paymentStatusDto.getStatus());

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() ->  new EntityNotFoundException("Pagamento não encontrado."));

        ValidationUtils.verifyWaiting(payment.getStatus());

        payment.setStatus(paymentStatusDto.getStatus());

        return DozerMapper.parseObject(paymentRepository.save(payment), PaymentDto.class);
    }
    
    public void createPayment(Event event){
        try{
            checkCurrentValidation(event);
            createPendingPayment(event);

            var payment = findByOrderIdAndTransactionId(event);
            validateAmount(payment.getValue().doubleValue());

            handleSuccess(event);
        } catch (Exception ex) {
            logger.info("Erro ao tentar criar pagamento: " + ex.getMessage());
            handleFailCurrentNotExecuted(event, ex.getMessage());
        }

        kafkaProducer.sendEvent(jsonUtil.toJson(event));
    }

    private void checkCurrentValidation(Event event) {
        if(paymentRepository.existsByOrderIdAndTransactionId(event.getOrderId(), event.getTransactionId()))
            throw new ValidationException("Há outro transactionId para esta validação.");
    }

    private void createPendingPayment(Event event) {
        var totalAmount = calculateAmount(event);
        var totalItems = calculateTotalItems(event);

        var payment = new Payment(
                event.getPayload().getId(),
                event.getTransactionId(),
                new BigDecimal(totalAmount),
                LocalDateTime.now(),
                WAITING.getDescription()
        );
        save(payment);
        setEventAmountItems(event, payment, totalItems);
    }

    private double calculateAmount(Event event) {
        return event.getPayload()
                .getItems()
                .stream()
                .map(product -> product.getQuantity() * product.getUnitPrice().doubleValue())
                .reduce(REDUCE_SUM_VALUE, Double::sum);
    }

    private int calculateTotalItems(Event event) {
        return event.getPayload()
                .getItems()
                .stream()
                .map(ItemOrder::getQuantity)
                .reduce(REDUCE_SUM_VALUE.intValue(), Integer::sum);
    }

    private void setEventAmountItems(Event event, Payment payment, int totalItems){
        event.getPayload().setTotalAmount(payment.getValue().doubleValue());
        event.getPayload().setTotalItems(totalItems);
    }

    private void validateAmount(double amount){
        if(amount < MIN_AMOUNT_VALUE)
            throw new ValidationException("A quantidade minima é: " + MIN_AMOUNT_VALUE);

    }

    private void handleSuccess(Event event) {
        event.setStatus(SUCCESS);
        event.setSource(CURRENT_SOURCE);

        addHistory(event, "Pagamento criado com sucesso!");
    }

    private void addHistory(Event event, String message){
        var history = new History(
                event.getSource(),
                event.getStatus(),
                message,
                LocalDateTime.now()
        );
        event.addToHistory(history);
    }

    private void handleFailCurrentNotExecuted(Event event, String message) {
        event.setStatus(ESagaStatus.ROLLBACK_PENDING);
        event.setSource(CURRENT_SOURCE);

        addHistory(event, "Falha ao criar pagamento: " + message);
    }

    public void realizeRefund(Event event) {
        event.setStatus(ESagaStatus.FAIL);
        event.setSource(CURRENT_SOURCE);

        try {
            changePaymentStatusToRefund(event);
            addHistory(event, "Rollback executado para pagamento");
        } catch (Exception ex) {
            addHistory(event, "Rollback não executado para pagamento: " + ex.getMessage());
        }

        kafkaProducer.sendEvent(jsonUtil.toJson(event));
    }

    private void changePaymentStatusToRefund(Event event){
        var payment = findByOrderIdAndTransactionId(event);

        payment.setStatus(CANCELED.getDescription());
        setEventAmountItems(event, payment, calculateTotalItems(event));
        save(payment);
    }

    private Payment findByOrderIdAndTransactionId(Event event){
        return paymentRepository
                .findByOrderIdAndTransactionId(event.getPayload().getId(), event.getTransactionId())
                .orElseThrow(() -> new ValidationException("Pagamento não encontrado por OrderID e TransactionID."));
    }

    private void save(Payment payment){
        paymentRepository.save(payment);
    }
}
