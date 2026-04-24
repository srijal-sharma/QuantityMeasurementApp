public class uc3 {

    // Enum for units (conversion to FEET)
    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0);

        private final double toFeet;

        LengthUnit(double toFeet) {
            this.toFeet = toFeet;
        }

        public double toFeet(double value) {
            return value * toFeet;
        }
    }

    // Generic Quantity class
    static class Quantity {
        private final double value;
        private final LengthUnit unit;

        public Quantity(double value, LengthUnit unit) {
            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }
            this.value = value;
            this.unit = unit;
        }

        // Convert to base unit (feet)
        private double toFeet() {
            return unit.toFeet(value);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;

            if (obj == null || getClass() != obj.getClass()) return false;

            Quantity other = (Quantity) obj;

            return Double.compare(this.toFeet(), other.toFeet()) == 0;
        }
    }

    // Main method (ALL TEST CASES)
    public static void main(String[] args) {

        Quantity f1 = new Quantity(1.0, LengthUnit.FEET);
        Quantity f2 = new Quantity(1.0, LengthUnit.FEET);
        Quantity f3 = new Quantity(2.0, LengthUnit.FEET);

        Quantity i1 = new Quantity(12.0, LengthUnit.INCH);
        Quantity i2 = new Quantity(1.0, LengthUnit.INCH);
        Quantity i3 = new Quantity(2.0, LengthUnit.INCH);

        // Same unit equality
        System.out.println("Feet Same Value: " + f1.equals(f2)); // true
        System.out.println("Feet Different Value: " + f1.equals(f3)); // false

        System.out.println("Inch Same Value: " + i2.equals(new Quantity(1.0, LengthUnit.INCH))); // true
        System.out.println("Inch Different Value: " + i2.equals(i3)); // false

        // Cross-unit equality
        System.out.println("Feet to Inch Equal: " + f1.equals(i1)); // true
        System.out.println("Inch to Feet Equal: " + i1.equals(f1)); // true

        // Null comparison
        System.out.println("Null Comparison: " + f1.equals(null)); // false

        // Same reference
        System.out.println("Same Reference: " + f1.equals(f1)); // true

        // Invalid unit test
        try {
            Quantity invalid = new Quantity(1.0, null);
        } catch (Exception e) {
            System.out.println("Invalid Unit Handled: true");
        }
    }
}