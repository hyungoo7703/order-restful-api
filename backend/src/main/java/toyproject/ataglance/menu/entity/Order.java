package toyproject.ataglance.menu.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Getter;
import lombok.Setter;

@Table("ATAGLANCE_MENU_ORDER")
@Getter @Setter
public class Order {

	@Id
	@Column("order_id")
	private Long id;
	private int tableNumber; // 추가 주문시 구분을 위해
	private String orderHistory; // 주문 내역
	private int totalPrice;
	private OrderStatus orderStatus;
	
	@CreatedDate
	private LocalDateTime dateCreated;
	@LastModifiedDate
	private LocalDateTime dateUpdated;
	
	protected Order() {}

	public Order(int tableNumber, String orderHistroy, int totalPrice) {
		this.tableNumber = tableNumber;
		this.orderHistory = orderHistroy;
		this.totalPrice = totalPrice;
		this.orderStatus = OrderStatus.PAY_YET;
	}
	
	
	
}
