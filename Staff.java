public class Staff extends User {
    private String staffID;
    private int accessLevel;

    public Staff() {}

    public Staff(String userID, String name, String email, String password,
                 String contactNum, String staffID, int accessLevel) {
        super(userID, name, email, password, contactNum);
        this.staffID = staffID;
        this.accessLevel = accessLevel;
    }

    public String getStaffID() { return staffID; }
    public void setStaffID(String staffID) { this.staffID = staffID; }

    public int getAccessLevel() { return accessLevel; }
    public void setAccessLevel(int accessLevel) { this.accessLevel = accessLevel; }
}