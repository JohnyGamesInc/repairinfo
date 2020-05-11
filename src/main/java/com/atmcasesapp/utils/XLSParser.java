package com.atmcasesapp.utils;

import com.atmcasesapp.entity.AtmCase;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@UtilityClass
public class XLSParser {
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    public static List<AtmCase> parseXls(String name) {
        InputStream in;
        XSSFWorkbook wb = null;
        List<AtmCase> atmCases = new ArrayList<>();

        try {
            in = new FileInputStream(name);
            wb = new XSSFWorkbook(in);
        } catch (IOException e) {
            log.error(String.valueOf(e));
        }

        Sheet sheet = wb.getSheetAt(0);
        Iterator<Row> it = sheet.iterator();
        Row row = it.next();
        while (it.hasNext()) {
            row = it.next();
            Iterator<Cell> cells = row.iterator();
            var atmCase = new AtmCase();
            while (cells.hasNext()) {
                Cell cell = cells.next();
                int colNum = cell.getColumnIndex();
                var column = AtmCaseColumn.getAsAtmColumn(colNum);
                defineColumnAndFillAtmCaseObject(atmCase, cell, column);
            }
            atmCases.add(atmCase);
        }
        return atmCases;
    }

    private static void defineColumnAndFillAtmCaseObject(AtmCase atmCase, Cell cell, AtmCaseColumn column) {
        switch (column) {
            case CASE_ID:
                atmCase.setCaseId((long) cell.getNumericCellValue());
                break;
            case ATM_ID:
                atmCase.setAtmId(getCellDataAsStringIgnoreNumeric(cell));
                break;
            case DESCRIPTION:
                atmCase.setCaseDescription(cell.getStringCellValue());
                break;
            case START:
                atmCase.setStart(cell.getLocalDateTimeCellValue());
                break;
            case FINISH:
                atmCase.setFinish(cell.getLocalDateTimeCellValue());
                break;
            case SERIAL:
                atmCase.setSerial(getCellDataAsStringIgnoreNumeric(cell));
                break;
            case BANK:
                atmCase.setBankNM(cell.getStringCellValue());
                break;
            case CHANNEL:
                atmCase.setChannel(cell.getStringCellValue());
                break;
        }
    }

    private String getCellDataAsStringIgnoreNumeric(Cell cell) {
        return cell.getCellType() == CellType.STRING ?
                cell.getStringCellValue() : String.valueOf((int) (cell.getNumericCellValue()));
    }
}
