package com.materials.material.service;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExcelService {

    public Map<String, List<String>> readExcel(InputStream inputStream) throws IOException {
        Map<String, List<String>> data = new HashMap<>();

        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);

        // İlk satırı (başlıkları) oku
        Row headerRow = sheet.getRow(0);
        List<String> methods = new ArrayList<>();
        for (int i = 1; i < headerRow.getLastCellNum(); i++) {
            methods.add(headerRow.getCell(i).getStringCellValue().trim());
        }

        // Malzeme ve yöntemleri işleme
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            String material = row.getCell(0).getStringCellValue().trim();
            List<String> applicableMethods = new ArrayList<>();
            for (int j = 1; j < row.getLastCellNum(); j++) {
                if ("X".equalsIgnoreCase(row.getCell(j).getStringCellValue())) {
                    applicableMethods.add(methods.get(j - 1).trim());
                }
            }
            data.put(material, applicableMethods);
            System.out.println("excel readed.. : " + data);
        }

        workbook.close();
        return data;
    }
}
