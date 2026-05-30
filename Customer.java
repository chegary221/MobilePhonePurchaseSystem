public class Customer extends User {
    private String address;
    private Cart cart;

    public Customer(String userID, String name, String email, String password,
                    String contactNum, String address) {
        super(userID, name, email, password, contactNum);
        this.address = address;
        this.cart = new Cart(10);}

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public Cart getCart() { return cart; }

    @Override
    public String getRole() {
        return "Customer";
    }
}
