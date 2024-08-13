package com.project.mmdo.model;

public class Rat {

    public static Rat ZERO = new Rat(0);

    public static Rat MINUS_ONE = new Rat(-1);

    public static Rat ONE = new Rat(1);

    private final long num;

    private final long den;

    public Rat(long num) {
        this.num = num;
        this.den = 1;
    }

    public Rat(long num, long den) {
        long gcf = gcfLong(num, den);
        long newNum = num / gcf;
        long newDen = den / gcf;

        if (newDen < 0 && newNum >= 0) {
            this.num = -newNum;
            this.den = -newDen;
        } else {
            this.num = newNum;
            this.den = newDen;
        }
    }

    public static Rat doubleToRat(double value) {

        String string = String.valueOf(value);
        string = string.substring(string.indexOf('.') + 1);
        int numerator = Integer.parseInt(string);
        int denominator = (int) Math.pow(10, string.length());
        int gcf = gcf(numerator, denominator);
        if (gcf != 0) {
            numerator /= gcf;
            denominator /= gcf;
        }

        return new Rat(numerator + ((int) value) * denominator, denominator);

    }

    static int gcf(int valueA, int valueB) {
        if (valueB == 0) return valueA;
        else return gcf(valueB, valueA % valueB);
    }

    static long gcfLong(long valueA, long valueB) {
        if (valueB == 0) return valueA;
        else return gcfLong(valueB, valueA % valueB);
    }

    public long getNum() {
        return num;
    }

    public long getDen() {
        return den;
    }

    private Rat simplify() {
        long gcf = gcfLong(num, den);
        long newNum = num / gcf;
        long newDen = den / gcf;
        return new Rat(newNum, newDen);
    }

    public Rat add(Rat o) {
        return new Rat(num * o.den + o.num * den, den * o.den);

    }

    public Rat sub(Rat o) {

        return new Rat(num * o.den - o.num * den, den * o.den);
    }

    public Rat mul(Rat o) {
        return new Rat(num * o.num, den * o.den);
    }

    public Rat div(Rat o) {
        return new Rat(num * o.den, o.num * den);
    }

    public boolean isInteger() {
        return num % den == 0;
    }

    public boolean equalsTo(Rat o) {
        return num * o.den == o.num * den;
    }

    public boolean greaterThan(Rat o) {
        long leftNum = this.num * o.den;
        long rightNum = o.num * this.den;

        if ((this.den < 0 && o.den > 0) || (this.den > 0 && o.den < 0)) {
            return leftNum < rightNum;
        } else {
            return leftNum > rightNum;
        }
    }

    public boolean lessThan(Rat o) {
        long leftNum = this.num * o.den;
        long rightNum = o.num * this.den;

        if ((this.den < 0 && o.den > 0) || (this.den > 0 && o.den < 0)) {
            return leftNum > rightNum;
        } else {
            return leftNum < rightNum;
        }
    }

    public boolean greaterEqualsThan(Rat o) {
        return num * o.den >= o.num * den;
    }

    public boolean lessEqualsThan(Rat o) {
        if ((den < 0 && o.den > 0) || (den > 0 && o.den < 0)) {
            return num * o.den >= o.num * den;
        } else {
            return num * o.den <= o.num * den;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rat rat = (Rat) o;
        return rat.equalsTo(this);
    }

    public static double ratToDouble(Rat rat) {
        return (double) rat.num / rat.den;
    }
    public static double getFractional(Rat rat) {
        double result = (double) (rat.num % rat.den) / rat.den;
        return result;
    }

    public static Rat wholePart(Rat rat) {
        int wholePart = (int) (rat.num / rat.den);
        return new Rat(wholePart, 1);
    }

    @Override
    public String toString() {
        return String.valueOf((double) num / den);
    }
}
