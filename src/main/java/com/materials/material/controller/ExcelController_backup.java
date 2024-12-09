//package com.materials.material.controller;
//
//
//import com.materials.material.model.ExcelData;
//import com.materials.material.service.ExcelService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.multipart.MultipartFile;
//
//@Controller
//public class ExcelController_backup {
//
//    @Autowired
//    private ExcelService excelService;
//
//    // Ana sayfayı yükler
//    @GetMapping("/")
//    public String index() {
//        return "index"; // index.html'i döner
//    }
//
//    // Excel dosyasını işleyen endpoint
//    @PostMapping("/upload")
//    public String uploadFile(MultipartFile file, Model model) {
//        System.out.println("Dosya yükleniyor: " + file.getOriginalFilename());
//        try {
//            // Excel dosyasını işleyip veriyi al
//            ExcelData data = excelService.processExcel(file);
//            System.out.println("Dosya başarıyla işlendi.");
//
//            // UI'a malzemeler, yöntemler ve fotoğraflar gibi bilgileri ilet
//            model.addAttribute("materials", data.getMaterials());
//            model.addAttribute("methods", data.getMethods());
//            model.addAttribute("photos", data.getPhotos());
//            model.addAttribute("intersections", data.getIntersections());
//            System.out.println("Model verileri UI'ye eklendi.");
//            System.out.println("materials : " + data.getMaterials());
//            System.out.println("methods : " + data.getMethods());
//            System.out.println("photos : " + data.getPhotos());
//            System.out.println("intersections : " + data.getIntersections());
//        } catch (Exception e) {
//            model.addAttribute("error", "Dosya işlenirken bir hata oluştu: " + e.getMessage());
//        }
//
//        return "index"; // İşlenmiş veriyi UI'da göstermek için tekrar index.html'e döner
//    }
//}
