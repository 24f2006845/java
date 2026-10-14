public class exceptionHandling {
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
    }
}
