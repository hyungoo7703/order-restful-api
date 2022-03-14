package toyproject.ataglance.menu.service;

import java.text.ParseException;
import java.util.Locale;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import toyproject.ataglance.formatter.MyNumberFormatter;
import toyproject.ataglance.menu.entity.Order;
import toyproject.ataglance.menu.model.CreateOrder;
import toyproject.ataglance.menu.repository.OrderRepository;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final MyNumberFormatter myNumberFormatter;
	private final OrderRepository orderRepository;
	
	public void calculatorOrder(CreateOrder createOrder) throws ParseException {
		String orderHistory = "";
		int totalPrice = 0;
		
		for (Entry<String, String> entry : createOrder.getOrders().entrySet()) {
			orderHistory += "{" + entry.getKey() + "} ";
			totalPrice += myNumberFormatter.parse(entry.getValue(), Locale.KOREA);
		}
		
		Order order = new Order(orderHistory, totalPrice);
		orderRepository.save(order);
	}
}
