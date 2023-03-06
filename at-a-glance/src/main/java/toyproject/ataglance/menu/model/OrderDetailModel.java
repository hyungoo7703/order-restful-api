package toyproject.ataglance.menu.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import toyproject.ataglance.menu.entity.OrderDetail;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class OrderDetailModel {

    private Long id;
    private Long orderId;
    private final String detailId;
    private final int quantity;

    public OrderDetail toEntity() {
        return new OrderDetail(orderId, detailId, quantity);
    }

    public static OrderDetailModel fromEntity(OrderDetail entity) {
        return new OrderDetailModel(
                entity.getId(),
                entity.getOrderId(),
                entity.getDetailId(),
                entity.getQuantity()
        );
    }

}
