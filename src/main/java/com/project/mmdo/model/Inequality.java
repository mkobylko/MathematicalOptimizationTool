package com.project.mmdo.model;
import java.util.Arrays;
import java.util.Objects;

public class Inequality {

    public double[] coefficients;

    private InequalitySymbol symbol;

    public enum InequalitySymbol {
        LESS_EQUAL_THAN, GREATER_EQUAL_THAN, EQUAL
    }

    public double value;


    public Inequality(double[] coefficients, InequalitySymbol symbol, double result) {
        this.coefficients = coefficients;
        this.symbol = symbol;
        this.value = result;
    }

    public void setSymbol(InequalitySymbol newSymbol) {
        this.symbol = newSymbol;
    }

    public InequalitySymbol getSymbol() {
        return symbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inequality that = (Inequality) o;
        return Double.compare(value, that.value) == 0 && Arrays.equals(coefficients, that.coefficients) && symbol == that.symbol;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(symbol, value);
        result = 31 * result + Arrays.hashCode(coefficients);
        return result;
    }

    @Override
    public String toString() {
        return "Inequality{" +
                "coefficients=" + Arrays.toString(coefficients) +
                ", symbol=" + symbol +
                ", value=" + value +
                '}';
    }
}
