package toyproject.ataglance.menu.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import toyproject.ataglance.menu.entity.Detail;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class DetailModel {

    private final String id;
    private final String name;
    private final int price;
    private final int margin;
    private final String memo;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
    private boolean enabled;
    private final boolean onEvent;
    private final String themeId;

    public Detail toEntity() {
        return new Detail(id, name, price, margin, memo, true, onEvent, themeId);
    }

    public static DetailModel fromEntity(Detail entity) {
        DetailModel model = new DetailModel(
                entity.getId(),
                entity.getName(),
                entity.getPrice(),
                entity.getMargin(),
                entity.getMemo(),
                entity.getDateCreated(),
                entity.getDateUpdated(),
                entity.isEnabled(),
                entity.isOnEvent(),
                entity.getThemeId());
        return model;
    }
}
