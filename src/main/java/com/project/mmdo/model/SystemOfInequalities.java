package com.project.mmdo.model;

import java.util.List;
import java.util.Objects;

public class SystemOfInequalities {


    private List<Inequality> inequalities;

    public SystemOfInequalities(List<Inequality> inequalities) {
        this.inequalities = inequalities;
    }

    public void addInequality(Inequality inequality) {
        inequalities.add(inequality);
    }

    public List<Inequality> getInequalities() {
        return inequalities;
    }

    public int count() {
        return inequalities.size();
    }

    public Inequality getInequality(int index) {
        if (index < 0 || index >= inequalities.size()) {
            throw new IndexOutOfBoundsException("out of value!!!");
        }
        return inequalities.get(index);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SystemOfInequalities that = (SystemOfInequalities) o;
        return Objects.equals(inequalities, that.inequalities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inequalities);
    }
}
