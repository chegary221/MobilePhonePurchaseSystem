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

        // ── Demo customer with pre-loaded cart (to be moved inside dashboard) ─
        Customer customer = new Customer("C001", "Gary", "gary@gmail.com",
                                         "123456", "0123456789", "Penang");
        Cart     cart     = customer.getCart();

        // PhoneController wires the catalog + cart together
        PhoneController phoneController = new PhoneController(catalog, cart);

        System.out.println("===================================================");
        System.out.println("📱 WELCOME TO THE MOBILE PHONE PURCHASE SYSTEM 📱");
        System.out.println("===================================================");

        while (true) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Register a New Account");
            System.out.println("2. Log In");
            System.out.println("3. Browse Phones (UC 3)");
            System.out.println("4. Exit System");
            System.out.print("Select an option (1-4): ");

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

                    // 1. Capture the returned user!
                    User loggedInUser = loginPage.enterLogin(loginEmail, loginPassword);

                    // 2. If login is successful (not null), route them to the right UI
                    if (loggedInUser != null) {
                        if (loggedInUser.getRole().equals("Staff")) {
                            // This clears the inventoryUI warning!
                            inventoryUI.navigateInventoryDashboard(); 
                        } 
                        else if (loggedInUser.getRole().equals("Customer")) {
                            // This clears the systemUI warning!
                            System.out.println("\n[Redirecting to Customer Shopping Cart...]");
            
                            // You can trigger your mock checkout here for now:
                            // systemUI.initiateCheckout(customerOrder); 
                        }
                    }
                    break;

                // ── UC 3: Browse Phones & Add to Cart (:System role) ──────────
                case "3":
                    browsePhonesFlow(scanner, phoneController);
                    break;

                // ── Exit ──────────────────────────────────────────────────────
                case "4":
                    System.out.println("\nExiting system. Have a great day!");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("\n⚠️  Invalid selection! Please type 1, 2, 3, or 4.");
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

        System.out.println("\n[System] clickBrowsePhones()");

        // :System → PhoneController: requestPhoneList()
        // PhoneController internally calls Database.retrieveCatalogData()
        List<Phone> phoneList = phoneController.requestPhoneList();

        // ── E1: Database Connection Failure ───────────────────────────────────
        // Simulated: if catalog returns empty, treat as connection error
        if (phoneList == null || phoneList.isEmpty()) {
            // Database → PhoneController: connectionError
            // PhoneController → :System: returnError()
            // :System → Customer: displayErrorMsg()
            System.out.println("[System] displayErrorMsg(\"Unable to load phone catalog." +
                               " Please try again later.\")");
            return;
        }

        // ── Basic Flow ────────────────────────────────────────────────────────
        // Database → PhoneController: catalogData
        // PhoneController → :System: phoneList
        // :System → Customer: displayPhoneList()
        System.out.println("\n[System] displayPhoneList()");
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
            System.out.println("[System] displayErrorMsg(\"Phone model not found.\")");
            return;
        }

        // :System → Customer: displayDetailedProductPage()
        System.out.println("\n[System] displayDetailedProductPage()");
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
            System.out.println("[System] Returning to main menu.");
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
            System.out.println("[System] ⚠️  Invalid quantity. Returning to main menu.");
            return;
        }

        // :System → PhoneController: addItemToCart(modelID, variant, qty)
        // PhoneController → Cart: updateCartItems(product, qty)
        // Cart → PhoneController: cartUpdatedSuccess
        boolean success = phoneController.addToCart(modelID, variant, qty);

        if (success) {
            // PhoneController → :System: showSuccessNotification()
            // :System → Customer: displayConfirmationMsg()
            System.out.println("[System] showSuccessNotification()");
            System.out.println("[System] displayConfirmationMsg()");
            System.out.println("✅ " + selected.getBrand() + " " + selected.getModel()
                    + " (" + variant + ") x" + qty + " has been added to your cart.");
        } else {
            System.out.println("[System] displayErrorMsg(\"Could not add item to cart.\")");
        }
    }
}
// 【留言区】
// 优先完成自己负责的 USE CASE 的雏形
// 不同人负责的 USE CASE 之间的衔接/建议/建构方向在这里沟通（？ --gary

/* <Checkout相关>
1. add to cart：弄input
2. process payment：由于amount有了在order payment只需要存status和paymethod（？
--gary */

/* <整体系统相关及隐患>
1. order status 似乎需要staff处理 但我发现UC没有manage order之类的 该加还是直接把status删掉？
2. 感觉user的data member可以删掉一两个（？）还有customer和staff那两个id也删掉 user已经有id了 
   代码的部分直接搞成用Cxxx和Sxxx来区分两者
3. generalization目前只有user一种 该往phone上加分支吗 还是有没有其他更优解
*/
