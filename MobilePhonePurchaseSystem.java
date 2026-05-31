import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MobilePhonePurchaseSystem {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // 1. Start the Database 
        Database centralDatabase = new Database();

        // 2. Build ALL Controllers first
        AccountController controller = new AccountController();
        InventoryController invController = new InventoryController(centralDatabase);
        PaymentController payController = new PaymentController();
        CheckoutController checkoutController = new CheckoutController();
        
        // (Optional: If you used the AuthController fix for your friend's code, add it here)
        // AuthController authController = new AuthController(controller);

        // 3. Build UIs and plug the controllers in
        InventoryUI inventoryUI = new InventoryUI(invController);
        SystemUI systemUI = new SystemUI(payController);
        RegisterPage registerPage = new RegisterPage(controller);
        
        // (If using AuthController fix, change this to: new LoginPage(authController))
        LoginPage loginPage = new LoginPage(); 

        // 4. Create your test user
        controller.saveNewUser(new Staff("S001", "CC", "cc_admin@email.com", "123456", "0123", 3));

        // ── Phone catalog (simulates Database) ────────────────────────────────
        List<Phone> catalog = new ArrayList<>();
        catalog.add(new Phone("F001", "Apple",   "iPhone 15",   3899.00, 10));
        catalog.add(new Phone("F002", "Samsung", "Galaxy S24",  3599.00, 10));
        catalog.add(new Phone("F003", "Honor",   "X9b",         2778.00,  5));

        System.out.println("===================================================");
        System.out.println("📱 WELCOME TO THE MOBILE PHONE PURCHASE SYSTEM 📱");
        System.out.println("===================================================");

        User currentUser = null;
        
        while (true) {
            System.out.println("\n--- MAIN MENU ---");
            
            // Helpful UI to show who is logged in
            if (currentUser == null) {
                System.out.println("[Status: Guest (Not Logged In)]");
            } else {
                System.out.println("[Status: Logged In as " + currentUser.getName() + " (" + currentUser.getRole() + ")]");
            }
            
            System.out.println("1. Register a New Account");
            System.out.println("2. Log In");
            System.out.println("3. Browse Phones (UC 3)");
            System.out.println("4. Go to Checkout"); // NEW OPTION
            System.out.println("5. Exit System");
            System.out.print("Select an option (1-5): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {

                // ── UC: Register ──────────────────────────────────────────────
                case "1":
                    System.out.println("\n--- REGISTRATION FORM ---");
                    System.out.print("Enter Username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter Password: ");
                    String password = scanner.nextLine();
                    System.out.print("Enter Phone Number: ");
                    String phone = scanner.nextLine();
                    System.out.print("Enter Address: ");
                    String address = scanner.nextLine();

                    registerPage.inputDetails(username, email, password, phone, address);
                    break;

                // ── UC: Login ─────────────────────────────────────────────────
                case "2":
                    System.out.println("\n--- LOGIN SCREEN ---");
                    System.out.print("Enter Email: ");
                    String loginEmail = scanner.nextLine();
                    System.out.print("Enter Password: ");
                    String loginPassword = scanner.nextLine();

                    User loggedInUser = loginPage.enterLogin(loginEmail, loginPassword);

                    if (loggedInUser != null) {
                        currentUser = loggedInUser; // Save the user in the system's memory!

                        if (currentUser.getRole().equals("Staff")) {
                            // Staff usually go straight to their dashboard
                            inventoryUI.navigateInventoryDashboard(); 
                        } 
                        else if (currentUser.getRole().equals("Customer")) {
                            // NO MORE TELEPORTING! Just welcome them back to the menu.
                            System.out.println("\nLogin successful! Returning to Main Menu so you can browse phones.");
                        }
                    }
                    break;

                // ── UC 3: Browse Phones & Add to Cart ─────────────────────────
                case "3":
                    if (currentUser == null || !currentUser.getRole().equals("Customer")) {
                        System.out.println("\n⚠️ Please log in as a Customer to browse and add items to your cart.");
                    } else {
                        Customer currentCustomer = (Customer) currentUser;

                        PhoneController phoneController = new PhoneController(catalog, currentCustomer.getCart());

                        browsePhonesFlow(scanner, phoneController);
                    }
                    break;

                // ── NEW UC: Checkout ──────────────────────────────────────────
                case "4":
                    if (currentUser == null || !currentUser.getRole().equals("Customer")) {
                        System.out.println("\n⚠️ Please log in as a Customer to access the checkout.");
                    } else {
                        // 1. Get the current logged-in Customer
                        Customer currentCustomer = (Customer) currentUser;
                        
                        // 2. Grab the data from their cart
                        // (Note: Adjust getItems() and calculateTotal() if your Cart class uses different method names!)
                        // 3. Build the REAL order using your teammate's best constructor!
                        checkoutController.buyNow(currentCustomer, currentCustomer.getCart());
                        
                        // 4. Send the fully built order to the UI!
                        systemUI.initiateCheckout(checkoutController.getOrder()); // Assuming you have a method to get the current order in your controller
                    }
                    break;

                // ── Exit ──────────────────────────────────────────────────────
                case "5":
                    System.out.println("\nExiting system. Have a great day!");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("\n⚠️ Invalid selection! Please type 1, 2, 3, 4, or 5.");
                    break;
            }
        }
    }

    // =========================================================================
    // UC 3 — Browse Phones & Add to Cart
    // Represents the :System lifeline in the sequence diagram.
    // =========================================================================

    /**
     * clickBrowsePhones() — entry point triggered when user selects "Browse Phones".
     * Calls PhoneController.requestPhoneList(); handles DB connection failure (E1)
     * or renders the phone list on success.
     */
    private static void browsePhonesFlow(Scanner scanner, PhoneController phoneController) {

        // :System → PhoneController: requestPhoneList()
        // PhoneController internally calls Database.retrieveCatalogData()
        List<Phone> phoneList = phoneController.requestPhoneList();

        // ── E1: Database Connection Failure ───────────────────────────────────
        // Simulated: if catalog returns empty, treat as connection error
        if (phoneList == null || phoneList.isEmpty()) {
            // Database → PhoneController: connectionError
            // PhoneController → :System: returnError()
            // :System → Customer: displayErrorMsg()
            System.out.println("Unable to load phone catalog." +
                               " Please try again later.");
            return;
        }

        // ── Basic Flow ────────────────────────────────────────────────────────
        // Database → PhoneController: catalogData
        // PhoneController → :System: phoneList
        // :System → Customer: displayPhoneList()
        System.out.println("------------------------------------------");
        System.out.printf("%-6s %-10s %-15s %s%n", "No.", "ID", "Model", "Price (RM)");
        System.out.println("------------------------------------------");
        for (int i = 0; i < phoneList.size(); i++) {
            Phone p = phoneList.get(i);
            System.out.printf("%-6d %-10s %-15s %.2f%n",
                    i + 1, p.getPhoneID(), p.getBrand() + " " + p.getModel(), p.getPrice());
        }
        System.out.println("------------------------------------------");

        // Customer: clickPhoneModel(modelID)
        System.out.print("\nEnter Phone ID to view details (or 'back' to return): ");
        String modelID = scanner.nextLine().trim();

        if (modelID.equalsIgnoreCase("back")) return;

        // :System → PhoneController: getPhoneDetails(modelID)
        // PhoneController → Database: queryPhoneSpecs(modelID)
        // Database → PhoneController: phoneDetails
        // PhoneController → :System: details
        Phone selected = phoneController.getPhoneDetails(modelID);

        if (selected == null) {
            System.out.println("Phone model not found.");
            return;
        }

        // :System → Customer: displayDetailedProductPage()
        System.out.println("==========================================");
        System.out.println("  " + selected.getBrand() + " " + selected.getModel());
        System.out.println("  Price        : RM" + String.format("%.2f", selected.getPrice()));
        System.out.println("  Stock        : " + selected.getStockQuantity());
        System.out.println("  Stock Status : " + selected.getStockStatus());
        System.out.println("==========================================");

        // ── opt [Customer clicks "Add to Cart"] ───────────────────────────────
        System.out.print("\nAdd to cart? (yes / no): ");
        String addChoice = scanner.nextLine().trim();

        if (!addChoice.equalsIgnoreCase("yes")) {
            System.out.println("Returning to main menu.");
            return;
        }

        // Customer: clickAddToCart(variant, qty)
        System.out.print("Enter variant (e.g. Black 256GB): ");
        String variant = scanner.nextLine().trim();

        System.out.print("Enter quantity: ");
        int qty;
        try {
            qty = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("⚠️  Invalid quantity. Returning to main menu.");
            return;
        }

        // :System → PhoneController: addItemToCart(modelID, variant, qty)
        // PhoneController → Cart: updateCartItems(product, qty)
        // Cart → PhoneController: cartUpdatedSuccess
        boolean success = phoneController.addToCart(modelID, variant, qty);

        if (success) {
            // PhoneController → :System: showSuccessNotification()
            // :System → Customer: displayConfirmationMsg()
            System.out.println("✅ " + selected.getBrand() + " " + selected.getModel()
                    + " (" + variant + ") x" + qty + " has been added to your cart.");
        } else {
            System.out.println("Could not add item to cart.");
        }
    }
}
