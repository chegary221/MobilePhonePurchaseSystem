public class PhoneItem extends Item {
    private String modelName;
    private int stockLevel;

    public PhoneItem(String itemID, double price, String modelName, int stockLevel) {
        super(itemID, price);
        this.modelName = modelName;
        this.stockLevel = stockLevel;
    }

    public String getModelName() { return modelName; }
    public void setModelName(String modelName) { this.modelName = modelName; }
    public int getStockLevel() { return stockLevel; }
    public void setStockLevel(int stockLevel) { this.stockLevel = stockLevel; }

    @Override
    public void displayItemDetails() {
        System.out.println("Model: " + modelName + " | Price: RM" + price + " | Stock: " + stockLevel);
    }

    public void removeRecord() {
        this.stockLevel = 0;
        System.out.println("Item " + modelName + " removed.");
    }
}
