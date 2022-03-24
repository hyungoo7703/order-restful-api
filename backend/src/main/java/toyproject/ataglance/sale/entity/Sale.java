package toyproject.ataglance.sale.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("ATAGLANCE_SALE_MASTER")
public class Sale { // 총 매출 엔티티 

	@Id
	@Column("sale_id")
	private Long id; // Auto-Increment
	
}
