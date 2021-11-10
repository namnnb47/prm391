package com.example.it.firebasetest.Model;

public class Order {

    private String ProductId;
    private String ProductName;
    private String Quantity;
    private String Price;
    private String Discounts;

    public Order() {
    }

    public Order(String productId, String productName, String quantity, String price, String discounts) {
        ProductId = productId;
        ProductName = productName;
        Quantity = quantity;
        Price = price;
        Discounts = discounts;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDiscounts() {
        return Discounts;
    }

    public void setDiscounts(String discounts) {
        Discounts = discounts;
    }
}
