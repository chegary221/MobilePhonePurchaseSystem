public class RegisterPage {
    private AccountController controller = new AccountController();

    public void inputDetails(String username, String email, String password, String phone) {
        System.out.println("\n[Register UI] Submitting form data...");
        controller.submitData(username, email, password, phone, this);
    }

    public void displayError(String message) {
        System.out.println("[Register UI] ⚠️ Error: " + message);
    }

    public void displaySuccess() {
        System.out.println("[Register UI] 🎉 Registration Successful! Welcome aboard.");
    }
}