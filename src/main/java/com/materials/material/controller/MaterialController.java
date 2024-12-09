package com.materials.material.controller;


import com.materials.material.model.ExcelData;
import com.materials.material.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class MaterialController {

    @Autowired
    private ExcelService excelService;

    @GetMapping("/")
    public String home(Model model) {
        // Example data
        Map<String, Map<String, List<String>>> intersections = Map.of(
                "key1", Map.of("method1", List.of("value1", "value2")),
                "key2", Map.of("method1", List.of("value3", "value4"))
        );

        String method = "method1";

        // Process the data
        List<String> photoIds = intersections.values().stream()
                .filter(map -> map.containsKey(method))
                .flatMap(map -> map.get(method).stream())
                .collect(Collectors.toList());

        // Join into a comma-separated string
        String photoIdsString = String.join(",", photoIds);

        // Add the result to the model
        model.addAttribute("photoIds", photoIdsString);
        return "index"; // Thymeleaf template name
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) {
        try {
            ExcelData data = excelService.processExcel(file);
            model.addAttribute("materials", data.getMaterials());
            model.addAttribute("methods", data.getMethods());
            model.addAttribute("intersections", data.getIntersections());
            model.addAttribute("photos", data.getPhotoMap());
            System.out.println("materials: " + data.getMaterials());
            System.out.println("methods: " + data.getMethods());
            System.out.println("intersections: " + data.getIntersections());
            System.out.println("photos: " + data.getPhotoMap());
        } catch (Exception e) {
            model.addAttribute("error", "Error processing file: " + e.getMessage());
        }
        return "index";
    }
}

