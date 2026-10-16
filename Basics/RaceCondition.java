/**
 * Demonstrates a race condition using a shared bank account.
 *
 * A race condition occurs when multiple threads access shared data at the
 * same time and the final result depends on the order in which they run.
 */
public class RaceCondition {

    public static void main(String[] args) throws InterruptedException {
        BankAccount unsafeAccount = new BankAccount(1_000);

        // Both customers try to withdraw 700 from the same account.
        Thread customerA = new Thread(
                () -> unsafeAccount.withdrawWithoutLock(700), "Customer-A");
        Thread customerB = new Thread(
                () -> unsafeAccount.withdrawWithoutLock(700), "Customer-B");

        customerA.start();
        customerB.start();
        customerA.join();
        customerB.join();

        System.out.println("Balance after unsafe withdrawals: "
                + unsafeAccount.getBalance());
        System.out.println();

        BankAccount safeAccount = new BankAccount(1_000);
        Thread safeCustomerA = new Thread(
                () -> safeAccount.withdrawSafely(700), "Customer-A");
        Thread safeCustomerB = new Thread(
                () -> safeAccount.withdrawSafely(700), "Customer-B");

        safeCustomerA.start();
        safeCustomerB.start();
        safeCustomerA.join();
        safeCustomerB.join();

        System.out.println("Balance after synchronized withdrawals: "
                + safeAccount.getBalance());
    }

    private static class BankAccount {
        private int balance;

        private BankAccount(int balance) {
            this.balance = balance;
        }

        /**
         * This method contains a race condition.
         *
         * The check and the subtraction are separate operations. Two threads
         * can both see enough money before either thread updates the balance.
         * This may allow both withdrawals and produce an invalid balance.
         */
        private void withdrawWithoutLock(int amount) {
            if (balance >= amount) {
                System.out.println(Thread.currentThread().getName()
                        + " found enough money.");

                try {
                    // Simulates time spent validating a payment.
                    Thread.sleep(100);
                } catch (InterruptedException exception) {
                    Thread.currentThread().interrupt();
                    return;
                }

                balance -= amount;
                System.out.println(Thread.currentThread().getName()
                        + " withdrew " + amount);
            } else {
                System.out.println(Thread.currentThread().getName()
                        + " could not withdraw " + amount);
            }
        }

        /**
         * The synchronized keyword makes the complete withdrawal operation
         * atomic from the perspective of other threads. Only one thread can
         * execute this method for this account at a time.
         */
        private synchronized void withdrawSafely(int amount) {
            if (balance >= amount) {
                balance -= amount;
                System.out.println(Thread.currentThread().getName()
                        + " safely withdrew " + amount);
            } else {
                System.out.println(Thread.currentThread().getName()
                        + " could not withdraw " + amount);
            }
        }

        private synchronized int getBalance() {
            return balance;
        }
    }
}
