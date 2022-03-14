package toyproject.ataglance.menu.repository;

import org.springframework.data.repository.CrudRepository;

import toyproject.ataglance.menu.entity.Order;

public interface OrderRepository extends CrudRepository<Order, Long>{

}
