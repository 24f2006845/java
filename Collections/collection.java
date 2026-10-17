package Collections;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Real-world example: an online store managing products and customer orders.
 *
 * This example demonstrates the most commonly used parts of the Java
 * Collection API:
 * - List: keeps products in insertion order and allows duplicates.
 * - Set: stores unique product categories.
 * - Map: connects each product ID to its product.
 * - Queue: processes orders in first-in, first-out (FIFO) order.
 */
public class collection {

    public static void main(String[] args) {
        /*
         * ArrayList is a List implementation that stores items in order.
         * It allows duplicate values and provides fast access by index.
         * In this example, it represents the store's product catalog.
         */
        List<Product> products = new ArrayList<>();
        products.add(new Product("P101", "Laptop", "Electronics", 850.00));
        products.add(new Product("P102", "Headphones", "Electronics", 75.00));
        products.add(new Product("P103", "Office Chair", "Furniture", 180.00));

        // The products are displayed in the same order in which they were added.
        System.out.println("Products in catalog:");
        products.forEach(System.out::println);

        /*
         * HashSet stores only unique values and does not guarantee iteration
         * order. The repeated "Electronics" category will appear only once.
         */
        Set<String> categories = new HashSet<>();
        for (Product product : products) {
            categories.add(product.category());
        }
        System.out.println("\nUnique categories: " + categories);

        /*
         * HashMap stores data as key-value pairs. The product ID is the key
         * and the Product object is the value. This allows quick product
         * lookup instead of searching through the complete product list.
         */
        Map<String, Product> productById = new HashMap<>();
        for (Product product : products) {
            productById.put(product.id(), product);
        }
        System.out.println("\nProduct P102: " + productById.get("P102"));

        /*
         * Comparator.comparingDouble creates a sorting rule based on price.
         * The original list is sorted from the lowest price to the highest.
         */
        products.sort(Comparator.comparingDouble(Product::price));
        System.out.println("\nProducts sorted by price:");
        products.forEach(System.out::println);

        /*
         * ArrayDeque is an efficient Queue implementation. A queue follows
         * FIFO (First In, First Out), so the earliest order is processed first.
         * offer() adds an order, and poll() removes the next order.
         */
        Queue<String> pendingOrders = new ArrayDeque<>();
        pendingOrders.offer("Order-1001");
        pendingOrders.offer("Order-1002");
        pendingOrders.offer("Order-1003");

        System.out.println("\nProcessing orders:");
        while (!pendingOrders.isEmpty()) {
            // poll() returns and removes the next order from the queue.
            System.out.println("Processed " + pendingOrders.poll());
        }
    }

    /**
     * A record is a compact immutable data class.
     *
     * Java automatically provides a constructor, accessor methods such as
     * id() and price(), equals(), hashCode(), and toString() for this record.
     */
    private record Product(String id, String name, String category, double price) {
    }
}
