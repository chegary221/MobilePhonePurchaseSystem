public class InventoryController {
    private Database db;

    public InventoryController(Database db) {
        this.db = db;
    }

    public Database getDb() { return db; }
    public void setDb(Database db) { this.db = db; }

    public Phone[] requestInventory() {
        return db.fetchStockLevels();
    }

    public void submitChanges(String action, Phone data) {
        if(validatesInput(data)) {
            db.updateCentralDatabase(data);
        }
    }

    public boolean validatesInput(Phone data) {
        return data.getPrice() > 0 && data.getStockQuantity() >= 0;
    }
}
