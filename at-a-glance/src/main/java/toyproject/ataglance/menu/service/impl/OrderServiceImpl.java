package toyproject.ataglance.menu.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import toyproject.ataglance.menu.dto.OrderCreatedDto;
import toyproject.ataglance.menu.dto.OrderDetailDto;
import toyproject.ataglance.menu.dto.OrderDto;
import toyproject.ataglance.menu.entity.Order;
import toyproject.ataglance.menu.entity.OrderDetail;
import toyproject.ataglance.menu.model.OrderDetailModel;
import toyproject.ataglance.menu.model.OrderModel;
import toyproject.ataglance.menu.repository.OrderRepository;
import toyproject.ataglance.menu.service.OrderService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public List<OrderDto> findAll(LocalDateTime start, LocalDateTime end) {
        List<Order> orders = orderRepository.findByDateCreatedBetween(start, end);
        List<OrderDto> orderDtos = new ArrayList<>();

        for (Order order : orders) {
            OrderModel orderModel = OrderModel.formEntity(order);
            OrderDto orderDto = new OrderDto();
            orderDto.setId(orderModel.getId());
            orderDto.setOrderNumber(orderModel.getOrderNumber());
            orderDto.setOrderStatus(orderModel.getOrderStatus());

            Set<OrderDetailDto> orderDetailDtos = new HashSet<>();
            for(OrderDetailModel orderDetailModel : orderModel.getOrderDetailModels()) {
                OrderDetailDto orderDetailDto = new OrderDetailDto();
                orderDetailDto.setDetailId(orderDetailModel.getDetailId());
                orderDetailDto.setQuantity(orderDetailModel.getQuantity());
                orderDetailDtos.add(orderDetailDto);
            }
            orderDto.setOrderDetailDtos(orderDetailDtos);
            orderDtos.add(orderDto);
        }
        return orderDtos;
    }

    @Override
    public OrderCreatedDto save(OrderModel orderModel) {
        /*
        주문 번호 생성
         */
        String orderNumber = "";
        Long sameDayOrder = orderRepository.countByOrderStatus();
        if(sameDayOrder == 0L)
            orderNumber = "000";
        else if(sameDayOrder >= 1 && sameDayOrder <= 8)
            orderNumber = "00" + sameDayOrder.toString();
        else
            orderNumber = "0" + sameDayOrder.toString();
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
