public class uc9 {

    // Weight class (similar to Quantity in UC8)
    static class QuantityWeight {
        private final double value;
        private final WeightUnit unit;

        public QuantityWeight(double value, WeightUnit unit) {
            if (unit == null) throw new IllegalArgumentException("Unit cannot be null");
            if (!Double.isFinite(value)) throw new IllegalArgumentException("Invalid value");

            this.value = value;
            this.unit = unit;
        }

        private double toKg() {
            return unit.convertToBaseUnit(value);
        }

        // Conversion
        public QuantityWeight convertTo(WeightUnit target) {
            double base = this.toKg();
            double result = target.convertFromBaseUnit(base);
            return new QuantityWeight(result, target);
        }

        // Addition (default)
        public QuantityWeight add(QuantityWeight other) {
            return add(other, this.unit);
        }

        // Addition (target unit)
        public QuantityWeight add(QuantityWeight other, WeightUnit target) {
            if (other == null) throw new IllegalArgumentException("Other cannot be null");
            if (target == null) throw new IllegalArgumentException("Target cannot be null");

            double sumKg = this.toKg() + other.toKg();
            double result = target.convertFromBaseUnit(sumKg);

            return new QuantityWeight(result, target);
        }

        // Equality
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityWeight other = (QuantityWeight) obj;
            return Double.compare(this.toKg(), other.toKg()) == 0;
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    public static void main(String[] args) {

        QuantityWeight kg = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight g = new QuantityWeight(1000.0, WeightUnit.GRAM);
        QuantityWeight lb = new QuantityWeight(2.20462, WeightUnit.POUND);

        // Equality
        System.out.println("Kg == Gram: " + kg.equals(g)); // true
        System.out.println("Kg == Pound: " + kg.equals(lb)); // true

        // Conversion
        System.out.println("1 KG to Gram: " + kg.convertTo(WeightUnit.GRAM));
        System.out.println("500 Gram to Pound: " + new QuantityWeight(500, WeightUnit.GRAM).convertTo(WeightUnit.POUND));

        // Addition
        System.out.println("Kg + Gram: " + kg.add(g)); // 2 KG
        System.out.println("Gram + Kg: " + g.add(kg)); // 2000 GRAM

        // Explicit target
        System.out.println("Kg + Gram → Gram: " + kg.add(g, WeightUnit.GRAM));

        // Zero
        System.out.println("Zero: " + kg.add(new QuantityWeight(0, WeightUnit.GRAM)));

        // Negative
        System.out.println("Negative: " + kg.add(new QuantityWeight(-1000, WeightUnit.GRAM)));

        // Category safety (IMPORTANT)
        System.out.println("Weight vs Length (should be false): " +
                kg.equals("not a weight"));
    }
}
