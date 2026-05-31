public class Cart {

    private CartItem[] items;
    private int size;

    public Cart(int capacity) {
        items = new CartItem[capacity];
        size = 0;}

    // add item into cart
    public void addItem(CartItem item) {
        if (size < items.length) {
            items[size] = item;
            size++;
            System.out.println("Item added into cart.");} 
        else System.out.println("Cart is full.");}
        
    public CartItem[] getItems() {
        CartItem[] actualItems = new CartItem[size];
        for (int i = 0; i < size; i++) {actualItems[i] = items[i];}
        return actualItems;}

    public void clearCart() {
        for (int i = 0; i < size; i++) items[i] = null;
        size = 0;}

    // Calculates the total price of all items currently in the cart
    public double calculateTotal() {
        double total = 0.0;
        
        // Loop through the array only up to the current 'size'
        for (int i = 0; i < size; i++) {
            if (items[i] != null) {
                // Multiplies the price of the phone by the quantity added
                total += (items[i].getPhone().getPrice() * items[i].getQuantity());
            }
        }
        
        return total;
    }
}
