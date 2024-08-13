package com.project.mmdo;

import com.project.mmdo.controllers.TablesWindowController;
import com.project.mmdo.model.*;
import javafx.fxml.FXMLLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.project.mmdo.model.Inequality.InequalitySymbol.GREATER_EQUAL_THAN;
import static com.project.mmdo.model.Inequality.InequalitySymbol.LESS_EQUAL_THAN;
import static com.project.mmdo.model.ObjectiveFunction.OptimizationType.MAXIMIZE;
import static com.project.mmdo.model.ObjectiveFunction.OptimizationType.MINIMIZE;
import static com.project.mmdo.model.Rat.MINUS_ONE;
import static com.project.mmdo.model.Rat.ZERO;

public class Calculation {
    public int artificialVars;
    public boolean hasResult = true;
    public TablesWindowController controller;

    public void calculate(SystemOfInequalities inequalities, ObjectiveFunction function, FXMLLoader loader) {


        countInequalityLess(inequalities);
        SystemOfEqualities equalities = createSystemOfEqualities(inequalities, function);

        //якщо цільова функція до min, то всі коофіцієнти *(-1) і тепер стремиться до max і якийсь флажок, що типу була на мін раніше
        if (function.getOptimizationType() == MINIMIZE) {
            function.setOptimizationType(MAXIMIZE);
            function.setEarlierWasMin();
            for (int i = 0; i < function.coefficients.length; i++) {
                function.coefficients[i].numAfter = function.coefficients[i].numAfter.mul(MINUS_ONE);
            }

        }

        ObjectValue[] newCoef = Arrays.copyOf(function.coefficients, function.coefficients.length + artificialVars + inequalities.count());
        //вільні зміні
        for (int j = function.quantityOfInitialVal; j < newCoef.length - artificialVars; j++) {
            newCoef[j] = new ObjectValue(ZERO, ZERO);
        }
        //штучні
        for (int j = function.quantityOfInitialVal + inequalities.getInequalities().size(); j < newCoef.length; j++) {
            newCoef[j] = new ObjectValue(MINUS_ONE, ZERO);
        }
        function = new ObjectiveFunction(newCoef, function.quantityOfInitialVal, function.getOptimizationType(), function.isEarlierWasMin());
        //симплекс таблиця
        int[] numX = new int[inequalities.count()];
        ObjectValue[] numBasis = new ObjectValue[inequalities.count()];

        //шукаємо змінін з 1 щоб додати в базис
        for (int i = 0; i < equalities.count(); i++) {
            Equality equality = equalities.getEqualities().get(i);

            for (int j = function.quantityOfInitialVal; j < equality.coefficients.length; j++) {

                if (equality.coefficients[j] == 1.0) {
                    numBasis[i] = new ObjectValue(function.coefficients[j].coefficientM, function.coefficients[j].numAfter);
                    numX[i] = j + 1;
                    break;
                }


            }
        }

        SimplexTableRow[] mainTable = new SimplexTableRow[equalities.count()];


        for (int i = 0; i < equalities.getAllCoefficients().length; i++) {
            Rat[] oneRow = new Rat[function.coefficients.length + 1];

            Equality equality = equalities.getEqualities().get(i);

            for (int j = 0; j < equalities.getAllCoefficients()[0].length; j++) {

                oneRow[0] = Rat.doubleToRat(equality.value);
                oneRow[j + 1] = Rat.doubleToRat(equalities.getAllCoefficients()[i][j]);
            }
            mainTable[i] = new SimplexTableRow(numX[i], oneRow);
        }


        FunctionValInTable[] functionValues = new FunctionValInTable[function.coefficients.length];

        for (int i = 0; i < functionValues.length; i++) {
            ObjectValue coefficient = function.coefficients[i];
            functionValues[i] = new FunctionValInTable(coefficient, i + 1);
        }

        SimplexTable simplexTable = new SimplexTable(numBasis, mainTable, functionValues);

        controller = loader.getController();
        controller.systemToWindow(simplexTable.mainTable);


        calculateIndexes(simplexTable);
        calculateSimplexTable(simplexTable, function.isEarlierWasMin, function);

    }

    public void calculateSimplexTable(SimplexTable table, boolean isEarlierWasMin, ObjectiveFunction function) {

        boolean hasNegativeIndexes;

        System.out.println("Початкова таблиця");
        System.out.println(table);
        controller.firstTableToScreen(table);
        System.out.println(Arrays.toString(table.indexes));

        do {

            int minColumn = getMinColumn(table);
            int minRow = getMinRow(table, minColumn);
            controller.elementToScreen(minRow, minColumn, table);
            deleteColumnWithArtificialVal(table, minRow);

            changeInBasis(table, minRow, minColumn);
            System.out.println("Таблиця з видаленимим колонками і зміна базисом");
            System.out.println(table);
            System.out.println(Arrays.toString(table.indexes));

            SimplexTableRow[] newMainTable = multiplyTable(table, minRow, minColumn);
            table = new SimplexTable(table.numThatInBasis, newMainTable, table.functionValues);


            calculateIndexes(table);


            controller.tablesToScreen(table);

            System.out.println("Нова таблиця порахована і індекси");
            System.out.println(table);
            System.out.println(Arrays.toString(table.indexes));

            hasNegativeIndexes = isIndexesHaveNegativeValue(table.indexes, isEarlierWasMin);

        } while (hasNegativeIndexes);
        if (hasResult) {
            //вивід результату
            Result res = getResult(table, function.quantityOfInitialVal, isEarlierWasMin);
            boolean isOk = checkInteger(table, res, function, controller);
            controller.resultToScreen(res, isOk);
        } else {
            System.out.println("немає розв'язку");
            controller.resultToScreen(null, false);
        }


    }

    public static Result getResult(SimplexTable table, int quantityOfInitialVal, boolean isEarlierWasMin) {

        Rat resFunction;
        if (isEarlierWasMin) {
            resFunction = table.indexes[0].numAfter.mul(MINUS_ONE);
        } else {
            resFunction = table.indexes[0].numAfter;
        }
        /*if (table.indexes[0].numAfter < 0 && table.indexes[0].coefficientM == 0) {
            resFunction = table.indexes[0].numAfter * (-1);
        } else if (table.indexes[0].numAfter > 0 && table.indexes[0].coefficientM == 0) {
            resFunction = table.indexes[0].numAfter;
        }*/

        Rat[] results = new Rat[quantityOfInitialVal];
        for (int i = 0; i < results.length; i++) {
            results[i] = ZERO;
        }

        for (int i = 0; i < table.getRows(); i++) {
            if (table.mainTable[i].numOfX <= quantityOfInitialVal) {
                results[table.mainTable[i].numOfX - 1] = table.mainTable[i].row[0];
            }
        }

        Result res = new Result(resFunction, results);
        return res;
    }

    public static boolean checkInteger(SimplexTable table, Result res, ObjectiveFunction function, TablesWindowController controller) {

        boolean fl = false;
        int quantityOfDoubleEl = 0;
        for (int i = 0; i < res.resultsOfX.length; i++) {

            if (!res.resultsOfX[i].isInteger()) {
                fl = true;
                quantityOfDoubleEl++;
            }
        }
        if (fl) {
            System.out.println("Розв'язок не є цілочисельний");
            GomoriMethod gm = new GomoriMethod();
            gm.gomoriCalculate(res, table, quantityOfDoubleEl, function, controller);
            return false;
        } else {
            System.out.println("Розв`язок цілочисельний");
            return true;
        }
    }

    public static int getMinRow(SimplexTable table, int minColumn) {
        //пошук мін рядка
        Rat minEl = new Rat(Integer.MAX_VALUE);
        int minRow = 0;
        for (int i = 0; i < table.getRows(); i++) {
            if ((table.get(i, 0).div(table.get(i, minColumn)).greaterThan(ZERO)) && (table.get(i, 0).div(table.get(i, minColumn)).lessThan(minEl))) {
                minEl = table.get(i, 0).div(table.get(i, minColumn));
                minRow = i;
            }
        }
        System.out.println(minRow);
        return minRow;
    }

    public static int getMinColumn(SimplexTable table) {
        Rat minEl;
        //пошук найменьшого індекса
        if (!table.indexes[1].coefficientM.equalsTo(ZERO)) {
            minEl = table.indexes[1].coefficientM;
        } else {
            minEl = table.indexes[1].numAfter;
        }


        int minColumn = 1;
        for (int i = 2; i < table.indexes.length; i++) {
            if (!table.indexes[i].coefficientM.equalsTo(ZERO)) {
                if (table.indexes[i].coefficientM.lessThan(minEl)) {
                    minEl = table.indexes[i].coefficientM;
                    minColumn = i;
                }
            } else {
                if (table.indexes[i].numAfter.lessThan(minEl)) {
                    minEl = table.indexes[i].numAfter;
                    minColumn = i;
                }
            }

        }
        System.out.println("smaller index" + table.indexes[minColumn]);
        return minColumn;
    }

    public static void changeInBasis(SimplexTable table, int minRow, int minColumn) {
        //зміна числа в базисі
        //зміна х на колонку мінімальну
        table.mainTable[minRow].numOfX = minColumn;
        //зміна значення х-са в базисі на квідповідний кофіцієнт
        table.numThatInBasis[minRow] = table.functionValues[minColumn - 1].value;
        // було - table.numThatInBasis[minRow] = table.functionValues[minColumn - 1];
    }

    public static void deleteColumnWithArtificialVal(SimplexTable table, int minRow) {
        //забрати з мейн тейбл штчуну колонку
        //беремо колонку мінімального елемента - по ньому номера х-са базису, по х з базису - коофіцієнт, якщо має М - штучна зміна, видалємо рядок
//minRow = 0
        int indexXInBasis = table.mainTable[minRow].numOfX; //6
        for (int i = 1; i < table.functionValues.length; i++) {

            if (table.functionValues[i].valNum == indexXInBasis) {

                if (!table.functionValues[i].value.coefficientM.equalsTo(ZERO)) {
                    for (int j = 0; j < table.mainTable.length; j++) {
                        table.mainTable[j].deleteColumn(indexXInBasis);
                    }
                    table.deleteIndex(indexXInBasis);
                    table.deleteFunctionValue(indexXInBasis);
                }

            }
        }

    }

    public static SimplexTableRow[] multiplyTable(SimplexTable table, int minRow, int minColumn) {
        SimplexTableRow[] newMainTable = new SimplexTableRow[table.mainTable.length];

        //формування нової таблиці
        Rat minElement = table.get(minRow, minColumn);

        for (int i = 0; i < table.getRows(); i++) {
            Rat[] rowS = new Rat[table.getColumns()];
            for (int j = 0; j < rowS.length; j++) {

                if (i == minRow) {
                    rowS[j] = table.get(i, j).div(minElement);
                } else {
                    rowS[j] = table.get(i, j).sub(table.get(i, minColumn).mul(table.get(minRow, j)).div(minElement));
                }
            }
            newMainTable[i] = new SimplexTableRow(table.mainTable[i].numOfX, rowS);
        }
        return newMainTable;
    }

    public boolean isIndexesHaveNegativeValue(ObjectValue[] indexes, boolean isEarlierWasMin) {

        int start = isEarlierWasMin ? 1 : 0;
        int quantity = 0;
        //якщо цільова функція максимізується, і всі значення індексів у симплекс-таблиці або нульові, або від'ємні, то немає допустимих розв'язків для системи обмежень.
        for (int i = start; i < indexes.length; i++) {
            Rat m = indexes[i].coefficientM;
            if (m.lessThan(ZERO) || (m.equalsTo(ZERO) && indexes[i].numAfter.lessThan(ZERO))) {
                quantity++;
            }
        }
        if (quantity == indexes.length) {
            System.out.println("Розв'язків немає");
            hasResult = false;
            return false;
        }

        for (int i = start; i < indexes.length; i++) {
            Rat m = indexes[i].coefficientM;
            if (m.lessThan(ZERO) || m.equalsTo(ZERO) && indexes[i].numAfter.lessThan(ZERO)) {
                return true;
            }
        }
        return false;
    }

    public static void calculateIndexes(SimplexTable table) {

        ObjectValue[] indexes = new ObjectValue[table.getColumns()];

        for (int j = 0; j < table.getColumns(); j++) {

            ObjectValue ob = new ObjectValue(ZERO, ZERO);

            for (int i = 0; i < table.getRows(); i++) {

                if (table.numThatInBasis[i].coefficientM.equalsTo(ZERO)) {
                    ob.numAfter = table.get(i, j).mul(table.numThatInBasis[i].numAfter).add(ob.numAfter);
                } else {
                    ob.coefficientM = table.get(i, j).mul(table.numThatInBasis[i].coefficientM).add(ob.coefficientM);
                }
            }
            indexes[j] = ob;

            if (j != 0) {
                if (!table.functionValues[j - 1].value.coefficientM.equalsTo(ZERO)) {
                    indexes[j].coefficientM = indexes[j].coefficientM.sub(table.functionValues[j - 1].value.coefficientM);
                } else {
                    indexes[j].numAfter = indexes[j].numAfter.sub(table.functionValues[j - 1].value.numAfter);
                }
            }
        }

        System.out.println("indexes");
        System.out.println(Arrays.toString(indexes));
        table.setIndexes(indexes);
    }

    public SystemOfEqualities createSystemOfEqualities(SystemOfInequalities inequalities, ObjectiveFunction objFunc) {

        List<Equality> equalities = new ArrayList<>();

        for (int i = 0; i < inequalities.getInequalities().size(); i++) {

            //отримується одна нерівність
            Inequality inequality = inequalities.getInequalities().get(i);

            //1. перевірити чи є в нерівностях результат з мунісом, якщо є в нерівності:
            //коофіцієнти поножити на -1, знак поміняти, результат помножити на -1
            if (inequality.value < 0) {
                if (inequality.getSymbol() == LESS_EQUAL_THAN) {
                    inequality.setSymbol(GREATER_EQUAL_THAN);
                } else if (inequality.getSymbol() == GREATER_EQUAL_THAN) {
                    inequality.setSymbol(LESS_EQUAL_THAN);
                }
                inequality.value = inequality.value * (-1);

                for (int j = 0; j < inequality.coefficients.length; j++) {
                    inequality.coefficients[j] = inequality.coefficients[j] * (-1);
                }
            }
            //2. перетворити нерівності в рівності:

            //*вільні змінні
            //якщо знак >=, то -1 в масив коофіцієнтів на той порядок який це номер нерівності, все решта нулі
            //якщо знак <=, то +1 в масив коофіцієнтів на той порядок який це номер нерівності, все решта нулі
            if (inequality.getSymbol() == LESS_EQUAL_THAN) {
                double[] newCoefficients = Arrays.copyOf(inequality.coefficients, inequalities.count() + inequality.coefficients.length + artificialVars);
                newCoefficients[inequality.coefficients.length + i] = 1;

                Equality equality = new Equality(newCoefficients, inequality.value);
                equalities.add(equality);

            } else if (inequality.getSymbol() == GREATER_EQUAL_THAN) {
                //якщо кооіфіцієнт -1 то вводиться штучна зміна +1
                double[] newCoefficients = Arrays.copyOf(inequality.coefficients, inequalities.count() + inequality.coefficients.length + artificialVars);

                newCoefficients[inequality.coefficients.length + i] = -1;
                newCoefficients[inequality.coefficients.length + i + artificialVars] = 1;

                Equality equality = new Equality(newCoefficients, inequality.value);
                equalities.add(equality);


            }
        }
        SystemOfEqualities systemOfEqualities = new SystemOfEqualities(equalities);
        return systemOfEqualities;

    }

    public void countInequalityLess(SystemOfInequalities inequalities) {
        for (int i = 0; i < inequalities.count(); i++) {
            Inequality inequality = inequalities.getInequalities().get(i);
            if (inequality.getSymbol() == GREATER_EQUAL_THAN && inequality.value >= 0) {
                artificialVars++;
            }
        }
    }

}
