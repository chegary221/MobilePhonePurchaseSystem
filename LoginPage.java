public class LoginPage {
    private AuthController controller = new AuthController();

    public User enterLogin(String email, String password) {
        System.out.println("\n[Login UI] Submitting login request...");
        return controller.authRequest(email, password, this);
    }

    public void displayError(String message) {
        System.out.println("[Login UI] ⚠️ Error: " + message);
    }

    public void displayLockout() {
        System.out.println("[Login UI] 🔒 Error: Account is locked due to too many failed attempts.");
    }

    public void loadDashboard(String username) {
        System.out.println("[Login UI] 🔓 Success! Loading Dashboard. Welcome back, " + username + "!");
    }
}
