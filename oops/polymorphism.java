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

class ParentWithFinalMethod {
    // final method: a subclass cannot override it
    final void finalMethod() {
        System.out.println("This is a final method.");
    }
}

class ChildWithFinalMethod extends ParentWithFinalMethod {
    // Uncommenting the method below will cause a compile error:
    // void finalMethod() {
    //     System.out.println("This is not allowed");
    // }
}

// final class: cannot be inherited by any subclass
final class FinalClassExample {
    void display() {
        System.out.println("This is a final class.");
    }
}

// class AnotherClass extends FinalClassExample { }
// This will cause a compile error because FinalClassExample is final

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