public class Interfaces {
    public static void main(String[] args) {
        PaymentGateway gateway = new UpiPaymentGateway();
        NotificationService notificationService = new EmailNotificationService();

        OrderService orderService = new OrderService(gateway, notificationService);
        orderService.placeOrder("ORD-1001", 1499.0);

        DiscountPolicy festivalDiscount = amount -> amount * 0.90;
        double discountedAmount = festivalDiscount.apply(1499.0);
        System.out.println("Festival price: Rs." + discountedAmount);
    }

    interface PaymentGateway {
        boolean pay(String orderId, double amount);
    }

    static class UpiPaymentGateway implements PaymentGateway {
        @Override
        public boolean pay(String orderId, double amount) {
            System.out.println("Paid Rs." + amount + " for " + orderId + " using UPI");
            return true;
        }
    }

    interface NotificationService {
        void send(String recipient, String message);
    }

    @FunctionalInterface
    interface DiscountPolicy {
        double apply(double amount);
    }

    static class EmailNotificationService implements NotificationService {
        @Override
        public void send(String recipient, String message) {
            System.out.println("Email sent to " + recipient + ": " + message);
        }
    }

    static class OrderService {
        private final PaymentGateway paymentGateway;
        private final NotificationService notificationService;

        OrderService(
                PaymentGateway paymentGateway,
                NotificationService notificationService) {
            this.paymentGateway = paymentGateway;
            this.notificationService = notificationService;
        }

        void placeOrder(String orderId, double amount) {
            boolean successful = paymentGateway.pay(orderId, amount);

            if (successful) {
                notificationService.send(
                        "customer@example.com",
                        "Your order " + orderId + " was confirmed.");
            }
        }
    }
}
