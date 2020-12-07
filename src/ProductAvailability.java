public class ProductAvailability {
    public int sellerId;
    public int ProductId;
    public int ProductPrice;
    public int ProductAmount;

    public ProductAvailability(int sellerId, int productId, int productPrice, int productAmount) {
        this.sellerId = sellerId;
        this.ProductId = productId;
        this.ProductPrice = productPrice;
        this.ProductAmount = productAmount;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public int getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(int productPrice) {
        ProductPrice = productPrice;
    }

    public int getProductAmount() {
        return ProductAmount;
    }

    public void setProductAmount(int productAmount) {
        ProductAmount = productAmount;
    }

    @Override
    public String toString() {
        return "sellerId=" + sellerId +
                ", ProductId=" + ProductId +
                ", ProductPrice=" + ProductPrice +
                ", ProductAmount=" + ProductAmount;
    }
}
