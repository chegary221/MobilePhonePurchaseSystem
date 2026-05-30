public interface IPaymentGateway {
    boolean processTransaction(String details);
    boolean checkConnectionStatus();
}
