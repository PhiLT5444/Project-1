package Models;

public class Product {

    private Integer productID;
    private String productName;
    private Integer categoryId;
    private String productDescribe;
    private Double productPrice;
    private String productImage;
    private Integer productStatus;

    // Constructors
    public Product() {
    }

    public Product(Integer productID, String productName, Integer categoryId, String productDescribe,
                   Double productPrice, String productImage, Integer productStatus) {
        this.productID = productID;
        this.productName = productName;
        this.categoryId = categoryId;
        this.productDescribe = productDescribe;
        this.productPrice = productPrice;
        this.productImage = productImage;
        this.productStatus = productStatus;
    }

    // Getter and Setter methods

    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getProductDescribe() {
        return productDescribe;
    }

    public void setProductDescribe(String productDescribe) {
        this.productDescribe = productDescribe;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public Integer getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(Integer productStatus) {
        this.productStatus = productStatus;
    }

    // Other methods (if needed)
}
