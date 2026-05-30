import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Scanner object to capture real-time keyboard inputs
        Scanner scanner = new Scanner(System.in);
        
        RegisterPage registerPage = new RegisterPage();
        LoginPage loginPage = new LoginPage();

        System.out.println("===================================================");
        System.out.println("📱 WELCOME TO THE MOBILE PHONE PURCHASE SYSTEM 📱");
        System.out.println("===================================================");

        while (true) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Register a New Account");
            System.out.println("2. Log In");
            System.out.println("3. Exit System");
            System.out.print("Select an option (1-3): ");
            
            String choice = scanner.nextLine().trim();

            switch (choice) {
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

                    // This triggers your validation and saves data dynamically
                    registerPage.inputDetails(username, email, password, phone);
                    break;

                case "2":
                    System.out.println("\n--- LOGIN SCREEN ---");
                    System.out.print("Enter Email: ");
                    String loginEmail = scanner.nextLine();

                    System.out.print("Enter Password: ");
                    String loginPassword = scanner.nextLine();

                    // This checks whatever you just typed against saved data
                    loginPage.enterLogin(loginEmail, loginPassword);
                    break;

                case "3":
                    System.out.println("\nExiting system. Have a great day!");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("\n⚠️ Invalid selection! Please type 1, 2, or 3.");
                    break;
            }
        }
    }
}
