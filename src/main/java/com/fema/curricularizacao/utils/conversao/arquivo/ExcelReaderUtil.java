package com.fema.curricularizacao.utils.conversao.arquivo;

import com.fema.curricularizacao.utils.exceptions.custom.PersistenciaDadosException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Iterator;

public class ExcelReaderUtil {

    public static Iterator<Row> buscarExcel(MultipartFile arquivoForms) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(arquivoForms.getInputStream());
            XSSFSheet sheetAluno = workbook.getSheetAt(0);

            return sheetAluno.iterator();
        } catch (IOException e) {
            throw new PersistenciaDadosException("Erro de I/O ao processar o arquivo: " + e.getMessage(), e);
        }
    }

    public static String convertNumericForString(Cell cell){
        if (cell.getCellType() == CellType.NUMERIC){
            DataFormatter dataFormatter = new DataFormatter();
            return dataFormatter.formatCellValue(cell);
        }
        return cell.getStringCellValue();
    }

    public static Long convertStringForNumeric(Cell cell){
        String valorString;

        if (cell.getCellType() == CellType.NUMERIC){
            DataFormatter dataFormatter = new DataFormatter();
            valorString = dataFormatter.formatCellValue(cell);
        } else {
            valorString = cell.getStringCellValue();
        }

        try {
            return Long.valueOf(valorString.replaceAll("[^\\d]", ""));
        } catch (NumberFormatException e) {
            System.err.println("Erro ao converter valor para Long: " + valorString);
            return null;
        }
    }
}