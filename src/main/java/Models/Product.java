package Models;

public class Product {

    private  static String productName;
    private  static String Price;
    private static String category;

    public Product(String productName, String price, String category) {
        this.productName = productName;
        this.Price = price;
        this.category = category;
    }

    public Product() {
    }

    public static String getProductName() {
        return productName;
    }
    public static String getPrice() {
        return Price;
    }
    public static String getCategory() {
        return category;
    }
}
