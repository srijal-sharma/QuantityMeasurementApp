public class uc5 {

    // Enum with conversion factors (base: FEET)
    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0),
        YARD(3.0),
        CM(0.0328084);

        private final double toFeet;

        LengthUnit(double toFeet) {
            this.toFeet = toFeet;
        }

        public double toFeet(double value) {
            return value * toFeet;
        }

        public double fromFeet(double valueInFeet) {
            return valueInFeet / toFeet;
        }
    }

    // Quantity class
    static class Quantity {
        private final double value;
        private final LengthUnit unit;

        public Quantity(double value, LengthUnit unit) {
            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }
            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Invalid value");
            }
            this.value = value;
            this.unit = unit;
        }

        private double toFeet() {
            return unit.toFeet(value);
        }

        // Convert to another unit (instance method)
        public Quantity convertTo(LengthUnit target) {
            double feetValue = this.toFeet();
            double converted = target.fromFeet(feetValue);
            return new Quantity(converted, target);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Quantity other = (Quantity) obj;
            return Double.compare(this.toFeet(), other.toFeet()) == 0;
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    // 🔥 Static API method (IMPORTANT)
    public static double convert(double value, LengthUnit source, LengthUnit target) {
        if (source == null || target == null) {
            throw new IllegalArgumentException("Units cannot be null");
        }
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid numeric value");
        }

        double valueInFeet = source.toFeet(value);
        return target.fromFeet(valueInFeet);
    }

    // Demonstration methods (overloading)
    public static void demonstrateLengthConversion(double value, LengthUnit from, LengthUnit to) {
        double result = convert(value, from, to);
        System.out.println("Convert(" + value + ", " + from + " -> " + to + ") = " + result);
    }

    public static void demonstrateLengthConversion(Quantity q, LengthUnit to) {
        Quantity converted = q.convertTo(to);
        System.out.println("Convert(" + q + " -> " + to + ") = " + converted.value);
    }

    public static void main(String[] args) {

        // Basic conversions
        demonstrateLengthConversion(1.0, LengthUnit.FEET, LengthUnit.INCH); // 12
        demonstrateLengthConversion(3.0, LengthUnit.YARD, LengthUnit.FEET); // 9
        demonstrateLengthConversion(36.0, LengthUnit.INCH, LengthUnit.YARD); // 1
        demonstrateLengthConversion(1.0, LengthUnit.CM, LengthUnit.INCH); // ~0.393701

        // Instance conversion
        Quantity q = new Quantity(2.0, LengthUnit.YARD);
        demonstrateLengthConversion(q, LengthUnit.FEET); // 6

        // Equality check
        Quantity f = new Quantity(1.0, LengthUnit.FEET);
        Quantity i = new Quantity(12.0, LengthUnit.INCH);
        System.out.println("Equality (Feet vs Inch): " + f.equals(i)); // true

        // Edge cases
        System.out.println("Zero Conversion: " + convert(0.0, LengthUnit.FEET, LengthUnit.INCH));
        System.out.println("Negative Conversion: " + convert(-1.0, LengthUnit.FEET, LengthUnit.INCH));

        // Round-trip test
        double v = convert(convert(5.0, LengthUnit.FEET, LengthUnit.INCH),
                LengthUnit.INCH, LengthUnit.FEET);
        System.out.println("Round Trip (5 feet): " + v);

        // Invalid input handling
        try {
            convert(Double.NaN, LengthUnit.FEET, LengthUnit.INCH);
        } catch (Exception e) {
            System.out.println("Handled invalid value");
        }
    }
}
