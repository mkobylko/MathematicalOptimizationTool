package com.project.mmdo.model;

import java.util.Objects;

public class FunctionValInTable {

    public ObjectValue value;

    public int valNum;

    public FunctionValInTable(ObjectValue functionValues, int valNum) {
        this.value = functionValues;
        this.valNum = valNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FunctionValInTable that = (FunctionValInTable) o;
        return valNum == that.valNum && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, valNum);
    }
}
