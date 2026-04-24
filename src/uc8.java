public class uc8 {

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
            return unit.convertToBaseUnit(value);
        }

        // Conversion
        public Quantity convertTo(LengthUnit target) {
            double base = this.toFeet();
            double result = target.convertFromBaseUnit(base);
            return new Quantity(result, target);
        }

        // Addition (UC7 style)
        public Quantity add(Quantity other, LengthUnit targetUnit) {
            if (other == null) throw new IllegalArgumentException("Other cannot be null");
            if (targetUnit == null) throw new IllegalArgumentException("Target unit cannot be null");

            double sumFeet = this.toFeet() + other.toFeet();
            double result = targetUnit.convertFromBaseUnit(sumFeet);

            return new Quantity(result, targetUnit);
        }

        // Equality
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

        // Conversion
        System.out.println("Convert FEET to INCH: " + f.convertTo(LengthUnit.INCH));

        // Equality
        System.out.println("Feet equals Inches: " + f.equals(i));

        // Addition
        System.out.println("Add (FEET + INCH → FEET): " +
                f.add(i, LengthUnit.FEET));

        System.out.println("Add (YARD + FEET → YARD): " +
                y.add(new Quantity(3.0, LengthUnit.FEET), LengthUnit.YARD));

        // CM conversion
        Quantity cm = new Quantity(2.54, LengthUnit.CM);
        System.out.println("CM to INCH: " + cm.convertTo(LengthUnit.INCH));

        // Base unit conversion check
        System.out.println("12 INCH to FEET: " +
                LengthUnit.INCH.convertToBaseUnit(12.0));
    }
}