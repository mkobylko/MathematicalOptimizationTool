package com.project.mmdo.model;

import java.util.Arrays;
import java.util.Objects;

public class ObjectiveFunction {

    public ObjectValue[] coefficients;
    public boolean isEarlierWasMin;

    public int quantityOfInitialVal;

    private OptimizationType optimizationType;

    public enum OptimizationType {
        MINIMIZE, MAXIMIZE
    }

    public ObjectiveFunction(ObjectValue[] coefficients, int quantityOfInitialVal, OptimizationType optimizationType, boolean isEarlierWasMin) {
        this.coefficients = coefficients;
        this.quantityOfInitialVal = quantityOfInitialVal;
        this.optimizationType = optimizationType;
        this.isEarlierWasMin = isEarlierWasMin;
    }

    public void setOptimizationType(OptimizationType optimizationType) {
        this.optimizationType = optimizationType;
    }

    public void setEarlierWasMin() {
        this.isEarlierWasMin = true;
    }

    public OptimizationType getOptimizationType() {
        return optimizationType;
    }

    public boolean isEarlierWasMin() {
        return isEarlierWasMin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObjectiveFunction that = (ObjectiveFunction) o;
        return isEarlierWasMin == that.isEarlierWasMin && quantityOfInitialVal == that.quantityOfInitialVal && Arrays.equals(coefficients, that.coefficients) && optimizationType == that.optimizationType;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(isEarlierWasMin, quantityOfInitialVal, optimizationType);
        result = 31 * result + Arrays.hashCode(coefficients);
        return result;
    }
}
