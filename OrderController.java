import java.util.ArrayList;
import java.util.List;

public class OrderController {
    private List<Order> orderList;

    public OrderController(List<Order> orderList) {
        this.orderList = orderList;
    }

    public OrderController() {
        this.orderList = new ArrayList<>();
    }

    public String getPaymentStatus() {
        return this.status;
    }

    public List<Order> getTransactionHistory() {
        if (orderList == null || orderList.isEmpty()) {
            System.out.println("No transaction history found.");
            return new ArrayList<>();
        }

        System.out.println("=== Transaction History ===");
        for (Order order : orderList) {
            System.out.println("Order ID   : " + order.getOrderID());
            System.out.println("Customer   : " + order.getCustomer().getName());
            System.out.println("Date       : " + order.getOrderDate());
            System.out.println("Amount     : RM" + String.format("%.2f", order.getAmount()));
            System.out.println("Status     : " + order.getStatus());
            System.out.println("---------------------------");
        }
        return orderList;
    }

    public Order getOrderDetails(String orderID) {
        if (orderID == null || orderID.isEmpty()) {
            System.out.println("Invalid order ID provided.");
            return null;
        }

        for (Order order : orderList) {
            if (order.getOrderID().equalsIgnoreCase(orderID)) {
                System.out.println("=== Order Details ===");
                System.out.println("Order ID   : " + order.getOrderID());
                System.out.println("Customer   : " + order.getCustomer().getName());
                System.out.println("Date       : " + order.getOrderDate());
                System.out.println("Amount     : RM" + String.format("%.2f", order.getAmount()));
                System.out.println("Status     : " + order.getStatus());

                CartItem[] items = order.getItems();
                if (items != null && items.length > 0) {
                    System.out.println("Items:");
                    for (CartItem item : items) {
                        System.out.println("  - " + item.getPhone().getBrand()
                                + " " + item.getPhone().getModel()
                                + " x" + item.getQty()
                                + " @ RM" + String.format("%.2f", item.getPhone().getPrice())
                                + " | Subtotal: RM" + String.format("%.2f", item.calculateSubtotal()));
                    }
                }

                Payment payment = order.getPayment();
                if (payment != null) {
                    System.out.println("Payment    : " + payment.getPaymentMethod()
                            + " | " + payment.getPaymentStatus());
                }

                return order;
            }
        }

        System.out.println("Order with ID '" + orderID + "' not found.");
        return null;
    }

    public List<Order> getOrderList() { return orderList; }
    public void setOrderList(List<Order> orderList) { this.orderList = orderList; }

    public void addOrder(Order order) {
        if (order != null) {
            orderList.add(order);
        }
    }
}
