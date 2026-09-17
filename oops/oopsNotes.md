# OOPs Concepts Basics

Object-Oriented Programming (OOP) is a programming style where code is organized around objects and classes.

Java is an object-oriented language, so everything is based on classes and objects.

## 1. Class
A class is a blueprint or template.
It defines:
- what data an object has
- what actions an object can do

Example:
```java
class Calculator {
    int a;
    int b;

    public int add(int x, int y) {
        return x + y;
    }
}
```

Here, `Calculator` is a class.

## 2. Object
An object is an instance of a class.
It is created from the class blueprint.

Example:
```java
Calculator calc = new Calculator();
```

This means:
- `Calculator` = class name
- `calc` = object reference variable
- `new Calculator()` = creates a new object in memory

## 3. How Java understands a class
Java reads a class as a template and creates objects from it.

When we write:
```java
Calculator calc = new Calculator();
```

Java does this internally:
1. Looks for the class named `Calculator`
2. Allocates memory for a new object
3. Calls the constructor of that class
4. Stores the object in variable `calc`

So, `calc` is not the class itself; it is an object created from the class.

## 4. How methods are called inside a class
Methods belong to a class, and objects call those methods.

Example:
```java
class Calculator {
    public int add(int a, int b) {
        return a + b;
    }
}

class Demo {
    public static void main(String[] args) {
        Calculator calc = new Calculator();
        int result = calc.add(5, 3);
        System.out.println(result); // 8
    }
}
```

Here:
- `calc.add(5, 3)` means: call the `add` method on the object `calc`
- `calc` is the object
- `add` is the method
- `5, 3` are arguments passed to the method

## 5. Class inside class
In Java, a class can contain:
- fields
- methods
- constructors
- nested classes

But usually, we do not define one class inside another in normal programs.
We create an object of one class inside another class, like this:

```java
class Calculator {
    public int add(int a, int b) {
        return a + b;
    }
}

class Demo {
    public static void main(String[] args) {
        Calculator calc = new Calculator();
        System.out.println(calc.add(5, 3));
    }
}
```

This is the normal OOP approach:
- one class defines behavior
- another class uses that behavior by creating an object

## 6. Real-world analogy
Think of a class like a blueprint for a car.

```java
class Car {
    void drive() {
        System.out.println("Car is moving");
    }
}
```

Then:
```java
Car myCar = new Car();
myCar.drive();
```

`Car` is the blueprint.
`myCar` is the actual object.
`drive()` is the action performed by the object.

## 7. Why OOP is useful
OOP helps us:
- reuse code
- organize logic
- make programs easier to understand
- create real-world models in software

## 8. Summary
- Class = blueprint
- Object = real instance of class
- `new` keyword = creates an object
- Object calls methods using dot operator `.`
- Example: `calc.add(5, 3)`

## 9. Example with calculator
```java
class Calculator {
    public int add(int a, int b) {
        return a + b;
    }

    public static void main(String[] args) {
        Calculator calc = new Calculator();
        int result = calc.add(10, 5);
        System.out.println("Result: " + result);
    }
}
```

Output:
```java
Result: 15
```

## 10. Very important rule
When we call a method of an object, we use:
```java
objectName.methodName();
```

Example:
```java
calc.add(5, 3);
```

This means: use the object `calc` and call its `add` method.

## 11. Short key points
- Class defines behavior
- Object stores actual data
- We create object with `new`
- We call methods using the dot operator `.`
- Java creates memory for object automatically when `new` is used

This is the basic idea of OOP in Java.
