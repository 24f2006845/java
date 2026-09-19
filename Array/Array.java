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