/**
 * Created by bamboo on 28.05.14.
 */
public enum ExtendedOperation implements Operation {
    PLUS("+") {
        @Override
        public double apply(double a, double b) {
            return a + b;
        }
    },

    MINUS("-") {
        @Override
        public double apply(double a, double b) {
            return a - b;
        }
    },

    MULT("*") {
        @Override
        public double apply(double a, double b) {
            return a * b;
        }
    },

    DIIV("/") {
        @Override
        public double apply(double a, double b) {
            return a / b;
        }
    };

    private final String action;

    ExtendedOperation(String action) {
        this.action = action;
    }


    @Override
    public String toString() {
        return action;
    }
}
