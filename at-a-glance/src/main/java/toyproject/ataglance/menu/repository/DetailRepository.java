package toyproject.ataglance.menu.repository;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import org.springframework.data.repository.query.Param;
import toyproject.ataglance.menu.dto.DetailUpdateDto;
import toyproject.ataglance.menu.entity.Detail;

public interface DetailRepository extends CrudRepository<Detail, String> {

	@Modifying
	@Query("UPDATE ATAGLANCE_MENU_DETAIL d SET d.enabled = :enabled, d.date_updated = CURRENT_TIMESTAMP WHERE d.detail_id = :detailId")
	void updateDetailEnabledStatus(String detailId, boolean enabled);

	@Modifying
	@Query("UPDATE ATAGLANCE_MENU_DETAIL d SET d.price = :price, d.margin = :margin, d.on_event = :onEvent, d.date_updated = CURRENT_TIMESTAMP WHERE d.detail_id = :detailId")
	void updateDetail(String detailId, int price, int margin, boolean onEvent);

}
