package toyproject.ataglance.menu.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toyproject.ataglance.menu.dto.CategoryCreateDto;
import toyproject.ataglance.menu.dto.OrderCreateDto;
import toyproject.ataglance.menu.dto.OrderCreatedDto;
import toyproject.ataglance.menu.dto.OrderDetailDto;
import toyproject.ataglance.menu.model.OrderDetailModel;
import toyproject.ataglance.menu.model.OrderModel;
import toyproject.ataglance.menu.service.OrderService;

import java.util.HashSet;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    //주문 생성
    @PostMapping("/order")
    public ResponseEntity<OrderCreatedDto> save(@RequestBody OrderCreateDto createDto) {
        OrderModel orderModel = new OrderModel();
        Set<OrderDetailModel> orderDetailModels = new HashSet<>();
        for (OrderDetailDto orderDetailDto : createDto.getOrderDetailDtos()) {
            OrderDetailModel orderDetailModel = new OrderDetailModel(
                    orderDetailDto.getDetailId(),
                    orderDetailDto.getQuantity()
            );
            orderDetailModels.add(orderDetailModel);
        }
        orderModel.setOrderDetailModels(orderDetailModels);
        OrderCreatedDto createdDto = orderService.save(orderModel);
        return new ResponseEntity<>(createdDto, HttpStatus.CREATED);
    }

}
