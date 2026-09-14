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
    }
}