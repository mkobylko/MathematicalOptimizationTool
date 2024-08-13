package com.project.mmdo.model;

import org.junit.jupiter.api.Test;

import static com.project.mmdo.model.Rat.ONE;
import static org.junit.jupiter.api.Assertions.*;
import static com.project.mmdo.model.Rat.ZERO;


class RatTest {


    @Test
    void constructorCheck(){
        Rat rat1 = new Rat(1,2);
        assertEquals(rat1.getNum(), 1);
        assertEquals(rat1.getDen(), 2);

        Rat rat2 = new Rat(-1,2);
        assertEquals(rat2.getNum(), -1);
        assertEquals(rat2.getDen(), 2);

        Rat rat3 = new Rat(1,-2);
        assertEquals(rat3.getNum(), -1);
        assertEquals(rat3.getDen(), 2);

        Rat rat4 = new Rat(-1,-2);
        assertEquals(rat4.getNum(), 1);
        assertEquals(rat4.getDen(), 2);

        Rat rat5 = new Rat(0,-1);
        assertEquals(rat5.getNum(), 0);
        assertEquals(rat5.getDen(), 1);

    }


    @Test
    void doubleToRat() {

        double dbl = 6.78;

        Rat rt = new Rat(339, 50);

        Rat unknown = Rat.doubleToRat(dbl);
        assertEquals(rt, unknown);

    }

    @Test
    void getFractionalTest() {

        Rat rat = new Rat(5, 2);
        double unknown = Rat.getFractional(rat);
        assertEquals(unknown, 0.5);

    }

    @Test
    void getFractionalTest2() {

        Rat rat = new Rat(5, 4);
        double unknown = Rat.getFractional(rat);
        assertEquals(unknown, 0.25);

    }

    @Test
    void wholePart() {
        Rat rat = new Rat(5, 2);
        Rat unknown = Rat.wholePart(rat);
        assertEquals(unknown, new Rat(2, 1));
    }

    @Test
    void lessThanTest() {
        Rat rat = new Rat(1);
        boolean unknown = rat.lessThan(ZERO);
        assertEquals(unknown, false);
    }


    @Test
    void lessThan() {
        Rat rat = new Rat(-13, 5);
        boolean unknown = rat.lessThan(ZERO);
        assertEquals(unknown, true);
    }

    @Test
    void lessEqualsThan() {
        Rat rat = new Rat(-1, 2);
        boolean unknown = rat.lessEqualsThan(ONE);
        assertEquals(unknown, true);
    }
}