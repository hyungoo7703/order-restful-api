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
	
	public Order calculatorOrder(CreateOrder createOrder) throws ParseException { // 영수증 형태 처럼 주문 내역 저장, 총 금액 계산 로직
		
		String orderHistory = "";
		int totalPrice = 0;
		
		for (Entry<String, String> entry : createOrder.getOrders().entrySet()) {
			orderHistory += "{ " + entry.getKey() + " : " + entry.getValue() + " }\\n";
			totalPrice += myNumberFormatter.parse(entry.getValue(), Locale.KOREA);
		}
		
		Order order = new Order(createOrder.getTableNumber(), orderHistory, totalPrice);
		
		return orderRepository.save(order);
	}
	
	public void additionalOrder(CreateOrder createOrder) { // 추가 주문 로직
		
	}
	
	// TODO 현재 총 가격과 총 주문내역만 출력시 전표로 보는 거와 차이가 없고, 사용의 이유는 없다. 따라서 어떤점을 차별성으로 줄 지 고려해본다.
	// TODO Map 자료형의 특징상 key 값이 중복되면 안되기 때문에 추가 주문시 단순 업데이트로 안된다. -> 효과적인 방법 고려	
	
}
