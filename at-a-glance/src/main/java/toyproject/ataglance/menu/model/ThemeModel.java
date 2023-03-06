package toyproject.ataglance.menu.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import toyproject.ataglance.menu.entity.Theme;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class ThemeModel {

    private final String id;
    private final String name;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
    private boolean enabled;
    private final String categoryId;

    private List<DetailModel> detailModels = new ArrayList<>();

    public Theme toEntity() {
        return new Theme(id, name, true, categoryId);
    }

    public static ThemeModel fromEntity(Theme entity) {
        List<DetailModel> detailModels = new ArrayList<>();
        entity.getDetails().forEach(detail -> detailModels.add(DetailModel.fromEntity(detail)));
        return new ThemeModel(
                entity.getId(),
                entity.getName(),
                entity.getDateCreated(),
                entity.getDateUpdated(),
                entity.isEnabled(),
                entity.getCategoryId(),
                detailModels);
    }
}