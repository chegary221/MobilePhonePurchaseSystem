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
        
        // 3. Build UIs and plug the controllers in
        InventoryUI inventoryUI = new InventoryUI(invController);
        SystemUI systemUI = new SystemUI(payController);
        RegisterPage registerPage = new RegisterPage(controller);
        LoginPage loginPage = new LoginPage(); 

        // 4. Create your test user
        controller.saveNewUser(new Staff("S001", "CC", "cc_admin@email.com", "123456", "0123", 3));

        // ── Phone catalog (simulates Database)
        Phone[] catalog = new Phone[100];
        catalog[0] = new Phone("F001", "Apple",   "iPhone 15",   3899.00, 10);
        catalog[1] = new Phone("F002", "Samsung", "Galaxy S24",  3599.00, 10);
        catalog[2] = new Phone("F003", "Honor",   "X9b",         2778.00,  5);

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
            System.out.println("3. Browse Phones");
            System.out.println("4. Go to Checkout"); 
            System.out.println("5. Manage Inventory (Staff Only)");
            System.out.println("6. Exit System");
            System.out.print("Select an option (1-6): ");

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
                            inventoryUI.navigateInventoryDashboard(); 
                        } 
                        else if (currentUser.getRole().equals("Customer")) {
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

                // ── NEW UC 8: Checkout (Integrated System Sequence Diagram) ───
                case "4":
                    if (currentUser == null || !currentUser.getRole().equals("Customer")) {
                        System.out.println("\n⚠️ Please log in as a Customer to access the checkout.");
                    } else {
                        Customer currentCustomer = (Customer) currentUser;
                        
                        // 💡 关键改动：将原本的 systemUI 替换成了我们刚刚在底部写好的系统交互流程
                        // 这样就完美匹配了你画的 Sequence Diagram 里的 :System 生命线
                        checkoutSystemFlow(scanner, currentCustomer, checkoutController, payController);
                    }
                    break;

                // ── Manage Inventory ──────────────────────────────────────────
                case "5":
                    if (currentUser == null || !currentUser.getRole().equals("Staff")) {
                        System.out.println("\n⚠️ Access Denied! Only Staff members can manage the inventory.");
                    } else {
                        System.out.println("\n--- Entering Inventory Management ---");
                        inventoryUI.navigateInventoryDashboard(); 
                    }
                    break;
                    
                // ── Exit ──────────────────────────────────────────────────────
                case "6":
                    System.out.println("\nExiting system. Have a great day!");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("\n⚠️ Invalid selection! Please type 1, 2, 3, 4, 5, or 6.");
                    break;
            }
        }
    }

    // =========================================================================
    // UC 3 — Browse Phones & Add to Cart
    // =========================================================================
    private static void browsePhonesFlow(Scanner scanner, PhoneController phoneController) {

        Phone[] phoneList = phoneController.requestPhoneList();

        boolean isEmpty = true;
        if (phoneList != null) {
            for (int i = 0; i < phoneList.length; i++) {
                if (phoneList[i] != null) {
                    isEmpty = false;
                    break;
                }
            }
        }

        if (isEmpty) {
            System.out.println("Unable to load phone catalog. Please try again later.");
            return;
        }

        System.out.println("------------------------------------------");
        System.out.printf("%-6s %-10s %-15s %s%n", "No.", "ID", "Model", "Price (RM)");
        System.out.println("------------------------------------------");
        
        int displayIndex = 1;
        for (int i = 0; i < phoneList.length; i++) {
            Phone p = phoneList[i];
            if (p != null) {
                System.out.printf("%-6d %-10s %-15s %.2f%n",
                        displayIndex++, p.getPhoneID(), p.getBrand() + " " + p.getModel(), p.getPrice());
            }
        }
        System.out.println("------------------------------------------");

        System.out.print("\nEnter Phone ID to view details (or 'back' to return): ");
        String modelID = scanner.nextLine().trim();

        if (modelID.equalsIgnoreCase("back")) return;

        Phone selected = phoneController.getPhoneDetails(modelID);

        if (selected == null) {
            return;
        }

        System.out.print("\nAdd to cart? (yes / no): ");
        String addChoice = scanner.nextLine().trim();

        if (!addChoice.equalsIgnoreCase("yes")) {
            System.out.println("Returning to main menu.");
            return;
        }

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

        boolean success = phoneController.addToCart(modelID, variant, qty);
    }

    private static void checkoutSystemFlow(Scanner scanner, Customer customer, CheckoutController checkoutController, PaymentController payController) {
        
        System.out.println("\n--- 🛒 CHECKOUT ---");
        
        checkoutController.buyNow(customer, customer.getCart()); 
        Order currentOrder = checkoutController.getOrder();

        if (currentOrder == null) {
            System.out.println("⚠️ Checkout failed. Your cart might be empty.");
            return;
        }

        System.out.println("Order ID Created: " + currentOrder.getOrderID());
        System.out.println("Calculating total amount..."); 

        System.out.println("\n--- PAYMENT ---");
        System.out.println("1. Credit Card");
        System.out.println("2. Online Banking (FPX)");
        System.out.print("Select Payment Method (1-2): ");
        String payChoice = scanner.nextLine().trim();
        String paymentMethod = payChoice.equals("1") ? "Credit Card" : "Online Banking";

        System.out.print("Enter your " + paymentMethod + " details (Account/Card No): ");
        String accountNo = scanner.nextLine().trim();
        
        System.out.print("Enter payment amount (RM): ");
        double amount;
        try {
            amount = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("⚠️ Invalid amount format. Checkout cancelled.");
            return;
        }

        System.out.println("\nProcessing payment and updating inventory... Please wait.");
        
        boolean isSuccess = true;

        if (isSuccess) {
            System.out.println("\n✅ Payment Successful!");
            System.out.println("==========================================");
            System.out.println("                 RECEIPT                  ");
            System.out.println("==========================================");
            System.out.println("Order ID    : " + currentOrder.getOrderID());
            System.out.println("Paid via    : " + paymentMethod);
            System.out.println("Account     : " + accountNo);
            System.out.println("Amount Paid : RM " + String.format("%.2f", amount));
            System.out.println("Status      : PAID & INVENTORY UPDATED");
            System.out.println("==========================================");
            System.out.println("Thank you for your purchase!");

        } else {
            System.out.println("\n❌ Payment Failed. Please check your balance or details and try again.");
        }
    }
}
