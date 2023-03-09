package toyproject.ataglance.menu.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import toyproject.ataglance.menu.entity.Category;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, String> {

    @Modifying
    @Query("UPDATE ATAGLANCE_MENU_CATEGORY c SET c.enabled = :enabled, c.date_updated = CURRENT_TIMESTAMP WHERE c.category_id = :categoryId")
    void updateCategoryEnabledStatus(String categoryId, boolean enabled);

    @Modifying
    @Query("UPDATE ATAGLANCE_MENU_THEME t SET t.enabled = :enabled, t.date_updated = CURRENT_TIMESTAMP WHERE t.fk_category_id = :categoryId")
    void updateThemeEnabledStatus(String categoryId, boolean enabled);

    @Modifying
    @Query("UPDATE ATAGLANCE_MENU_DETAIL d SET d.enabled = :enabled, d.date_updated = CURRENT_TIMESTAMP WHERE d.fk_theme_id IN (SELECT theme_id FROM ATAGLANCE_MENU_THEME WHERE fk_category_id = :categoryId)")
    void updateDetailEnabledStatus(String categoryId, boolean enabled);
}
