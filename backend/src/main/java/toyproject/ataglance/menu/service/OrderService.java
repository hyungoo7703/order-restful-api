package toyproject.ataglance.menu.service;

import java.util.Set;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import toyproject.ataglance.formatter.MyNumberFormatter;
import toyproject.ataglance.menu.entity.Order;
import toyproject.ataglance.menu.model.CreateOrder;
import toyproject.ataglance.menu.repository.DetailRepository;
import toyproject.ataglance.menu.repository.OrderRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

	private final OrderRepository orderRepository;
	private final DetailRepository detailRepository;
	
	public void createOrder(Set<CreateOrder> createOrders) {
		String orderNumber = "";
		Long sameDayOrder = orderRepository.countByOrderStatus();
		log.info("sameDayOrder={}", sameDayOrder);
		
		if(sameDayOrder == 0L) 
			orderNumber = "000";
		else if(sameDayOrder >= 1 && sameDayOrder <= 9)
			orderNumber = "00" + sameDayOrder.toString();
		else 
			orderNumber = "0" + sameDayOrder.toString();
		log.info("orderNumber={}", orderNumber);
		
		Order order = new Order(orderNumber);
		for (CreateOrder createOrder : createOrders) {
			order.addDetail(detailRepository.findByName(createOrder.getDetailName()).get(), createOrder.getQuantity());
		}
		orderRepository.save(order);
	}
	
	// TODO 현재 총 가격과 총 주문내역만 출력시 전표로 보는 거와 차이가 없고, 사용의 이유는 없다. 따라서 어떤점을 차별성으로 줄 지 고려해본다.

}
