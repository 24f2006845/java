
class Calculator {
    public int add(int a, int b) {
        return a + b;
    }
    public int subtract(int a, int b) {
        return a - b;
    }
    public int multiply(int a, int b) {
        return a * b;
    }
    public int divide(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("Cannot divide by zero");
        }
        return a / b;
    }

}

class Computer {
    public void performCalculations() {
        Calculator calculator = new Calculator();
        int sum = calculator.add(10, 5);
        int difference = calculator.subtract(10, 5);
        int product = calculator.multiply(10, 5);
        int quotient = calculator.divide(10, 2);

        System.out.println("Sum: " + sum);
        System.out.println("Difference: " + difference);
        System.out.println("Product: " + product);
        System.out.println("Quotient: " + quotient);
    }
}


public class demo {
    public static void main(String[] args) {
        Computer computer = new Computer();
        computer.performCalculations();
    }
}
