import java.util.Scanner;

public class InventoryUI {
    private InventoryController invController;
    private Scanner scanner;

    public InventoryUI(InventoryController controller) {
        this.invController = controller;
        this.scanner = new Scanner(System.in);
    }

    public void navigateInventoryDashboard() {
        System.out.println("\n--- Inventory Dashboard ---");
        retrieveListOfPhoneAndStock();
        selectAction();
    }

    public void retrieveListOfPhoneAndStock() {
        PhoneItem[] stockList = invController.requestInventory();
        displayListOfPhoneAndStock(stockList);
    }

    public void displayListOfPhoneAndStock(PhoneItem[] stockList) {
        System.out.println("Current Stock:");
        for(PhoneItem item : stockList) {
            if(item != null) {
                item.displayItemDetails(); 
            }
        }
    }

    public void selectAction() {
        System.out.println("Enter action (Add/Delete/Exit):");
        String action = scanner.nextLine();
        if(action.equalsIgnoreCase("Add")) {
            PhoneItem newItem = inputRequiredData();
            invController.submitChanges(action, newItem);
            displaySuccessConfirmation();
        }
    }

    public Phone inputRequiredData() {
        System.out.println("Enter Brand (e.g., Apple, Samsung): ");
        String brand = scanner.nextLine();
        
        System.out.println("Enter Model: ");
        String model = scanner.nextLine();
        
        System.out.println("Enter Price: ");
        double price = scanner.nextDouble();
        
        System.out.println("Enter Stock Quantity: ");
        int stockQuantity = scanner.nextInt();
        
        return new Phone("PHN" + System.currentTimeMillis(), brand, model, price, stockQuantity);
    }

    public void displaySuccessConfirmation() { System.out.println("Action Completed Successfully."); }
}
