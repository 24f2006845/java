package String;

public class String {
	public static void main(java.lang.String[] args) {
		// Use java.lang.String explicitly because this class is also named String.
		java.lang.String name = "Ana";
		java.lang.String greeting = "Hello, " + name + "!";
		System.out.println(greeting); // Hello, Ana!

		// String indexes start at 0, and length() returns the character count.
		System.out.println(name.length()); // 3
		System.out.println(name.charAt(0)); // A

		// substring(start, end) includes start but excludes end.
		java.lang.String language = "Java Programming";
		System.out.println(language.substring(0, 4)); // Java

		// equals compares the text. The == operator compares object references.
		java.lang.String first = new java.lang.String("Java");
		java.lang.String second = new java.lang.String("Java");
		System.out.println(first.equals(second)); // true
		System.out.println(first == second); // false

		// Strings are immutable. toUpperCase() returns a new String.
		java.lang.String word = "hello";
		word.toUpperCase(); // The original word is still "hello".
		word = word.toUpperCase(); // Store the returned String.
		System.out.println(word); // HELLO

		// Immutable String: concat() creates a new object; originalText is unchanged.
		java.lang.String originalText = "Java";
		java.lang.String updatedText = originalText.concat(" notes");
		System.out.println(originalText); // Java
		System.out.println(updatedText); // Java notes

		// Mutable StringBuilder: append() changes the same builder object.
		StringBuilder mutableText = new StringBuilder("Java");
		mutableText.append(" notes");
		System.out.println(mutableText); // Java notes

		// Common searching and replacement methods return new values or results.
		java.lang.String sentence = "Java is useful";
		System.out.println(sentence.contains("useful")); // true
		System.out.println(sentence.indexOf("is")); // 5
		System.out.println(sentence.replace("useful", "powerful"));
		// Java is powerful

		// split() creates an array of strings from matching separators.
		java.lang.String colorsText = "red,green,blue";
		java.lang.String[] colors = colorsText.split(",");
		for (java.lang.String color : colors) {
			System.out.println(color);
		}

		// StringBuilder is useful when text changes repeatedly.
		StringBuilder builder = new StringBuilder();
		builder.append("Java");
		builder.append(" notes");
		builder.insert(0, "My ");
		System.out.println(builder); // My Java notes
	}
}
