package String;

public class String {
	public static void main(java.lang.String[] args) {
		// This class is also named String, so use java.lang.String for text values.
		java.lang.String name = "Ana"; // A literal is stored in the String pool.
		java.lang.String copiedName = new java.lang.String("Ana"); // Creates an object.
		char[] letters = {'J', 'a', 'v', 'a'};
		java.lang.String language = new java.lang.String(letters); // Creates from char[].
		System.out.println(name + " / " + copiedName + " / " + language);

		// length, charAt, isEmpty, and isBlank inspect a string.
		java.lang.String text = "  Java Programming  ";
		System.out.println("length: " + text.length()); // Number of characters.
		System.out.println("charAt: " + text.charAt(2)); // Indexes start at 0: J.
		System.out.println("isEmpty: " + text.isEmpty()); // True only when length is 0.
		System.out.println("isBlank: " + text.isBlank()); // True for empty or whitespace-only text.

		// substring extracts text; the end index is exclusive.
		System.out.println("substring: " + text.substring(2));
		System.out.println("substring range: " + text.substring(2, 6)); // Java.

		// equals compares contents; == compares object references.
		java.lang.String first = new java.lang.String("Java");
		java.lang.String second = new java.lang.String("Java");
		System.out.println("equals: " + first.equals(second)); // true.
		System.out.println("==: " + (first == second)); // false for these objects.
		System.out.println("equalsIgnoreCase: " + first.equalsIgnoreCase("JAVA"));
		System.out.println("compareTo: " + first.compareTo("Java")); // 0 means equal.
		System.out.println("safe null comparison: " + "Java".equals(null));

		// Searching methods return positions, or -1 when text is not found.
		java.lang.String sentence = "Java is useful";
		System.out.println("contains: " + sentence.contains("useful"));
		System.out.println("indexOf: " + sentence.indexOf("a")); // First matching index.
		System.out.println("lastIndexOf: " + sentence.lastIndexOf("a")); // Last matching index.
		System.out.println("startsWith: " + sentence.startsWith("Java"));
		System.out.println("endsWith: " + sentence.endsWith("useful"));

		// Case and whitespace methods return a new String; they do not change text.
		System.out.println("upper: " + text.toUpperCase());
		System.out.println("lower: " + text.toLowerCase());
		System.out.println("trim: [" + text.trim() + "]"); // Removes ASCII edge spaces.
		System.out.println("strip: [" + text.strip() + "]"); // Removes Unicode whitespace.
		System.out.println("stripLeading: [" + text.stripLeading() + "]");
		System.out.println("stripTrailing: [" + text.stripTrailing() + "]");

		// replace treats its arguments as literal text; regex methods use patterns.
		System.out.println("replace char: " + sentence.replace('a', 'o'));
		System.out.println("replace text: " + sentence.replace("useful", "powerful"));
		System.out.println("replaceFirst: " + "one one".replaceFirst("one", "two"));
		System.out.println("replaceAll: " + "Java   notes".replaceAll("\\s+", " "));

		// split creates an array; join combines values using a delimiter.
		java.lang.String[] colors = "red,green,blue".split(",");
		for (java.lang.String color : colors) {
			System.out.println("color: " + color);
		}
		System.out.println("join: " + java.lang.String.join(" - ", colors));

		// concat, +, format, and formatted combine values into text.
		java.lang.String greeting = "Hello, ".concat(name).concat("!");
		int score = 95;
		System.out.println(greeting + " Score: " + score);
		System.out.println(java.lang.String.format("Name: %s, Score: %d", name, score));
		System.out.println("Total: $" + java.lang.String.format("%.2f", 12.5));

		// Convert text to numbers with parse methods, and values to text with valueOf.
		int number = Integer.parseInt("42");
		double price = Double.parseDouble("19.99");
		System.out.println("parsed values: " + number + ", " + price);
		System.out.println("valueOf: " + java.lang.String.valueOf(number));

		// String is immutable. concat returns a new value and leaves originalText unchanged.
		java.lang.String originalText = "Java";
		java.lang.String updatedText = originalText.concat(" notes");
		System.out.println(originalText); // Java.
		System.out.println(updatedText); // Java notes.

		// StringBuilder is mutable and is efficient for repeated changes.
		StringBuilder builder = new StringBuilder("Java");
		builder.append(" notes"); // Adds text at the end.
		builder.insert(0, "My "); // Inserts text at an index.
		builder.replace(3, 7, "String"); // Replaces a range; end is exclusive.
		builder.delete(0, 3); // Deletes a range; end is exclusive.
		builder.setCharAt(0, 'j'); // Changes one character.
		System.out.println("builder length: " + builder.length());
		System.out.println("builder reversed: " + builder.reverse());
		System.out.println("builder result: " + builder.toString()); // Converts to String.
	}
}
