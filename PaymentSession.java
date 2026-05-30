public class PaymentSession {
    private String sessionID;
    private Order currentOrder;
    private IPaymentGateway gateway;

    public PaymentSession(Order order) {
        this.sessionID = "SESS_" + System.currentTimeMillis();
        this.currentOrder = order;
        this.gateway = new PaymentGateway(); 
    }

    public String[] returnAvailableMethods() {
        return new String[]{"Credit Card", "FPX", "E-Wallet"};
    }

    public boolean validateTransaction() { return true; }
    public void sendTransactionRequest(String details) {
        boolean success = gateway.processTransaction(details);
        if(success) {
            System.out.println("Transaction Approved.");
        }
    }
    
    public void timeoutError() { System.out.println("Timeout Error."); }
    public void orderDeclined() { System.out.println("Order Declined."); }
}
