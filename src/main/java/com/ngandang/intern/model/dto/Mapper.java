package com.ngandang.intern.model.dto;

import com.ngandang.intern.entity.Order;
import com.ngandang.intern.entity.OrderDetails;
import com.ngandang.intern.entity.User;

import java.util.stream.Collectors;

public class Mapper {
    public static UserInformDTO toUserDTO(User user) {
        return new UserInformDTO(user.getId(),user.getUsername(),user.getPhone(),user.getEmail(),user.getRoles());
    }
    public static UserBaseDTO toUserBaseDTO(User user) {
        return new UserBaseDTO(user.getUsername(),user.getPhone(),user.getEmail());
    }
    public static OrderDTO toOrderDTO(Order order){
        OrderDTO tmp = new OrderDTO(order.getId(),
                order.getLocation(),
                order.getSchedule(),
                order.getTotal(),
                order.getWeight(),
                order.getStatus()
                );
        tmp.setDetails(order.getDetails().stream().map(Mapper::toOrderDetailDTO).collect(Collectors.toList()));
        tmp.setCreatedAt(order.getCreatedAt());
        tmp.setUpdatedAt(order.getUpdatedAt());
        if (order.getBuyer()!=null) tmp.setBuyer(Mapper.toUserBaseDTO(order.getBuyer()));
        if (order.getSeller()!=null) tmp.setSeller(Mapper.toUserBaseDTO(order.getSeller()));
        return tmp;
    }

    public static OrderDetailsDTO toOrderDetailDTO(OrderDetails details){
        return new OrderDetailsDTO(details.getId(),details.getTotal(),details.getWeight(), details.getScrap());
    }
}