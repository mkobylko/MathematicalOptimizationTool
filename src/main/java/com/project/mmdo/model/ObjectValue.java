package com.project.mmdo.model;

import java.text.DecimalFormat;

import static com.project.mmdo.model.Rat.ZERO;



public class ObjectValue {

    public Rat coefficientM;

    public Rat numAfter;

    public ObjectValue(Rat coefficientM, Rat numAfter) {
        this.coefficientM = coefficientM;
        this.numAfter = numAfter;
    }


    @Override
    public String toString() {
        if (coefficientM.equalsTo(ZERO) && numAfter.equalsTo(ZERO)) {
            return "0";
        }
        DecimalFormat df = new DecimalFormat("0.#");
        StringBuilder sb = new StringBuilder();
        if (!coefficientM.equalsTo(ZERO)) {
            sb.append(df.format(Rat.ratToDouble(coefficientM)));
            sb.append("M");
            if (numAfter.greaterThan(ZERO)) {
                sb.append("+");
            }
        }
        if (!numAfter.equalsTo(ZERO)) {
            sb.append(df.format(Rat.ratToDouble(numAfter)));
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObjectValue that = (ObjectValue) o;
        return coefficientM.equalsTo(that.coefficientM) && numAfter.equalsTo(that.numAfter);
    }

}
