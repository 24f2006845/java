class Hello {
    public static void main(String a[]) 
    {
        int num1 = 10;
        int num2 = 20;
        byte by = 30;
        short sh = 40;
        long lg = 50;
        float fl = 60.0f;
        double db = 70.0;
        char ch = 'A';
        boolean bl = true;
        int sum = num1 + num2;

        // Widening conversion: smaller type to larger type
        int wholeNumber = 100;
        double widenedNumber = wholeNumber;

        // Narrowing conversion: larger type to smaller type using casting
        double decimalNumber = 99.99;
        int narrowedNumber = (int) decimalNumber;

        // Casting can also convert between numeric types explicitly
        int largeValue = 130;
        byte castByte = (byte) largeValue;

        System.out.println("Sum of two numbers: " + sum);
        System.out.println("Byte value: " + by);
        System.out.println("Short value: " + sh);
        System.out.println("Long value: " + lg);
        System.out.println("Float value: " + fl);
        System.out.println("Double value: " + db);
        System.out.println("Character value: " + ch);
        System.out.println("Boolean value: " + bl);
        System.out.println("Widened int to double: " + widenedNumber);
        System.out.println("Narrowed double to int: " + narrowedNumber);
        System.out.println("Cast int to byte: " + castByte);

        int age = 20;
        boolean hasId = true;

        // Relational operators compare values.
        System.out.println("Age is greater than 18: " + (age > 18));
        System.out.println("Age is equal to 20: " + (age == 20));

        // Logical operators combine conditions.
        if (age >= 18 && hasId) {
            System.out.println("Allowed to enter.");
        }

        if (age < 13 || age > 65) {
            System.out.println("Eligible for a special discount.");
        } else {
            System.out.println("Regular ticket price.");
        }

        // Nested if: an if statement inside another if statement.
        int marks = 85;
        if (marks >= 40) {
            if (marks >= 80) {
                System.out.println("Passed with distinction.");
            } else {
                System.out.println("Passed.");
            }
        } else {
            System.out.println("Failed.");
        }

        // Switch selects one block based on a value.
        int day = 2;
        switch (day) {
            case 1:
                System.out.println("Monday");
                break;
            case 2:
                System.out.println("Tuesday");
                break;
            default:
                System.out.println("Another day");
        }

        // Ternary operator: short form of a simple if-else.
        String result = marks >= 40 ? "Pass" : "Fail";
        System.out.println("Result: " + result);
    }
}