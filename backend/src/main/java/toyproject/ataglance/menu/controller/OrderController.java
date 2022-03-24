package toyproject.ataglance.menu.controller;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import toyproject.ataglance.menu.entity.Order;
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
	
	// == 임시 확인 수정 예정 == //

	@GetMapping
	public ResponseEntity<Iterable<Order>> findAll() {
		return new ResponseEntity<>(orderRepository.findAll(), HttpStatus.OK);
	}
	
	
	@PostMapping
	public ResponseEntity<HttpStatus> createOrder(@RequestBody Set<CreateOrder> createOrders) {
		orderService.createOrder(createOrders);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
}
