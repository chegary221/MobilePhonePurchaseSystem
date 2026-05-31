public class PhoneController {

    private Phone[] phoneList;
    private int phoneCount;
    private Cart cart;

    public PhoneController(Phone[] phoneList, Cart cart) {
        this.phoneList = phoneList;
        this.cart = cart;
        this.phoneCount = 0;
        if (phoneList != null) {
            for (int i = 0; i < phoneList.length; i++) {
                if (phoneList[i] != null) {
                    this.phoneCount++;
                }
            }
        }
    }

    public PhoneController() {
        this.phoneList = new Phone[100];
        this.phoneCount = 0;
    }

    // 返回纯数组
    public Phone[] requestPhoneList() {
        if (phoneList == null || phoneCount == 0) {
            System.out.println("No phones available in the catalog.");
            return new Phone[0];
        }
        
        System.out.println("=== Phone List ===");
        for (int i = 0; i < phoneCount; i++) {
            if (phoneList[i] != null) {
                phoneList[i].displayInfo();
            }
        }
        return phoneList;
    }

    public Phone getPhoneDetails(String modelID) {
        if (modelID == null || modelID.isEmpty()) {
            System.out.println("Invalid model ID provided.");
            return null;
        }

        for (int i = 0; i < phoneCount; i++) {
            if (phoneList[i] != null && phoneList[i].getPhoneID().equalsIgnoreCase(modelID)) {
                System.out.println("=== Phone Details ===");
                System.out.println("Brand        : " + phoneList[i].getBrand());
                System.out.println("Model        : " + phoneList[i].getModel());
                System.out.println("Price        : RM" + String.format("%.2f", phoneList[i].getPrice()));
                System.out.println("Stock        : " + phoneList[i].getStockQuantity());
                System.out.println("Stock Status : " + phoneList[i].getStockStatus());
                return phoneList[i];
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

    public void addPhone(Phone phone) {
        if (phone != null && phoneCount < phoneList.length) {
            phoneList[phoneCount] = phone;
            phoneCount++;
        } else {
            System.out.println("Phone catalog is full, cannot add more.");
        }
    }

    public Phone[] getPhoneList() { return phoneList; }
    public void setPhoneList(Phone[] phoneList) { this.phoneList = phoneList; }

    public Cart getCart() { return cart; }
    public void setCart(Cart cart) { this.cart = cart; }
}
