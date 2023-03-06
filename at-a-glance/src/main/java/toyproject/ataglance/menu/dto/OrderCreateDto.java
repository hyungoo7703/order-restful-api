package toyproject.ataglance.menu.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.Set;
@Getter
@Setter
public class OrderCreateDto {

    private Set<OrderDetailDto> orderDetailDtos;

}

