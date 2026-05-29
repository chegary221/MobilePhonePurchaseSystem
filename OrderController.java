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

    public String fetchTrackingLifecycle(String orderID) {
        if (orderID == null || orderID.isEmpty()) {
            System.out.println("Invalid order ID provided.");
            return null;
        }

        Order target = null;
        for (Order order : orderList) {
            if (order.getOrderID().equalsIgnoreCase(orderID)) {
                target = order;
                break;
            }
        }

        if (target == null) {
            System.out.println("Cannot fetch tracking: order '" + orderID + "' not found.");
            return null;
        }

        String currentStatus = target.getStatus();
        String[] stages = {"Pending", "Confirmed", "Processing", "Shipped", "Delivered"};

        System.out.println("=== Tracking Lifecycle: " + orderID + " ===");
        boolean reached = false;
        for (String stage : stages) {
            if (stage.equalsIgnoreCase(currentStatus)) {
                System.out.println("  [*] " + stage + "  <-- Current");
                reached = true;
            } else if (!reached) {
                System.out.println("  [/] " + stage + "  (Completed)");
            } else {
                System.out.println("  [ ] " + stage);
            }
        }

        return currentStatus;
    }

    public List<Order> retrieveOrders() {
        if (orderList == null || orderList.isEmpty()) {
            System.out.println("No orders found.");
            return new ArrayList<>();
        }

        System.out.println("=== All Orders ===");
        System.out.printf("%-12s %-20s %-12s %-12s%n",
                "Order ID", "Customer", "Amount (RM)", "Status");
        System.out.println("------------------------------------------------------------");

        for (Order order : orderList) {
            System.out.printf("%-12s %-20s %-12.2f %-12s%n",
                    order.getOrderID(),
                    order.getCustomer().getName(),
                    order.getAmount(),
                    order.getStatus());
        }

        return orderList;
    }

    public List<Order> getOrderList() { return orderList; }
    public void setOrderList(List<Order> orderList) { this.orderList = orderList; }

    public void addOrder(Order order) {
        if (order != null) {
            orderList.add(order);
        }
    }
}
