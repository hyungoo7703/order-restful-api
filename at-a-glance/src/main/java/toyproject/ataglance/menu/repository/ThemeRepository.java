package toyproject.ataglance.menu.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import toyproject.ataglance.menu.entity.Theme;

public interface ThemeRepository extends CrudRepository<Theme, String>{

    @Modifying
    @Query("UPDATE ATAGLANCE_MENU_THEME t SET t.enabled = :enabled, t.date_updated = CURRENT_TIMESTAMP WHERE t.theme_id = :themeId")
    void updateThemeEnabledStatus(String themeId, boolean enabled);

    @Modifying
    @Query("UPDATE ATAGLANCE_MENU_DETAIL d SET d.enabled = :enabled, d.date_updated = CURRENT_TIMESTAMP WHERE d.fk_theme_id = :themeId")
    void updateDetailEnabledStatus(String themeId, boolean enabled);
}
