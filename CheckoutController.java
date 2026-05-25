public class CheckoutController {

    public void buyNow(Customer customer, Phone phone, Cart cart, int qty) {
        CartItem[] items = cart.getItems();
        String status = phone.checkStock(qty);
        if (status.equals("available")) {
            double amount = calculateAmount(items, phone, qty);
            Order order = new Order(items, amount);
            phone.reduceStock(qty);
            cart.clear();
            displaySummary(order);} 
        else if (status.equals("out of stock")) displayOutOfStock();
        else  displayInsufficient();}

    private double calculateAmount(CartItem[] items, Phone phone, int qty) {return phone.getPrice() * qty;}

    private void displaySummary(Order order) {System.out.println("Order created: " + order.getOrderID());}

    private void displayOutOfStock() {System.out.println("Out of stock");}
    private void displayInsufficient() {System.out.println("Insufficient stock");}
}