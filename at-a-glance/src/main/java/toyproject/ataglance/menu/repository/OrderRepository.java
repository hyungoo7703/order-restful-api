package toyproject.ataglance.menu.repository;


import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import toyproject.ataglance.menu.entity.Order;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long>{
	
	@Query("SELECT count(*) FROM ATAGLANCE_MENU_ORDER WHERE order_status = 'PAY_YET'")
	Long countByOrderStatus();

	List<Order> findByDateCreatedBetween(LocalDateTime start, LocalDateTime end);

}
