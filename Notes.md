# Java Notes

## Table of Contents

1. [Java Basics](#1-java-basics)
2. [JDK, JRE, and JVM](#2-jdk-jre-and-jvm)
3. [Variables, Data Types, and Literals](#3-variables-data-types-and-literals)
4. [Type Conversion, Casting, and Parsing](#4-type-conversion-casting-and-parsing)
5. [Operators and Conditional Statements](#5-operators-and-conditional-statements)
6. [Loops](#6-loops)
7. [Static Keyword and Methods](#7-static-keyword-and-methods)
8. [Enhanced for Loop](#8-enhanced-for-loop)

---

## 1. Java Basics

Java is a high-level, object-oriented programming language. Java source code is compiled into bytecode, which can run on any system with a compatible Java Runtime Environment (JRE).

### Main Features

- Platform independent: Java bytecode runs on different operating systems through the JVM.
- Object-oriented: Programs are organized around classes and objects.
- Robust: Java provides strong type checking and automatic memory management.
- Secure: Java runs bytecode inside a controlled runtime environment.
- Multithreaded: Java supports multiple tasks at the same time.

### How Java Works

```text
Java source code (.java)
        |
        | javac compiler
        v
Java bytecode (.class)
        |
        | JVM
        v
Machine code executed by the computer
```

Example:

```java
public class Hello {
    public static void main(String[] args) {
        System.out.println("Hello, Java!");
    }
}
```

Compile and run:

```bash
javac Hello.java
java Hello
```

### Source Code, Compiler, and Bytecode

1. Write Java source code in a .java file.
2. The javac compiler checks the code and converts it into bytecode.
3. Bytecode is stored in a .class file.
4. The JVM loads and executes the bytecode.
5. The JVM may use a JIT compiler to optimize frequently used bytecode.

---

## 2. JDK, JRE, and JVM

### JVM: Java Virtual Machine

The JVM is the engine that runs Java bytecode. It:

- Loads .class files
- Verifies bytecode
- Executes bytecode
- Manages memory
- Performs garbage collection
- Provides platform independence

The JVM is platform-specific, but Java bytecode is platform-independent.

### JRE: Java Runtime Environment

The JRE provides everything needed to run Java programs:

```text
JRE = JVM + Java class libraries + supporting files
```

The JRE does not normally include development tools such as the Java compiler.

### JDK: Java Development Kit

The JDK provides everything needed to develop and run Java programs:

```text
JDK = JRE + development tools
```

Common JDK tools include:

- javac: compiles Java source code
- java: starts a Java application
- javadoc: generates documentation
- jar: creates and manages Java archive files

### Relationship

```text
JDK
└── JRE
    └── JVM
```

---

## 3. Variables, Data Types, and Literals

Java is strongly typed. Every variable must have a type, and that type determines what values it can store.

### Primitive Data Types

| Type | Size | Range / Values | Example |
| --- | ---: | --- | --- |
| byte | 8 bits | -128 to 127 | byte age = 25; |
| short | 16 bits | -32,768 to 32,767 | short count = 1000; |
| int | 32 bits | -2^31 to 2^31 - 1 | int score = 95; |
| long | 64 bits | -2^63 to 2^63 - 1 | long population = 8_000_000_000L; |
| float | 32 bits | approx. 6-7 decimal digits | float price = 19.99F; |
| double | 64 bits | approx. 15 decimal digits | double pi = 3.14159; |
| char | 16 bits | a single UTF-16 character | char grade = 'A'; |
| boolean | JVM-dependent | true or false | boolean passed = true; |

Important notes:

- Whole-number literals default to int.
- Decimal literals default to double.
- A long literal usually needs L.
- A float literal needs F.
- char uses single quotes, while String uses double quotes.

### Reference Data Types

Reference types store a reference to an object instead of the object value itself. Examples include:

- Classes
- Objects
- Arrays
- Interfaces
- String
- Enums

```java
String name = "Alex";
int[] numbers = {10, 20, 30};
```

A reference may also be null, which means it does not refer to an object:

```java
String message = null;
```

### Literals

#### Integer Literals

```java
int decimal = 100;
int binary = 0b1100100;
int octal = 0144;
int hexadecimal = 0x64;
long largeNumber = 10000000000L;
int readableNumber = 1_000_000;
```

Underscores improve readability and do not change the value.

#### Floating-Point Literals

```java
double decimal = 12.5;
double scientific = 1.25e3;
float preciseEnough = 12.5F;
```

#### Character Literals

```java
char letter = 'J';
char newline = '\n';
char unicode = '\u0041';
```

#### String Literals

```java
String language = "Java";
String text = "A line\nAnother line";
```

String is a reference type, even though string literals are written directly in the source code.

#### Boolean Literals

```java
boolean isJavaFun = true;
boolean isFinished = false;
```

#### null Literal

```java
String value = null;
// int number = null; // Error
```

null represents the absence of an object reference and can be assigned only to reference types.

---

## 4. Type Conversion, Casting, and Parsing

### Widening Conversion

Widening conversion changes a smaller compatible type into a larger type. It is usually automatic because it does not typically lose information.

```java
int number = 50;
long largerNumber = number;
double decimalNumber = largerNumber;
```

Common order:

```text
byte -> short -> int -> long -> float -> double
```

char can also be widened to int, long, float, or double.

### Narrowing Conversion

Narrowing conversion changes a larger type into a smaller type. It must be written explicitly because data may be lost.

```java
double price = 19.99;
int wholePrice = (int) price; // 19
```

This removes the decimal part without rounding.

```java
int largeValue = 130;
byte smallValue = (byte) largeValue; // -126 due to overflow
```

Narrowing may produce overflow or loss of precision.

### Conversion During Expressions

Java applies numeric promotion when performing arithmetic:

```java
byte first = 10;
byte second = 20;
// byte result = first + second; // Error: result is promoted to int
int result = first + second;
```

When arithmetic uses byte, short, or char, Java promotes the values to int first.

### Casting

Casting tells Java to treat a value as another compatible type.

#### Primitive Casting

```java
double temperature = 36.6;
int roundedDown = (int) temperature;
```

Syntax:

```java
targetType variable = (targetType) value;
```

#### Reference Casting

```java
class Animal {
}

class Dog extends Animal {
    void bark() {
        System.out.println("Woof");
    }
}

Animal animal = new Dog(); // Upcasting: automatic
Dog dog = (Dog) animal;    // Downcasting: explicit
dog.bark();
```

- Upcasting: a child object is treated as a parent type and is usually automatic.
- Downcasting: a parent reference is converted to a child type and must be explicit.

Use instanceof before downcasting when the actual object type is uncertain:

```java
if (animal instanceof Dog) {
    Dog dog = (Dog) animal;
    dog.bark();
}
```

### Conversion vs Casting vs Parsing

| Concept | Meaning | Example |
| --- | --- | --- |
| Conversion | Changes one type to another, often automatically | long value = intValue; |
| Casting | Explicitly requests a type change | int value = (int) doubleValue; |
| Parsing | Converts text into a value | int value = Integer.parseInt("42"); |

```java
String text = "42";
int number = Integer.parseInt(text);
double decimal = Double.parseDouble("3.14");
String result = String.valueOf(number);
```

---

## 5. Operators and Conditional Statements

### Relational Operators

Relational operators compare two values and return a boolean result.

| Operator | Meaning | Example |
| --- | --- | --- |
| == | Equal to | age == 18 |
| != | Not equal to | age != 18 |
| > | Greater than | marks > 50 |
| < | Less than | marks < 50 |
| >= | Greater than or equal to | age >= 18 |
| <= | Less than or equal to | age <= 60 |

```java
int age = 20;
System.out.println(age >= 18); // true
System.out.println(age == 16); // false
```

### Logical Operators

| Operator | Meaning | Example |
| --- | --- | --- |
| && | Logical AND | age >= 18 && hasId |
| || | Logical OR | age < 13 || age > 65 |
| ! | Logical NOT | !isClosed |

```java
int age = 20;
boolean hasId = true;

if (age >= 18 && hasId) {
    System.out.println("Allowed to enter.");
}
```

```java
boolean isWeekend = true;
boolean isHoliday = false;

if (isWeekend || isHoliday) {
    System.out.println("The office is closed.");
}
```

Java uses short-circuit evaluation:

- With &&, the second condition is only checked if the first is true.
- With ||, the second condition is only checked if the first is false.

### if Statement

```java
int marks = 75;

if (marks >= 40) {
    System.out.println("Passed");
} else {
    System.out.println("Failed");
}
```

Multiple conditions can be handled with else if:

```java
if (marks >= 80) {
    System.out.println("Grade A");
} else if (marks >= 60) {
    System.out.println("Grade B");
} else {
    System.out.println("Needs improvement");
}
```

### Nested if

```java
int marks = 85;

if (marks >= 40) {
    if (marks >= 80) {
        System.out.println("Passed with distinction");
    } else {
        System.out.println("Passed");
    }
} else {
    System.out.println("Failed");
}
```

### switch Statement

```java
int day = 2;

switch (day) {
    case 1:
        System.out.println("Monday");
        break;
    case 2:
        System.out.println("Tuesday");
        break;
    default:
        System.out.println("Another day");
}
```

### Ternary Operator

```java
condition ? valueIfTrue : valueIfFalse;
```

Example:

```java
int marks = 75;
String result = marks >= 40 ? "Pass" : "Fail";
System.out.println(result);
```

Use ternary for short expressions; use if-else when the logic is longer or more complex.

---

## 6. Loops

A loop repeats a block of code while a condition is true.

### for Loop

Use a for loop when you know the start value, stopping condition, and update step.

```java
for (int number = 1; number <= 5; number++) {
    System.out.println(number);
}
```

### while Loop

Use a while loop when the number of repetitions is not known in advance.

```java
int number = 1;
while (number <= 5) {
    System.out.println(number);
    number++;
}
```

The loop variable must be updated inside the loop when it controls the condition.

### do-while Loop

Use a do-while loop when the body must run at least once.

```java
int choice;
do {
    System.out.println("Show the menu");
    choice = 0;
} while (choice != 0);
```

### Loop Control Statements

- break exits the loop immediately.
- continue skips the rest of the current iteration and continues with the next one.

### Choosing a Loop

| Loop | Condition checked | Best used when |
| --- | --- | --- |
| for | Before each repetition | The number of repetitions is known |
| while | Before each repetition | The loop may run zero or more times |
| do-while | After each repetition | The loop must run at least once |

---

## 7. Static Keyword and Methods

The static keyword means a member belongs to the class itself rather than to an individual object.

### Static Variables

```java
class Student {
    static int studentCount = 0;

    Student() {
        studentCount++;
    }
}

Student first = new Student();
Student second = new Student();

System.out.println(Student.studentCount); // 2
```

A static variable is shared across all objects of the class.

### Static Constants

```java
class MathValues {
    static final double PI = 3.14159;
    static final int DAYS_IN_WEEK = 7;
}

System.out.println(MathValues.PI);
```

final prevents reassignment after initialization.

### Static Methods

```java
class Calculator {
    static int add(int first, int second) {
        return first + second;
    }
}

int total = Calculator.add(10, 20);
System.out.println(total); // 30
```

Static methods can access static data directly, but not instance data without an object reference.

```java
class Example {
    int instanceNumber = 10;
    static int sharedNumber = 20;

    static void showValues() {
        System.out.println(sharedNumber); // valid
        // System.out.println(instanceNumber); // Error
    }
}
```

### Non-Static Methods

A non-static method is also called an instance method and works with a specific object.

```java
class Counter {
    static int totalCounters = 0;
    int value = 0;

    static void showTotalCounters() {
        System.out.println(totalCounters);
    }

    void increaseValue() {
        value++;
    }
}
```

### void Methods

void means the method does not return a value.

```java
class Printer {
    void printMessage() {
        System.out.println("Hello");
    }
}
```

A void method may use return; to exit early, but it cannot return a value.

### main Method

```java
public class Greeting {
    public static void main(String[] args) {
        if (args.length > 0) {
            System.out.println("Hello, " + args[0]);
        } else {
            System.out.println("Hello, Java");
        }
    }
}
```

The JVM starts a Java application by calling the main method.

### Static Blocks

```java
class Configuration {
    static String environment;

    static {
        environment = "development";
    }
}
```

A static block runs once when the class is loaded.

### Static Nested Classes

```java
class Outer {
    static class Helper {
        static void show() {
            System.out.println("Helper method");
        }
    }
}

Outer.Helper.show();
```

### Static vs Instance Members

| Member | Belongs to | Accessed with | Copies |
| --- | --- | --- | --- |
| Static variable | Class | ClassName.variable | One shared copy |
| Static method | Class | ClassName.method() | One class-level method |
| Instance variable | Object | object.variable | One copy per object |
| Instance method | Object | object.method() | Uses an object's state |

---

## 8. Enhanced for Loop

The enhanced for loop, also called the for-each loop, visits each element of an array or collection in order.

### Syntax

```java
for (Type variable : arrayOrCollection) {
    // use variable
}
```

Example:

```java
int[] numbers = {10, 20, 30, 40};

for (int number : numbers) {
    System.out.println(number);
}
```

Output:

```text
10
20
30
40
```

### When to Use It

Use the enhanced for loop when:

- Every element should be visited
- The index is not needed
- The array or collection should be read clearly and simply

Example with strings:

```java
String[] names = {"Ana", "Ben", "Chris"};

for (String name : names) {
    System.out.println("Hello, " + name);
}
```

### Compared with Traditional for Loop

Use a regular for loop when the index is needed:

```java
int[] numbers = {10, 20, 30};

for (int index = 0; index < numbers.length; index++) {
    System.out.println("Index " + index + ": " + numbers[index]);
}
```

The enhanced version is shorter, but it does not provide the array index directly.

### Updating Elements

Changing the loop variable does not update the original array value:

```java
int[] numbers = {1, 2, 3};

for (int number : numbers) {
    number *= 2;
}

// numbers is still {1, 2, 3}
```

To update actual array elements, use indexes:

```java
for (int index = 0; index < numbers.length; index++) {
    numbers[index] *= 2;
}
```

---

## Final Quick Revision

- Java is platform-independent because bytecode runs on the JVM.
- JDK includes development tools, JRE includes the runtime environment, and JVM executes bytecode.
- Primitive types store simple values; reference types store references to objects.
- Widening conversion is automatic, while narrowing needs explicit casting.
- Operators help compare and decide logic.
- Loops reduce repetitive code.
- Static members belong to the class; instance members belong to each object.
- The main method is the entry point of a Java application.
- Enhanced for loops are best for reading through arrays and collections.
