public class Staff extends User {
    private int accessLevel;

    public Staff(String userID, String name, String email, String password,
                 String contactNum, int accessLevel) {
        super(userID, name, email, password, contactNum);
        this.accessLevel = accessLevel;
    }

    public int getAccessLevel() { return accessLevel; }
    public void setAccessLevel(int accessLevel) { this.accessLevel = accessLevel; }

    @Override
    public String getRole() {
        return "Staff";
    }
}
