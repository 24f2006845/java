import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Real-world example: an online store processing several customer orders.
 *
 * Notes:
 * 1. A thread is an independent path of execution.
 * 2. ExecutorService manages a pool of reusable threads for us.
 * 3. Different orders can be processed at the same time, so the application
 *    does not need to wait for one order to finish before starting another.
 * 4. AtomicInteger is used because multiple threads update the same counter.
 *    It prevents lost updates that could happen with "counter++".
 * 5. shutdown() stops new work from being submitted, while awaitTermination()
 *    gives already submitted work time to finish.
 */
public class multiThread {

    private static final AtomicInteger completedOrders = new AtomicInteger();

    public static void main(String[] args) {
        List<String> orders = List.of(
                "Order-101",
                "Order-102",
                "Order-103",
                "Order-104",
                "Order-105");

        // Three worker threads can process three orders concurrently.
        ExecutorService orderProcessor = Executors.newFixedThreadPool(3);

        for (String order : orders) {
            orderProcessor.submit(() -> processOrder(order));
        }

        orderProcessor.shutdown();

        try {
            // Wait until all submitted orders are completed.
            if (!orderProcessor.awaitTermination(10, TimeUnit.SECONDS)) {
                orderProcessor.shutdownNow();
                System.out.println("Some orders could not be completed in time.");
            }
        } catch (InterruptedException exception) {
            orderProcessor.shutdownNow();
            Thread.currentThread().interrupt();
            System.out.println("Order processing was interrupted.");
        }

        System.out.println("Completed orders: " + completedOrders.get());
    }

    private static void processOrder(String orderId) {
        String workerName = Thread.currentThread().getName();
        System.out.println(workerName + " started processing " + orderId);

        try {
            // Represents work such as payment, inventory, and shipping checks.
            Thread.sleep(1000);
            completedOrders.incrementAndGet();
            System.out.println(workerName + " completed " + orderId);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            System.out.println(workerName + " was interrupted while processing " + orderId);
        }
    }
}
