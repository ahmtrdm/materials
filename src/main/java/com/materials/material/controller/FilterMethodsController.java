package com.materials.material.controller;

import com.materials.material.model.ExcelData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/filter-methods")
public class FilterMethodsController {

    @GetMapping
    public ResponseEntity<String> filterMethods(@RequestParam String material) {
        // Backend logic for filtering methods based on material
        List<String> methods = getMethodsByMaterial(material);
        ExcelData.selectedMaterial = material;

        // Return a simple HTML list (for simplicity; in production, use templates)
        StringBuilder response = new StringBuilder();
        for (String method : methods) {
            response.append("<li onclick=\"selectMethod(this.textContent)\">")
                    .append(method)
                    .append("</li>");
        }
        return ResponseEntity.ok(response.toString());
    }

    private List<String> getMethodsByMaterial(String material) {
        // Example logic; replace with actual filtering logic
        Map<String, List<String>> selectedIntersections = new HashMap<>();

        return new ArrayList<>(ExcelData.intersections.get(material).keySet());
    }

}

