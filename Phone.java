public class Phone extends Item {

    private String brand;
    private String model;
    private int stockQuantity;
    private String stockStatus;

    public Phone() {
        super("", 0.0); 
    }

    public Phone(String itemID, String brand, String model, double price, int stockQuantity) {
        super(itemID, price);
        this.brand = brand;
        this.model = model;
        this.stockQuantity = stockQuantity;
        if (stockQuantity > 0) this.stockStatus="available";
        else this.stockStatus="out of stock";
    }


    public String checkStock(int qty) {
        if (stockQuantity < qty && stockQuantity > 0) return "insufficient";
        else return stockStatus;
    }

    public void reduceStock(int qty) {
        stockQuantity -= qty;
        if (stockQuantity <= 0) stockStatus = "out of stock";
    }
    
    public String getPhoneID() { return itemID; }
    public void setPhoneID(String phoneID) { this.itemID = phoneID; }

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
        System.out.println(brand + " " + model + " - Price: RM" + price + ", Stock: " + stockQuantity);
    }


    @Override
    public void displayItemDetails() {
        displayInfo(); 
    }

    public void removeRecord() {
        this.stockQuantity = 0;
        this.stockStatus = "out of stock";
        System.out.println("Item " + brand + " " + model + " removed.");
    }
}
