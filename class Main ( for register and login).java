public class Main {
    public static void main(String[] args) {
        RegisterPage registerPage = new RegisterPage();
        LoginPage loginPage = new LoginPage();

        // 1. Try Invalid Registration (E2 - Validation Error)
        registerPage.inputDetails("JohnDoe", "john.com", "pass123", "12345");

        // 2. Try Valid Registration (Basic Flow)
        registerPage.inputDetails("JohnDoe", "john@test.com", "securePass", "012-345678");

        // 3. Try Registering same email again (E1 - Duplicate Identity)
        registerPage.inputDetails("JohnClone", "john@test.com", "anotherPass", "012-999999");

        // 4. Try Login with Wrong Password (E2 - Auth Failure)
        loginPage.enterLogin("john@test.com", "wrongPass1");
        loginPage.enterLogin("john@test.com", "wrongPass2");
        loginPage.enterLogin("john@test.com", "wrongPass3"); // This 3rd failure triggers lockout

        // 5. Try Logging in after Account Lockout (E1 - Account Lockout)
        loginPage.enterLogin("john@test.com", "securePass"); 
    }
}