package com.project.mmdo.model;

import java.util.Arrays;
import java.util.Objects;

public class Equality {

    public double[] coefficients;

    public double value;


    public Equality(double[] coefficients, double result) {
        this.coefficients = coefficients;
        this.value = result;
    }

    @Override
    public String toString() {
        return "Equality{" +
                "coefficients=" + Arrays.toString(coefficients) +
                ", value=" + value +
                '}';
    }

    public void printCoefficients() {
        System.out.println(Arrays.toString(coefficients));
    }

    public double getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equality equality = (Equality) o;
        return Double.compare(value, equality.value) == 0 && Arrays.equals(coefficients, equality.coefficients);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(value);
        result = 31 * result + Arrays.hashCode(coefficients);
        return result;
    }
}
