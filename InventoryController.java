public class InventoryController {
    private Database db;

    public InventoryController(Database db) {
        this.db = db;
    }

    public Database getDb() { return db; }
    public void setDb(Database db) { this.db = db; }

    public PhoneItem[] requestInventory() {
        return db.fetchStockLevels();
    }

    public void submitChanges(String action, PhoneItem data) {
        if(validatesInput(data)) {
            db.updateCentralDatabase(data);
        }
    }

    public boolean validatesInput(PhoneItem data) {
        return data.getPrice() > 0 && data.getStockLevel() >= 0;
    }
}
