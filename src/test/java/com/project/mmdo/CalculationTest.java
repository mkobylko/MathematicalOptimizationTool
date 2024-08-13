package com.project.mmdo;

import com.project.mmdo.model.*;
import org.junit.jupiter.api.Test;

import static com.project.mmdo.model.Rat.*;
import static org.junit.jupiter.api.Assertions.*;

class CalculationTest {

    @Test
    void isIndexesHaveNegativeValueTest1() {
        Calculation calc = new Calculation();
        ObjectValue[] indexes = {
                new ObjectValue(ONE, ZERO),
                new ObjectValue(ONE, ONE),
                new ObjectValue(ZERO, ONE)
        };
        boolean result = calc.isIndexesHaveNegativeValue(indexes, false);
        assertFalse(result);
    }

    @Test
    void isIndexesHaveNegativeValueTest2() {
        Calculation calc = new Calculation();
        ObjectValue[] indexes = {
                new ObjectValue(new Rat(-1), ZERO),
                new ObjectValue(ONE, ONE),
                new ObjectValue(ZERO, ONE)
        };
        boolean result = calc.isIndexesHaveNegativeValue(indexes, false);
        assertTrue(result);
    }

    @Test
    void isIndexesHaveNegativeValueTest3() {
        Calculation calc = new Calculation();
        ObjectValue[] indexes = {
                new ObjectValue(new Rat(-1), ZERO),
                new ObjectValue(ONE, ONE),
                new ObjectValue(ZERO, ONE)
        };
        boolean result = calc.isIndexesHaveNegativeValue(indexes, true);
        assertFalse(result);
    }

    @Test
    void multiplyTableTest() {
        SimplexTable table = new SimplexTable(
                new ObjectValue[]{new ObjectValue(ONE, ZERO), new ObjectValue(ONE, ZERO)},
                new SimplexTableRow[]{
                        new SimplexTableRow(6, new Rat[]{new Rat(10), new Rat(2), new Rat(4), ZERO, MINUS_ONE, ZERO, ZERO}),
                        new SimplexTableRow(7, new Rat[]{new Rat(10), new Rat(3), new Rat(2), new Rat(2), ZERO, MINUS_ONE, ONE})
                },
                new FunctionValInTable[]{
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(5)), 1),
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(6)), 2),
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(6)), 3),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 4),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 5),
                        new FunctionValInTable(new ObjectValue(ONE, ZERO), 6),
                        new FunctionValInTable(new ObjectValue(ONE, ZERO), 7)
                }
        );

        SimplexTableRow[] rows = Calculation.multiplyTable(table, 0, 2);

        assertEquals(2, rows.length);
        assertArrayEquals(new Rat[]{new Rat(10, 4), new Rat(1, 2), ONE, ZERO, new Rat(-1, 4), ZERO, ZERO}, rows[0].row);
        assertArrayEquals(new Rat[]{new Rat(5), new Rat(2), ZERO, new Rat(2), new Rat(1, 2), MINUS_ONE, ONE}, rows[1].row);

    }

    @Test
    void calculateIndexesTest() {
        Calculation calc = new Calculation();
        SimplexTable table = new SimplexTable(
                new ObjectValue[]{
                        new ObjectValue(new Rat(-1), ZERO),
                        new ObjectValue(new Rat(-1), ZERO),
                        new ObjectValue(ZERO, new Rat(-5))
                },
                new SimplexTableRow[]{
                        new SimplexTableRow(5, new Rat[]{new Rat(20), ZERO, new Rat(8, 5), new Rat(-1), ZERO, new Rat(1, 5), ONE, ZERO}),
                        new SimplexTableRow(6, new Rat[]{new Rat(5), ZERO, ONE, ZERO, new Rat(-1), ZERO, ZERO, ONE}),
                        new SimplexTableRow(0, new Rat[]{new Rat(30), ONE, new Rat(2, 5), ZERO, ZERO, new Rat(-1, 5), ZERO, ZERO})
                },
                new FunctionValInTable[]{
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(-5)), 1),
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(-30)), 2),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 3),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 4),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 5),
                        new FunctionValInTable(new ObjectValue(new Rat(-1), ZERO), 6),
                        new FunctionValInTable(new ObjectValue(new Rat(-1), ZERO), 7)
                }
        );
        calc.calculateIndexes(table);

        assertArrayEquals(
                new ObjectValue[]{
                        new ObjectValue(new Rat(-25), new Rat(-150)),
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(new Rat(-13, 5), new Rat(28)),
                        new ObjectValue(ONE, ZERO),
                        new ObjectValue(ONE, ZERO),
                        new ObjectValue(new Rat(-1, 5), ONE),
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(ZERO, ZERO)
                },
                table.indexes
        );
    }

    @Test
    void calculateIndexesTest2() {
        Calculation calc = new Calculation();
        SimplexTable table = new SimplexTable(
                new ObjectValue[]{
                        new ObjectValue(new Rat(-1), ZERO),
                        new ObjectValue(new Rat(-1), ZERO),
                        new ObjectValue(new Rat(-1), ZERO)
                },
                new SimplexTableRow[]{
                        new SimplexTableRow(5, new Rat[]{new Rat(50), ONE, new Rat(2), new Rat(-1), ZERO, ZERO, ONE, ZERO, ZERO}),
                        new SimplexTableRow(6, new Rat[]{new Rat(5), ZERO, ONE, ZERO, new Rat(-1), ZERO, ZERO, ONE, ZERO}),
                        new SimplexTableRow(7, new Rat[]{new Rat(150), new Rat(5), new Rat(2), ZERO, ZERO, new Rat(-1), ZERO, ZERO, ONE})
                },
                new FunctionValInTable[]{
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(-5)), 1),
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(-30)), 2),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 3),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 4),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 5),
                        new FunctionValInTable(new ObjectValue(new Rat(-1), ZERO), 6),
                        new FunctionValInTable(new ObjectValue(new Rat(-1), ZERO), 7),
                        new FunctionValInTable(new ObjectValue(new Rat(-1), ZERO), 8)
                }
        );

        calc.calculateIndexes(table);

        ObjectValue[] expectedIndexes = new ObjectValue[]{
                new ObjectValue(new Rat(-205), ZERO),
                new ObjectValue(new Rat(-6), new Rat(5)),
                new ObjectValue(new Rat(-5), new Rat(30)),
                new ObjectValue(ONE, ZERO),
                new ObjectValue(ONE, ZERO),
                new ObjectValue(ONE, ZERO),
                new ObjectValue(ZERO, ZERO),
                new ObjectValue(ZERO, ZERO),
                new ObjectValue(ZERO, ZERO)
        };

        assertArrayEquals(expectedIndexes, table.indexes
        );
    }

    @Test
    void calculateIndexesTest3() {
        Calculation calc = new Calculation();
        SimplexTable table = new SimplexTable(
                new ObjectValue[]{
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(new Rat(-1), ZERO)
                },
                new SimplexTableRow[]{
                        new SimplexTableRow(2, new Rat[]{new Rat(3), ONE, new Rat(-2), ONE, ZERO, ZERO, ZERO, ZERO}),
                        new SimplexTableRow(3, new Rat[]{new Rat(10), new Rat(2), new Rat(-1), ZERO, ONE, ZERO, ZERO, ZERO}),
                        new SimplexTableRow(4, new Rat[]{new Rat(5), new Rat(-3), ONE, ZERO, ZERO, ONE, ZERO, ZERO}),
                        new SimplexTableRow(6, new Rat[]{new Rat(3), new Rat(-1), ONE, ZERO, ZERO, ZERO, new Rat(-1), ONE})
                },
                new FunctionValInTable[]{
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(-3)), 1),
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(-2)), 2),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 3),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 4),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 5),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 6),
                        new FunctionValInTable(new ObjectValue(new Rat(-1), ZERO), 7)

                }

        );
        table.setIndexes(
                new ObjectValue[]{
                        new ObjectValue(new Rat(-3), ZERO),
                        new ObjectValue(ONE, new Rat(3)),
                        new ObjectValue(new Rat(-1), new Rat(2)),
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(ONE, ZERO),
                        new ObjectValue(ZERO, ZERO)
                }
        );
        calc.calculateIndexes(table);
//
        assertArrayEquals(
                new ObjectValue[]{
                        new ObjectValue(new Rat(-3), ZERO),
                        new ObjectValue(ONE, new Rat(3)),
                        new ObjectValue(new Rat(-1), new Rat(2)),
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(ONE, ZERO),
                        new ObjectValue(ZERO, ZERO)
                },
                table.indexes
        );
    }


    @Test
    void deleteColumnWithArtificialValTest() {
        Calculation calc = new Calculation();
        SimplexTable table = new SimplexTable(
                new ObjectValue[]{
                        new ObjectValue(new Rat(-1), ZERO),
                        new ObjectValue(new Rat(-1), ZERO)
                },
                new SimplexTableRow[]{
                        new SimplexTableRow(6, new Rat[]{new Rat(10), new Rat(2), new Rat(4), ZERO, new Rat(-1), ZERO, ONE, ZERO}),
                        new SimplexTableRow(7, new Rat[]{new Rat(2), new Rat(3), new Rat(2), new Rat(2), ZERO, new Rat(-1), ZERO, ONE})
                },
                new FunctionValInTable[]{
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(-5)), 1),
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(-6)), 2),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 3),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 4),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 5),
                        new FunctionValInTable(new ObjectValue(new Rat(-1), ZERO), 6),
                        new FunctionValInTable(new ObjectValue(new Rat(-1), ZERO), 7)

                }

        );
        table.setIndexes(
                new ObjectValue[]{
                        new ObjectValue(new Rat(-20), ZERO),
                        new ObjectValue(new Rat(-5), new Rat(5)),
                        new ObjectValue(new Rat(-6), new Rat(6)),
                        new ObjectValue(new Rat(-2), new Rat(6)),
                        new ObjectValue(ONE, ZERO),
                        new ObjectValue(ONE, ZERO),
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(ZERO, ZERO)
                }
        );

        calc.deleteColumnWithArtificialVal(table, 0);


        assertArrayEquals(
                new SimplexTableRow[]{
                        new SimplexTableRow(6, new Rat[]{new Rat(10), new Rat(2), new Rat(4), ZERO, new Rat(-1), ZERO, ZERO}),
                        new SimplexTableRow(7, new Rat[]{new Rat(2), new Rat(3), new Rat(2), new Rat(2), ZERO, new Rat(-1), ONE})
                },
                table.mainTable
        );
        assertArrayEquals(
                new FunctionValInTable[]{
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(-5)), 1),
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(-6)), 2),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 3),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 4),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 5),
                        new FunctionValInTable(new ObjectValue(new Rat(-1), ZERO), 7)

                }, table.functionValues
        );
        assertArrayEquals(
                new ObjectValue[]{
                        new ObjectValue(new Rat(-20), ZERO),
                        new ObjectValue(new Rat(-5), new Rat(5)),
                        new ObjectValue(new Rat(-6), new Rat(6)),
                        new ObjectValue(new Rat(-2), new Rat(6)),
                        new ObjectValue(ONE, ZERO),
                        new ObjectValue(ONE, ZERO),
                        new ObjectValue(ZERO, ZERO)
                }, table.indexes
        );

    }

    @Test
    void deleteColumnWithArtificialValTest2() {
        Calculation calc = new Calculation();
        SimplexTable table = new SimplexTable(
                new ObjectValue[]{
                        new ObjectValue(ZERO, new Rat(-6)),
                        new ObjectValue(new Rat(-1), ZERO)
                },
                new SimplexTableRow[]{
                        new SimplexTableRow(2, new Rat[]{new Rat(5, 2), new Rat(1, 2), ONE, ZERO, new Rat(-1, 4), ZERO, ZERO}),
                        new SimplexTableRow(7, new Rat[]{new Rat(5), new Rat(2), ZERO, new Rat(2), new Rat(1, 2), new Rat(-1), ONE})
                },
                new FunctionValInTable[]{
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(-5)), 1),
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(-6)), 2),
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(-6)), 3),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 4),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 5),
                        new FunctionValInTable(new ObjectValue(new Rat(-1), ZERO), 7)

                }

        );
        table.setIndexes(
                new ObjectValue[]{
                        new ObjectValue(new Rat(-5), new Rat(-15)),
                        new ObjectValue(new Rat(-2), new Rat(2)),
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(new Rat(-2), new Rat(6)),
                        new ObjectValue(new Rat(-1, 2), new Rat(-3, 2)),
                        new ObjectValue(ONE, ZERO),
                        new ObjectValue(ZERO, ZERO)
                }
        );

        calc.deleteColumnWithArtificialVal(table, 1);


        assertArrayEquals(
                new SimplexTableRow[]{
                        new SimplexTableRow(2, new Rat[]{new Rat(5, 2), new Rat(1, 2), ONE, ZERO, new Rat(-1, 4), ZERO}),
                        new SimplexTableRow(7, new Rat[]{new Rat(5), new Rat(2), ZERO, new Rat(2), new Rat(1, 2), new Rat(-1)})
                },
                table.mainTable
        );
        assertArrayEquals(
                new FunctionValInTable[]{
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(-5)), 1),
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(-6)), 2),
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(-6)), 3),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 4),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 5)

                }, table.functionValues
        );
        assertArrayEquals(
                new ObjectValue[]{
                        new ObjectValue(new Rat(-5), new Rat(-15)),
                        new ObjectValue(new Rat(-2), new Rat(2)),
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(new Rat(-2), new Rat(6)),
                        new ObjectValue(new Rat(-1, 2), new Rat(-3, 2)),
                        new ObjectValue(ONE, ZERO)
                }, table.indexes
        );

    }

    @Test
    void getMinColumnTest1() {
        Calculation calc = new Calculation();
        SimplexTable table = new SimplexTable(
                new ObjectValue[]{
                        new ObjectValue(new Rat(-1), ZERO),
                        new ObjectValue(new Rat(-1), ZERO),
                        new ObjectValue(ZERO, new Rat(-5))
                },
                new SimplexTableRow[]{
                        new SimplexTableRow(5, new Rat[]{new Rat(20), ZERO, new Rat(8, 5), MINUS_ONE, ZERO, new Rat(1, 5), ONE, ZERO}),
                        new SimplexTableRow(6, new Rat[]{new Rat(5), ZERO, ONE, ZERO, MINUS_ONE, ZERO, ZERO, ONE}),
                        new SimplexTableRow(0, new Rat[]{new Rat(30), ONE, new Rat(2, 5), ZERO, ZERO, new Rat(-1, 5), ZERO, ZERO})
                },

                new FunctionValInTable[]{
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(-5)), 1),
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(-30)), 2),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 3),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 4),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 5),
                        new FunctionValInTable(new ObjectValue(new Rat(-1), ZERO), 6),
                        new FunctionValInTable(new ObjectValue(new Rat(-1), ZERO), 7),
                }

        );
        table.setIndexes(
                new ObjectValue[]{
                        new ObjectValue(new Rat(-25), new Rat(-150)),
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(new Rat(-13, 5), new Rat(28)),
                        new ObjectValue(ONE, ZERO),
                        new ObjectValue(ONE, ZERO),
                        new ObjectValue(new Rat(-1, 5), ONE),
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(ZERO, ZERO)
                }
        );


        int minCol = calc.getMinColumn(table);

        assertEquals(2, minCol);
    }


    @Test
    void getMinColumnTest2() {
        Calculation calc = new Calculation();
        SimplexTable table = new SimplexTable(
                new ObjectValue[]{
                        new ObjectValue(new Rat(-1), new Rat(0)),
                        new ObjectValue(new Rat(-1), new Rat(0)),
                        new ObjectValue(new Rat(0), new Rat(-5))
                },
                new SimplexTableRow[]{
                        new SimplexTableRow(5, new Rat[]{new Rat(20), ZERO, new Rat(8, 5), new Rat(-1), ZERO, new Rat(1, 5), ONE, ZERO}),
                        new SimplexTableRow(6, new Rat[]{new Rat(5), ZERO, ONE, ZERO, new Rat(-1), ZERO, ZERO, ONE}),
                        new SimplexTableRow(0, new Rat[]{new Rat(30), ONE, new Rat(2, 5), ZERO, ZERO, new Rat(-1, 5), ZERO, ZERO})
                },
                new FunctionValInTable[]{
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(-5)), 1),
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(-30)), 2),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 3),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 4),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 5),
                        new FunctionValInTable(new ObjectValue(new Rat(-1), ZERO), 6),
                        new FunctionValInTable(new ObjectValue(new Rat(-1), ZERO), 7),
                }
        );
        table.setIndexes(
                new ObjectValue[]{
                        new ObjectValue(new Rat(-25), new Rat(-150)),
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(new Rat(-13, 5), new Rat(28)),
                        new ObjectValue(ONE, ZERO),
                        new ObjectValue(ONE, ZERO),
                        new ObjectValue(new Rat(-1, 5), ONE),
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(ZERO, ZERO)
                }
        );


        int minCol = calc.getMinColumn(table);

        assertEquals(2, minCol);
    }

    @Test
    void getMinRow() {
        Calculation calc = new Calculation();
        SimplexTable table = new SimplexTable(
                new ObjectValue[]{
                        new ObjectValue(new Rat(-1), new Rat(0)),
                        new ObjectValue(new Rat(-1), new Rat(0)),
                        new ObjectValue(new Rat(0), new Rat(-5))
                },
                new SimplexTableRow[]{
                        new SimplexTableRow(5, new Rat[]{new Rat(20), ZERO, new Rat(8, 5), new Rat(-1), ZERO, new Rat(1, 5), ONE, ZERO}),
                        new SimplexTableRow(6, new Rat[]{new Rat(5), ZERO, ONE, ZERO, new Rat(-1), ZERO, ZERO, ONE}),
                        new SimplexTableRow(0, new Rat[]{new Rat(30), ONE, new Rat(2, 5), ZERO, ZERO, new Rat(-1, 5), ZERO, ZERO})
                },
                new FunctionValInTable[]{
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(-5)), 1),
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(-30)), 2),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 3),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 4),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 5),
                        new FunctionValInTable(new ObjectValue(new Rat(-1), ZERO), 6),
                        new FunctionValInTable(new ObjectValue(new Rat(-1), ZERO), 7),
                }
        );
        table.setIndexes(
                new ObjectValue[]{
                        new ObjectValue(new Rat(-25), new Rat(-150)),
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(new Rat(-13, 5), new Rat(28)),
                        new ObjectValue(ONE, ZERO),
                        new ObjectValue(ONE, ZERO),
                        new ObjectValue(new Rat(-1, 5), ONE),
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(ZERO, ZERO)
                }
        );


        int minRow = calc.getMinRow(table, 2);

        assertEquals(1, minRow);
    }


    @Test
    void changeInBasisTest1() {

        Calculation calc = new Calculation();
        SimplexTable table = new SimplexTable(
                new ObjectValue[]{
                        new ObjectValue(new Rat(-1), new Rat(0)),
                        new ObjectValue(new Rat(-1), new Rat(0))
                },
                new SimplexTableRow[]{
                        new SimplexTableRow(6, new Rat[]{new Rat(10), new Rat(2), new Rat(4), ZERO, MINUS_ONE, ZERO, ONE, ZERO}),
                        new SimplexTableRow(7, new Rat[]{new Rat(10), new Rat(3), new Rat(2), new Rat(2), ZERO, MINUS_ONE, ZERO, ONE})
                },
                new FunctionValInTable[]{
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(-5)), 1),
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(-6)), 2),
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(-6)), 3),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 4),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 5),
                        new FunctionValInTable(new ObjectValue(new Rat(-1), ZERO), 6),
                        new FunctionValInTable(new ObjectValue(new Rat(-1), ZERO), 7)
                }
        );
        table.setIndexes(
                new ObjectValue[]{
                        new ObjectValue(new Rat(-20), ZERO),
                        new ObjectValue(new Rat(-5), new Rat(5)),
                        new ObjectValue(new Rat(-6), new Rat(6)),
                        new ObjectValue(new Rat(-2), new Rat(6)),
                        new ObjectValue(ONE, ZERO),
                        new ObjectValue(ONE, ZERO),
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(ZERO, ZERO)
                }
        );
        calc.changeInBasis(table, 0, 2);
        assertArrayEquals(
                new ObjectValue[]{
                        new ObjectValue(ZERO, new Rat(-6)),
                        new ObjectValue(new Rat(-1), ZERO)
                }, table.numThatInBasis
        );
        assertArrayEquals(
                new SimplexTableRow[]{
                        new SimplexTableRow(2, new Rat[]{new Rat(10), new Rat(2), new Rat(4), ZERO, MINUS_ONE, ZERO, ONE, ZERO}),
                        new SimplexTableRow(7, new Rat[]{new Rat(10), new Rat(3), new Rat(2), new Rat(2), ZERO, MINUS_ONE, ZERO, ONE})
                }, table.mainTable
        );
    }

    @Test
    void changeInBasisTest2() {

        Calculation calc = new Calculation();
        SimplexTable table = new SimplexTable(
                new ObjectValue[]{
                        new ObjectValue(ZERO, new Rat(-6)),
                        new ObjectValue(new Rat(-1), ZERO)
                },
                new SimplexTableRow[]{
                        new SimplexTableRow(2, new Rat[]{new Rat(5, 2), new Rat(1, 2), ONE, ZERO, new Rat(-1, 4), ZERO, ZERO}),
                        new SimplexTableRow(7, new Rat[]{new Rat(5), new Rat(2), ZERO, new Rat(2), new Rat(1, 2), new Rat(-1), ONE})
                },
                new FunctionValInTable[]{
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(-5)), 1),
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(-6)), 2),
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(-6)), 3),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 4),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 5),
                        new FunctionValInTable(new ObjectValue(new Rat(-1), ZERO), 7)

                }

        );
        table.setIndexes(
                new ObjectValue[]{
                        new ObjectValue(new Rat(-5), new Rat(-15)),
                        new ObjectValue(new Rat(-2), new Rat(2)),
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(new Rat(-2), new Rat(6)),
                        new ObjectValue(new Rat(-1, 2), new Rat(-3, 2)),
                        new ObjectValue(ONE, ZERO),
                        new ObjectValue(ZERO, ZERO)
                }
        );


        calc.changeInBasis(table, 1, 1);
        assertArrayEquals(
                new ObjectValue[]{
                        new ObjectValue(ZERO, new Rat(-6)),
                        new ObjectValue(ZERO, new Rat(-5))
                }, table.numThatInBasis
        );

        assertArrayEquals(
                new SimplexTableRow[]{
                        new SimplexTableRow(2, new Rat[]{new Rat(5, 2), new Rat(1, 2), ONE, ZERO, new Rat(-1, 4), ZERO, ZERO}),
                        new SimplexTableRow(1, new Rat[]{new Rat(5), new Rat(2), ZERO, new Rat(2), new Rat(1, 2), new Rat(-1), ONE})
                }, table.mainTable
        );
    }

    /*AAAAAAAAAAAAAAA*/
    @Test
    void getMinColumnTestNew() {
        Calculation calc = new Calculation();
        SimplexTable table = new SimplexTable(
                new ObjectValue[]{
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(ZERO, new Rat(1))
                },
                new SimplexTableRow[]{
                        new SimplexTableRow(4, new Rat[]{new Rat(20), ZERO, new Rat(-11, 40), new Rat(-1, 40), ONE, ZERO, new Rat(-3, 4)}),
                        new SimplexTableRow(5, new Rat[]{new Rat(10), ZERO, new Rat(-17, 40), new Rat(1, 40), ZERO, ONE, new Rat(-5, 5)}),
                        new SimplexTableRow(1, new Rat[]{new Rat(100), ONE, new Rat(5, 4), new Rat(3, 4), ZERO, ZERO, new Rat(5, 2)})
                },

                new FunctionValInTable[]{
                        new FunctionValInTable(new ObjectValue(ZERO, ONE), 1),
                        new FunctionValInTable(new ObjectValue(ZERO, ONE), 2),
                        new FunctionValInTable(new ObjectValue(ZERO, ONE), 3),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 4),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 5),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 6)
                }

        );
        table.setIndexes(
                new ObjectValue[]{
                        new ObjectValue(ZERO, new Rat(100)),
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(ZERO, new Rat(1, 4)),
                        new ObjectValue(ZERO, new Rat(-1, 4)),
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(ZERO, new Rat(5, 2))
                }
        );


        int minCol = calc.getMinColumn(table);

        assertEquals(3, minCol);
    }

    @Test
    void getMinRowTestNew() {
        Calculation calc = new Calculation();
        SimplexTable table = new SimplexTable(
                new ObjectValue[]{
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(ZERO, new Rat(1))
                },
                new SimplexTableRow[]{
                        new SimplexTableRow(4, new Rat[]{new Rat(20), ZERO, new Rat(-11, 40), new Rat(-1, 40), ONE, ZERO, new Rat(-3, 4)}),
                        new SimplexTableRow(5, new Rat[]{new Rat(10), ZERO, new Rat(-17, 40), new Rat(1, 40), ZERO, ONE, new Rat(-5, 5)}),
                        new SimplexTableRow(1, new Rat[]{new Rat(100), ONE, new Rat(5, 4), new Rat(3, 4), ZERO, ZERO, new Rat(5, 2)})
                },

                new FunctionValInTable[]{
                        new FunctionValInTable(new ObjectValue(ZERO, ONE), 1),
                        new FunctionValInTable(new ObjectValue(ZERO, ONE), 2),
                        new FunctionValInTable(new ObjectValue(ZERO, ONE), 3),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 4),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 5),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 6)
                }

        );
        table.setIndexes(
                new ObjectValue[]{
                        new ObjectValue(ZERO, new Rat(100)),
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(ZERO, new Rat(1, 4)),
                        new ObjectValue(ZERO, new Rat(-1, 4)),
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(ZERO, new Rat(5, 2))
                }
        );


        int minRow = calc.getMinRow(table, 3);

        assertEquals(2, minRow);
    }
}



