import java.util.Date;

public class Order {
    private String orderID;
    private Date orderDate;
    private int qty;
    private Payment payment;   // composition
    private Phone phone;       // aggregation
    private String status;

    public Order() {}

    public Order(String orderID, Date orderDate, int qty,
             Phone phone, String status) {
            this.orderID = orderID;
            this.orderDate = orderDate;
            this.qty = qty;
            this.payment = new Payment(); // composition ✔
            this.phone = phone;           // aggregation ✔
            this.status = status;
        }

    public String getOrderID() { return orderID; }
    public void setOrderID(String orderID) { this.orderID = orderID; }

    public Date getOrderDate() { return orderDate; }
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }

    public int getQty() { return qty; }
    public void setQty(int qty) { this.qty = qty; }

    public Payment getPayment() { return payment; }
    public void setPayment(Payment payment) { this.payment = payment; }

    public Phone getPhone() { return phone; }
    public void setPhone(Phone phone) { this.phone = phone; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}