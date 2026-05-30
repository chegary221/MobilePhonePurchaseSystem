public class PaymentGateway implements IPaymentGateway {
    @Override
    public boolean processTransaction(String details) {
        System.out.println("Gateway processing details: " + details);
        return true; 
    }

    @Override
    public boolean checkConnectionStatus() {
        return true;
    }
}
