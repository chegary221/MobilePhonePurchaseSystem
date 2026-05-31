public class AuthController {

    public User authRequest(String email, String password, LoginPage page) {
        User user = getUser(email);

        if (user == null) {
            page.displayError("Authentication Failure: User not found.");
            return null;
        }

        // E1: Account Lockout Check
        if (user.isLocked()) {
            page.displayLockout();
            return null;
        }

        // Verify password
        if (user.getPassword().equals(password)) {
            // Else: Correct Password Flow
            user.resetAttempts();
            page.loadDashboard(user.getName());
            return user;
        } else {
            // E2: Authentication Failure (Wrong Credentials)
            user.addFailAttempt();
            page.displayError("Authentication Failure: Wrong Credentials.");
            return null;
        }
    }

    public User getUser(String email) {
        // Fetching user from our simulated database in AccountController
        return AccountController.findUserByEmail(email);
    }
}
