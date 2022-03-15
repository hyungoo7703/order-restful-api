package toyproject.ataglance.menu.controller;

import java.text.ParseException;

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
@RequestMapping("/api/menus/order")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;
	private final OrderRepository orderRepository;
	
	@GetMapping
	public ResponseEntity<Iterable<Order>> findAll() {
		return new ResponseEntity<>(orderRepository.findAll(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Order> save(@RequestBody CreateOrder createOrder) throws ParseException {
		
		Order order = orderService.calculatorOrder(createOrder);
		
		return new ResponseEntity<>(order, HttpStatus.CREATED); 
	}
}
