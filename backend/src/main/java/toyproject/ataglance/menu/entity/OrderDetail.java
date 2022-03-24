package toyproject.ataglance.menu.entity;

import org.springframework.data.relational.core.mapping.Table;

import lombok.Getter;

@Table("ATAGLANCE_ORDER_DETAIL")
@Getter 
public class OrderDetail {
	
	private String detailId;
	private int price; // detail 가격 * quantity
	private int quantity;
	
	protected OrderDetail() {}

	public OrderDetail(String detailId, int price, int quantity) {
		this.detailId = detailId;
		this.price = price;
		this.quantity = quantity;
	}
	
	
}
