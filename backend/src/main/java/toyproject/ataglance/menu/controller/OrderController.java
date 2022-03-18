package toyproject.ataglance.menu.controller;

import java.text.ParseException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import toyproject.ataglance.exception.ResourceNotFoundException;
import toyproject.ataglance.menu.entity.Order;
import toyproject.ataglance.menu.model.CreateOrAddOrder;
import toyproject.ataglance.menu.repository.OrderRepository;
import toyproject.ataglance.menu.service.OrderService;

@CrossOrigin
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;
	private final OrderRepository orderRepository;
	
	@GetMapping
	public ResponseEntity<Iterable<Order>> findAll() {
		return new ResponseEntity<>(orderRepository.findAll(), HttpStatus.OK);
	}
	
	@PostMapping // 주문
	public ResponseEntity<Order> save(@RequestBody CreateOrAddOrder createOrder) throws ParseException {
		Order order = orderService.calculatorOrder(createOrder);
		return new ResponseEntity<>(order, HttpStatus.CREATED); 
	}
	
	@PutMapping // 추가 주문
	public ResponseEntity<Order> addtionalOrder(@RequestBody CreateOrAddOrder addOrder) throws ParseException {
		Order order = orderService.additionalOrder(addOrder);
		return new ResponseEntity<>(order, HttpStatus.OK); 
	}
	
	@PutMapping("/{tableNumber}") // 결제 전 최종 주문 확정
	public ResponseEntity<Order> completeOrder(@PathVariable("tableNumber") int tableNumber) {
		Order order = orderService.completeOrder(tableNumber);
		return new ResponseEntity<>(order, HttpStatus.OK); 
	}
}
