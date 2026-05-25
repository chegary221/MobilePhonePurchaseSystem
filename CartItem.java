public class CartItem {

    private Phone phone;
    private int qty;

    public CartItem(Phone phone, int qty) {
        this.phone = phone;
        this.qty = qty;}

    public int getQty() {return qty;}
    public Phone getPhone() {return phone;}
}