public class User {
    private String username;
    private String email;
    private String passwordHash; 
    private String phoneNumber;
    private int failedAttempts;
    private boolean isLocked;

    public User(String username, String email, String passwordHash, String phoneNumber) {
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.phoneNumber = phoneNumber;
        this.failedAttempts = 0;
        this.isLocked = false;
    }

    public void addFailAttempt() {
        this.failedAttempts++;
        if (this.failedAttempts >= 3) { 
            this.isLocked = true;
        }
    }

    public void resetAttempts() {
        this.failedAttempts = 0;
    }


    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public boolean isLocked() { return isLocked; }
    public String getUsername() { return username; }
}