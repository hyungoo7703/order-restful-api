package toyproject.ataglance.menu.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DetailCreatedDto {

    private String id;
    private String name;
    private String price;
    private String eventDiscountPrice;
    private String memo;
    private LocalDateTime dateCreated;
    private boolean onEvent;
    private String themeId;
}
