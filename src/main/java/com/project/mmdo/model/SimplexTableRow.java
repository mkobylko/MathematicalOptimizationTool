package com.project.mmdo.model;

import java.util.Arrays;
import java.util.Objects;

public class SimplexTableRow {

    public int numOfX;
    public Rat[] row;

    public SimplexTableRow(int numOfX, Rat[] row) {
        this.numOfX = numOfX;
        this.row = row;
    }

    public void deleteColumn(int numCol) {
        Rat[] newRow = Arrays.copyOf(row, row.length - 1);
        for (int i = numCol + 1; i < row.length; i++) {
            newRow[i - 1] = row[i];
        }
        this.row = newRow;
    }



    @Override
    public String toString() {
        return "RowInSimplexTable{" +
                "numX=" + numOfX +
                ", oneRow=" + Arrays.toString(row) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimplexTableRow that = (SimplexTableRow) o;
        return numOfX == that.numOfX && Arrays.equals(row, that.row);
    }

}
