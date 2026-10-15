public class exceptionHandling {
    static class InvalidAgeException extends Exception {
        public InvalidAgeException(String message) {
            super(message);
        }
    }

    static class BankAccount {
        private double balance;

        public BankAccount(double balance) {
            this.balance = balance;
        }

        public void withdraw(double amount) {
            if (amount <= 0) {
                throw new IllegalArgumentException("Withdrawal amount must be greater than zero.");
            }

            if (amount > balance) {
                throw new IllegalStateException("Insufficient balance in the account.");
            }

            balance -= amount;
            System.out.println("Withdrawal successful. Remaining balance: " + balance);
        }
    }

    static void registerUser(int age) {
        if (age < 18) {
            throw new IllegalArgumentException("User must be at least 18 years old.");
        }

        System.out.println("User registered successfully.");
    }

    static void registerUserWithCustomException(int age) throws InvalidAgeException {
        if (age < 18) {
            throw new InvalidAgeException("Age must be at least 18 to register.");
        }

        System.out.println("User registered with a valid age.");
    }

    static void validateAge(int age) throws InvalidAgeException {
        if (age < 18) {
            throw new InvalidAgeException("Age must be at least 18.");
        }
    }

    static void createAccount(int age) throws InvalidAgeException {
        validateAge(age); // Passes the exception to the caller.
        System.out.println("Account created successfully.");
    }

    public static void main(String[] args) {
        BankAccount account = new BankAccount(1000.0);

        try {
            String input = "2500";
            double amount = Double.parseDouble(input);
            account.withdraw(amount);
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount entered. Please type a number.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Transaction failed: " + e.getMessage());
        } finally {
            System.out.println("Transaction attempt finished.");
        }

        try {
            registerUser(16);
        } catch (IllegalArgumentException e) {
            System.out.println("Registration failed: " + e.getMessage());
        }

        try {
            registerUserWithCustomException(16);
        } catch (InvalidAgeException e) {
            System.out.println("Custom exception: " + e.getMessage());
        }

        try {
            createAccount(16);
        } catch (InvalidAgeException e) {
            System.out.println("Account creation failed: " + e.getMessage());
        }
    }
}
