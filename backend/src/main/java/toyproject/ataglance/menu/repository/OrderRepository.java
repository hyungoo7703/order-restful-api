package toyproject.ataglance.menu.repository;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import toyproject.ataglance.menu.entity.Order;

public interface OrderRepository extends CrudRepository<Order, Long>{
	
	@Query("SELECT * FROM ATAGLANCE_MENU_ORDER WHERE table_number = :tableNumber AND order_status = 'PAY_YET'")
	Optional<Order> findByTableNumberAndOrderStatus(int tableNumber);
	
}
