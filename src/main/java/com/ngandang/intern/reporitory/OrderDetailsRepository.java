package com.ngandang.intern.reporitory;

import com.ngandang.intern.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails,Long> {
    List<OrderDetails> findByOrderId(Long id);
    @Query(value ="SELECT * FROM order_details JOIN scrap " +
            "ON order_details.scrap_id = scrap.id" +
            "WHERE order_details.order_id = ?1 and scrap.category_id= ?1", nativeQuery=true)
    List<OrderDetails> findByOrderIdAndCategoryId(Long orderId, Integer categoryId);
}
