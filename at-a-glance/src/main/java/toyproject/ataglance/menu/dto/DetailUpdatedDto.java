package toyproject.ataglance.menu.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DetailUpdatedDto {

    private String id;
    private String name;
    private int price;
    private int margin;
    private LocalDateTime dateUpdated;
    private boolean enabled;
    private boolean onEvent;
}
