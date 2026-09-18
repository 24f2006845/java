# Stack and Heap in Java

Java uses memory mainly through the **stack** and the **heap**. Understanding both helps explain how method calls, local variables, objects, and returned values work.

## 1. Stack memory

The stack stores information for method execution. Each time a method is called, Java creates a **stack frame** for that method.

A stack frame can contain:
- local primitive variables, such as `int number`
- method parameters
- references to objects stored in the heap
- the place where execution should continue after the method returns

When the method finishes, its stack frame is removed automatically. The stack follows **LIFO** order: last in, first out.

Example call order:
```text
main() calls calculateTotal()
calculateTotal() calls createStudent()
createStudent() finishes and is removed
calculateTotal() finishes and is removed
main() finishes and is removed
```

Each method call creates a new frame on the same thread's stack. A normal method call does not create a completely separate stack; it adds another frame to the current stack.

## 2. Heap memory

The heap stores objects and arrays created with `new`.

Example:
```java
Student student = new Student("Asha", 20);
```

What happens:
1. `new Student(...)` creates a `Student` object in the heap.
2. The object stores its fields, such as `name` and `age`, in the heap.
3. The local variable `student` is a reference stored in the current stack frame.
4. The reference points to the `Student` object in the heap.

The reference is not the object itself. It is a way to find the object.

## 3. How a method calls another method

Consider this call:
```java
int total = calculateTotal();
```

The process is:
1. Java creates a stack frame for `calculateTotal()`.
2. `calculateTotal()` calls another method, so Java creates another stack frame for that method.
3. The called method calculates a value and uses `return` to send the value back.
4. Its stack frame is removed.
5. The calling method receives the returned value and stores it in `total`.

Primitive values such as `int` are returned as values. If a method returns an object, it returns a reference to an object in the heap.

## 4. Complete example

The matching file is `StackAndHeapsInJava.java`.

```java
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
		// The main() method gets the first stack frame for this program.
		int marks = 85;

		// new creates a Student object in the heap.
		// student is a local reference stored in the main() stack frame.
		Student student = createStudent("Asha", 20);

		// calculateTotal() creates another stack frame.
		// Its returned int value is copied into total in main().
		int total = calculateTotal(marks, 10);

		System.out.println(student.name + " has " + total + " marks.");
	}

	private static Student createStudent(String name, int age) {
		// This method gets its own stack frame for name and age.
		// The Student object itself is created in the heap.
		Student newStudent = new Student(name, age);

		// The reference to the heap object is returned to main().
		// After this return, this method's stack frame is removed.
		return newStudent;
	}

	private static int calculateTotal(int firstMark, int secondMark) {
		// firstMark and secondMark are local values in this stack frame.
		int result = firstMark + secondMark;

		// The int value is returned to main().
		// After this return, calculateTotal()'s stack frame is removed.
		return result;
	}
}
```

Output:
```text
Asha has 95 marks.
```

## 5. Memory picture during `createStudent()`

While `createStudent()` is running, memory can be understood like this:
```text
STACK                                      HEAP
-----                                      ----
createStudent() frame                     Student object
name -> "Asha"                             name = "Asha"
age = 20                                   age = 20
newStudent ------------------------------> object address
                                           
main() frame                               
student ---------------------------------> same object
```

The `student` reference in `main()` and the `newStudent` reference in `createStudent()` can point to the same heap object. Once `createStudent()` returns, `newStudent` disappears with its stack frame, but the object remains reachable through `student`.

## 6. What happens after a method returns?

- The method's stack frame is removed.
- Its local variables are no longer directly available.
- A returned primitive value is received by the caller.
- A returned object reference can be stored by the caller.
- An unreachable heap object can later be removed by Java's garbage collector.

Example:
```java
Student student = createStudent("Asha", 20);
```

The `Student` object remains available because `student` still points to it. If no reference points to that object anymore, the object becomes eligible for garbage collection.

## 7. Stack versus heap

| Stack | Heap |
|---|---|
| Stores method frames and local variables | Stores objects and arrays |
| Works per thread | Shared by threads in the Java process |
| Frames are removed when methods return | Objects remain until unreachable and collected |
| Usually faster to manage | Used for dynamically created data |
| Limited in size | Usually larger than the stack |

## 8. Common mistakes

### Mistake 1: Thinking the reference is the object
```java
Student student = new Student("Asha", 20);
```

`student` is a reference in the stack frame. The actual `Student` object is in the heap.

### Mistake 2: Thinking every method gets a new stack

Each method call gets a new **stack frame** on the current thread's stack. A new stack is normally associated with a new thread, not with every method call.

### Mistake 3: Expecting local variables to work after the method returns
```java
private static int getNumber() {
	int number = 10;
	return number;
}
```

`number` belongs to `getNumber()`'s stack frame. The variable disappears when the method returns, but the value `10` is sent back to the caller.

### Mistake 4: Assuming Java immediately deletes objects

When no references point to an object, it becomes eligible for garbage collection. Java decides when garbage collection runs; the object is not necessarily deleted immediately.

## 9. Summary

- A method call creates a stack frame.
- A method call can call another method by creating another frame above it.
- `return` sends a value or object reference back to the caller.
- `new` creates objects and arrays in the heap.
- A local object variable usually stores a heap reference in the stack frame.
- Stack frames are removed when methods return.
- Heap objects are removed later when they are no longer reachable and garbage collection reclaims them.
