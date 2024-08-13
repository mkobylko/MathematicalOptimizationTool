package com.project.mmdo.model;

import java.util.Arrays;

public class SimplexTable {

    public ObjectValue[] numThatInBasis;

    public SimplexTableRow[] mainTable;


    public ObjectValue[] indexes;
    public FunctionValInTable[] functionValues;

    public SimplexTable(ObjectValue[] numThatInBasis, SimplexTableRow[] mainTable, FunctionValInTable[] functionValues) {
        this.numThatInBasis = numThatInBasis;
        this.mainTable = mainTable;
        this.functionValues = functionValues;
    }


    public void setIndexes(ObjectValue[] indexes) {
        this.indexes = indexes;
    }

    public int getColumns() {
        return mainTable[0].row.length;
    }

    public int getRows() {
        return mainTable.length;
    }

    public Rat get(int row, int column) {
        return mainTable[row].row[column];
    }

    //приймає x і повертає порядковий номер відповідного рядка
    public int getRowIndexByX(int x) {
        for (int i = 0; i < mainTable.length; i++) {
            if (mainTable[i].numOfX == x) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mainTable.length; i++) {
            sb.append(numThatInBasis[i]);
            sb.append("\tx");
            sb.append(mainTable[i].numOfX);
            sb.append(": ");
            for (Rat v : mainTable[i].row) {
                sb.append(v).append(", ");
            }
            sb.setLength(sb.length() - 2);
            sb.append("\n");
        }
        return sb.toString();
    }

    public void deleteIndex(int column) {
        ObjectValue[] newIndex = Arrays.copyOf(indexes, indexes.length - 1);
        for (int i = column + 1; i < indexes.length; i++) {
            newIndex[i - 1] = indexes[i];
        }
        this.indexes = newIndex;
    }
    public void deleteFunctionValue(int column){
        FunctionValInTable[] newFunctionVal = Arrays.copyOf(functionValues, functionValues.length - 1);
        for (int i = column; i < functionValues.length; i++) {
            newFunctionVal[i - 1] = functionValues[i];
        }
        this.functionValues = newFunctionVal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimplexTable that = (SimplexTable) o;
        return Arrays.equals(numThatInBasis, that.numThatInBasis) && Arrays.equals(mainTable, that.mainTable) && Arrays.equals(indexes, that.indexes) && Arrays.equals(functionValues, that.functionValues);
    }


    public void addTableRow(SimplexTableRow newRow) {

        SimplexTableRow[] newMainTable = Arrays.copyOf(mainTable, mainTable.length + 1);
        //новий рядок в кінець масиву
        newMainTable[newMainTable.length - 1] = newRow;

        mainTable = newMainTable;


    }


}
