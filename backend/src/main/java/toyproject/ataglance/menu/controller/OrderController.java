package toyproject.ataglance.menu.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import toyproject.ataglance.menu.entity.Order;
import toyproject.ataglance.menu.model.AddtionalOrder;
import toyproject.ataglance.menu.model.CreateOrder;
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
	public ResponseEntity<List<Order>> findAll() {
		List<Order> orders = new ArrayList<>();
		
		orderRepository.findAll().forEach(orders::add); // 나눠 입력할 것인지 판단 (일단은 이렇게)
		
		
		
		
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Order> createOrder(@RequestBody Set<CreateOrder> createOrders) {
		return new ResponseEntity<>(orderService.createOrder(createOrders), HttpStatus.CREATED);
	}
	
	@PostMapping("/addtional") // 추가 주문 개념이 강하기에 Post Method 사용
	public ResponseEntity<Order> addtionalOrder(@RequestBody AddtionalOrder addtionalOrder) {
		return new ResponseEntity<>(orderService.addtionalOrder(addtionalOrder), HttpStatus.CREATED);
	}
	
	@PatchMapping("/{id}/pay-complete")
	public ResponseEntity<Order> confirmationOfOrder(@PathVariable("id") Long id) {
		return new ResponseEntity<>(orderService.confirmationOfOrder(id), HttpStatus.OK);
	}
	
}
