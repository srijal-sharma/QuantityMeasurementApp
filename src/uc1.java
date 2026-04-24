public class uc1 {

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

    public static void main(String[] args) {

        Feet f1 = new Feet(1.0);
        Feet f2 = new Feet(1.0);
        Feet f3 = new Feet(2.0);

        System.out.println("testEquality_SameValue: " + f1.equals(f2));
        System.out.println("testEquality_DifferentValue: " + f1.equals(f3));
        System.out.println("testEquality_NullComparison: " + f1.equals(null));
        System.out.println("testEquality_SameReference: " + f1.equals(f1));
        System.out.println("testEquality_NonNumericInput: " + f1.equals("abc"));
    }
}