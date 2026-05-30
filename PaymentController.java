public class PaymentController {
    private PaymentSession activeSession;

    public PaymentController() { }

    public void startPayment(Order order) {
        this.activeSession = new PaymentSession(order);
        System.out.println("Payment Session Started.");
    }

    public void processPayment(String details) {
        activeSession.sendTransactionRequest(details);
        recordPayment();
    }

    public void processPayment(String details, String voucherCode) {
        System.out.println("Applying voucher: " + voucherCode);
        activeSession.sendTransactionRequest(details);
        recordPayment();
    }

    public void returnSuccessConfirmation() { System.out.println("Payment Successful!"); }
    public void recordPayment() { System.out.println("Payment recorded in system."); }
}
