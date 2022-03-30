package toyproject.ataglance.menu.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Getter;

@Getter
@Table("ATAGLANCE_MENU_ORDER")
public class Order {

	@Id
	@Column("order_id")
	private Long id; 
	private String orderNumber;
	private OrderStatus orderStatus;
	
	@CreatedDate
	private LocalDateTime dateCreated;
	@LastModifiedDate
	private LocalDateTime dateUpdated;
	
	@MappedCollection(idColumn = "order_id")
	private Set<OrderDetail> orderDetails = new HashSet<>();
	
	protected Order() {}

	public Order(String orderNumber) {
		this.orderNumber = orderNumber;
		this.orderStatus = OrderStatus.PAY_YET;
	}
	
	public void addDetail(Detail detail, int quantity) {
		this.orderDetails.add(new OrderDetail(detail.getId(), detail.getPrice() * quantity, quantity));
	}

	public Order(Order order, OrderStatus orderStatus) {
		this.id = order.id;
		this.orderNumber = order.orderNumber;
		this.dateCreated = order.dateCreated;
		this.orderDetails = order.orderDetails;
		this.orderStatus = orderStatus;
	}
	
	
	
}
