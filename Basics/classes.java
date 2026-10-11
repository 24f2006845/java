public class classes {
    public static void main(String[] args) {
        Payment payment = new CardPayment("CUST-101");
        payment.pay(2500);

        payment.printReceipt();
        payment.sendNotification();
    }

    // Abstract class: every payment method must define how it pays.
    abstract static class Payment {
        private final String customerId;

        Payment(String customerId) {
            this.customerId = customerId;
        }

        abstract void pay(double amount);

        void printReceipt() {
            System.out.println("Receipt generated for " + customerId);
        }

        void sendNotification() {
            Notifier notifier = new Notifier() {
                @Override
                void send(String message) {
                    System.out.println("SMS notification: " + message);
                }
            };
            notifier.send("Payment completed");
        }

        // Inner class: this helper belongs to a Payment object.
        class Notifier {
            void send(String message) {
                System.out.println(message);
            }
        }
    }

    static class CardPayment extends Payment {
        CardPayment(String customerId) {
            super(customerId);
        }

        @Override
        void pay(double amount) {
            System.out.println("Paid Rs." + amount + " using a debit card");
        }
    }
}
