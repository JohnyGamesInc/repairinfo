package com.atmcasesapp.utils;

import java.util.Arrays;

public enum AtmCaseColumn {
    CASE_ID(0),
    ATM_ID(1),
    DESCRIPTION(2),
    START(3),
    FINISH(4),
    SERIAL(5),
    BANK(6),
    CHANNEL(7);

    private final int colNum;

    AtmCaseColumn(int colNum) {
        this.colNum = colNum;
    }

    public int colNum() {
        return colNum;
    }

    public static AtmCaseColumn getAsAtmColumn(int colNum) {
        return Arrays.stream(AtmCaseColumn.values())
                .filter(c -> c.colNum() == colNum)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No such column for colNum"));
    }
}
