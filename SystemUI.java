import java.util.Scanner;

public class SystemUI {
    private PaymentController payController;
    private Scanner scanner;

    public SystemUI(PaymentController controller) {
        this.payController = controller;
        this.scanner = new Scanner(System.in);
    }

    public void initiateCheckout(Order order) {
        System.out.println("\n--- Checkout System ---");
        displayTotalAmount(order.Amount());
        payController.startPayment(order);
        requestPaymentDetails();
    }

    public void displayTotalAmount(double amount) {
        System.out.println("Total Amount Due: RM" + amount);
    }

    public void requestPaymentDetails() {
        System.out.println("Enter Card Number to pay:");
        String details = scanner.nextLine();
        confirmPayment(details);
    }

    public void confirmPayment(String details) {
        payController.processPayment(details);
        generateReceipt();
    }

    public void generateReceipt() {
        System.out.println("Generating Receipt...");
        displayDigitalReceipt("PAID IN FULL");
    }

    public void displayDigitalReceipt(String data) {
        System.out.println("=== DIGITAL RECEIPT ===");
        System.out.println(data);
        System.out.println("=======================");
    }
}
