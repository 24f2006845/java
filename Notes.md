# Java Notes

## Enhanced `for` Loop

The enhanced `for` loop, also called the `for-each` loop, visits each element
of an array or collection in order.

### Syntax

```java
for (Type variable : arrayOrCollection) {
	// use variable
}
```

The loop assigns one element at a time to the loop variable:

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

Use an enhanced `for` loop when:

- Every element should be visited.
- The index is not needed.
- The array or collection should be read clearly and simply.

It also works with other types:

```java
String[] names = {"Ana", "Ben", "Chris"};

for (String name : names) {
	System.out.println("Hello, " + name);
}
```

### Compared with a Traditional `for` Loop

Use a traditional `for` loop when the index is needed:

```java
int[] numbers = {10, 20, 30};

for (int index = 0; index < numbers.length; index++) {
	System.out.println("Index " + index + ": " + numbers[index]);
}
```

The enhanced version is shorter, but it does not directly provide the index:

```java
for (int number : numbers) {
	System.out.println(number);
}
```

### Updating Elements

Changing the loop variable does not update a primitive array:

```java
int[] numbers = {1, 2, 3};

for (int number : numbers) {
	number *= 2;
}

// numbers is still {1, 2, 3}
```

To update array elements, use their indexes:

```java
for (int index = 0; index < numbers.length; index++) {
	numbers[index] *= 2;
}
```

The enhanced `for` loop is also called a `for-each` loop because it performs an
operation for each element.

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

## 7. Loops

A loop repeats a block of code while a condition is true. Loops reduce repeated code and are useful for processing numbers, arrays, and other collections.

### 7.1 `for` Loop

Use a `for` loop when you know the starting value, stopping condition, and update step. Its syntax is:

```java
for (initialization; condition; update) {
	// Code to repeat
}
```

The initialization runs once. Before every repetition, Java checks the condition. After the body runs, Java performs the update.

```java
for (int number = 1; number <= 5; number++) {
	System.out.println(number);
}
```

This prints the numbers from 1 through 5. The loop stops when `number <= 5` becomes false.

### 7.2 `while` Loop

Use a `while` loop when the number of repetitions may not be known in advance. The condition is checked before the body, so the body may run zero times.

```java
int number = 1;
while (number <= 5) {
	System.out.println(number);
	number++;
}
```

The loop variable must be updated inside the loop when it controls the condition. Otherwise, the loop may never end.

### 7.3 `do-while` Loop

Use a `do-while` loop when the body must run at least once. The condition is checked after the body.

```java
int choice;
do {
	System.out.println("Show the menu");
	choice = 0; // Replace this with input in a real program.
} while (choice != 0);
```

Even if the condition is false after the first run, the body has already executed once. The `do-while` statement ends with a semicolon.

### 7.4 Choosing a Loop

| Loop | Condition checked | Best used when |
|---|---|---|
| `for` | Before each repetition | The number of repetitions is known |
| `while` | Before each repetition | The loop may run zero or more times |
| `do-while` | After each repetition | The loop must run at least once |

### 7.5 Loop Control Statements

- `break` immediately exits the loop.
- `continue` skips the rest of the current repetition and starts the next one.

Use these statements carefully so the loop remains easy to understand.

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

## 11. `static` Keyword and `void` Methods

The `static` keyword means that a member belongs to the class itself instead of
belonging to each individual object. A static member can be used through the
class name, without creating an object.

### 11.1 Static Variables

A static variable has one shared copy for the whole class. Every object of that
class sees the same value.

```java
class Student {
	// One shared counter for all Student objects.
	static int studentCount = 0;

	Student() {
		studentCount++;
	}
}

Student first = new Student();
Student second = new Student();

System.out.println(Student.studentCount); // 2
```

Use a static variable for class-level data that should be shared. A normal
instance variable gets a separate copy inside every object:

```java
class Account {
	static String bankName = "ABC Bank"; // shared by all accounts
	double balance;                     // different for each account
}
```

### 11.2 Static Constants

Constants are commonly declared with both `static` and `final`:

```java
class MathValues {
	static final double PI = 3.14159;
	static final int DAYS_IN_WEEK = 7;
}

System.out.println(MathValues.PI);
```

`final` prevents the variable from being assigned a new value after it is
initialized. Constant names are conventionally written in uppercase with
underscores.

### 11.3 Static Methods

A static method belongs to the class and can be called using the class name.
It does not require an object:

```java
class Calculator {
	static int add(int first, int second) {
		return first + second;
	}
}

int total = Calculator.add(10, 20);
System.out.println(total); // 30
```

Static methods can directly access static variables and call other static
methods. They cannot directly access instance variables or instance methods,
because no particular object is selected:

```java
class Example {
	int instanceNumber = 10;
	static int sharedNumber = 20;

	static void showValues() {
		System.out.println(sharedNumber); // valid
		// System.out.println(instanceNumber); // Error: no object selected
	}

	void printInstanceNumber() {
		System.out.println(instanceNumber); // valid in an instance method
	}
}
```

An object reference is needed when a static method must use instance data:

```java
class Person {
	String name;

	Person(String name) {
		this.name = name;
	}

	static void printName(Person person) {
		System.out.println(person.name); // use the supplied object
	}
}

Person person = new Person("Ana");
Person.printName(person); // Ana
```

### 11.3.1 Static Method vs Non-Static Method

A non-static method is also called an instance method. The main difference is
which object the method belongs to and whether an object is required to call it.

```java
class Counter {
	static int totalCounters = 0; // shared by the class
	int value = 0;                 // separate for each object

	static void showTotalCounters() {
		System.out.println(totalCounters); // static method uses static data
	}

	void increaseValue() {
		value++;                           // instance method uses object data
	}
}

Counter first = new Counter();
Counter second = new Counter();
Counter.totalCounters = 2;

Counter.showTotalCounters(); // call static method with the class name
first.increaseValue();       // call non-static method with an object
second.increaseValue();
```

| Feature | Static method | Non-static method |
| --- | --- | --- |
| Belongs to | The class | An object, or instance |
| Called with | `ClassName.method()` | `object.method()` |
| Needs an object? | No | Yes |
| Directly accesses static members? | Yes | Yes |
| Directly accesses instance members? | No | Yes |
| Can use `this` or `super`? | No | Yes |
| State used | Shared class-level state | The selected object's state |
| Typical use | Utility operations and shared behavior | Behavior depending on object data |

A static method can still work with an object if that object is passed as a
parameter. It cannot use instance data automatically because it has no implicit
`this` object:

```java
class Message {
	String text;

	Message(String text) {
		this.text = text;
	}

	// Static method: the object must be supplied explicitly.
	static void printMessage(Message message) {
		System.out.println(message.text);
	}

	// Non-static method: this.text refers to the current object.
	void printOwnMessage() {
		System.out.println(this.text);
	}
}

Message message = new Message("Hello");
Message.printMessage(message); // static call with an argument
message.printOwnMessage();     // instance call on the object
```

Use a static method when the operation does not depend on one object's state,
such as `Math.max(10, 20)`. Use a non-static method when the operation needs
the fields of a particular object, such as `message.printOwnMessage()`.

### 11.4 `void` Methods

`void` is a return type that means a method does not return a value. It can
perform an action, such as printing or changing an object.

```java
class Printer {
	void printMessage() {
		System.out.println("Hello");
		// No return value is needed.
	}

	static void printLine(String text) {
		System.out.println(text);
	}
}

Printer printer = new Printer();
printer.printMessage();
Printer.printLine("Java");
```

A `void` method can use `return;` to stop early, but it cannot return a value:

```java
static void printIfPositive(int number) {
	if (number <= 0) {
		return; // exit the method early
	}

	System.out.println(number);
}
```

Do not write `return number;` inside a `void` method. A method that returns a
value must declare that value's type:

```java
static int square(int number) {
	return number * number;
}
```

### 11.5 The Static `main` Method

The JVM starts a traditional Java application by calling this method:

```java
public static void main(String[] args) {
	System.out.println("Program started");
}
```

Each keyword has a purpose:

| Part | Meaning |
| --- | --- |
| `public` | The JVM can access the method from outside the class |
| `static` | The JVM can call it without creating an object |
| `void` | The method does not return a value to the JVM |
| `main` | The conventional application entry-point name |
| `String[] args` | Command-line arguments passed to the program |

Example using command-line arguments:

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

Run it with:

```bash
javac Greeting.java
java Greeting Ana
```

Output:

```text
Hello, Ana
```

### 11.6 Static Blocks

A static block runs once when the class is loaded, before `main` or other
static members are used. It is useful for class-level initialization:

```java
class Configuration {
	static String environment;

	static {
		// This block runs once when Configuration is loaded.
		environment = "development";
	}
}

System.out.println(Configuration.environment); // development
```

### 11.7 Static Nested Classes

A class declared inside another class can also be static. A static nested class
does not need an object of the outer class:

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

### 11.8 Important Rules and Common Mistakes

- Access static members with the class name, such as `Math.max(4, 8)`.
- Avoid using an object reference to access a static member; it can confuse
	readers because the member belongs to the class.
- A static method cannot use `this` or `super`, because they refer to an
	instance and a static method may run without one.
- Static methods cannot directly access instance fields or call instance
	methods.
- Static data is shared, so changing it through one object affects the whole
	class.
- Use instance members when each object needs its own state.
- Use `void` when a method performs an action and has no result to give back.
- Use a value-returning type when the caller needs a result.

### Static vs Instance Members

| Member | Belongs to | Accessed with | Copies |
| --- | --- | --- | --- |
| Static variable | The class | `ClassName.variable` | One shared copy |
| Static method | The class | `ClassName.method()` | One class-level method |
| Instance variable | An object | `object.variable` | One copy per object |
| Instance method | An object | `object.method()` | Uses a specific object's state |
