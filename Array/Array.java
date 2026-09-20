package Array;
public class Array {
    public static void main(String[] args) {
        int numbers[] = {1, 2, 3, 4, 5};
        MultidimensionalArray multiArray = new MultidimensionalArray();
        multiArray.main(args);
        multidimensionalArray2 multiArray2 = new multidimensionalArray2();
        multiArray2.main(args);
        System.out.println("Array elements:");
        for (int number : numbers) {
            System.out.println(number);
        }
        JaggedArray jaggedArray = new JaggedArray();
        jaggedArray.main(args);
        CreateJaggedArray createJaggedArray = new CreateJaggedArray();
        createJaggedArray.main(args);


        student s1 = new student();
        s1.marks = 90;
        s1.name = "John";
        s1.age = 20;    
        
        student s2 = new student();
        s2.marks = 85;
        s2.name = "Alice";
        s2.age = 22;


        student StudentArray[] = new student[2];
        StudentArray[0] = s1;
        StudentArray[1] = s2;
        System.out.println("Student Array elements:");
        for (student student : StudentArray) {
            System.out.println("Name: " + student.name + ", Age: " + student.age + ", Marks: " + student.marks);
        }
    }
}

class MultidimensionalArray {
    public static void main(String[] args) {
        int[][] matrix = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        System.out.println("Multidimensional Array elements:");
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}

class multidimensionalArray2 {
    public static void main(String[] args) {
        int multiArray[][] = new int[3][3];

        System.out.println("Multidimensional Array with Random Values:");

        for (int i = 0; i < multiArray.length; i++) {
            for (int j = 0; j < multiArray[i].length; j++) {
                multiArray[i][j] =  (int)(Math.random() * 100); // Assign random values between 0 and 99
            }
        }
        for (int i = 0; i < multiArray.length; i++) {
            for (int j = 0; j < multiArray[i].length; j++) {
                System.out.print(multiArray[i][j] + " ");
            }
            System.out.println();
        }
    }
}


class JaggedArray {
    public static void main(String[] args) {
        int[][] jaggedArray = new int[3][];
        jaggedArray[0] = new int[]{1, 2};
        jaggedArray[1] = new int[]{3, 4, 5};
        jaggedArray[2] = new int[]{6};

        System.out.println("Jagged Array elements:");
        for (int i = 0; i < jaggedArray.length; i++) {
            for (int j = 0; j < jaggedArray[i].length; j++) {
                System.out.print(jaggedArray[i][j] + " ");
            }
            System.out.println();
        }
    }
}

class CreateJaggedArray {
    public static void main(String[] args) {
        int JaggestArray[][] = new int[3][];
        JaggestArray[0] = new int[2];
        JaggestArray[1] = new int[4];
        JaggestArray[2] = new int[3];

        for (int i = 0; i < JaggestArray.length; i++) {
            for (int j = 0; j < JaggestArray[i].length; j++) {
                JaggestArray[i][j] = (int)(Math.random() * 100); // Assign random values between 0 and 99
            }
        }
        System.out.println("Jagged Array elements:");
        for (int i = 0; i < JaggestArray.length; i++) {
            for (int j = 0; j < JaggestArray[i].length; j++) {
                System.out.print(JaggestArray[i][j] + " ");
            }
            System.out.println();
        }
    }
}

class student {
    int marks;
    String name;
    int age;
}

