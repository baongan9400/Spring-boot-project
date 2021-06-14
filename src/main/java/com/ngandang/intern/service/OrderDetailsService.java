package com.ngandang.intern.service;

import com.ngandang.intern.entity.OrderDetails;
import com.ngandang.intern.entity.Scrap;
import com.ngandang.intern.exception.ResourceNotFoundException;
import com.ngandang.intern.model.dto.Mapper;
import com.ngandang.intern.model.dto.OrderDetailsDTO;
import com.ngandang.intern.model.request.ReqAddOrderDetails;
import com.ngandang.intern.model.response.ResponseTransfer;
import com.ngandang.intern.reporitory.OrderDetailsRepository;
import com.ngandang.intern.reporitory.OrderRepository;
import com.ngandang.intern.reporitory.ScrapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderDetailsService {
    @Autowired
    private OrderDetailsRepository detailsRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ScrapRepository scrapRepository;

    public List<OrderDetailsDTO> getAll() {
        return detailsRepository.findAll()
                .stream()
                .map(Mapper::toOrderDetailDTO)
                .collect(Collectors.toList());
    }
    public List<OrderDetails> getAllOrderDetails(@RequestParam Long orderId) {
        return detailsRepository.findByOrderId(orderId);
    }
    public List<OrderDetails> getDetailsByCategory(@RequestParam Long orderId,@RequestParam Integer categoryId ){
        return detailsRepository.findByOrderIdAndCategoryId(orderId,categoryId);
    }
    public OrderDetailsDTO save(@Valid @RequestBody ReqAddOrderDetails req)
    {
        return orderRepository.findById(req.getOrderId()).map(order ->
        {
            Scrap scrap = scrapRepository.findById(req.getScrapId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Scrap Id " + req.getScrapId() + " not found"));
            Double total = req.getWeight() * scrap.getPrice();

            OrderDetails order_details = new OrderDetails(total,req.getWeight(), order, scrap);
            order.setDetails(order_details);

            detailsRepository.save(order_details);
            return Mapper.toOrderDetailDTO(order_details);
        }).orElseThrow(() -> new ResourceNotFoundException("Order Id " + req.getOrderId() + " not found"));
    }

}
