public class User {
    protected String userID;
    protected String name;
    protected String email;
    protected String password;
    protected String contactNum;

    public User() {}

    public User(String userID, String name, String email, String password, String contactNum) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.contactNum = contactNum;
    }

    public String getUserID() { return userID; }
    public void setUserID(String userID) { this.userID = userID; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getContactNum() { return contactNum; }
    public void setContactNum(String contactNum) { this.contactNum = contactNum; }

    public abstract String getRole();
}
