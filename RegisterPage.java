public class RegisterPage {
    private AccountController controller;

    public RegisterPage(AccountController controller) {
        this.controller = controller;
    }

    public void inputDetails(String username, String email, String password, String phone, String address) {
        System.out.println("\n[Register UI] Submitting form data...");
        controller.submitData(username, email, password, phone, address,this);
    }

    public void displayError(String message) {
        System.out.println("[Register UI] ⚠️ Error: " + message);
    }

    public void displaySuccess() {
        System.out.println("[Register UI] 🎉 Registration Successful! Welcome aboard.");
    }
}