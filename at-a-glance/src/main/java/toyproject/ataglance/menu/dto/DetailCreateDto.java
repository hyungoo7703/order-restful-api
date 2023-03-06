package toyproject.ataglance.menu.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetailCreateDto {

    private String id;
    private String name;
    private int price;
    private int margin;
    private String memo;
    private boolean onEvent;
    private String themeId;
}
