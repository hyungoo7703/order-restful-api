package toyproject.ataglance.menu.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ThemeCreatedDto {

    private String id;
    private String name;
    private LocalDateTime dateCreated;
    private String categoryId;
}
