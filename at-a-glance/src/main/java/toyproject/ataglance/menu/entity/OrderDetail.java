package toyproject.ataglance.menu.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Getter;

@Table("ATAGLANCE_MENU_ORDER_DETAIL")
@Getter 
public class OrderDetail {

	@Id
	@Column("ORDER_DETAIL_ID")
	private Long id;
	private Long orderId;
	private String detailId;
	private int quantity;

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	protected OrderDetail() {}

	public OrderDetail(Long orderId, String detailId, int quantity) {
		this.orderId = orderId;
		this.detailId = detailId;
		this.quantity = quantity;
	}
}
