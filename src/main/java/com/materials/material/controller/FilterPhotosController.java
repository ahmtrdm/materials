package com.materials.material.controller;

import com.materials.material.model.ExcelData;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

@RestController
@RequestMapping("/filter-photos")
public class FilterPhotosController {

    @GetMapping
    public ResponseEntity<String> filterPhotos(@RequestParam String method) {
        String selectedMaterial = ExcelData.selectedMaterial;
        // Retrieve photo IDs based on material and method
        List<String> photoIds = ExcelData.intersections.getOrDefault(selectedMaterial, Collections.emptyMap())
                .getOrDefault(method, Collections.emptyList());

        Map<String, String> photoMap = new HashMap();
        photoMap = getPhotoMap();
        // Build HTML response
        StringBuilder response = new StringBuilder();
        for (String photoId : photoIds) {
            String photoName = photoMap.get(photoId);
            if (photoName != null) {
                //response.append("<li>").append("<img src=").append(photoName).append(">").append("</li>");
                //response.append("<img src=").append(photoName).append(">");
                //response.append("<li>").append("<img src=\"/photos/").append(photoName).append("\">").append("</li>");
                response.append("<li>")
                        .append("<img src=\"/photos/")
                        .append(photoName) // Sadece dosya adı döndürülüyor
                        .append("\" alt=\"Photo\">")
                        .append("</li>");

            }
        }

        return ResponseEntity.ok(response.toString());
    }

    private Map<String, Map<String, List<String>>> getIntersections() {
        // Mock data (replace with your actual data source)
        Map<String, Map<String, List<String>>> intersections = new HashMap<>();
        intersections = ExcelData.intersections;
        return intersections;
    }

    private Map<String, String> getPhotoMap() {
        // Mock photo map (replace with your actual data source)
        Map<String, String> photoMap = new HashMap<>();
        photoMap.putAll(ExcelData.photoMap);
        return photoMap;
    }
}
