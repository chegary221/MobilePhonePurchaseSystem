import java.util.Date;

public class Order {
    private static int counter = 1000;

    private String orderID;
    private Date orderDate;
    private CartItem[] items;
    private Payment payment;
    private String status;
    private double amount;
   

    // MAIN constructor ✔
    public Order(CartItem[] items, double amount) {

        this.orderID = "ORD" + counter++;
        this.orderDate = new Date();
        this.items = items;
        this.amount = amount;
        this.status = "Pending";
        this.payment = new Payment(); // composition ✔
    }
            
    public Order() {}

    public String getOrderID() { return orderID; }
    public void setOrderID(String orderID) { this.orderID = orderID; }

    public Date getOrderDate() { return orderDate; }
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }

    public Payment getPayment() { return payment; }
    public void setPayment(Payment payment) { this.payment = payment; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }


}
