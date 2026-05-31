public class Database {
    private String connectionString;
    private boolean isConnected;
    private Phone[] inventoryRecord;
    private int itemCount;

    // ── UC 8: order store ────────────────────────────────────────────────────
    private Order[] orderStore;
    private int orderCount;

    public Database() {
        this.connectionString = "jdbc:mock:localDB";
        this.inventoryRecord = new Phone[100];
        this.itemCount = 0;
        this.orderStore = new Order[100];
        this.orderCount = 0;
        connectToDatabase();
    }

    public void connectToDatabase() {
        this.isConnected = true;
        System.out.println("Database connected using: " + connectionString);
    }

    public Phone[] getInventoryRecord() { return inventoryRecord; }

    public void updateCentralDatabase(Phone item) {
        if (itemCount < inventoryRecord.length) {
            inventoryRecord[itemCount] = item;
            itemCount++;
            System.out.println("Item added to database.");
        }
    }

    public Phone[] fetchStockLevels() {
        return inventoryRecord;
    }

    public boolean checkDependencies(String phoneID) { return false; }
    public boolean activeOrderConflict(String phoneID) { return false; }
    public boolean clearToDelete(String phoneID) { return true; }
    public boolean isConnected() { return isConnected; }

    // =========================================================================
    // UC 3 — Browse Phones
    // =========================================================================

    /**
     * retrieveCatalogData()
     * Called by PhoneController.requestPhoneList().
     * Returns the current inventory as catalog data.
     * Returns null to signal a connection error (E1).
     *
     * Sequence:  PhoneController → Database : retrieveCatalogData()
     *            Database        → PhoneController : catalogData / connectionError
     */
    public Phone[] retrieveCatalogData() {
        if (!isConnected) {
            System.out.println("[Database] connectionError: not connected.");
            return null;                            // triggers E1 in UC 3
        }
        System.out.println("[Database] retrieveCatalogData() → returning "
                + itemCount + " record(s).");
        return inventoryRecord;
    }

    /**
     * queryPhoneSpecs(modelID)
     * Called by PhoneController.getPhoneDetails(modelID).
     * Returns the Phone whose phoneID matches modelID, or null if not found.
     *
     * Sequence:  PhoneController → Database : queryPhoneSpecs(modelID)
     *            Database        → PhoneController : phoneDetails
     */
    public Phone queryPhoneSpecs(String modelID) {
        if (modelID == null || modelID.isEmpty()) {
            System.out.println("[Database] queryPhoneSpecs(): invalid modelID.");
            return null;
        }
        for (int i = 0; i < itemCount; i++) {
            if (inventoryRecord[i] != null
                    && inventoryRecord[i].getPhoneID().equalsIgnoreCase(modelID)) {
                System.out.println("[Database] queryPhoneSpecs(" + modelID
                        + ") → phoneDetails found.");
                return inventoryRecord[i];
            }
        }
        System.out.println("[Database] queryPhoneSpecs(" + modelID + ") → not found.");
        return null;
    }

    // =========================================================================
    // UC 8 — Track Order Status
    // =========================================================================

    /**
     * retrieveOrders(customerID)
     * Called by OrderController.getTransactionHistory().
     * Returns all orders belonging to the given customer.
     * Returns an empty array to signal noOrdersFound (E1).
     *
     * Sequence:  OrderController → Database : retrieveOrders()
     *            Database        → OrderController : ordersData / noOrdersFound
     */
    public Order[] retrieveOrders(String customerID) {
        if (customerID == null || customerID.isEmpty()) {
            System.out.println("[Database] retrieveOrders(): invalid customerID.");
            return new Order[0];                    // empty = noOrdersFound (E1)
        }

        // Count matches first so we can size the result array exactly
        int matchCount = 0;
        for (int i = 0; i < orderCount; i++) {
            if (orderStore[i] != null
                    && orderStore[i].getCustomer() != null
                    && orderStore[i].getCustomer().getUserID()
                                    .equalsIgnoreCase(customerID)) {
                matchCount++;
            }
        }

        if (matchCount == 0) {
            System.out.println("[Database] retrieveOrders(" + customerID
                    + ") → noOrdersFound.");
            return new Order[0];                    // triggers E1 in UC 8
        }

        Order[] result = new Order[matchCount];
        int idx = 0;
        for (int i = 0; i < orderCount; i++) {
            if (orderStore[i] != null
                    && orderStore[i].getCustomer() != null
                    && orderStore[i].getCustomer().getUserID()
                                    .equalsIgnoreCase(customerID)) {
                result[idx++] = orderStore[i];
            }
        }
        System.out.println("[Database] retrieveOrders(" + customerID
                + ") → ordersData: " + matchCount + " order(s) found.");
        return result;
    }

    /**
     * fetchTrackingLifecycle(orderID)
     * Called by OrderController.fetchTrackingLifecycle(orderID).
     * Returns the current status string of the order.
     * Returns null to signal notFoundError (E2: Invalid Manual Tracking ID).
     *
     * Sequence:  OrderController → Database : fetchTrackingLifecycle(orderID)
     *            Database        → OrderController : statusDetails  [Valid Order ID]
     *            Database        → OrderController : notFoundError  [E2]
     */
    public String fetchTrackingLifecycle(String orderID) {
        if (orderID == null || orderID.isEmpty()) {
            System.out.println("[Database] fetchTrackingLifecycle(): invalid orderID.");
            return null;                            // triggers E2 in UC 8
        }
        for (int i = 0; i < orderCount; i++) {
            if (orderStore[i] != null
                    && orderStore[i].getOrderID().equalsIgnoreCase(orderID)) {
                String status = orderStore[i].getStatus();
                System.out.println("[Database] fetchTrackingLifecycle(" + orderID
                        + ") → statusDetails: " + status);
                return status;                      // [Valid Order ID] path
            }
        }
        System.out.println("[Database] fetchTrackingLifecycle(" + orderID
                + ") → notFoundError.");
        return null;                                // triggers E2 in UC 8
    }

    // =========================================================================
    // Helper — save an order (called after checkout completes)
    // =========================================================================

    /** Persists a completed order into the order store. */
    public void saveOrder(Order order) {
        if (order != null && orderCount < orderStore.length) {
            orderStore[orderCount] = order;
            orderCount++;
            System.out.println("[Database] saveOrder(): Order "
                    + order.getOrderID() + " saved.");
        }
    }
}
