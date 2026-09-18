class Hello {
    public static void main(String a[]) 
    {
        // Primitive data types store different kinds of simple values.
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

        // Widening conversion happens automatically when a smaller type
        // is assigned to a larger compatible type.
        int wholeNumber = 100;
        double widenedNumber = wholeNumber;

        // Narrowing conversion requires an explicit cast and may lose data.
        double decimalNumber = 99.99;
        int narrowedNumber = (int) decimalNumber;

        // Casting explicitly converts a value to another compatible type.
        int largeValue = 130;
        byte castByte = (byte) largeValue;

        // Display the values and the results of the conversions.
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

        // Relational operators compare values and return true or false.
        int age = 20;
        boolean hasId = true;
        System.out.println("Age is greater than 18: " + (age > 18));
        System.out.println("Age is equal to 20: " + (age == 20));

        // Logical operators combine multiple boolean conditions.
        if (age >= 18 && hasId) {
            System.out.println("Allowed to enter.");
        }

        if (age < 13 || age > 65) {
            System.out.println("Eligible for a special discount.");
        } else {
            System.out.println("Regular ticket price.");
        }

        // OR (||) is true when at least one condition is true.
        boolean isWeekend = true;
        boolean isHoliday = false;
        if (isWeekend || isHoliday) {
            System.out.println("The office is closed.");
        }

        // A nested if checks a second condition inside the first condition.
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

        // An if-else-if-else chain checks conditions from top to bottom.
        if (marks >= 80) {
            System.out.println("Grade: A");
        } else if (marks >= 60) {
            System.out.println("Grade: B");
        } else if (marks >= 40) {
            System.out.println("Grade: C");
        } else {
            System.out.println("Grade: F");
        }

        // A switch selects one block based on the value of an expression.
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

            // The ternary operator is a compact form of a simple if-else.
        String result = marks >= 40 ? "Pass" : "Fail";
        System.out.println("Result: " + result);
    }
}