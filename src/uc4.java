public class uc4 {

    // Enum with ALL units (base: FEET)
    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0),
        YARD(3.0),
        CM(0.0328084); // 1 cm = 0.0328084 feet

        private final double toFeet;

        LengthUnit(double toFeet) {
            this.toFeet = toFeet;
        }

        public double toFeet(double value) {
            return value * toFeet;
        }
    }

    // Generic Quantity class (same as UC3)
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

    public static void main(String[] args) {

        // Yard tests
        Quantity y1 = new Quantity(1.0, LengthUnit.YARD);
        Quantity y2 = new Quantity(1.0, LengthUnit.YARD);
        Quantity y3 = new Quantity(2.0, LengthUnit.YARD);

        System.out.println("Yard Same Value: " + y1.equals(y2)); // true
        System.out.println("Yard Different Value: " + y1.equals(y3)); // false

        // Yard conversions
        Quantity f = new Quantity(3.0, LengthUnit.FEET);
        Quantity i = new Quantity(36.0, LengthUnit.INCH);

        System.out.println("Yard to Feet: " + y1.equals(f)); // true
        System.out.println("Yard to Inches: " + y1.equals(i)); // true

        // CM tests
        Quantity c1 = new Quantity(1.0, LengthUnit.CM);
        Quantity c2 = new Quantity(1.0, LengthUnit.CM);

        System.out.println("CM Same Value: " + c1.equals(c2)); // true

        // CM conversion
        Quantity inchEquivalent = new Quantity(0.393701, LengthUnit.INCH);
        System.out.println("CM to Inch: " + c1.equals(inchEquivalent)); // true

        // Multi-unit test
        Quantity yMulti = new Quantity(2.0, LengthUnit.YARD);
        Quantity fMulti = new Quantity(6.0, LengthUnit.FEET);
        Quantity iMulti = new Quantity(72.0, LengthUnit.INCH);

        System.out.println("Multi Unit (Yard-Feet): " + yMulti.equals(fMulti)); // true
        System.out.println("Multi Unit (Feet-Inch): " + fMulti.equals(iMulti)); // true
        System.out.println("Multi Unit (Yard-Inch): " + yMulti.equals(iMulti)); // true

        // Null check
        System.out.println("Null Comparison: " + y1.equals(null)); // false

        // Same reference
        System.out.println("Same Reference: " + y1.equals(y1)); // true

        // Invalid unit
        try {
            Quantity invalid = new Quantity(1.0, null);
        } catch (Exception e) {
            System.out.println("Invalid Unit Handled: true");
        }
    }
}
