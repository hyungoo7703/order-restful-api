package toyproject.ataglance.menu.dto;

import lombok.Getter;
import lombok.Setter;
import toyproject.ataglance.menu.constant.OrderStatus;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class OrderCreatedDto {

    private Long id;
    private String orderNumber;
    private OrderStatus orderStatus;
    private LocalDateTime dateCreated;
    private Set<OrderDetailDto> orderDetailDtos;
}
