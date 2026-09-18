package StackAndHeap;

class Student {
	String name;
	int age;

	Student(String name, int age) {
		this.name = name;
		this.age = age;
	}
}

public class StackAndHeapsInJava {
	public static void main(String[] args) {
		// main() starts with the first stack frame for this program.
		int marks = 85;

		// new creates a Student object in the heap.
		// student is a reference stored in main()'s stack frame.
		Student student = createStudent("Asha", 20);

		// This method call creates another stack frame.
		// Its returned value is stored in total in main().
		int total = calculateTotal(marks, 10);

		System.out.println(student.name + " has " + total + " marks.");
	}

	private static Student createStudent(String name, int age) {
		// name and age are parameters in this method's stack frame.
		// The Student object is created in the heap.
		Student newStudent = new Student(name, age);

		// The heap object's reference is returned to main().
		// This stack frame is removed after the return.
		return newStudent;
	}

	private static int calculateTotal(int firstMark, int secondMark) {
		// These values and result belong to this stack frame.
		int result = firstMark + secondMark;

		// The int value is returned to main().
		// This stack frame is removed after the return.
		return result;
	}
}
