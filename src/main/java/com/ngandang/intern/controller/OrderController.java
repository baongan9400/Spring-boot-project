package com.ngandang.intern.controller;

import com.ngandang.intern.entity.Order;
import com.ngandang.intern.model.dto.OrderDTO;
import com.ngandang.intern.model.request.ReqAddOrder;
import com.ngandang.intern.model.request.ReqUpdateOrder;
import com.ngandang.intern.model.response.ResponseTransfer;
import com.ngandang.intern.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(path="/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    @ResponseBody
    public List<OrderDTO> getAllOrder() {
        return orderService.getAllOrder();
    }
    @GetMapping("/")
    @ResponseBody
    public OrderDTO getOrder(@RequestParam Long id) {
        return orderService.getOrder(id);
    }

    @GetMapping("/{role}/")
    @ResponseBody
    public List<Order> getOrderByRole(@RequestParam Integer id, @PathVariable String role) {
        return orderService.getOrderByRole(id,role);
    }

    @PostMapping(path="/add")
    @ResponseBody
    public ResponseTransfer add(@Valid @RequestBody ReqAddOrder reqOrder)
    {
        orderService.save(reqOrder);
        return new ResponseTransfer("Add successfully", reqOrder );
    }
    @PutMapping(path="/confirm")
    @ResponseBody
    public ResponseTransfer confirmedOrder(@Valid @RequestBody ReqUpdateOrder reqOrder)
    {
        return new ResponseTransfer("Add successfully", orderService.confirm(reqOrder));
    }

}
