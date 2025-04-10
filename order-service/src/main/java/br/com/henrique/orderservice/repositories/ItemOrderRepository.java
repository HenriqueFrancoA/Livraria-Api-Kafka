package br.com.henrique.orderservice.repositories;

import br.com.henrique.orderservice.models.ItemOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemOrderRepository extends JpaRepository<ItemOrder, Long> {

    List<ItemOrder> findByOrderId(Long orderId);
}
