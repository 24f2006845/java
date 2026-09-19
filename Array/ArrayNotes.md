# Java Arrays

An array stores a fixed number of values of the same type. Array positions are
zero-indexed, so the first element is at index `0` and the last element is at
index `length - 1`.

## Creating Arrays

```java
// Declare a reference (no array has been created yet)
int[] numbers;

// Create an array with a fixed size. Values get default values.
numbers = new int[5];       // [0, 0, 0, 0, 0]

// Declare and create in one statement
int[] scores = new int[3];

// Create with initial values
int[] values = {10, 20, 30, 40};
int[] otherValues = new int[]{10, 20, 30, 40};

// Other common types
String[] names = {"Ana", "Ben"};
boolean[] flags = new boolean[2];
char[] letters = {'A', 'B', 'C'};
```

The size cannot be changed after creation. To store a resizable collection,
use `ArrayList` instead.

## Default Values

When an array is created with `new`, every element receives a default value:

| Type | Default |
| --- | --- |
| Numeric primitives (`byte`, `short`, `int`, `long`, `float`, `double`) | `0` (or `0.0`) |
| `char` | `\\u0000` |
| `boolean` | `false` |
| Reference types such as `String` | `null` |

## Indexing and Updating

```java
int[] numbers = {10, 20, 30};

int first = numbers[0];       // 10
int last = numbers[numbers.length - 1]; // 30
numbers[1] = 99;              // [10, 99, 30]

System.out.println(numbers.length); // 3; length is a field, not a method
```

Valid indexes are from `0` through `array.length - 1`. An invalid index throws
`ArrayIndexOutOfBoundsException`.

## Traversing an Array

### Traditional `for` loop

Use this when the index is needed or when changing elements.

```java
int[] numbers = {1, 2, 3, 4, 5};

for (int index = 0; index < numbers.length; index++) {
	System.out.println("index " + index + ": " + numbers[index]);
	numbers[index] *= 2;
}
```

### Enhanced `for` loop

Use this for reading every element when the index is not needed.

```java
for (int number : numbers) {
	System.out.println(number);
}
```

Changing `number` does not change the array. For objects, the loop variable is
a copy of the reference, not a replacement for the array element.

## Multidimensional Arrays

Java multidimensional arrays are arrays containing other arrays.

```java
// 2 rows and 3 columns
int[][] matrix = new int[2][3];

matrix[0][0] = 1;
matrix[0][1] = 2;
matrix[1][2] = 6;

System.out.println(matrix.length);       // number of rows: 2
System.out.println(matrix[0].length);    // columns in row 0: 3
System.out.println(matrix[1][2]);        // 6
```

Initialize directly:

```java
int[][] table = {
	{1, 2, 3},
	{4, 5, 6}
};
```

Nested loops visit every value:

```java
for (int row = 0; row < table.length; row++) {
	for (int column = 0; column < table[row].length; column++) {
		System.out.print(table[row][column] + " ");
	}
	System.out.println();
}
```

## Jagged Arrays

A jagged array is a two-dimensional array whose rows can have different
lengths. Java creates the outer array first, and each row is created separately.
This can save space when each row needs a different number of values.

### Creating a Jagged Array

```java
int[][] jagged = new int[3][]; // 3 row references; rows are not created yet

jagged[0] = new int[2]; // row 0 has indexes 0-1
jagged[1] = new int[4]; // row 1 has indexes 0-3
jagged[2] = new int[1]; // row 2 has index 0
```

The rows can also be initialized directly:

```java
int[][] marks = {
	{80, 90},
	{75, 85, 95},
	{88}
};
```

Access an element with two indexes. The first index selects the row and the
second index selects a value inside that row:

```java
System.out.println(marks[0][1]); // 90
marks[1][0] = 78;                 // changes the first value in row 1
System.out.println(marks.length);    // 3 rows
System.out.println(marks[1].length); // 3 values in row 1
```

Use `row.length` rather than one shared column count when traversing a jagged
array:

```java
for (int row = 0; row < marks.length; row++) {
	for (int column = 0; column < marks[row].length; column++) {
		System.out.print(marks[row][column] + " ");
	}
	System.out.println();
}
```

### Uses of Jagged Arrays

Jagged arrays are useful when groups do not all contain the same number of
values:

```java
// Each student has completed a different number of assignments.
int[][] assignmentScores = {
	{90, 85, 88},
	{76, 81},
	{95, 91, 89, 93}
};
```

Other examples include a calendar where months have different numbers of days,
seating rows with different capacities, and graph adjacency lists where each
vertex has a different number of neighbors.

## Uses and Benefits of Arrays

### One-Dimensional Arrays

Use a one-dimensional array for a fixed-size sequence of related values.

```java
int[] temperatures = {18, 21, 24, 20, 17};
int total = 0;

for (int temperature : temperatures) {
	total += temperature;
}

double average = (double) total / temperatures.length;
System.out.println("Average: " + average);
```

Benefits include:

- Fast access by index: `temperatures[2]` is direct access.
- Low memory overhead compared with many collection types.
- A fixed size makes the number of elements predictable.
- Easy traversal with `for` and enhanced `for` loops.

Common uses include scores, prices, IDs, sensor readings, and lookup tables.

### Multidimensional Arrays

Use a regular multidimensional array when data naturally forms rows and
columns, such as a seating chart or a board:

```java
char[][] board = {
	{'X', 'O', 'X'},
	{'O', 'X', 'O'},
	{' ', ' ', 'X'}
};

board[2][0] = 'O';
System.out.println(board[2][0]); // O
```

Benefits include:

- Clear row-and-column indexing such as `board[row][column]`.
- Natural representation of tables, grids, and matrices.
- Efficient storage when every row has the same length.
- Simple nested-loop processing.

### Jagged Arrays

Use a jagged array when rows contain different amounts of data:

```java
String[][] seating = {
	{"A1", "A2", "A3"},
	{"B1", "B2"},
	{"C1", "C2", "C3", "C4"}
};
```

Benefits include:

- Avoiding unused cells in rows of different sizes.
- Flexible row sizes that can match the data.
- Natural representation of grouped or irregular data.

### Important Limitations

- An array has a fixed size after it is created.
- All elements must have the same declared type.
- Indexes must be checked carefully to avoid exceptions.
- Inserting or removing an element requires creating a new array and copying
  values.

Use `ArrayList` when the number of elements changes often, or when convenient
insertion and removal are more important than direct array access.

## `java.util.Arrays` Methods

Arrays themselves have no methods. Import the utility class for common array
operations:

```java
import java.util.Arrays;
```

### Print an array: `toString`

```java
int[] numbers = {3, 1, 2};
System.out.println(Arrays.toString(numbers)); // [3, 1, 2]
```

For nested arrays use `deepToString`:

```java
System.out.println(Arrays.deepToString(table)); // [[1, 2, 3], [4, 5, 6]]
```

### Compare arrays: `equals` and `deepEquals`

```java
int[] first = {1, 2, 3};
int[] second = {1, 2, 3};

System.out.println(Arrays.equals(first, second)); // true
System.out.println(first == second);              // false: compares references
System.out.println(Arrays.deepEquals(new int[][]{{1}}, new int[][]{{1}}));
```

Use `equals` for one-dimensional arrays and `deepEquals` for nested arrays.

### Sort: `sort`

```java
int[] numbers = {4, 1, 3, 2};
Arrays.sort(numbers); // [1, 2, 3, 4]
```

Sort part of an array with the range `fromIndex` (inclusive) and `toIndex`
(exclusive):

```java
Arrays.sort(numbers, 1, 3); // sorts indexes 1 and 2 only
```

### Search: `binarySearch`

The array must be sorted first. The method returns the index when found. If it
is not found, it returns a negative value.

```java
int[] numbers = {1, 2, 3, 4, 5};
int index = Arrays.binarySearch(numbers, 4); // 3
```

### Fill: `fill`

```java
int[] numbers = new int[4];
Arrays.fill(numbers, 7); // [7, 7, 7, 7]
Arrays.fill(numbers, 1, 3, 9); // indexes 1 and 2 become 9
```

### Copy: `copyOf` and `copyOfRange`

```java
int[] original = {1, 2, 3};
int[] larger = Arrays.copyOf(original, 5);       // [1, 2, 3, 0, 0]
int[] part = Arrays.copyOfRange(original, 1, 3); // [2, 3]
```

Both methods create a new array. The end index in `copyOfRange` is exclusive.

### Other useful methods

```java
Arrays.setAll(numbers, index -> index * 10); // calculate each value
Arrays.parallelSort(numbers);                // parallel sort for large arrays
Arrays.parallelPrefix(numbers, (a, b) -> a + b); // cumulative operation
```

`Arrays.asList` works with reference-type arrays, not primitive arrays:

```java
String[] names = {"Ana", "Ben"};
System.out.println(Arrays.asList(names)); // [Ana, Ben]
```

For `int[]`, `Arrays.asList(numbers)` creates a list containing the whole
`int[]` as one item. Use `Arrays.stream(numbers)` when working with primitive
arrays and streams.

## Common Errors

```java
int[] numbers = new int[3];
// numbers[3] = 10;       // Error: valid indexes are 0, 1, and 2
// numbers.length();      // Error: length is not a method
// numbers = {1, 2, 3};   // Error outside a declaration
numbers = new int[]{1, 2, 3};
```

Use `.length` for arrays, `.length()` for `String`, and `.size()` for
collections such as `ArrayList`.

## Drawbacks of Arrays

Arrays are useful for fixed-size data, but they also have limitations:

- **Fixed size:** The length cannot change after the array is created.
- **Expensive insertion and removal:** Adding or removing an element may
	require creating a new array and copying the existing values.
- **Same data type:** An array normally stores values of one declared type.
- **Index errors:** Accessing an invalid index causes an
	`ArrayIndexOutOfBoundsException`.
- **Possible wasted memory:** A large array may contain unused positions when
	the exact number of values is not known.
- **Limited built-in operations:** Arrays do not provide methods for searching,
	sorting, or resizing. Use `java.util.Arrays` for many utility operations.
- **Manual size management:** The programmer must track the number of used
	elements when an array is only partly filled.

Example of a partly filled array:

```java
int[] numbers = new int[5];
int used = 0;

numbers[used++] = 10;
numbers[used++] = 20;

for (int index = 0; index < used; index++) {
		System.out.println(numbers[index]);
}
```

Use `ArrayList` when the collection needs to grow, shrink, or frequently
insert and remove values:

```java
import java.util.ArrayList;

ArrayList<Integer> numbers = new ArrayList<>();
numbers.add(10);
numbers.add(20);
numbers.remove(0);
```
