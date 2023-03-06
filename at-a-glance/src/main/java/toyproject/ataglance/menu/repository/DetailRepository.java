package toyproject.ataglance.menu.repository;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import toyproject.ataglance.menu.entity.Detail;

public interface DetailRepository extends CrudRepository<Detail, String> {

	Iterable<Detail> findByThemeId(String themeId);
	
	@Modifying
	@Query("DELETE FROM ataglance_menu_detail WHERE fk_theme_id=:themeId")
	void deleteAllByThemeId(String themeId);
	
	Optional<Detail> findByName(String detailName);
}
