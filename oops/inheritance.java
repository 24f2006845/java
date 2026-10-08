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
class ScientificCalculator extends Calculator {
    public double sin(double a) {
        return Math.sin(a);
    }
    public double cos(double a) {
        return Math.cos(a);
    }
    public double tan(double a) {
        return Math.tan(a);
    }
}
public class inheritance {
    public static void main(String[] args) {
        ScientificCalculator sc = new ScientificCalculator();
        System.out.println("Addition: " + sc.add(5, 10));
        System.out.println("Subtraction: " + sc.subtract(10, 5));
        System.out.println("Multiplication: " + sc.multiply(5, 10));
        System.out.println("Division: " + sc.divide(10, 2));
        System.out.println("Sine: " + sc.sin(Math.PI / 2));
        System.out.println("Cosine: " + sc.cos(Math.PI));
        System.out.println("Tangent: " + sc.tan(Math.PI / 4));
    }
}   
