import java.time.LocalDate;

public class Sales {
    private int salesId;
    private int sellerId;
    private int productId;
    private int numberOfProducts;
    private LocalDate date;

    public Sales(int salesId, int sellerId, int productId, int amountOfProducts, LocalDate date) {
        this.salesId = salesId;
        this.sellerId = sellerId;
        this.productId = productId;
        this.numberOfProducts = amountOfProducts;
        this.date = date;
    }

    public int getSalesId() {
        return salesId;
    }

    public void setSalesId(int salesId) {
        this.salesId = salesId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getNumberOfProducts() {
        return numberOfProducts;
    }

    public void setNumberOfProducts(int amountOfProducts) {
        this.numberOfProducts = amountOfProducts;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "salesId=" + salesId +
                ", sellerId=" + sellerId +
                ", productId=" + productId +
                ", numberOfProducts=" + numberOfProducts +
                ", date=" + date;
    }
}
