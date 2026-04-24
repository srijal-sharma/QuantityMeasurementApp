public class uc7 {

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

        // UC6 method (default → first operand unit)
        public Quantity add(Quantity other) {
            return add(other, this.unit);
        }

        // 🔥 UC7 method (target unit specified)
        public Quantity add(Quantity other, LengthUnit targetUnit) {
            if (other == null) throw new IllegalArgumentException("Other cannot be null");
            if (targetUnit == null) throw new IllegalArgumentException("Target unit cannot be null");

            double sumFeet = this.toFeet() + other.toFeet();
            double resultValue = targetUnit.fromFeet(sumFeet);

            return new Quantity(resultValue, targetUnit);
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

        Quantity f = new Quantity(1.0, LengthUnit.FEET);
        Quantity i = new Quantity(12.0, LengthUnit.INCH);
        Quantity y = new Quantity(1.0, LengthUnit.YARD);

        // Different target units
        System.out.println("Feet result: " + f.add(i, LengthUnit.FEET));     // 2 FEET
        System.out.println("Inches result: " + f.add(i, LengthUnit.INCH));   // 24 INCH
        System.out.println("Yards result: " + f.add(i, LengthUnit.YARD));    // ~0.667 YARD

        // Yard + Feet
        System.out.println("Yard + Feet (YARD): " + y.add(new Quantity(3.0, LengthUnit.FEET), LengthUnit.YARD));

        // Inches + Yard → Feet
        System.out.println("Inch + Yard (FEET): " +
                new Quantity(36.0, LengthUnit.INCH).add(y, LengthUnit.FEET));

        // CM + Inch
        System.out.println("CM + Inch (CM): " +
                new Quantity(2.54, LengthUnit.CM).add(new Quantity(1.0, LengthUnit.INCH), LengthUnit.CM));

        // Zero case
        System.out.println("Zero case: " +
                new Quantity(5.0, LengthUnit.FEET).add(new Quantity(0.0, LengthUnit.INCH), LengthUnit.YARD));

        // Negative case
        System.out.println("Negative case: " +
                new Quantity(5.0, LengthUnit.FEET).add(new Quantity(-2.0, LengthUnit.FEET), LengthUnit.INCH));

        // Commutativity
        System.out.println("Commutativity: " +
                f.add(i, LengthUnit.YARD).equals(i.add(f, LengthUnit.YARD)));

        // Null target
        try {
            f.add(i, null);
        } catch (Exception e) {
            System.out.println("Handled null target unit");
        }
    }
}
