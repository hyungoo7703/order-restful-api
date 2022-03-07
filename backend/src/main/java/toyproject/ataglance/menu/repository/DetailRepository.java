package toyproject.ataglance.menu.repository;

import org.springframework.data.repository.CrudRepository;

import toyproject.ataglance.menu.entity.Detail;

public interface DetailRepository extends CrudRepository<Detail, String> {

	Iterable<Detail> findByThemeId(String themeId);
}
