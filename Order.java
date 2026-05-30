import java.util.Date;

public class Order {
   
    private static int counter = 1000;
    private Customer customer;
    private CartItem[] items; 
    private Payment payment;
    private String orderID;
    private Date orderDate;
    private String status;  
    private double amount;    

    public Order(Customer customer, CartItem[] items, double amount) {
        this.customer = customer;
        this.orderID = "ORD" + counter++;
        this.orderDate = new Date();
        this.items = items;
        this.amount = amount;
        this.status = "Pending";
        this.payment = new Payment(); 
    }
    
    public Order() {}

    public Order(String orderID, double amount) {
        this.orderID = orderID;
        this.amount = amount;
        this.status = "Pending";
        this.orderDate = new Date();
    }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public String getOrderID() { return orderID; }
    public void setOrderID(String orderID) { this.orderID = orderID; }

    public Date getOrderDate() { return orderDate; }
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }

    public CartItem[] getItems() { return items; }
    public void setItems(CartItem[] items) { this.items = items; }

    public Payment getPayment() { return payment; }
    public void setPayment(Payment payment) { this.payment = payment; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public void updateOrderStatus(String status) {
        this.status = status;
    }
}
