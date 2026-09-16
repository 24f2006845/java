public class loops {
	public static void main(String[] args) {
		// A for loop is useful when the number of repetitions is known.
		// Initialization runs once, the condition is checked before each
		// repetition, and the update runs after each repetition.
		System.out.println("For loop:");
		for (int number = 1; number <= 5; number++) {
			System.out.println("Number: " + number);
		}

		// A while loop checks its condition before running its body.
		// Make sure the loop variable changes so the loop can finish.
		System.out.println("\nWhile loop:");
		int countdown = 3;
		while (countdown > 0) {
			System.out.println("Countdown: " + countdown);
			countdown--;
		}

		// A do-while loop runs its body at least once because the condition
		// is checked after the body executes.
		System.out.println("\nDo-while loop:");
		int attempt = 1;
		do {
			System.out.println("Attempt: " + attempt);
			attempt++;
		} while (attempt <= 3);
	}
}
