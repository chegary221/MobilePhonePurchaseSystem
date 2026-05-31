public class OrderController {
    
    private Order[] orderList;
    private int orderCount; // 记录当前存了多少个订单

    public OrderController(Order[] orderList) {
        this.orderList = orderList;
        this.orderCount = 0;
        if (orderList != null) {
            for (int i = 0; i < orderList.length; i++) {
                if (orderList[i] != null) {
                    this.orderCount++;
                }
            }
        }
    }

    public OrderController() {
        this.orderList = new Order[100]; // 默认容量设为 100
        this.orderCount = 0;
    }

    // 返回纯数组
    public Order[] getTransactionHistory() {
        if (orderList == null || orderCount == 0) {
            System.out.println("No transaction history found.");
            return new Order[0];
        }

        System.out.println("=== Transaction History ===");
        for (int i = 0; i < orderCount; i++) {
            Order order = orderList[i];
            if (order != null) {
                System.out.println("Order ID   : " + order.getOrderID());
                System.out.println("Customer   : " + order.getCustomer().getName());
                System.out.println("Date       : " + order.getOrderDate());
                System.out.println("Amount     : RM" + String.format("%.2f", order.getAmount()));
                System.out.println("Status     : " + order.getStatus());
                System.out.println("---------------------------");
            }
        }
        return orderList;
    }

    public Order getOrderDetails(String orderID) {
        if (orderID == null || orderID.isEmpty()) {
            System.out.println("Invalid order ID provided.");
            return null;
        }

        for (int i = 0; i < orderCount; i++) {
            Order order = orderList[i];
            if (order != null && order.getOrderID().equalsIgnoreCase(orderID)) {
                System.out.println("=== Order Details ===");
                System.out.println("Order ID   : " + order.getOrderID());
                System.out.println("Customer   : " + order.getCustomer().getName());
                System.out.println("Date       : " + order.getOrderDate());
                System.out.println("Amount     : RM" + String.format("%.2f", order.getAmount()));
                System.out.println("Status     : " + order.getStatus());
                
                CartItem[] items = order.getItems();
                if (items != null && items.length > 0) {
                    System.out.println("Items:");
                    for (int j = 0; j < items.length; j++) {
                        CartItem item = items[j];
                        if (item != null && item.getPhone() != null) {
                            System.out.println("  - " + item.getPhone().getBrand()
                                    + " " + item.getPhone().getModel()
                                    + " x" + item.getQty()
                                    + " @ RM" + String.format("%.2f", item.getPhone().getPrice())
                                    + " | Subtotal: RM" + String.format("%.2f", item.calculateSubtotal()));
                        }
                    }
                }

                Payment payment = order.getPayment();
                if (payment != null) {
                    System.out.println("Payment    : " + payment.getPaymentMethod() + " | " + payment.getPaymentStatus());
                }

                return order;
            }
        }

        System.out.println("Order with ID '" + orderID + "' not found.");
        return null;
    }

    // 替代原来的 List.add()
    public void addOrder(Order order) {
        if (order != null) {
            if (orderCount < orderList.length) {
                orderList[orderCount] = order;
                orderCount++;
            } else {
                System.out.println("Order list is full, cannot add more orders.");
            }
        }
    }

    public Order[] getOrderList() { return orderList; }
    public void setOrderList(Order[] orderList) { this.orderList = orderList; }
}
