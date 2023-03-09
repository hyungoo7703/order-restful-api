package toyproject.ataglance.menu.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toyproject.ataglance.menu.dto.*;
import toyproject.ataglance.menu.entity.Order;
import toyproject.ataglance.menu.model.OrderDetailModel;
import toyproject.ataglance.menu.model.OrderModel;
import toyproject.ataglance.menu.repository.OrderRepository;
import toyproject.ataglance.menu.service.OrderService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;

    //주문 조회
    @GetMapping
    public ResponseEntity<List<OrderDto>> findAll(@RequestParam(value = "start", required = false)  String start,
                                                  @RequestParam(value = "end", required = false) String end) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS");

        // Default: 당일 주문 조회
        LocalDateTime startDateTime = (start == null) ? LocalDateTime.of(LocalDate.now(), LocalTime.MIN) : LocalDateTime.parse(start, formatter);
        LocalDateTime endDateTime = (end == null) ? LocalDateTime.of(LocalDate.now(), LocalTime.MAX) : LocalDateTime.parse(end, formatter);

        List<OrderDto> orderDtos = orderService.findAll(startDateTime, endDateTime);
        if (orderDtos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(orderDtos, HttpStatus.OK);
    }

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
