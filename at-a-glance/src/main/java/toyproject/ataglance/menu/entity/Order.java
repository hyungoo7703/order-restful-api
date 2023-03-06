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
import toyproject.ataglance.menu.constant.OrderStatus;

@Getter
@Table("ATAGLANCE_MENU_ORDER")
public class  Order {

	@Id
	@Column("ORDER_ID")
	private Long id; 
	private String orderNumber;
	private OrderStatus orderStatus;
	
	@CreatedDate
	private LocalDateTime dateCreated;
	@LastModifiedDate
	private LocalDateTime dateUpdated;
	
	@MappedCollection(idColumn = "ORDER_ID")
	private Set<OrderDetail> orderDetails = new HashSet<>();

	public void setOrderDetails(Set<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
		for (OrderDetail orderDetail : orderDetails) {
			orderDetail.setOrderId(this.id);
		}
	}

	protected Order() {}

	public Order(String orderNumber) {
		this.orderNumber = orderNumber;
		this.orderStatus = OrderStatus.PAY_YET;
	}
}
