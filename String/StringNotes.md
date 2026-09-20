# Java Strings

A `String` is a sequence of characters used to store text such as names,
messages, and sentences. In Java, `String` is a class from `java.lang`, so it
is available without an import.

```java
String name = "Ana";
String message = "Hello, Java!";
```

## Creating Strings

The most common way to create a string is with a string literal. Java stores
string literals in the String pool so equal literals can share memory.

```java
String first = "Hello";
String second = new String("Hello");
```

Both variables contain the text `Hello`, but `new String(...)` explicitly
creates a new String object. Usually, use the literal form because it is
simpler and lets Java reuse pooled strings.

A string can also be created from a character array:

```java
char[] letters = {'J', 'a', 'v', 'a'};
String word = new String(letters); // Java
```

## Strings Are Immutable

Strings are immutable, which means their contents cannot be changed after the
object is created. Methods that appear to change a string actually return a
new string.

```java
String text = "hello";
text.toUpperCase();             // does not change text
System.out.println(text);       // hello

text = text.toUpperCase();      // stores the new String reference
System.out.println(text);       // HELLO
```

Immutability makes strings safe to share and useful as keys in collections,
but repeated changes can create many temporary objects. Use `StringBuilder`
when building text repeatedly in a loop.

### Immutable `String` Versus Mutable `StringBuilder`

An immutable object cannot change its contents after creation. A mutable object
can change its contents without creating a replacement object.

```java
// String is immutable. concat() returns a new String.
String originalText = "Java";
String updatedText = originalText.concat(" notes");

System.out.println(originalText); // Java: the original is unchanged
System.out.println(updatedText);  // Java notes: a new String

// StringBuilder is mutable. append() changes the existing builder.
StringBuilder mutableText = new StringBuilder("Java");
mutableText.append(" notes");

System.out.println(mutableText); // Java notes
```

The variable `originalText` still refers to the original `"Java"` object after
`concat`. With `StringBuilder`, `append` changes the characters held by the
existing builder object.

| Type | Mutable? | Best use |
| --- | --- | --- |
| `String` | No | Fixed text, values that should not change, map keys |
| `StringBuilder` | Yes | Repeated text changes in one thread |
| `StringBuffer` | Yes | Repeated text changes when synchronized access is required |

Use `String` for normal text. Use `StringBuilder` when repeatedly appending,
inserting, or deleting text, especially inside a loop. Convert the builder to a
regular string with `toString()` when the final text is ready:

```java
StringBuilder builder = new StringBuilder();
builder.append("Item: ").append("Book");
String result = builder.toString(); // result is an immutable String
```

## Comparing Strings

Use `equals` to compare string contents. Do not use `==` for normal text
comparison because `==` compares object references.

```java
String first = new String("Java");
String second = new String("Java");

System.out.println(first.equals(second)); // true: same characters
System.out.println(first == second);      // false: different objects
```

Useful comparison methods:

```java
String answer = "yes";

answer.equals("yes");              // true; case-sensitive
answer.equalsIgnoreCase("YES");    // true; ignores letter case
answer.compareTo("yes");            // 0 when equal
answer.compareTo("no");             // negative or positive ordering value
```

To avoid a `NullPointerException` when a variable may be `null`, compare with
a known non-null literal:

```java
String input = null;
System.out.println("yes".equals(input)); // false
```

## Common String Methods

Assume the following variable in the examples:

```java
String text = "  Java Programming  ";
```

### Size and characters

```java
text.length();              // number of characters: 20
text.charAt(2);             // 'J'
text.isEmpty();             // false; length is 0 only for ""
text.isBlank();             // false; true for empty or whitespace-only text
```

Indexes start at `0`. `charAt` throws `StringIndexOutOfBoundsException` if
the index is invalid. `isBlank` is available in Java 11 and later.

### Extracting text

```java
text.substring(2);          // "Java Programming  "
text.substring(2, 6);       // "Java"; end index is exclusive
text.substring(2, 6).length();
```

### Searching

```java
text.contains("Java");          // true
text.indexOf("a");              // index of first 'a'
text.lastIndexOf("a");          // index of last 'a'
text.startsWith("  Ja");        // true
text.endsWith("  ");            // true
```

Searching methods return `-1` when the requested text is not found.

### Changing case and whitespace

```java
text.toUpperCase();              // "  JAVA PROGRAMMING  "
text.toLowerCase();              // "  java programming  "
text.trim();                     // removes leading/trailing ASCII spaces
text.strip();                    // removes leading/trailing Unicode whitespace
text.stripLeading();
text.stripTrailing();
```

`strip`, `stripLeading`, and `stripTrailing` are available in Java 11 and
later. None of these methods changes `text`; each returns a new value.

### Replacing text

```java
text.replace('a', 'o');             // replaces every matching character
text.replace("Java", "Python");    // replaces every matching sequence
text.replaceFirst("a", "A");      // replaces the first match
text.replaceAll("\\s+", " ");      // regex replacement
```

`replaceAll` and `replaceFirst` use regular expressions. Use `replace` when
the search text should be treated as ordinary characters.

### Splitting and joining

```java
String csv = "red,green,blue";
String[] colors = csv.split(",");

for (String color : colors) {
		 System.out.println(color);
}

String joined = String.join(" - ", colors); // red - green - blue
```

`split` returns a `String[]`. Its argument is a regular expression, so some
characters such as `.` need escaping.

### Combining and formatting

```java
String greeting = "Hello" + " " + "Ana"; // Hello Ana
String result = "Age: ".concat(" twenty");

String formatted = String.format("Name: %s, Age: %d", "Ana", 20);
System.out.printf("Total: $%.2f%n", 12.5); // Total: $12.50
```

The `+` operator converts non-string values to text when one side is a
string:

```java
int score = 95;
String message = "Score: " + score; // Score: 95
```

## Converting Values

```java
int number = Integer.parseInt("42");
double price = Double.parseDouble("19.99");

String numberText = String.valueOf(42); // "42"
String priceText = String.valueOf(19.99); // "19.99"
```

`parseInt` and similar methods convert text into primitive values. They throw
`NumberFormatException` when the text is not a valid number.

## StringBuilder for Repeated Changes

Use `StringBuilder` when text is assembled or modified many times. It is
mutable, so it can update one object instead of creating a new String for
every operation.

```java
StringBuilder builder = new StringBuilder();
builder.append("Java");
builder.append(" ");
builder.append("notes");
builder.insert(0, "My ");
builder.replace(3, 7, "String");
builder.delete(0, 3);
builder.reverse();

String finalText = builder.toString();
```

Common `StringBuilder` methods include `append`, `insert`, `delete`,
`replace`, `setCharAt`, `reverse`, `length`, and `toString`.

## Benefits of Strings

- Easy and readable syntax for storing and working with text.
- Immutable values are safe to share and work reliably as map keys.
- Many built-in methods support searching, extracting, comparing, and
	 formatting text.
- String literals can be reused through the String pool.
- Works naturally with input, output, files, and user interfaces.

## Drawbacks and Common Problems

- Strings are immutable, so repeated concatenation can create extra objects.
- Text comparison with `==` can produce incorrect results; use `equals`.
- A `null` reference is different from the empty string `""` and can cause a
	 `NullPointerException` when a method is called.
- `substring`, `charAt`, and similar methods require valid indexes.
- Case-sensitive comparisons may not match user expectations; use
	 `equalsIgnoreCase` when appropriate.
- Strings use a fixed sequence of characters; use `StringBuilder` for heavy
	 editing and `StringBuffer` when synchronized access is specifically needed.

```java
String empty = "";       // a real String containing zero characters
String missing = null;   // no String object is referenced

System.out.println(empty.length()); // 0
// missing.length();                // NullPointerException
```

## Quick Method Reference

| Method | Purpose |
| --- | --- |
| `length()` | Returns the number of characters |
| `charAt(index)` | Returns one character |
| `substring(start, end)` | Extracts part of a string; end is exclusive |
| `equals(value)` | Compares contents, case-sensitive |
| `equalsIgnoreCase(value)` | Compares contents without case |
| `compareTo(value)` | Compares strings lexicographically |
| `contains(value)` | Checks whether text exists |
| `indexOf(value)` | Finds the first index, or `-1` |
| `startsWith(value)` / `endsWith(value)` | Checks text at the beginning or end |
| `toUpperCase()` / `toLowerCase()` | Changes letter case in a new String |
| `trim()` / `strip()` | Removes whitespace at both ends |
| `replace(old, new)` | Replaces characters or literal text |
| `split(regex)` | Breaks a string into a `String[]` |
| `concat(value)` | Appends another string |
| `isEmpty()` / `isBlank()` | Checks empty or whitespace-only text |
| `String.valueOf(value)` | Converts a value to a String |
| `String.format(format, values)` | Creates formatted text |

## Naming Note for This Project

The file `String.java` currently declares a class named `String` inside the
package also named `String`. That name can hide Java's built-in
`java.lang.String`. For normal Java programs, prefer a class name such as
`StringExamples` and use `java.lang.String` for text.
