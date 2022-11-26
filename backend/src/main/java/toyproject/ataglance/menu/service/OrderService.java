package toyproject.ataglance.menu.service;

import java.util.Set;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import toyproject.ataglance.exception.NotANormalRequestException;
import toyproject.ataglance.exception.ResourceNotFoundException;
import toyproject.ataglance.menu.entity.Detail;
import toyproject.ataglance.menu.entity.Order;
import toyproject.ataglance.menu.entity.OrderDetail;
import toyproject.ataglance.menu.entity.OrderStatus;
import toyproject.ataglance.menu.model.AddtionalOrder;
import toyproject.ataglance.menu.model.CreateOrder;
import toyproject.ataglance.menu.repository.DetailRepository;
import toyproject.ataglance.menu.repository.OrderRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

	private final OrderRepository orderRepository;
	private final DetailRepository detailRepository;
	
	public Order createOrder(Set<CreateOrder> createOrders) {
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
		return orderRepository.save(order);
	}
	
	public Order addtionalOrder(AddtionalOrder addtionalOrder) { // 추가주문
		Order order = orderRepository.findById(addtionalOrder.getId()).orElseThrow(() -> new ResourceNotFoundException("찾을 수 없는 주문 입니다."));
		log.info("orderStatus={}", order.getOrderStatus());
		if(order.getOrderStatus() == OrderStatus.PAY_COMPLETE) {
			throw new NotANormalRequestException("이미 계산 완료된 주문입니다.");
		}
		
		Set<OrderDetail> orderDetails = order.getOrderDetails();
		Detail detail = detailRepository.findByName(addtionalOrder.getDetailName()).orElseThrow(() -> new ResourceNotFoundException("찾을 수 없는 메뉴 입니다."));
				
		for (OrderDetail orderDetail : orderDetails) {
			if(orderDetail.getDetailId().equals(detail.getId())){
				orderDetails.remove(orderDetail);
				log.info("orderDetails={}", orderDetails);
				
				orderDetails.add(new OrderDetail(
						 detail.getId(), 
						 detail.getPrice() * (orderDetail.getQuantity() + addtionalOrder.getQuantity()), 
						 orderDetail.getQuantity() + addtionalOrder.getQuantity()
						));
				return orderRepository.save(order);
			}
		}
		
		orderDetails.add(new OrderDetail(
				 						 detail.getId(), 
				 						 detail.getPrice() * addtionalOrder.getQuantity(), 
				 						 addtionalOrder.getQuantity()
				 						));
		
		return orderRepository.save(order);
	}
	
	public Order confirmationOfOrder(Long id) { // 결제 전 주문확정
		Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("찾을 수 없는 주문 입니다."));
		Order confirmOrder = new Order(order, OrderStatus.PAY_COMPLETE);
		return orderRepository.save(confirmOrder);
	}

}
