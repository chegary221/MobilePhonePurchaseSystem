public class Database {
    private String connectionString;
    private boolean isConnected;
    private PhoneItem[] inventoryRecord;
    private int itemCount;

    public Database() {
        this.connectionString = "jdbc:mock:localDB";
        this.inventoryRecord = new PhoneItem[100];
        this.itemCount = 0;
        connectToDatabase();
    }

    public void connectToDatabase() {
        this.isConnected = true;
        System.out.println("Database connected using: " + connectionString);
    }

    public PhoneItem[] getInventoryRecord() { return inventoryRecord; }
    
    public void updateCentralDatabase(PhoneItem item) {
        if(itemCount < inventoryRecord.length) {
            inventoryRecord[itemCount] = item;
            itemCount++;
            System.out.println("Item added to database.");
        }
    }

    public PhoneItem[] fetchStockLevels() {
        return inventoryRecord;
    }

    public boolean checkDependencies(String phoneID) { return false; }
    public boolean activeOrderConflict(String phoneID) { return false; }
    public boolean clearToDelete(String phoneID) { return true; }
}
