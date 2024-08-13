package com.project.mmdo;

import com.project.mmdo.model.*;
import org.junit.jupiter.api.Test;

import static com.project.mmdo.model.Rat.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GomoriMethodTest {
    @Test
    void newFunctionValueTest() {
        SimplexTable table = new SimplexTable(
                new ObjectValue[]{
                        new ObjectValue(ZERO, new Rat(-6)),
                        new ObjectValue(ZERO, new Rat(-5))
                },
                new SimplexTableRow[]{
                        new SimplexTableRow(2, new Rat[]{new Rat(5, 4), ZERO, ONE, new Rat(-1, 2), new Rat(-374, 1000), new Rat(1, 4)}),
                        new SimplexTableRow(1, new Rat[]{new Rat(5, 2), ONE, ZERO, ONE, new Rat(1, 4), new Rat(-1, 2)})
                },
                new FunctionValInTable[]{
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(-5)), 1),
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(-6)), 2),
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(-6)), 3),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 4),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 5)
                }
        );
        table.setIndexes(
                new ObjectValue[]{
                        new ObjectValue(ZERO, new Rat(-20)),
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(ZERO, new Rat(4)),
                        new ObjectValue(ZERO, ONE),
                        new ObjectValue(ZERO, ONE)
                }
        );

        GomoriMethod gm = new GomoriMethod();
        gm.newFunctionValue(table);

        assertArrayEquals(
                new FunctionValInTable[]{
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(-5)), 1),
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(-6)), 2),
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(-6)), 3),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 4),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 5),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 6)
                }, table.functionValues
        );
    }

    @Test
    void newFunctionValueTest2() {
        SimplexTable table = new SimplexTable(
                new ObjectValue[]{
                        new ObjectValue(ZERO, new Rat(4)),
                        new ObjectValue(ZERO, ZERO)
                },
                new SimplexTableRow[]{
                        new SimplexTableRow(2, new Rat[]{new Rat(7, 4), new Rat(1, 2), ONE, new Rat(1, 4), ZERO}),
                        new SimplexTableRow(4, new Rat[]{new Rat(975, 100), new Rat(85, 10), ZERO, new Rat(-75, 100), ONE})
                },
                new FunctionValInTable[]{
                        new FunctionValInTable(new ObjectValue(ZERO, ONE), 1),
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(4)), 2),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 3),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 4)
                }
        );
        table.setIndexes(
                new ObjectValue[]{
                        new ObjectValue(ZERO, new Rat(7)),
                        new ObjectValue(ZERO, ONE),
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(ZERO, ONE),
                        new ObjectValue(ZERO, ZERO)
                }
        );

        GomoriMethod gm = new GomoriMethod();
        gm.newFunctionValue(table);

        assertArrayEquals(
                new FunctionValInTable[]{
                        new FunctionValInTable(new ObjectValue(ZERO, ONE), 1),
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(4)), 2),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 3),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 4),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 5)
                }, table.functionValues
        );
    }

    @Test
    void newNumsInBasicTest1() {
        GomoriMethod calc = new GomoriMethod();
        SimplexTable table = new SimplexTable(
                new ObjectValue[]{
                        new ObjectValue(ZERO, new Rat(-6)),
                        new ObjectValue(ZERO, new Rat(-5))
                },
                new SimplexTableRow[]{
                        new SimplexTableRow(2, new Rat[]{new Rat(125, 100), ZERO, ONE, new Rat(-50, 100), new Rat(-374, 1000), new Rat(25, 100)}),
                        new SimplexTableRow(1, new Rat[]{new Rat(5, 2), ONE, ZERO, ONE, new Rat(25, 100), new Rat(-50, 100)})
                },
                new FunctionValInTable[]{
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(-5)), 1),
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(-6)), 2),
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(-6)), 3),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 4),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 5),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 6)
                }
        );
        table.setIndexes(
                new ObjectValue[]{
                        new ObjectValue(ZERO, new Rat(-20)),
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(ZERO, new Rat(4)),
                        new ObjectValue(ZERO, ONE),
                        new ObjectValue(ZERO, ONE)
                }
        );

        calc.newNumsInBasic(table);

        assertArrayEquals(
                new ObjectValue[]{
                        new ObjectValue(ZERO, new Rat(-6)),
                        new ObjectValue(ZERO, new Rat(-5)),
                        new ObjectValue(ZERO, ZERO)
                }, table.numThatInBasis
        );
    }

    @Test
    void newNumsInBasicTest2() {
        GomoriMethod calc = new GomoriMethod();
        SimplexTable table = new SimplexTable(
                new ObjectValue[]{
                        new ObjectValue(ZERO, new Rat(4)),
                        new ObjectValue(ZERO, ZERO)
                },
                new SimplexTableRow[]{
                        new SimplexTableRow(2, new Rat[]{new Rat(175, 100), new Rat(50, 100), ONE, new Rat(25, 100), ZERO}),
                        new SimplexTableRow(4, new Rat[]{new Rat(975, 100), new Rat(850, 100), ZERO, new Rat(-75, 100), ONE})
                },
                new FunctionValInTable[]{
                        new FunctionValInTable(new ObjectValue(ZERO, ONE), 1),
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(4)), 2),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 3),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 4),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 5)
                }
        );
        table.setIndexes(
                new ObjectValue[]{
                        new ObjectValue(ZERO, new Rat(7)),
                        new ObjectValue(ZERO, ONE),
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(ZERO, ONE),
                        new ObjectValue(ZERO, ZERO)
                }
        );
        calc.newNumsInBasic(table);
        assertArrayEquals(
                new ObjectValue[]{
                        new ObjectValue(ZERO, new Rat(4)),
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(ZERO, ZERO)
                }, table.numThatInBasis
        );
    }

    @Test
    void oldSimplexTableNewColumnsTest1() {
        GomoriMethod calc = new GomoriMethod();
        SimplexTable table = new SimplexTable(
                new ObjectValue[]{
                        new ObjectValue(ZERO, new Rat(-6)),
                        new ObjectValue(ZERO, new Rat(-5)),
                        new ObjectValue(ZERO, ZERO)
                },
                new SimplexTableRow[]{
                        new SimplexTableRow(2, new Rat[]{new Rat(125, 100), ZERO, ONE, new Rat(-50, 100), new Rat(-374, 1000), new Rat(25, 100)}),
                        new SimplexTableRow(1, new Rat[]{new Rat(250, 100), ONE, ZERO, ONE, new Rat(25, 100), new Rat(-50, 100)})
                },
                new FunctionValInTable[]{
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(-5)), 1),
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(-6)), 2),
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(-6)), 3),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 4),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 5),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 6)
                }
        );
        table.setIndexes(
                new ObjectValue[]{
                        new ObjectValue(ZERO, new Rat(-20)),
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(ZERO, new Rat(4)),
                        new ObjectValue(ZERO, ONE),
                        new ObjectValue(ZERO, ONE)
                }
        );
        calc.oldSimplexTableNewColumns(table);
        assertArrayEquals(
                new SimplexTableRow[]{
                        new SimplexTableRow(2, new Rat[]{new Rat(125, 100), ZERO, ONE, new Rat(-50, 100), new Rat(-374, 1000), new Rat(25, 100), ZERO}),
                        new SimplexTableRow(1, new Rat[]{new Rat(250, 100), ONE, ZERO, ONE, new Rat(25, 100), new Rat(-50, 100), ZERO})
                }, table.mainTable
        );
    }

    @Test
    void oldSimplexTableNewColumnsTest2() {
        GomoriMethod calc = new GomoriMethod();
        SimplexTable table = new SimplexTable(
                new ObjectValue[]{
                        new ObjectValue(ZERO, new Rat(4)),
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(ZERO, ZERO)
                },
                new SimplexTableRow[]{
                        new SimplexTableRow(2, new Rat[]{new Rat(175, 100), new Rat(50, 100), ONE, new Rat(25, 100), ZERO}),
                        new SimplexTableRow(4, new Rat[]{new Rat(975, 100), new Rat(850, 100), ZERO, new Rat(-75, 100), ONE})
                },
                new FunctionValInTable[]{
                        new FunctionValInTable(new ObjectValue(ZERO, ONE), 1),
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(4)), 2),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 3),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 4),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 5)
                }
        );
        table.setIndexes(
                new ObjectValue[]{
                        new ObjectValue(ZERO, new Rat(7)),
                        new ObjectValue(ZERO, ONE),
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(ZERO, ONE),
                        new ObjectValue(ZERO, ZERO)
                }
        );
        calc.oldSimplexTableNewColumns(table);
        assertArrayEquals(
                new SimplexTableRow[]{
                        new SimplexTableRow(2, new Rat[]{new Rat(175, 100), new Rat(50, 100), ONE, new Rat(25, 100), ZERO, ZERO}),
                        new SimplexTableRow(4, new Rat[]{new Rat(975, 100), new Rat(850, 100), ZERO, new Rat(-75, 100), ONE, ZERO})
                }, table.mainTable
        );
    }

    @Test
    void methodVidsichenTest() {
        GomoriMethod calc = new GomoriMethod();
        SimplexTable table = new SimplexTable(
                new ObjectValue[]{
                        new ObjectValue(ZERO, new Rat(-6)),
                        new ObjectValue(ZERO, new Rat(-5)),
                        new ObjectValue(ZERO, ZERO)
                },
                new SimplexTableRow[]{
                        new SimplexTableRow(2, new Rat[]{new Rat(5, 4), ZERO, ONE, new Rat(-1, 2), new Rat(-3, 8), new Rat(1, 4), ZERO}),
                        new SimplexTableRow(1, new Rat[]{new Rat(5, 2), ONE, ZERO, ONE, new Rat(1, 4), new Rat(-1, 2), ZERO})
                },
                new FunctionValInTable[]{
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(-5)), 1),
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(-6)), 2),
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(-6)), 3),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 4),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 5),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 6)
                }
        );
        table.setIndexes(
                new ObjectValue[]{
                        new ObjectValue(ZERO, new Rat(-20)),
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(ZERO, new Rat(4)),
                        new ObjectValue(ZERO, ONE),
                        new ObjectValue(ZERO, ONE)
                }
        );
        Result res = new Result(new Rat(20), new Rat[]{new Rat(5, 2), new Rat(5, 4)});
        calc.methodVidsichen(res, table, 2, null);
        assertArrayEquals(
                new SimplexTableRow[]{
                        new SimplexTableRow(2, new Rat[]{new Rat(5, 4), ZERO, ONE, new Rat(-1, 2), new Rat(-3, 8), new Rat(1, 4), ZERO}),
                        new SimplexTableRow(1, new Rat[]{new Rat(5, 2), ONE, ZERO, ONE, new Rat(1, 4), new Rat(-1, 2), ZERO}),
                        new SimplexTableRow(6, new Rat[]{new Rat(-1, 2), ZERO, ZERO, ZERO, new Rat(-1, 4), new Rat(-1, 2), ONE})
                }, table.mainTable
        );
    }

    @Test
    void methodVidsichenTest2() {
        GomoriMethod calc = new GomoriMethod();
        SimplexTable table = new SimplexTable(
                new ObjectValue[]{
                        new ObjectValue(ZERO, new Rat(4)),
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(ZERO, ONE)
                },
                new SimplexTableRow[]{
                        new SimplexTableRow(2, new Rat[]{ONE, ZERO, ONE,ZERO, ZERO, ONE, ZERO}),
                        new SimplexTableRow(3, new Rat[]{new Rat(3, 5),ZERO, ZERO, ONE, new Rat(-1, 5), new Rat(-17, 5), ZERO}),
                        new SimplexTableRow(1, new Rat[]{new Rat(12, 10), ONE, ZERO, ZERO, new Rat(1, 10), new Rat(-3, 10), ZERO})
                },
                new FunctionValInTable[]{
                        new FunctionValInTable(new ObjectValue(ZERO, ONE), 1),
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(4)), 2),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 3),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 4),
                        new FunctionValInTable(new ObjectValue(ZERO, ZERO), 5)
                }
        );
        table.setIndexes(
                new ObjectValue[]{
                        new ObjectValue(ZERO, new Rat(52, 10)),
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(ZERO, new Rat(1, 10)),
                        new ObjectValue(ZERO, new Rat(37, 10))
                }
        );
        Result res = new Result(new Rat(52, 10), new Rat[]{new Rat(12, 10), ONE});
        calc.methodVidsichen(res, table, 1, null);
        assertArrayEquals(
                new SimplexTableRow[]{
                        new SimplexTableRow(2, new Rat[]{ONE, ZERO, ONE,ZERO, ZERO, ONE, ZERO}),
                        new SimplexTableRow(3, new Rat[]{new Rat(3, 5),ZERO, ZERO, ONE, new Rat(-1, 5), new Rat(-17, 5), ZERO}),
                        new SimplexTableRow(1, new Rat[]{new Rat(12, 10), ONE, ZERO, ZERO, new Rat(1, 10), new Rat(-3, 10), ZERO}),
                        new SimplexTableRow(6, new Rat[]{new Rat(-2, 10), ZERO, ZERO, ZERO, new Rat(-1, 10), new Rat(-7, 10), ONE})
                }, table.mainTable
        );
    }
    @Test
    void methodVidsichenTest3() {
        GomoriMethod calc = new GomoriMethod();
        SimplexTable table = new SimplexTable(
                new ObjectValue[]{
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(ZERO, ONE)
                },
                new SimplexTableRow[]{
                        new SimplexTableRow(4, new Rat[]{new Rat(70, 3), new Rat(1, 3), new Rat(-7, 30), ZERO, ONE, ZERO, new Rat(-2, 3), ZERO}),
                        new SimplexTableRow(5, new Rat[]{new Rat(20, 3), new Rat(-1, 30), new Rat(-7, 15), ZERO, ZERO, ONE, new Rat(-4, 3), ZERO}),
                        new SimplexTableRow(3, new Rat[]{new Rat(400, 3), new Rat(4, 3), new Rat(5, 3), ONE, ZERO, ZERO, new Rat(10, 3), ZERO})
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
                        new ObjectValue(ZERO, new Rat(400, 3)),
                        new ObjectValue(ZERO, new Rat(1, 3)),
                        new ObjectValue(ZERO, new Rat(2, 3)),
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(ZERO, new Rat(10, 3))
                }
        );
        Result res = new Result(new Rat(400, 3), new Rat[]{ZERO, ZERO, new Rat(400, 3)});
        calc.methodVidsichen(res, table, 1, null);
        assertArrayEquals(
                new SimplexTableRow[]{
                        new SimplexTableRow(4, new Rat[]{new Rat(70, 3), new Rat(1, 3), new Rat(-7, 30), ZERO, ONE, ZERO, new Rat(-2, 3), ZERO}),
                        new SimplexTableRow(5, new Rat[]{new Rat(20, 3), new Rat(-1, 30), new Rat(-7, 15), ZERO, ZERO, ONE, new Rat(-4, 3), ZERO}),
                        new SimplexTableRow(3, new Rat[]{new Rat(400, 3), new Rat(4, 3), new Rat(5, 3), ONE, ZERO, ZERO, new Rat(10, 3), ZERO}),
                        new SimplexTableRow(7, new Rat[]{new Rat(-1, 3),new Rat(-1, 3), new Rat(-2, 3), ZERO, ZERO, ZERO, new Rat(-1, 3), ONE})
                }, table.mainTable
        );
    }

    @Test
    void getMinRowTest1() {
        GomoriMethod calc = new GomoriMethod();

        //int res = calc.getMinRow(table);
        //
        //assertEquals(2, res);

    }

   /**/
   @Test
   void getMinRowTest2() {
       GomoriMethod calc = new GomoriMethod();
       SimplexTable table = new SimplexTable(
               new ObjectValue[]{
                       new ObjectValue(ZERO, new Rat(4)),
                       new ObjectValue(ZERO, new Rat(0)),
                       new ObjectValue(ZERO, new Rat(0))
               },
               new SimplexTableRow[]{
                       new SimplexTableRow(2, new Rat[]{new Rat(175, 100), new Rat(50, 100), new Rat(1, 1), new Rat(25, 100), new Rat(0, 1), new Rat(0, 1)}),
                       new SimplexTableRow(4, new Rat[]{new Rat(975, 100), new Rat(850, 100), new Rat(0, 1), new Rat(-75, 100), new Rat(1, 1), new Rat(0, 1)}),
                       new SimplexTableRow(5, new Rat[]{new Rat(-75, 100), new Rat(-50, 100), new Rat(0, 1), new Rat(-25, 100), new Rat(0, 1), new Rat(1, 1)})
               },
               new FunctionValInTable[]{
                       new FunctionValInTable(new ObjectValue(ZERO, new Rat(1)), 1),
                       new FunctionValInTable(new ObjectValue(ZERO, new Rat(4)), 2),
                       new FunctionValInTable(new ObjectValue(ZERO, new Rat(0)), 3),
                       new FunctionValInTable(new ObjectValue(ZERO, new Rat(0)), 4),
                       new FunctionValInTable(new ObjectValue(ZERO, new Rat(0)), 5)
               }
       );
       table.setIndexes(
               new ObjectValue[]{
                       new ObjectValue(ZERO, new Rat(7)),
                       new ObjectValue(ZERO, new Rat(1)),
                       new ObjectValue(ZERO, new Rat(0)),
                       new ObjectValue(ZERO, new Rat(1)),
                       new ObjectValue(ZERO, new Rat(0))
               }
       );
       int res = calc.getMinRow(table);

       assertEquals(2, res);
   }

    @Test
    void getMinColumnTest2() {
        GomoriMethod calc = new GomoriMethod();
        SimplexTable table = new SimplexTable(
                new ObjectValue[]{
                        new ObjectValue(ZERO, new Rat(4)),
                        new ObjectValue(ZERO, new Rat(0)),
                        new ObjectValue(ZERO, new Rat(0))
                },
                new SimplexTableRow[]{
                        new SimplexTableRow(2, new Rat[]{new Rat(175, 100), new Rat(50, 100), new Rat(1, 1), new Rat(25, 100), new Rat(0, 1), new Rat(0, 1)}),
                        new SimplexTableRow(4, new Rat[]{new Rat(975, 100), new Rat(850, 100), new Rat(0, 1), new Rat(-75, 100), new Rat(1, 1), new Rat(0, 1)}),
                        new SimplexTableRow(5, new Rat[]{new Rat(-75, 100), new Rat(-50, 100), new Rat(0, 1), new Rat(-25, 100), new Rat(0, 1), new Rat(1, 1)})
                },
                new FunctionValInTable[]{
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(1)), 1),
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(4)), 2),
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(0)), 3),
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(0)), 4),
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(0)), 5)
                }
        );
        table.setIndexes(
                new ObjectValue[]{
                        new ObjectValue(ZERO, new Rat(7)),
                        new ObjectValue(ZERO, new Rat(1)),
                        new ObjectValue(ZERO, new Rat(0)),
                        new ObjectValue(ZERO, new Rat(1)),
                        new ObjectValue(ZERO, new Rat(0)),
                        new ObjectValue(ZERO, new Rat(0))
                }
        );
        int res = calc.getMinCol(table, 2);

        assertEquals(1, res);
    }
    @Test
    void getMinColumnTest5() {
        GomoriMethod calc = new GomoriMethod();
        SimplexTable table = new SimplexTable(
                new ObjectValue[]{
                        new ObjectValue(ZERO, new Rat(-6)),
                        new ObjectValue(ZERO, new Rat(-5)),
                        new ObjectValue(ZERO, new Rat(0))
                },
                new SimplexTableRow[]{
                        new SimplexTableRow(2, new Rat[]{new Rat(5, 4), ZERO, ONE, new Rat(-1, 2), new Rat(-3, 8), new Rat(1, 4), ZERO}),
                        new SimplexTableRow(1, new Rat[]{new Rat(5, 2), ONE, ZERO, ONE, new Rat(1, 4), new Rat(-1, 2), ZERO}),
                        new SimplexTableRow(6, new Rat[]{new Rat(-1, 2), ZERO, ZERO,ZERO,  new Rat(-1,4), new Rat(-1, 2), ONE})
                },
                new FunctionValInTable[]{
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(-5)), 1),
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(-6)), 2),
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(-6)), 3),
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(0)), 4),
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(0)), 5),
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(0)), 6)

                }
        );
        table.setIndexes(
                new ObjectValue[]{
                        new ObjectValue(ZERO, new Rat(-20)),
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(ZERO, new Rat(4)),
                        new ObjectValue(ZERO, ONE),
                        new ObjectValue(ZERO, ONE),
                        new ObjectValue(ZERO, ZERO)
                }
        );
        int res = calc.getMinCol(table, 2);

        assertEquals(5, res);
    }
    @Test
    void getMinColumnTest3() {
        GomoriMethod calc = new GomoriMethod();
        SimplexTable table = new SimplexTable(
                new ObjectValue[]{
                        new ObjectValue(ZERO, new Rat(4)),
                        new ObjectValue(ZERO, new Rat(0)),
                        new ObjectValue(ZERO, new Rat(1))
                },
                new SimplexTableRow[]{
                        new SimplexTableRow(2, new Rat[]{ONE, ZERO,ONE, ZERO, ZERO, ONE}),
                        new SimplexTableRow(4, new Rat[]{new Rat(-3), ZERO, ZERO, new Rat(-5), ONE, new Rat(17)}),
                        new SimplexTableRow(1, new Rat[]{new Rat(3, 2), ONE, ZERO, new Rat(1,2), ZERO, new Rat(-2, 1)})
                },
                new FunctionValInTable[]{
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(1)), 1),
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(4)), 2),
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(0)), 3),
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(0)), 4),
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(0)), 5)
                }
        );
        table.setIndexes(
                new ObjectValue[]{
                        new ObjectValue(ZERO, new Rat(11, 2)),
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(ZERO, new Rat(1, 2)),
                        new ObjectValue(ZERO, ZERO),
                        new ObjectValue(ZERO, new Rat(2))
                }
        );
        int res = calc.getMinCol(table, 1);

        assertEquals(3, res);
    }

    @Test
    void getMinColumnTest4() {
        GomoriMethod calc = new GomoriMethod();
        SimplexTable table = new SimplexTable(
                new ObjectValue[]{
                        new ObjectValue(ZERO, new Rat(4)),
                        new ObjectValue(ZERO, new Rat(0)),
                        new ObjectValue(ZERO, new Rat(1)),
                        new ObjectValue(ZERO, new Rat(0))
                },
                new SimplexTableRow[]{
                        new SimplexTableRow(2, new Rat[]{new Rat(1, 1), new Rat(0, 1), new Rat(1, 1), new Rat(0, 1), new Rat(0, 1), new Rat(1, 1), new Rat(0, 1)}),
                        new SimplexTableRow(3, new Rat[]{new Rat(6, 10), new Rat(0, 1), new Rat(0, 1), new Rat(1, 1), new Rat(-2, 10), new Rat(-34, 10), new Rat(0, 1)}),
                        new SimplexTableRow(1, new Rat[]{new Rat(12, 10), new Rat(1, 1), new Rat(0, 1), new Rat(0, 1), new Rat(1, 10), new Rat(-3, 10), new Rat(0, 1)}),
                        new SimplexTableRow(6, new Rat[]{new Rat(-2, 10), new Rat(0, 1), new Rat(0, 1), new Rat(0, 1), new Rat(-1, 10), new Rat(-7, 10), new Rat(1, 1)})
                },
                new FunctionValInTable[]{
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(1)), 1),
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(4)), 2),
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(0)), 3),
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(0)), 4),
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(0)), 5),
                        new FunctionValInTable(new ObjectValue(ZERO, new Rat(0)), 6)
                }
        );
        table.setIndexes(
                new ObjectValue[]{
                        new ObjectValue(ZERO, new Rat(52, 10)),
                        new ObjectValue(ZERO, new Rat(0)),
                        new ObjectValue(ZERO, new Rat(0)),
                        new ObjectValue(ZERO, new Rat(0)),
                        new ObjectValue(ZERO, new Rat(1, 10)),
                        new ObjectValue(ZERO, new Rat(37, 10)),
                        new ObjectValue(ZERO, new Rat(0))
                }
        );
        int res = calc.getMinCol(table, 3);

        assertEquals(4, res);
    }

}