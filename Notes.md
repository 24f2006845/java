# Java Notes

## 1. What Is Java?

Java is a high-level, object-oriented programming language. Java programs are compiled into **bytecode**, which can run on any system that has a compatible Java Runtime Environment (JRE).

### Main Features

- **Platform independent:** Java bytecode runs on different operating systems through the JVM.
- **Object-oriented:** Programs are organized around classes and objects.
- **Robust:** Java provides strong type checking and automatic memory management.
- **Secure:** Java runs bytecode inside a controlled runtime environment.
- **Multithreaded:** Java supports running multiple tasks at the same time.

## 2. How Java Works

The basic process is:

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

Compile and run it with:

```bash
javac Hello.java
java Hello
```

### Source Code, Compiler, and Bytecode

1. We write Java source code in a `.java` file.
2. The `javac` compiler checks the code and converts it into bytecode.
3. Bytecode is stored in a `.class` file.
4. The JVM loads and executes the bytecode.
5. The JVM may use a **JIT compiler** (Just-In-Time compiler) to convert frequently used bytecode into native machine code for better performance.

## 3. JDK, JRE, and JVM

### JVM: Java Virtual Machine

The JVM is the engine that runs Java bytecode. It:

- Loads `.class` files.
- Verifies bytecode.
- Executes bytecode.
- Manages memory.
- Performs garbage collection.
- Provides platform independence.

The JVM is platform-specific, but Java bytecode is platform-independent.

### JRE: Java Runtime Environment

The JRE provides everything needed to **run** Java programs:

```text
JRE = JVM + Java class libraries + supporting files
```

The JRE does not normally include development tools such as the Java compiler.

### JDK: Java Development Kit

The JDK provides everything needed to **develop and run** Java programs:

```text
JDK = JRE + development tools
```

Common JDK tools include:

- `javac`: Compiles Java source code.
- `java`: Starts a Java application.
- `javadoc`: Generates documentation.
- `jar`: Creates and manages Java archive files.

### Relationship

```text
JDK
└── JRE
	└── JVM
```

## 4. Java Data Types

Java is a **strongly typed** language. Every variable must have a type, and the type determines what values it can store.

Java data types are divided into:

1. Primitive data types
2. Reference data types

### 4.1 Primitive Data Types

Primitive types store simple values directly.

| Type | Size | Values / Range | Example |
|---|---:|---|---|
| `byte` | 8 bits | -128 to 127 | `byte age = 25;` |
| `short` | 16 bits | -32,768 to 32,767 | `short count = 1000;` |
| `int` | 32 bits | -2^31 to 2^31 - 1 | `int score = 95;` |
| `long` | 64 bits | -2^63 to 2^63 - 1 | `long population = 8_000_000_000L;` |
| `float` | 32 bits | Approximately 6-7 decimal digits | `float price = 19.99F;` |
| `double` | 64 bits | Approximately 15 decimal digits | `double pi = 3.14159;` |
| `char` | 16 bits | A single UTF-16 character | `char grade = 'A';` |
| `boolean` | JVM-dependent | `true` or `false` | `boolean passed = true;` |

Notes:

- The default type for whole-number literals is `int`.
- The default type for decimal literals is `double`.
- A `long` literal usually needs `L`.
- A `float` literal needs `F` because decimal literals are `double` by default.
- `char` uses single quotes, while `String` uses double quotes.

### 4.2 Reference Data Types

Reference types store a reference to an object rather than the object value itself. Examples include:

- Classes
- Objects
- Arrays
- Interfaces
- `String`
- Enums

```java
String name = "Alex";
int[] numbers = {10, 20, 30};
```

A reference can also be `null`, which means it does not refer to an object:

```java
String message = null;
```

## 5. Literals

A literal is a fixed value written directly in Java source code.

### Integer Literals

```java
int decimal = 100;
int binary = 0b1100100;
int octal = 0144;
int hexadecimal = 0x64;
long largeNumber = 10000000000L;
int readableNumber = 1_000_000;
```

Underscores improve readability and do not change the value. They cannot be placed at the beginning or end of a number.

### Floating-Point Literals

```java
double decimal = 12.5;
double scientific = 1.25e3;
float preciseEnough = 12.5F;
```

### Character Literals

```java
char letter = 'J';
char newline = '\n';
char unicode = '\u0041';
```

### String Literals

```java
String language = "Java";
String text = "A line\nAnother line";
```

`String` is a reference type, even though string literals are written directly in the source code.

### Boolean Literals

```java
boolean isJavaFun = true;
boolean isFinished = false;
```

### The `null` Literal

`null` represents the absence of an object reference. It can be assigned to reference types, but not to primitive types.

```java
String value = null;
// int number = null; // Error
```

## 6. Type Conversion

Type conversion means changing a value from one data type to another.

### 6.1 Widening Conversion

Widening conversion changes a smaller compatible type into a larger type. It is usually automatic because it does not normally lose information.

```java
int number = 50;
long largerNumber = number;
double decimalNumber = largerNumber;
```

Common widening order:

```text
byte -> short -> int -> long -> float -> double
```

`char` can be widened to `int`, `long`, `float`, or `double`.

### 6.2 Narrowing Conversion

Narrowing conversion changes a larger type into a smaller type. It must be written explicitly because data may be lost.

```java
double price = 19.99;
int wholePrice = (int) price;  // 19
```

The decimal part is removed; the value is not rounded.

```java
int largeValue = 130;
byte smallValue = (byte) largeValue;  // -126 due to overflow
```

Narrowing can produce overflow or loss of precision.

### 6.3 Conversion During Expressions

Java applies numeric promotion when performing arithmetic:

```java
byte first = 10;
byte second = 20;
// byte result = first + second; // Error: result is promoted to int
int result = first + second;
```

For arithmetic involving `byte`, `short`, or `char`, Java usually promotes the operands to `int`.

## 7. Casting

Casting is explicitly telling Java to treat a value as another compatible type.

### Primitive Casting

```java
double temperature = 36.6;
int roundedDown = (int) temperature;
```

The syntax is:

```java
targetType variable = (targetType) value;
```

### Reference Casting

Reference casting is used with related classes in an inheritance hierarchy.

```java
class Animal {
}

class Dog extends Animal {
	void bark() {
		System.out.println("Woof");
	}
}

Animal animal = new Dog();       // Upcasting: automatic
Dog dog = (Dog) animal;          // Downcasting: explicit
dog.bark();
```

- **Upcasting:** A child object is treated as a parent type. It is usually automatic and safe.
- **Downcasting:** A parent reference is converted to a child type. It must be explicit and can fail.

Use `instanceof` before downcasting when the actual object type is uncertain:

```java
if (animal instanceof Dog) {
	Dog dog = (Dog) animal;
	dog.bark();
}
```

An invalid reference cast can throw `ClassCastException` at runtime.

## 8. Conversion vs Casting

| Concept | Meaning | Example |
|---|---|---|
| Conversion | Changing a value from one type to another, often automatically | `long value = intValue;` |
| Casting | Explicitly requesting a type change | `int value = (int) doubleValue;` |
| Parsing | Converting text into a value | `int value = Integer.parseInt("42");` |

Parsing is different from casting because a `String` is not directly cast to a primitive:

```java
String text = "42";
int number = Integer.parseInt(text);
double decimal = Double.parseDouble("3.14");
```

To convert a value into text:

```java
String result = String.valueOf(number);
```

## 9. Quick Summary

- The **JDK** is used to develop and run Java applications.
- The **JRE** provides the environment required to run Java applications.
- The **JVM** executes Java bytecode.
- Primitive types store simple values; reference types refer to objects.
- Whole-number literals are `int` by default.
- Decimal literals are `double` by default.
- Widening conversion is usually automatic.
- Narrowing conversion requires explicit casting and may lose data.
- Casting changes how Java treats a compatible value or reference.
- Parsing converts text, such as `"42"`, into a numeric value.

## 10. Operators and Conditional Statements

Operators perform operations on values. Conditional statements allow a program to choose which code to execute.

### 10.1 Relational Operators

Relational operators compare two values and return a `boolean` result:

| Operator | Meaning | Example |
|---|---|---|
| `==` | Equal to | `age == 18` |
| `!=` | Not equal to | `age != 18` |
| `>` | Greater than | `marks > 50` |
| `<` | Less than | `marks < 50` |
| `>=` | Greater than or equal to | `age >= 18` |
| `<=` | Less than or equal to | `age <= 60` |

```java
int age = 20;
System.out.println(age >= 18);  // true
System.out.println(age == 16);  // false
```

### 10.2 Logical Operators

Logical operators combine or reverse boolean conditions:

| Operator | Meaning | Example |
|---|---|---|
| `&&` | Logical AND; both conditions must be true | `age >= 18 && hasId` |
| `||` | Logical OR; at least one condition must be true | `age < 13 || age > 65` |
| `!` | Logical NOT; reverses a boolean result | `!isClosed` |

```java
int age = 20;
boolean hasId = true;

if (age >= 18 && hasId) {
	System.out.println("Allowed to enter.");
}
```

The OR operator `||` returns `true` when at least one condition is true. It returns `false` only when both conditions are false.

```java
boolean isWeekend = true;
boolean isHoliday = false;

if (isWeekend || isHoliday) {
	System.out.println("The office is closed.");
}
```

This example prints `The office is closed.` because `isWeekend` is true.

Java uses short-circuit evaluation. With `&&`, the second condition is checked only if the first is true. With `||`, the second condition is checked only if the first is false.

### 10.3 `if` Statement

An `if` statement runs a block only when its condition is true.

```java
int marks = 75;

if (marks >= 40) {
	System.out.println("Passed");
} else {
	System.out.println("Failed");
}
```

For multiple conditions, use `else if`:

```java
if (marks >= 80) {
	System.out.println("Grade A");
} else if (marks >= 60) {
	System.out.println("Grade B");
} else {
	System.out.println("Needs improvement");
}
```

### `if - else if - else`

Use an `if - else if - else` chain when there are several possible conditions. Java checks the conditions from top to bottom and runs only the first matching block.

```java
int marks = 85;

if (marks >= 80) {
	System.out.println("Grade: A");
} else if (marks >= 60) {
	System.out.println("Grade: B");
} else if (marks >= 40) {
	System.out.println("Grade: C");
} else {
	System.out.println("Grade: F");
}
```

Because `85 >= 80` is true, this example prints `Grade: A` and skips the remaining conditions.

### 10.4 Nested `if`

A nested `if` is an `if` statement inside another `if` statement.

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

### 10.5 `switch` Statement

Use `switch` when one expression is compared with several fixed values.

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

The `break` statement stops execution from continuing into the next case. The `default` block runs when no case matches.

### 10.6 Ternary Operator

The ternary operator is a short form of a simple `if-else` statement:

```java
condition ? valueIfTrue : valueIfFalse;
```

Example:

```java
int marks = 75;
String result = marks >= 40 ? "Pass" : "Fail";
System.out.println(result);
```

Use the ternary operator for short expressions. Use `if-else` when the logic has multiple statements or is harder to read.
