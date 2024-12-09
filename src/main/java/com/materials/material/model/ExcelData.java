package com.materials.material.model;

import java.util.*;


import java.util.List;
import java.util.Map;


import java.util.List;
import java.util.Map;

public class ExcelData {
    public static String selectedMaterial = null;
    public static List<String> materials = null;
    public static List<String> methods;
    public static Map<String, Map<String, List<String>>> intersections;
    public static Map<String, String> photoMap;

    // Getters ve Setters
    public List<String> getMaterials() { return materials; }
    public void setMaterials(List<String> materials) { ExcelData.materials = materials; }
    public List<String> getMethods() { return methods; }
    public void setMethods(List<String> methods) { ExcelData.methods = methods; }
    public Map<String, Map<String, List<String>>> getIntersections() { return intersections; }
    public void setIntersections(Map<String, Map<String, List<String>>> intersections) { ExcelData.intersections = intersections; }
    public Map<String, String> getPhotoMap() { return photoMap; }
    public void setPhotoMap(Map<String, String> photoMap) { ExcelData.photoMap = photoMap; }
}


