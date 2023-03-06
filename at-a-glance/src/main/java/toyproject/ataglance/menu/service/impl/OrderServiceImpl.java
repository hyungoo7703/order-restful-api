package toyproject.ataglance.menu.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import toyproject.ataglance.menu.dto.OrderCreatedDto;
import toyproject.ataglance.menu.dto.OrderDetailDto;
import toyproject.ataglance.menu.entity.Order;
import toyproject.ataglance.menu.entity.OrderDetail;
import toyproject.ataglance.menu.model.OrderDetailModel;
import toyproject.ataglance.menu.model.OrderModel;
import toyproject.ataglance.menu.repository.OrderRepository;
import toyproject.ataglance.menu.service.OrderService;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public OrderCreatedDto save(OrderModel orderModel) {
        /*
        주문 번호 생성
         */
        String orderNumber = "";
        Long sameDayOrder = orderRepository.countByOrderStatus();
        if(sameDayOrder == 0L)
            orderNumber = "001";
        else if(sameDayOrder >= 1 && sameDayOrder <= 8){
            sameDayOrder++;
            orderNumber = "00" + sameDayOrder.toString();
        }else {
            sameDayOrder++;
            orderNumber = "0" + sameDayOrder.toString();
        }
        log.info("orderNumber={}", orderNumber);
        orderModel.setOrderNumber(orderNumber);
        /*
        주문 저장
         */
        Order order = orderModel.toEntity();
        order = orderRepository.save(order); // Order 엔티티 저장 후, 저장된 엔티티 반환
        Set<OrderDetail> orderDetails = new HashSet<>();
        for (OrderDetailModel orderDetailModel : orderModel.getOrderDetailModels()) {
            orderDetails.add(orderDetailModel.toEntity());
        }
        order.setOrderDetails(orderDetails);
        Order savedOrder = orderRepository.save(order); // 갱신
        OrderCreatedDto createdDto = new OrderCreatedDto();
        createdDto.setId(savedOrder.getId());
        createdDto.setOrderNumber(savedOrder.getOrderNumber());
        createdDto.setOrderStatus(savedOrder.getOrderStatus());
        createdDto.setDateCreated(savedOrder.getDateCreated());
        Set<OrderDetailDto> orderDetailDtos = savedOrder.getOrderDetails().stream()
                .map(savedOrderDetail -> new OrderDetailDto(
                        savedOrderDetail.getDetailId(),
                        savedOrderDetail.getQuantity()))
                .collect(Collectors.toSet());
        createdDto.setOrderDetailDtos(orderDetailDtos);
        return createdDto;
    }
}
