package com.ngandang.intern.reporitory;

import com.ngandang.intern.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findAll();
    Optional<Order> findById(Long id);
    List<Order> findByBuyerId(Integer buyerId);
    List<Order> findBySellerId(Integer sellerId);
}
