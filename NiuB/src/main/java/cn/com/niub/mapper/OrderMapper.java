package cn.com.niub.mapper;

import cn.com.niub.dto.OrderDto;

import java.util.List;

public interface OrderMapper {

    List<OrderDto> findOrder(OrderDto orderDto);

}
