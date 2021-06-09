package com.ngandang.intern.controller;

import com.ngandang.intern.common.ERole;
import com.ngandang.intern.entity.Order;
import com.ngandang.intern.entity.Role;
import com.ngandang.intern.entity.ScrapCategory;
import com.ngandang.intern.exception.ResourceNotFoundException;
import com.ngandang.intern.model.dto.Mapper;
import com.ngandang.intern.model.dto.OrderDTO;
import com.ngandang.intern.model.request.ReqAddOrder;
import com.ngandang.intern.model.response.ResponseTransfer;
import com.ngandang.intern.reporitory.OrderRepository;
import com.ngandang.intern.reporitory.RoleRepository;
import com.ngandang.intern.reporitory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path="/order")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @GetMapping
    @ResponseBody
    public List<OrderDTO> getAllOrder() {
        return orderRepository.findAll()
                .stream()
                .map(Mapper::toOrderDTO)
                .collect(Collectors.toList());
    }
    @GetMapping("/")
    @ResponseBody
    public OrderDTO getOrder(@RequestParam Long id) {
        return orderRepository.findById(id).map(Mapper::toOrderDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Error: Order ID '"+ id+ "' is not found."));
    }

    @GetMapping("/{role}/")
    @ResponseBody
    public List<Order> getOrderByRole(@RequestParam Integer id, @PathVariable String role) {
        Role userRole;
        switch (role){
            case "buyer":
                userRole = roleRepository.findByName(ERole.ROLE_BUYER)
                        .orElseThrow(() -> new ResourceNotFoundException("Error: Role Buyer is not found."));
                break;
            case "seller":
                userRole = roleRepository.findByName(ERole.ROLE_SELLER)
                        .orElseThrow(() -> new ResourceNotFoundException("Error: Role Buyer is not found."));
                break;
            default: throw new RuntimeException("Error: Invalid path");
        }
        return userRepository.findUserByRole(userRole.getId(),id).map(user ->
                orderRepository.findByBuyerId(id)
        ).orElseThrow(() -> new ResourceNotFoundException("BuyerId " + id + " not found"));
    }

    @PostMapping(path="/add")
    @ResponseBody
    public ResponseTransfer add(@Valid @RequestBody ReqAddOrder reqOrder)
    {
        return userRepository.findUserById(reqOrder.getSellerId()).map(seller ->
        {
            Order order = new Order(reqOrder.getLocation(),
                    reqOrder.getSchedule(),
                    reqOrder.getStatus(),
                    seller);
            orderRepository.save(order);
            return new ResponseTransfer("Add successfully", Mapper.toOrderDTO(order));
        }).orElseThrow(() -> new ResourceNotFoundException("User Id " + reqOrder.getSellerId() + " not found"));
    }

}
