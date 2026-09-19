
public class javaProgram {
	public static void main(String[] args) {
		java.util.Scanner scanner = new java.util.Scanner(System.in);

		System.out.println("=== Student Result Manager ===");

		// String is a reference type used to store the student's name.
		System.out.print("Enter student name: ");
		String studentName = scanner.nextLine();

		// Parsing converts text entered by the user into an int value.
		System.out.print("Enter student age: ");
		int age = Integer.parseInt(scanner.nextLine());

		// An array stores the marks for the three subjects.
		String[] subjects = { "Math", "Science", "English" };
		int[] marks = new int[subjects.length];
		int totalMarks = 0;

		// A for loop is useful because the number of subjects is known.
		for (int subjectIndex = 0; subjectIndex < subjects.length; subjectIndex++) {
			int mark;

			// A while loop keeps asking until the mark is valid.
			while (true) {
				System.out.print("Enter marks for " + subjects[subjectIndex] + " (0-100): ");
				mark = Integer.parseInt(scanner.nextLine());

				if (mark >= 0 && mark <= 100) {
					break; // The input is valid, so leave the validation loop.
				}

				System.out.println("Please enter a mark between 0 and 100.");
			}

			marks[subjectIndex] = mark;
			totalMarks += mark;
		}

		// Widening conversion changes int to double automatically.
		double average = (double) totalMarks / subjects.length;

		// Narrowing conversion removes the decimal part from the average.
		int wholeAverage = (int) average;

		// A char stores the single-letter grade.
		char grade;
		if (average >= 90) {
			grade = 'A';
		} else if (average >= 80) {
			grade = 'B';
		} else if (average >= 70) {
			grade = 'C';
		} else if (average >= 60) {
			grade = 'D';
		} else {
			grade = 'F';
		}

		// The ternary operator is a short form of a simple if-else.
		boolean passed = average >= 40;
		String result = passed ? "Passed" : "Failed";

		// long can store a larger whole-number value than int.
		long totalPossibleMarks = (long) subjects.length * 100;

		// float stores decimal values with less precision than double.
		float attendancePercentage = 92.5F;

		// A do-while menu runs at least once and repeats until the user exits.
		int choice;
		do {
			System.out.println("\n1. View result");
			System.out.println("2. View grade advice");
			System.out.println("3. Exit");
			System.out.print("Choose an option: ");
			choice = Integer.parseInt(scanner.nextLine());

			// A switch selects an action based on the menu choice.
			switch (choice) {
				case 1:
					System.out.println("\n--- Result ---");
					System.out.println("Student: " + studentName);
					System.out.println("Age: " + age);
					for (int subjectIndex = 0; subjectIndex < subjects.length; subjectIndex++) {
						System.out.println(subjects[subjectIndex] + ": " + marks[subjectIndex]);
					}
					System.out.println("Total: " + totalMarks + "/" + totalPossibleMarks);
					System.out.println("Average: " + average + " (whole average: " + wholeAverage + ")");
					System.out.println("Grade: " + grade);
					System.out.println("Result: " + result);
					System.out.println("Attendance: " + attendancePercentage + "%");
					break;
				case 2:
					// The switch demonstrates behavior for each possible grade.
					switch (grade) {
						case 'A':
							System.out.println("Excellent work. Keep challenging yourself.");
							break;
						case 'B':
							System.out.println("Good work. Review small mistakes to improve.");
							break;
						case 'C':
							System.out.println("Keep practicing the difficult topics.");
							break;
						default:
							System.out.println("Create a study plan and ask for help where needed.");
					}
					break;
				case 3:
					System.out.println("Thank you. Goodbye, " + studentName + "!");
					break;
				default:
					System.out.println("Invalid option. Choose 1, 2, or 3.");
			}
		} while (choice != 3);

		scanner.close();
	}
}
