public class AuthController {

    public void authRequest(String email, String password, LoginPage page) {
        User user = getUser(email);

        if (user == null) {
            page.displayError("Authentication Failure: User not found.");
            return;
        }

        // E1: Account Lockout Check
        if (user.isLocked()) {
            page.displayLockout();
            return;
        }

        // Verify password
        if (user.getPassword().equals(password)) {
            // Else: Correct Password Flow
            user.resetAttempts();
            page.loadDashboard(user.getName());
        } else {
            // E2: Authentication Failure (Wrong Credentials)
            user.addFailAttempt();
            page.displayError("Authentication Failure: Wrong Credentials.");
        }
    }

    public User getUser(String email) {
        // Fetching user from our simulated database in AccountController
        return AccountController.findUserByEmail(email);
    }
}