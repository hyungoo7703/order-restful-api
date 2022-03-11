package toyproject.ataglance.menu.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("ATAGLANCE_SALE_MASTER")
public class Sale { // 음식 판매 매출 엔티티 

	/*  
	   메뉴판과 연관관계는 주지 않는다. -> 관리의 용이성 
       또 실제로 메뉴판의 있는 메뉴를 삭제한다고 관련 매출을 지우지 않기에
	*/
	@Id
	@Column("sale_id")
	private Long id; // Auto-Increment
	
	// TODO 어떻게 표현할 것인가?
}
