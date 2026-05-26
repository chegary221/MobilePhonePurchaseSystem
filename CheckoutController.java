public class CheckoutController {

    public void buyNow(Customer customer, Cart cart) {
        CartItem[] items = cart.getItems();
        String status = checkStock(items);
        if (status.equals("available")) {
            double amount = calculateAmount(items);
            Order order = new Order(items, amount);
            reduceStock(items);
            cart.clear();
            displaySummary(order, items);} 
        else if (status.equals("out of stock")) {displayOutOfStock();} 
        else {displayInsufficient();} }

    // CHECK STOCK (loop)
    private String checkStock(CartItem[] items) {
        for (CartItem item : items) {
            if (item != null) {
                String status = item.getPhone().checkStock(item.getQty());
                if (!status.equals("available")) return status; } }
            return "available";}

    // REDUCE STOCK
    private void reduceStock(CartItem[] items) {
        for (CartItem item : items) {
            if (item != null) item.getPhone().reduceStock(item.getQty());} }

    // CALCULATE TOTAL
    private double calculateAmount(CartItem[] items) {
        double total = 0;
        for (CartItem item : items) {
            if (item != null) {total += item.getPhone().getPrice() * item.getQty();} }
        return total; }

    // DISPLAY
    private void displaySummary(Order order, CartItem[] items) {
        System.out.println("Order ID: " + order.getOrderID());
        System.out.println("Amount: " + order.getAmount());
        for (CartItem item : items) {
            if (item != null) System.out.println(item.getPhone().getBrand()+ " x " + item.getQty());} }

    private void displayOutOfStock() {System.out.println("Out of stock");}
    private void displayInsufficient() {System.out.println("Insufficient stock");}
}