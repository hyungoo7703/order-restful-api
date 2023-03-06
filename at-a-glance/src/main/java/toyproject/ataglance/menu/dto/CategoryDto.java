package toyproject.ataglance.menu.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoryDto {
    private String id;
    private String name;
    private List<ThemeDto> themes;
}
