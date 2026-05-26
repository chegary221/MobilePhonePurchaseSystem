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
}