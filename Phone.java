public class Phone {
    private String phoneID;
    private String brand;
    private String model;
    private double price;
    private int stockQuantity;
    private String stockStatus;

    public Phone() {}

    public Phone(String phoneID, String brand, String model,
                 double price, int stockQuantity, String stockStatus) {
        this.phoneID = phoneID;
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.stockStatus = stockStatus;
    }

    public String getPhoneID() { return phoneID; }
    public void setPhoneID(String phoneID) { this.phoneID = phoneID; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }

    public String getStockStatus() { return stockStatus; }
    public void setStockStatus(String stockStatus) { this.stockStatus = stockStatus; }

    public void displayInfo() {
    System.out.println(brand + " " + model + " - 價格: RM" + price + ", 庫存: " + stockQuantity);}

}