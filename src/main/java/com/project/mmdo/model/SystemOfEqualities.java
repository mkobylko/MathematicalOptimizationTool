package com.project.mmdo.model;

import java.util.List;
import java.util.Objects;

public class SystemOfEqualities {

    //список екземплярів класу рівності
    private List<Equality> equalities;

    public SystemOfEqualities(List<Equality> equalities) {
        this.equalities = equalities;
    }
    public void addEquality(Equality equality) {
        equalities.add(equality);
    }

    public List<Equality> getEqualities() {
        return equalities;
    }


    public int count() {
        return equalities.size();
    }

    public Equality getEquality(int index) {
        if (index < 0 || index >= equalities.size()) {
            throw new IndexOutOfBoundsException("out of value!!!");
        }
        return equalities.get(index);
    }

    public double[] getEqualityCoefficients(int index) {
        Equality equality = getEquality(index);
        return equality.coefficients;
    }

    public double[] getAllValues() {
        double[] values = new double[equalities.size()];
        for (int i = 0; i < equalities.size(); i++) {
            values[i] = equalities.get(i).getValue();
        }
        return values;
    }

    public double[][] getAllCoefficients() {
        int numEqualities = equalities.size();
        int numCoefficients = equalities.get(0).coefficients.length;

        double[][] allCoefficients = new double[numEqualities][numCoefficients];

        for (int i = 0; i < numEqualities; i++) {
            allCoefficients[i] = equalities.get(i).coefficients;
        }

        return allCoefficients;    }

    @Override
    public String toString() {
        return "SystemOfEqualities{" +
                "equalities=" + equalities +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SystemOfEqualities that = (SystemOfEqualities) o;
        return Objects.equals(equalities, that.equalities);
    }

}
