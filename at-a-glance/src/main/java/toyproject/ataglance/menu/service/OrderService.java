package toyproject.ataglance.menu.service;

import toyproject.ataglance.menu.dto.OrderCreatedDto;
import toyproject.ataglance.menu.dto.OrderDto;
import toyproject.ataglance.menu.model.OrderModel;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {

    /*
    모든 주문 조회
    @return List<Menus>
     */
    List<OrderDto> findAll(LocalDateTime start, LocalDateTime end);

    /*
    주문 생성
    @param OrderModel
    @return OrderCreatedDto
     */
    OrderCreatedDto save(OrderModel orderModel);
}
