package toyproject.ataglance.menu.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import toyproject.ataglance.menu.entity.Category;
import toyproject.ataglance.menu.entity.Theme;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class CategoryModel {

    private final String id;
    private final String name;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
    private boolean enabled;

    private List<ThemeModel> themeModels = new ArrayList<>();

    public Category toEntity() {
        return new Category(id, name, true);
    }

    public static CategoryModel fromEntity(Category entity) {
        List<ThemeModel> themeModels = new ArrayList<>();
        entity.getThemes().forEach(theme -> themeModels.add(ThemeModel.fromEntity(theme)));
        CategoryModel model = new CategoryModel(
                entity.getId(),
                entity.getName(),
                entity.getDateCreated(),
                entity.getDateUpdated(),
                entity.isEnabled(),
                themeModels);
        return model;
    }
}
