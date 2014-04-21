package com.oyster.amo.lab3;

/**
 * @author bamboo
 * @since 4/10/14 2:48 AM
 */
public class Interpolation {

    enum InterpolationType {
        LINEAR,
        SQUARE
    }

    private double[] x;
    private double[] y;
    private Function function;
    private double a;
    private double b;
    private int numOfPoints;
    private InterpolationType type;
    private double deltaH;
    private double[] error;
    private double meanError = -47;

    private int n;


    private int typeToInt(InterpolationType type) {
        switch (type) {
            case LINEAR:
                return 1;
            case SQUARE:
                return 2;
            default:
                return 0;
        }
    }

    public Interpolation(Function f, int numOfPoints, InterpolationType type, double a, double b) {

        this.a = a;
        this.b = b;
        this.numOfPoints = numOfPoints;

        this.type = type;

        n = typeToInt(type);

        deltaH = (b - a) / (numOfPoints - 1);

        x = new double[numOfPoints];
        y = new double[numOfPoints];

        function = f;
        x[0] = a;
        y[0] = function.value(x[0]);
        for (int i = 1; i < x.length; i++) {
            x[i] = x[i - 1] + deltaH;
            y[i] = function.value(x[i]);
        }
    }

    public double interpolate(double value) {
        double result = 0;
        double mul;
        for (int k = 0; k < numOfPoints - 1; k++) {
            // point not in this interval
            if (!(value >= x[k] && value < x[k + 1])) {
                continue;
            }

            if (k <= numOfPoints / 2) {
                for (int j = k; j <= k + n; j++) {
                    mul = y[j];
                    for (int i = k; i <= k + n; i++) {
                        if (i != j) {
                            mul = mul * (value - x[i]) / (x[j] - x[i]);
                        }
                    }
                    result = result + mul;
                }
            } else {
                for (int j = k + 1; j >= k - n + 1; j--) {
                    mul = y[j];
                    for (int i = k + 1; i >= k - n + 1; i--) {
                        if (i != j) {
                            mul = mul * (value - x[i]) / (x[j] - x[i]);
                        }
                    }
                    result = result + mul;
                }
            }
        }

        return result;
    }

    public double[] getError() {
        if (error != null) {
            return error;
        }

        error = new double[numOfPoints];
        for (int i = 0; i < error.length; i++) {
            double value = deltaH / 2.0 + i * deltaH;
            error[i] = Math.abs(interpolate(value)
                    - function.value(value));
        }

        return error;
    }

    public double getMeanError() {
        if (meanError > -1.0) {
            return meanError;
        }
        getError();
        double result = 0;
        for (int i = 0; i < error.length; i++) {
            result += error[i];
        }
        result /= error.length;
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < x.length; i++) {
            builder.append("(" + x[i] + ", " + y[i] + ") \n");
        }
        return builder.toString();
    }

    public Function getFunction() {
        return function;
    }

}