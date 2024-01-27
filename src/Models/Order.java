package Models;

public class Order {
    private Integer orderId;
    private Integer customerId;
    private Double orderTotal;
    private String orderStatus;
    private String address;

    // Constructors
    public Order() {
        // Default constructor
    }

    public Order(Integer customerId, Double orderTotal, String orderStatus, String address) {
        this.customerId = customerId;
        this.orderTotal = orderTotal;
        this.orderStatus = orderStatus;
        this.address = address;
    }

    // Getter and Setter methods
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(Double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // toString method for easy debugging
    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", customerId=" + customerId +
                ", orderTotal=" + orderTotal +
                ", orderStatus='" + orderStatus + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
