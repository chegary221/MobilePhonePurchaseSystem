import java.util.ArrayList;
import java.util.List;

public class AccountController {
    // Simulated Database Table
    private static List<User> userDatabase = new ArrayList<>();

    public void submitData(String username, String email, String password, String phone,String address, RegisterPage page) {

        if (!email.contains("@") || !email.contains(".com")) {
            page.displayError("Validation Failure: Email is missing '@' or '.com'.");
            return;
        }
        else if (phone == null || phone.trim().isEmpty()) {
            page.displayError("Validation Failure: Phone Number cannot be blank.");
            return;
        }
        else if (address == null || address.trim().isEmpty()) {
            page.displayError("Validation Failure: Address cannot be blank.");
            return;
        }
        else if (checkEmail(email)) {
            page.displayError("This email is already associated with an account.");
            return;
        }
        else {
        String userID = "C" + String.format("%03d", userDatabase.size() + 1);
        Customer newUser = new Customer(userID, username, email, password, phone, address); 
        if (saveNewUser(newUser)) {page.displaySuccess();}
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