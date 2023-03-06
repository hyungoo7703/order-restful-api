package toyproject.ataglance.menu.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CategoryCreatedDto {

    private String id;
    private String name;
    private LocalDateTime dateCreated;
}
