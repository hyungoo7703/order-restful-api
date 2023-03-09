package toyproject.ataglance.menu.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CategoryUpdatedDto {

    private String id;
    private String name;
    private LocalDateTime dateUpdated;
    private boolean enabled;
}
