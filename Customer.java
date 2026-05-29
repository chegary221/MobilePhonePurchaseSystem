public class Customer extends User {
    private String address;

    public Customer() {}

    public Customer(String userID, String name, String email, String password,
                    String contactNum, String address) {
        super(userID, name, email, password, contactNum);
        this.address = address;
        this.accountID = accountID;}

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}
