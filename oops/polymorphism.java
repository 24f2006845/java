class Calculator {
    int add(int a, int b) {
        return a + b;
    }

    int add(int a, int b, int c) {
        return a + b + c;
    }
}

class Animal {
    void sound() {
        System.out.println("Animal makes a sound");
    }
}

class Dog extends Animal {
    @Override
    void sound() {
        System.out.println("Dog barks");
    }
}

class FinalKeywordNotes {
    // final variable: value cannot be changed after initialization
    final int MAX_SCORE = 100;

    // final method: cannot be overridden in subclasses
    final void showMessage() {
        System.out.println("This method is final and cannot be overridden.");
    }
}

// final class: cannot be inherited
final class FinalClass {
    int value = 50;
}

public class polymorphism {
    public static void main(String[] args) {
        // Compile-time polymorphism: method overloading
        Calculator calculator = new Calculator();
        System.out.println(calculator.add(2, 3));
        System.out.println(calculator.add(2, 3, 4));

        // Runtime polymorphism: method overriding
        Animal animal = new Dog();
        animal.sound();
    }
}