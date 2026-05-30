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
        Phone[] stockList = invController.requestInventory(); 
        displayListOfPhoneAndStock(stockList);
    }

    public void displayListOfPhoneAndStock(Phone[] stockList) { 
        System.out.println("Current Stock:");
        for(Phone item : stockList) { 
            if(item != null) {
                item.displayItemDetails(); 
            }
        }
    }

    public void selectAction() {
        System.out.println("Enter action (Add/Delete/Exit):");
        String action = scanner.nextLine();
        if(action.equalsIgnoreCase("Add")) {
            Phone newItem = inputRequiredData(); 
            invController.submitChanges(action, newItem);
            displaySuccessConfirmation();
        }
    }

    public Phone inputRequiredData() { 
        System.out.println("Enter Brand: ");
        String brand = scanner.nextLine();
        System.out.println("Enter Model: ");
        String model = scanner.nextLine();
        System.out.println("Enter Price: ");
        double price = scanner.nextDouble();
        System.out.println("Enter Stock Quantity: ");
        int stock = scanner.nextInt();
        return new Phone("PHN" + System.currentTimeMillis(), brand, model, price, stock);
    }

    public void displaySuccessConfirmation() { System.out.println("Action Completed Successfully."); }
}
