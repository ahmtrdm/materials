package com.materials.material.controller;

import com.materials.material.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class MaterialController {

    @Autowired
    private ExcelService excelService;

    @GetMapping("/")
    public String index(Model model) {
        // Boş bir model göndererek başlangıç sayfasını göster
        model.addAttribute("materials", new HashMap<>());
        model.addAttribute("methods", new ArrayList<>());

        return "index.html";
    }

    @PostMapping("/upload")
    public String uploadExcel(@RequestParam("file") MultipartFile file, Model model) {
        try {
            // Excel verisini okuyup malzeme-yöntem ilişkilerini alıyoruz
            Map<String, List<String>> materials = excelService.readExcel(file.getInputStream());

            // Tüm yöntemlerin benzersiz bir listesini çıkar
            Set<String> methods = materials.values()
                    .stream()
                    .flatMap(Collection::stream)
                    .map(String::trim) // Boşlukları temizle
                    .collect(Collectors.toSet());

            Map<String, String> joinedMaterials = materials.entrySet().stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            entry -> String.join(",", entry.getValue())
                    ));
            model.addAttribute("materials", joinedMaterials);
            // Malzeme ve yöntem listesini modele ekle
            //model.addAttribute("materials", materials); // Anahtar ve değerlerin tam ilişkisi gönderiliyor
            model.addAttribute("methods", methods); // Tüm yöntemlerin benzersiz listesi

        } catch (IOException e) {
            model.addAttribute("error", "Dosya okunurken bir hata oluştu.");
        }

        return "index.html"; // Aynı sayfayı döndür
    }
}
