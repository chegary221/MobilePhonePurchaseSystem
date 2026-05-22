public class Customer extends User {
    private String address;
    private String accountID;

    public Customer() {}

    public Customer(String userID, String name, String email, String password,
                    String contactNum, String address, String accountID) {
        super(userID, name, email, password, contactNum);
        this.address = address;
        this.accountID = accountID;
    }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getAccountID() { return accountID; }
    public void setAccountID(String accountID) { this.accountID = accountID; }
}