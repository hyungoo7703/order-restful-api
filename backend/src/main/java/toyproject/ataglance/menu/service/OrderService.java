package toyproject.ataglance.menu.service;

import java.text.ParseException;
import java.util.Locale;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import toyproject.ataglance.exception.ResourceNotFoundException;
import toyproject.ataglance.formatter.MyNumberFormatter;
import toyproject.ataglance.menu.entity.Order;
import toyproject.ataglance.menu.entity.OrderStatus;
import toyproject.ataglance.menu.model.CreateOrAddOrder;
import toyproject.ataglance.menu.repository.OrderRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

	private final MyNumberFormatter myNumberFormatter;
	private final OrderRepository orderRepository;
	
	public Order calculatorOrder(CreateOrAddOrder createOrder) throws ParseException { // 영수증 형태 처럼 주문 내역 저장, 총 금액 계산 로직
		
		String orderHistory = "";
		int totalPrice = 0;
		
		for (Entry<String, String> entry : createOrder.getOrders().entrySet()) {
			orderHistory += "{ " + entry.getKey() + " : " + entry.getValue() + " }\n";
			totalPrice += myNumberFormatter.parse(entry.getValue(), Locale.KOREA);
		}
		
		Order order = new Order(createOrder.getTableNumber(), orderHistory, totalPrice);
		
		return orderRepository.save(order);
	}
	
	public Order additionalOrder(CreateOrAddOrder AddOrder) throws ParseException { // 추가 주문 로직
		
		String orderHistoryPlus = "";
		int totalPricePlus = 0;
		
		for (Entry<String, String> entry : AddOrder.getOrders().entrySet()) {
			orderHistoryPlus += "추가:{ " + entry.getKey() + " : " + entry.getValue() + " }\n";
			totalPricePlus += myNumberFormatter.parse(entry.getValue(), Locale.KOREA);
		}
		
		log.info("orderHistoryPlus={}",orderHistoryPlus);
		log.info("totalPricePlus={}",totalPricePlus);
		
		Order order = orderRepository.findByTableNumberAndOrderStatus(AddOrder.getTableNumber()).orElseThrow(() -> new ResourceNotFoundException("NOT FOUND"));
		order.setOrderHistory(order.getOrderHistory() + orderHistoryPlus);
		order.setTotalPrice(order.getTotalPrice() + totalPricePlus);
		
		return orderRepository.save(order);
	}
	
	public Order completeOrder(int tableNumber) { // 결제 전 최종 주문 확정
		
		Order order = orderRepository.findByTableNumberAndOrderStatus(tableNumber).orElseThrow(() -> new ResourceNotFoundException("NOT FOUND"));
		order.setOrderStatus(OrderStatus.PAY_COMPLETE);
		
		return orderRepository.save(order);
	}
	
	// TODO 현재 총 가격과 총 주문내역만 출력시 전표로 보는 거와 차이가 없고, 사용의 이유는 없다. 따라서 어떤점을 차별성으로 줄 지 고려해본다.

}
