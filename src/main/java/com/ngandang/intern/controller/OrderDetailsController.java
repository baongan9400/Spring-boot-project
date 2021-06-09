package com.ngandang.intern.controller;

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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path="/order_details")
public class OrderDetailsController {
    @Autowired
    private OrderDetailsRepository detailsRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ScrapRepository scrapRepository;

    @GetMapping
    @ResponseBody
    public List<OrderDetailsDTO> getAll() {
        return detailsRepository.findAll()
                .stream()
                .map(Mapper::toOrderDetailDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/")
    @ResponseBody
    public List<OrderDetails> getAllOrderDetails(@RequestParam Long orderId) {
        return detailsRepository.findByOrderId(orderId);
    }

    @GetMapping("/category")
    @ResponseBody
    public List<OrderDetails> getDetailsByCategory(@RequestParam Long orderId,@RequestParam Integer categoryId ){
        return detailsRepository.findByOrderIdAndCategoryId(orderId,categoryId);
    }

    @PostMapping(path="/add")
    @ResponseBody
    public ResponseTransfer add(@Valid @RequestBody ReqAddOrderDetails req)
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
            return new ResponseTransfer("Add successfully", Mapper.toOrderDetailDTO(order_details));
        }).orElseThrow(() -> new ResourceNotFoundException("Order Id " + req.getOrderId() + " not found"));
    }
}
