package Models;

public class OrderDetail {
    private Integer orderDetailId;
    private Integer orderId;
    private Integer productId;
    private Double productQuantities;

    // Constructors
    public OrderDetail() {
        // Default constructor
    }

    public OrderDetail(Integer orderDetailId, Integer orderId, Integer productId, Double productQuantities) {
        this.orderDetailId = orderDetailId;
        this.orderId = orderId;
        this.productId = productId;
        this.productQuantities = productQuantities;
    }

    // Getter and Setter methods
    public Integer getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Integer orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Double getProductQuantities() {
        return productQuantities;
    }

    public void setProductQuantities(Double productQuantities) {
        this.productQuantities = productQuantities;
    }

    // Additional methods if needed

    @Override
    public String toString() {
        return "OrderDetail{" +
                "orderDetailId=" + orderDetailId +
                ", orderId=" + orderId +
                ", productId=" + productId +
                ", productQuantities=" + productQuantities +
                '}';
    }
}

