import java.util.Date;

public class Order {
    private static int counter = 1000;
    
    private Customer customer;
    private String orderID;
    private Date orderDate;
    private CartItem[] items;
    private Payment payment;
    private String status;
    private double amount;
   

    // MAIN constructor ✔
    public Order(Customer customer, CartItem[] items, double amount) {
        this.customer=customer;
        this.orderID = "ORD" + counter++;
        this.orderDate = new Date();
        this.items = items;
        this.amount = amount;
        this.status = "Pending";
        this.payment = new Payment(); }
            
    public Order() {}
    public Customer getCustomer(){ return customer;}

    public String getOrderID() { return orderID; }
    public void setOrderID(String orderID) { this.orderID = orderID; }

    public Date getOrderDate() { return orderDate; }
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }

    public Payment getPayment() { return payment; }
    public void setPayment(Payment payment) { this.payment = payment; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public double getAmount() {return amount;}
    public CartItem[] getItems(){return items;}

}
