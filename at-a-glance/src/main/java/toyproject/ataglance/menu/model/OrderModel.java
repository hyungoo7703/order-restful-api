package toyproject.ataglance.menu.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import toyproject.ataglance.menu.constant.OrderStatus;
import toyproject.ataglance.menu.entity.Order;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderModel {

    private Long id;
    private String orderNumber;
    private OrderStatus orderStatus;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
    private Set<OrderDetailModel> orderDetailModels = new HashSet<>();

    public void setOrderDetailModels(Set<OrderDetailModel> orderDetailModels) {
        this.orderDetailModels = orderDetailModels;
    }
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Order toEntity() {
        return new Order(orderNumber);
    }

    public static OrderModel formEntity(Order entity) {
        Set<OrderDetailModel> orderDetailModels = new HashSet<>();
        entity.getOrderDetails().forEach(orderDetail -> orderDetailModels.add(OrderDetailModel.fromEntity(orderDetail)));
        return new OrderModel(
                entity.getId(),
                entity.getOrderNumber(),
                entity.getOrderStatus(),
                entity.getDateCreated(),
                entity.getDateUpdated(),
                orderDetailModels);
    }

}
