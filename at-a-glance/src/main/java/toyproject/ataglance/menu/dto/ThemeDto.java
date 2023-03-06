package toyproject.ataglance.menu.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ThemeDto {

    private String id;
    private String name;
    private List<DetailDto> details;
}
