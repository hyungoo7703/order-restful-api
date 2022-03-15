package toyproject.ataglance.menu.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Getter;

@Table("ATAGLANCE_MENU_ORDER")
@Getter
public class Order {

	@Id
	@Column("order_id")
	private Long id;
	private int tableNumber; // 추가 주문시 구분을 위해
	private String orderHistroy; // 주문 내역
	private int totalPrice;
	
	@CreatedDate
	private LocalDateTime dateCreated;
	
	protected Order() {}

	public Order(int tableNumber, String orderHistroy, int totalPrice) {
		this.tableNumber = tableNumber;
		this.orderHistroy = orderHistroy;
		this.totalPrice = totalPrice;
	}
	
}
