import java.util.ArrayList;
import java.util.List;

public class AccountController {
    // Simulated Database Table
    private static List<User> userDatabase = new ArrayList<>();

    public void submitData(String username, String email, String password, String phone, RegisterPage page) {

        if (!email.contains("@") || !email.contains(".com")) {
            page.displayError("Validation Failure: Email is missing '@' or '.com'.");
            return;
        }

        if (phone == null || phone.trim().isEmpty()) {
            page.displayError("Validation Failure: Phone Number cannot be blank.");
            return;
        }


        if (checkEmail(email)) {
            page.displayError("This email is already associated with an account.");
            return;
        }


        User newUser = new User(username, email, password, phone); 
        if (saveNewUser(newUser)) {
            page.displaySuccess();
        }
    }

    public boolean checkEmail(String email) {
        for (User user : userDatabase) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return true; // Email already exists
            }
        }
        return false;
    }

    public boolean saveNewUser(User newUser) {
        return userDatabase.add(newUser);
    }
    
    public static User findUserByEmail(String email) {
        for (User user : userDatabase) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return user;
            }
        }
        return null;
    }
}