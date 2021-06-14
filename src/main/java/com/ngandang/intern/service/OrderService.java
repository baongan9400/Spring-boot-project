package com.ngandang.intern.service;

import com.ngandang.intern.common.ERole;
import com.ngandang.intern.common.EStatus;
import com.ngandang.intern.entity.Order;
import com.ngandang.intern.entity.Role;
import com.ngandang.intern.exception.ResourceNotFoundException;
import com.ngandang.intern.model.dto.Mapper;
import com.ngandang.intern.model.dto.OrderDTO;
import com.ngandang.intern.model.request.ReqAddOrder;
import com.ngandang.intern.model.request.ReqUpdateOrder;
import com.ngandang.intern.reporitory.OrderRepository;
import com.ngandang.intern.reporitory.RoleRepository;
import com.ngandang.intern.reporitory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    public List<OrderDTO> getAllOrder() {
        return orderRepository.findAll()
                .stream()
                .map(Mapper::toOrderDTO)
                .collect(Collectors.toList());
    }
    public OrderDTO getOrder(Long id) {
        return orderRepository.findById(id).map(Mapper::toOrderDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Error: Order ID '"+ id+ "' is not found."));
    }
    public OrderDTO save(ReqAddOrder reqOrder)
    {
        return userRepository.findUserById(reqOrder.getSellerId()).map(seller ->
        {
            Order order = new Order(reqOrder.getLocation(),
                    reqOrder.getSchedule(),
                    reqOrder.getStatus(),
                    seller);
            orderRepository.save(order);
            return  Mapper.toOrderDTO(order);
        }).orElseThrow(() -> new ResourceNotFoundException("User Id " + reqOrder.getSellerId() + " not found"));
    }
    public List<Order> getOrderByRole( Integer id,String role) {
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
    public OrderDTO confirm(ReqUpdateOrder reqOrder)
    {
        return orderRepository.findById(reqOrder.getOrderId()).map(order ->
        {
            order.setBuyer(userRepository.findUserById(reqOrder.getBuyer())
                    .orElseThrow(() -> new ResourceNotFoundException("User Id " + reqOrder.getBuyer() + " not found"))
            );
            order.setStatus(EStatus.CONFIRMED);
            orderRepository.save(order);
            return Mapper.toOrderDTO(order);
        }).orElseThrow(() -> new ResourceNotFoundException("Order Id " + reqOrder.getOrderId() + " not found"));
    }
}
