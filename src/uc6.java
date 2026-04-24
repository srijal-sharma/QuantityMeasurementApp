public class uc6 {

    // Enum (same as UC5)
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

        public double fromFeet(double feetValue) {
            return feetValue / toFeet;
        }
    }

    // Quantity class
    static class Quantity {
        private final double value;
        private final LengthUnit unit;

        public Quantity(double value, LengthUnit unit) {
            if (unit == null) throw new IllegalArgumentException("Unit cannot be null");
            if (!Double.isFinite(value)) throw new IllegalArgumentException("Invalid value");

            this.value = value;
            this.unit = unit;
        }

        private double toFeet() {
            return unit.toFeet(value);
        }

        // 🔥 ADD METHOD (core of UC6)
        public Quantity add(Quantity other) {
            if (other == null) throw new IllegalArgumentException("Other quantity cannot be null");

            double sumFeet = this.toFeet() + other.toFeet();

            // Convert result back to unit of first operand
            double resultValue = this.unit.fromFeet(sumFeet);

            return new Quantity(resultValue, this.unit);
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

    public static void main(String[] args) {

        // Same unit addition
        System.out.println("Feet + Feet: " +
                new Quantity(1.0, LengthUnit.FEET).add(new Quantity(2.0, LengthUnit.FEET)));

        // Cross unit (Feet + Inches)
        System.out.println("Feet + Inches: " +
                new Quantity(1.0, LengthUnit.FEET).add(new Quantity(12.0, LengthUnit.INCH)));

        // Reverse (Inches + Feet)
        System.out.println("Inches + Feet: " +
                new Quantity(12.0, LengthUnit.INCH).add(new Quantity(1.0, LengthUnit.FEET)));

        // Yard + Feet
        System.out.println("Yard + Feet: " +
                new Quantity(1.0, LengthUnit.YARD).add(new Quantity(3.0, LengthUnit.FEET)));

        // Inches + Yard
        System.out.println("Inches + Yard: " +
                new Quantity(36.0, LengthUnit.INCH).add(new Quantity(1.0, LengthUnit.YARD)));

        // CM + Inch
        System.out.println("CM + Inch: " +
                new Quantity(2.54, LengthUnit.CM).add(new Quantity(1.0, LengthUnit.INCH)));

        // Zero case
        System.out.println("Zero Addition: " +
                new Quantity(5.0, LengthUnit.FEET).add(new Quantity(0.0, LengthUnit.INCH)));

        // Negative case
        System.out.println("Negative Addition: " +
                new Quantity(5.0, LengthUnit.FEET).add(new Quantity(-2.0, LengthUnit.FEET)));

        // Commutativity check
        Quantity a = new Quantity(1.0, LengthUnit.FEET);
        Quantity b = new Quantity(12.0, LengthUnit.INCH);

        System.out.println("Commutativity Check: " +
                a.add(b).equals(b.add(a)));

        // Null test
        try {
            new Quantity(1.0, LengthUnit.FEET).add(null);
        } catch (Exception e) {
            System.out.println("Handled null operand");
        }
    }
}
