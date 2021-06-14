package com.ngandang.intern.controller;

import com.ngandang.intern.entity.OrderDetails;
import com.ngandang.intern.model.dto.OrderDetailsDTO;
import com.ngandang.intern.model.request.ReqAddOrderDetails;
import com.ngandang.intern.model.response.ResponseTransfer;
import com.ngandang.intern.service.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(path="/order_details")
public class OrderDetailsController {
    @Autowired
    private OrderDetailsService service;

    @GetMapping
    @ResponseBody
    public List<OrderDetailsDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/")
    @ResponseBody
    public List<OrderDetails> getAllOrderDetails(@RequestParam Long orderId) {
        return service.getAllOrderDetails(orderId);
    }

    @GetMapping("/category")
    @ResponseBody
    public List<OrderDetails> getDetailsByCategory(@RequestParam Long orderId,@RequestParam Integer categoryId ){
        return service.getDetailsByCategory(orderId,categoryId);
    }

    @PostMapping(path="/add")
    @ResponseBody
    public ResponseTransfer add(@Valid @RequestBody ReqAddOrderDetails req)
    {
        return new ResponseTransfer("Add successfully", service.save(req));
    }
}
