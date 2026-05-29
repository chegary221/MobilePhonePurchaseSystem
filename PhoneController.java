import java.util.ArrayList;
import java.util.List;

public class PhoneController {

    private List<Phone> phoneList;
    private Cart cart;

    public PhoneController(List<Phone> phoneList, Cart cart) {
        this.phoneList = phoneList;
        this.cart = cart;
    }

    public PhoneController() {
        this.phoneList = new ArrayList<>();
    }

    //Returns the full list of available phones
    public List<Phone> requestPhoneList() {
        if (phoneList == null || phoneList.isEmpty()) {
            System.out.println("No phones available in the catalog.");
            return new ArrayList<>();
        }
        System.out.println("=== Phone List ===");
        for (Phone phone : phoneList) {
            phone.displayInfo();
        }
        return phoneList;
    }

    public Phone getPhoneDetails(String modelID) {
        if (modelID == null || modelID.isEmpty()) {
            System.out.println("Invalid model ID provided.");
            return null;
        }

        for (Phone phone : phoneList) {
            if (phone.getPhoneID().equalsIgnoreCase(modelID)) {
                System.out.println("=== Phone Details ===");
                System.out.println("Brand        : " + phone.getBrand());
                System.out.println("Model        : " + phone.getModel());
                System.out.println("Price        : RM" + String.format("%.2f", phone.getPrice()));
                System.out.println("Stock        : " + phone.getStockQuantity());
                System.out.println("Stock Status : " + phone.getStockStatus());
                return phone;
            }
        }

        System.out.println("Phone with model ID '" + modelID + "' not found.");
        return null;
    }

    public boolean addToCart(String modelID, String variant, int qty) {
        if (qty <= 0) {
            System.out.println("Quantity must be greater than zero.");
            return false;
        }

        Phone phone = getPhoneDetails(modelID);
        if (phone == null) {
            System.out.println("Cannot add to cart: phone not found.");
            return false;
        }

        String stockCheck = phone.checkStock(qty);

        switch (stockCheck) {
            case "out of stock":
                System.out.println("Sorry, " + phone.getBrand() + " " + phone.getModel()
                        + " is currently out of stock.");
                return false;

            case "insufficient":
                System.out.println("Insufficient stock for " + phone.getBrand() + " "
                        + phone.getModel() + ". Available: " + phone.getStockQuantity());
                return false;

            default: 
            // "available"
                CartItem item = new CartItem(phone, qty);
                cart.addItem(item);
                System.out.println("Added to cart: " + phone.getBrand() + " "
                        + phone.getModel() + " (" + variant + ") x" + qty
                        + " | Subtotal: RM" + String.format("%.2f", item.calculateSubtotal()));
                return true;
        }
    }

    public List<Phone> getPhoneList() { return phoneList; }
    public void setPhoneList(List<Phone> phoneList) { this.phoneList = phoneList; }

    public Cart getCart() { return cart; }
    public void setCart(Cart cart) { this.cart = cart; }
}
