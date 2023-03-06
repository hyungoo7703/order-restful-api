package toyproject.ataglance.menu.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import toyproject.ataglance.menu.entity.Category;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, String> {
}
