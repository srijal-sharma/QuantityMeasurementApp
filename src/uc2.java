public class uc2 {

    // Feet class
    static class Feet {
        private final double value;

        public Feet(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Feet feet = (Feet) obj;
            return Double.compare(this.value, feet.value) == 0;
        }
    }

    // Inches class
    static class Inches {
        private final double value;

        public Inches(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Inches inch = (Inches) obj;
            return Double.compare(this.value, inch.value) == 0;
        }
    }

    // Static methods (as per requirement)
    public static boolean compareFeet(double v1, double v2) {
        Feet f1 = new Feet(v1);
        Feet f2 = new Feet(v2);
        return f1.equals(f2);
    }

    public static boolean compareInches(double v1, double v2) {
        Inches i1 = new Inches(v1);
        Inches i2 = new Inches(v2);
        return i1.equals(i2);
    }

    // Main method (test cases)
    public static void main(String[] args) {

        // FEET TESTS
        System.out.println("Feet Same Value: " + compareFeet(1.0, 1.0)); // true
        System.out.println("Feet Different Value: " + compareFeet(1.0, 2.0)); // false
        System.out.println("Feet Null Comparison: " + new Feet(1.0).equals(null)); // false
        System.out.println("Feet Same Reference: " + new Feet(1.0).equals(new Feet(1.0))); // true
        System.out.println("Feet Non-Numeric: " + new Feet(1.0).equals("abc")); // false

        // INCHES TESTS
        System.out.println("Inch Same Value: " + compareInches(1.0, 1.0)); // true
        System.out.println("Inch Different Value: " + compareInches(1.0, 2.0)); // false
        System.out.println("Inch Null Comparison: " + new Inches(1.0).equals(null)); // false
        System.out.println("Inch Same Reference: " + new Inches(1.0).equals(new Inches(1.0))); // true
        System.out.println("Inch Non-Numeric: " + new Inches(1.0).equals("abc")); // false
    }
}