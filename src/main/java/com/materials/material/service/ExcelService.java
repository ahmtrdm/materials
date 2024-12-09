package com.materials.material.service;


import com.materials.material.model.ExcelData;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.EncryptedDocumentException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class ExcelService {

    public ExcelData processExcel(MultipartFile file) throws IOException {

        InputStream inputStream = file.getInputStream();
        Workbook workbook = WorkbookFactory.create(inputStream);

        // İlk ve ikinci sheet'leri al
        Sheet sheet1 = workbook.getSheetAt(0);
        Sheet sheet2 = workbook.getSheetAt(1);

        List<String> materials = new ArrayList<>();
        List<String> methods = new ArrayList<>();
        Map<String, Map<String, List<String>>> intersections = new HashMap<>();
        Map<String, String> photoMap = new HashMap<>();

        try {
            // Sheet 1: Malzemeler ve Yöntemler
            Row headerRow = sheet1.getRow(0);
            for (int colIndex = 1; colIndex < headerRow.getPhysicalNumberOfCells(); colIndex++) {
                methods.add(headerRow.getCell(colIndex).getStringCellValue());
            }

            for (int rowIndex = 1; rowIndex <= sheet1.getLastRowNum(); rowIndex++) {
                Row row = sheet1.getRow(rowIndex);
                //String material = row.getCell(0).getStringCellValue();
                String material = getCellValue(row.getCell(0));
                materials.add(material);

                Map<String, List<String>> methodMap = new HashMap<>();
                for (int colIndex = 1; colIndex < row.getPhysicalNumberOfCells(); colIndex++) {
                    //String cellValue = row.getCell(colIndex).getStringCellValue();
                    String cellValue = getCellValue(row.getCell(colIndex));
                    if (!cellValue.isEmpty()) {
                        List<String> photoIds = Arrays.asList(cellValue.split(","));
                        methodMap.put(methods.get(colIndex - 1), photoIds);
                    }
                }
                intersections.put(material, methodMap);
            }
        } catch (Exception e) {
            System.out.println("error occured while processing excel sheet1");
            System.out.println(e.getMessage());
        }

        try {
            // Sheet 2: Fotoğraf ID'leri ve İsimleri
            for (int rowIndex = 1; rowIndex <= sheet2.getLastRowNum(); rowIndex++) {
                Row row = sheet2.getRow(rowIndex);
                //String photoName = row.getCell(0).getStringCellValue();
                String photoName = getCellValue(row.getCell(0));

                //String photoId = row.getCell(1).getStringCellValue();
                String photoId = getCellValue(row.getCell(1));
                photoMap.put(photoId, photoName);
            }
        } catch (Exception e) {
            System.out.println("error occured while processing excel sheet2");
            System.out.println(e.getMessage());
        }

        workbook.close();

        ExcelData data = new ExcelData();
        data.setMaterials(materials);
        data.setMethods(methods);
        data.setIntersections(intersections);
        data.setPhotoMap(photoMap);
        System.out.println("materials" + data.getMaterials());
        System.out.println("methods" + data.getMethods());
        System.out.println("intersections" + data.getIntersections());
        for(Map.Entry<String, Map<String, List<String>>> entry : intersections.entrySet()) {
            System.out.println("intersections " + entry.getKey());
            Map<String, List<String>> intersection = entry.getValue();
            System.out.println("intersections " + intersection);

        }
        System.out.println("photoMap" + data.getPhotoMap());
        return data;

    }

    public String getCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                int i = (int) cell.getNumericCellValue();
                return String.valueOf(i);
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            default:
                return "";

        }
    }
}
