package com.project.mmdo.model;

import java.util.Arrays;
import java.util.Objects;

public class Result {

     public Rat objFunctNum;

     public Rat[] resultsOfX;


    public Result(Rat objFunctNum, Rat[] resultsOfX) {
        this.objFunctNum = objFunctNum;
        this.resultsOfX = resultsOfX;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result result = (Result) o;

        return objFunctNum.equalsTo(result.objFunctNum) && Arrays.equals(resultsOfX, result.resultsOfX);
    }

}
