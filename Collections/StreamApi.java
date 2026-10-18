package Collections;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamApi {

    public static void main(String[] args) {
	/*
	 * A store receives orders throughout the day. The business wants to
	 * find completed orders, calculate their revenue, sort them, and
	 * group the revenue by customer.
	 */
	List<Order> orders = List.of(
		new Order("O-1001", "Asha", 120.00, "COMPLETED"),
		new Order("O-1002", "Ravi", 75.50, "PENDING"),
		new Order("O-1003", "Asha", 250.00, "COMPLETED"),
		new Order("O-1004", "Mina", 90.00, "CANCELLED"),
		new Order("O-1005", "Ravi", 180.00, "COMPLETED"));

	/*
	 * filter() keeps only orders that match a condition.
	 * sorted() orders the remaining elements.
	 * map() transforms each Order into the text we want to display.
	 * collect() is a terminal operation: it starts the pipeline and
	 * gathers the final values into a List.
	 */
	List<String> completedOrderSummary = orders.stream()
		.filter(order -> order.status().equals("COMPLETED"))
		.sorted(Comparator.comparingDouble(Order::amount).reversed())
		.map(order -> order.id() + " - " + order.customer()
			+ " - $" + order.amount())
		.collect(Collectors.toList());

	System.out.println("Completed orders by highest amount:");
	/*
	 * forEach() accepts a Consumer<T>. The lambda below receives one summary
	 * at a time and prints it. Because this is a sequential stream/list
	 * traversal, the summaries are printed in their list order.
	 */
	completedOrderSummary.forEach(summary -> System.out.println(summary));

	/*
	 * forEachOrdered() is useful when a parallel stream is used but the output
	 * must still follow the encounter order. Ordinary parallel forEach() does
	 * not promise that order.
	 */
	System.out.println("\nOrder IDs in their original order:");
	orders.parallelStream()
		.map(Order::id)
		.forEachOrdered(orderId -> System.out.println(orderId));

	/*
	 * groupingBy() creates a Map where each customer is a key. The
	 * downstream summingDouble() operation adds the completed order
	 * amounts for that customer.
	 */
	Map<String, Double> completedRevenueByCustomer = orders.stream()
		.filter(order -> order.status().equals("COMPLETED"))
		.collect(Collectors.groupingBy(
			Order::customer,
			Collectors.summingDouble(Order::amount)));

	System.out.println("\nCompleted revenue by customer:");
	completedRevenueByCustomer.forEach(
		(customer, revenue) -> System.out.println(customer + " - $" + revenue));
    }

    /** An immutable order represented with a compact record. */
    private record Order(String id, String customer, double amount, String status) {
    }
}
