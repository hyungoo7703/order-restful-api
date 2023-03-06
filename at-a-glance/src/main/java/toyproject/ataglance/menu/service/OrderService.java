package toyproject.ataglance.menu.service;

import toyproject.ataglance.menu.dto.OrderCreatedDto;
import toyproject.ataglance.menu.model.OrderModel;

public interface OrderService {

    /*
    주문 생성
    @param OrderModel
    @return OrderCreatedDto
     */
    OrderCreatedDto save(OrderModel orderModel);
}
