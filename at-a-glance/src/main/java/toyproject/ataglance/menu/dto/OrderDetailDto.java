package toyproject.ataglance.menu.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderDetailDto {

    private String detailId;
    private int quantity;
}
