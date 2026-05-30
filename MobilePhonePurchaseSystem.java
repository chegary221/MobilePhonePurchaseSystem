import java.util.Scanner;

public class MobilePhonePurchaseSystem {
        public static void main(String[] args) {
                Scanner scanner = new Scanner(System.in);
                AccountController controller = new AccountController();
                controller.saveNewUser( new Staff("S001","CC","cc_admin@email.com","123456","0123",3));
                RegisterPage registerPage = new RegisterPage(controller);
                LoginPage loginPage = new LoginPage();
     
                //~~~这些之后塞入login成功后的dashboard里
                Phone phone1 = new Phone("F001","Apple", "iPhone 15", 3899.00, 10);
                Phone phone2 = new Phone("F002","Samsung", "Galaxy S24", 3599.00, 10);
                Phone phone3 = new Phone("F003","Honor", "X9b", 2778.00, 5);

                phone1.displayInfo();
                phone2.displayInfo();
                phone3.displayInfo();
                CheckoutController checkout = new CheckoutController();
                Customer customer = new Customer("C001", "Gary","gary@gmail.com","123456","0123456789","Penang");
                customer.getCart().addItem(new CartItem(phone1, 2));
                customer.getCart().addItem(new CartItem(phone2, 3));
                customer.getCart().addItem(new CartItem(phone3, 5));
                checkout.buyNow(customer, customer.getCart());
                //~~~这些之后塞入login成功后的dashboard里

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

                                System.out.print("Enter Address: ");
                                String address = scanner.nextLine();

                                // This triggers your validation and saves data dynamically
                                registerPage.inputDetails(username, email, password, phone, address);
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