package toyproject.ataglance.menu.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetailDto {

    private String id;
    private String name;
    private String price;
    private String eventDiscountPrice;
    private String memo;
}
