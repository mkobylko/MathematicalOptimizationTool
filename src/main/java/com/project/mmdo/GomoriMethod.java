package com.project.mmdo;

import com.project.mmdo.controllers.TablesWindowController;
import com.project.mmdo.model.*;

import java.util.Arrays;

import static com.project.mmdo.model.Rat.*;

public class GomoriMethod {


    public void gomoriCalculate(Result result, SimplexTable table, int quantityOfDoubleEl, ObjectiveFunction function, TablesWindowController controller) {
        //викликає метод який додає  до цільової функції необіхдні зміні
        //додає коофіцієнти цільової функції в таблицю
        newFunctionValue(table);

        newNumsInBasic(table);
        oldSimplexTableNewColumns(table);

        //сформуємо метод правильних відсічень для змінної яка не є цілим числом
        methodVidsichen(result, table, quantityOfDoubleEl, controller);

        System.out.println("First gomori table");
        System.out.println(table);

        //знаходимо індекси
        Calculation.calculateIndexes(table);

        controller.gomoriFirstTableToScreen(table);
//рахування симплекс таблиці поки не буде всі додатні в А0
        do {
            table = getCalculatedSimplexTable(table, controller);
            controller.allGomoriTableToScreen(table);
        } while (firstColWithNegatives(table));


        //перевіряти чи відповідь цілочисельна
        Result res = Calculation.getResult(table, function.quantityOfInitialVal, function.isEarlierWasMin);
        System.out.println("Масив розв'язків = ");
        System.out.println(Arrays.toString(res.resultsOfX));
        System.out.println("Функція = ");
        System.out.println(res.objFunctNum);

        Calculation.checkInteger(table, res, function, controller);
        controller.resultGomoriToScreen(res, true);

    }

    public static void newFunctionValue(SimplexTable table) {
        FunctionValInTable[] newCoef = Arrays.copyOf(table.functionValues, table.functionValues.length + 1);

        newCoef[newCoef.length - 1] = new FunctionValInTable(new ObjectValue(ZERO, ZERO), newCoef.length);
        newCoef[newCoef.length - 1].valNum = newCoef.length;

        table.functionValues = newCoef;
        System.out.println(table);


        for (FunctionValInTable functionValue : table.functionValues) {

            System.out.println("Function Values: " + functionValue.value + ", Val Num: " + functionValue.valNum);
        }
    }

    private boolean firstColWithNegatives(SimplexTable table) {

        for (int i = 0; i < table.getRows(); i++) {
            if (table.get(i, 0).lessThan(ZERO)) {
                return true;
            }
        }
        return false;
    }

    public static SimplexTable getCalculatedSimplexTable(SimplexTable table, TablesWindowController controller) {
        int minRow = getMinRow(table);
        int minCol = getMinCol(table, minRow);

        controller.elementGomoriToScreen(minRow, minCol, table);
        System.out.println(table.get(minRow, minCol));

        Calculation.changeInBasis(table, minRow, minCol);
        SimplexTableRow[] newMainTable = Calculation.multiplyTable(table, minRow, minCol);
        table = new SimplexTable(table.numThatInBasis, newMainTable, table.functionValues);
        Calculation.calculateIndexes(table);
        System.out.println("Нова таблиця порахована і індекси");
        System.out.println(table);
        System.out.println(Arrays.toString(table.indexes));
        return table;
    }

    public static void methodVidsichen(Result result, SimplexTable table, int quantityOfDoubleEl, TablesWindowController controller) {

        Rat[] vidsichenia = new Rat[table.getColumns()];
        for (int i = 0; i < vidsichenia.length; i++) {
            vidsichenia[i] = ZERO;
        }

        //якщо в результаті декілька х-сів дробові то береться той у якого більша дробова частина
        double doubleRes = 0;
        int indexOfDoubleRes = 0;

        if (quantityOfDoubleEl != 1) {
            for (int i = 0; i < result.resultsOfX.length; i++) {

                double fractionalPart = Rat.getFractional(result.resultsOfX[i]);

                if (fractionalPart != 0 && (fractionalPart > doubleRes)) {
                    doubleRes = fractionalPart;
                    indexOfDoubleRes = i;
                }
            }
        } else {
            for (int i = 0; i < result.resultsOfX.length; i++) {
                //якщо число дробове
                if (!result.resultsOfX[i].isInteger()) {
                    doubleRes = Rat.getFractional(result.resultsOfX[i]);
                    indexOfDoubleRes = i;

                }
            }
        }

/*
якщо число дробове:
    1.якщо число додатнє:
	    віднімаємо дробову частину

    2. якщо число від'ємне
	    якщо ціла частина > 1,
	        то додаємо цілу частину
	    якщо ціла частина < 1,
	        то 1 + дріб зі знаком мінус



  */
        for (int j = 0; j < table.getColumns(); j++) {

            //дістаємо кожен  елемент рядка -> х-с якого результат (+1, бо масив результатів починається з нуля)
            Rat doubleEl = table.get(table.getRowIndexByX(indexOfDoubleRes + 1), j);
            //1
            if (!doubleEl.isInteger() && doubleEl.greaterThan(ZERO)) {
                //знаходжу рядок х в симплекс таблиці
                vidsichenia[j] = (doubleEl.sub(Rat.wholePart(doubleEl))).mul(MINUS_ONE);

            }
            //2
            else if (!doubleEl.isInteger() && doubleEl.lessEqualsThan(ONE)) {

                if (Rat.wholePart(doubleEl).getNum() * (-1) > 0) {
                    vidsichenia[j] = ((doubleEl.add(Rat.wholePart(doubleEl)))).mul(MINUS_ONE);

                } else if (Rat.wholePart(doubleEl).getNum() * (-1) < 1) {
                    vidsichenia[j] = doubleEl.add(ONE).mul(MINUS_ONE);

                }
            }

        }

        vidsichenia[vidsichenia.length - 1] = ONE;
        System.out.println("vidsichenia" + Arrays.toString(vidsichenia));
        if (controller != null){
            controller.vidsichenyaToScreen(vidsichenia);
        }

        //add row to simplex table
        table.addTableRow(new SimplexTableRow(table.getColumns() - 1, vidsichenia));
    }

    public static void oldSimplexTableNewColumns(SimplexTable table) {
        int col = table.getColumns();
        //simplex table columns
        for (int i = 0; i < table.getRows(); i++) {
            table.mainTable[i].row = Arrays.copyOf(table.mainTable[i].row, col + 1);
            table.mainTable[i].row[table.getColumns() - 1] = ZERO;
        }

    }

    public static void newNumsInBasic(SimplexTable table) {
        //new num in basic
        table.numThatInBasis = Arrays.copyOf(table.numThatInBasis, table.numThatInBasis.length + 1);
        for (int i = table.numThatInBasis.length - 1; i < table.numThatInBasis.length; i++) {
            table.numThatInBasis[i] = new ObjectValue(ZERO, ZERO);
        }
    }


    public static int getMinCol(SimplexTable table, int minRow) {
        //метод для знаходження направляючого стовбця
        Rat minVal = new Rat(Integer.MAX_VALUE);
        int minCol = 0;
        for (int i = 1; i < table.getColumns(); i++) {

            /*напрямний стовпець – за найменшим за абсолютним
             значенням з відношень оцінок індексного рядка  до від’ємних елементів
             напрямного рядка*/

            boolean res = table.get(minRow, i).lessThan(ZERO) && !table.indexes[i].numAfter.equalsTo(ZERO);

            if ((res) && (table.indexes[i].numAfter.div(table.get(minRow, i).mul(MINUS_ONE))).lessThan(minVal)) {
                minCol = i;
                minVal = table.indexes[i].numAfter.div(table.get(minRow, i).mul(MINUS_ONE));
            }
        }
        System.out.println("min col " + minCol);
        return minCol;
    }

    public static int getMinRow(SimplexTable table) {
        //метод для знаходження направляючого рядка
        int minRow = 0;
        Rat minVal = null;/*new Rat();*/
        for (int i = 0; i < table.getRows(); i++) {
            //найментший від'ємний елемент
            if (minVal == null || (table.get(i, 0).lessThan(ZERO) && table.get(i, 0).lessThan(minVal))) {
                minRow = i;
                minVal = table.get(i, 0);
            }
        }
        System.out.println("min row " + minRow);
        return minRow;
    }


}
