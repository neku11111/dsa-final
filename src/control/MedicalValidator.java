/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
/**
 *
 * @author LENOVO LOQ 15APH8
 */
public class MedicalValidator {
    public static boolean isValidTreatmentDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return false;
        }
        
        try {
            LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate now = LocalDate.now();
            
            // Treatment date shouldn't be more than 1 year in the past or future
            LocalDate oneYearAgo = now.minusYears(1);
            LocalDate oneYearFromNow = now.plusYears(1);
            
            return !date.isBefore(oneYearAgo) && !date.isAfter(oneYearFromNow);
            
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    
    // Validate cost
    public static boolean isValidCost(double cost) {
        return cost >= 0.0 && cost <= 10000.0; // Reasonable upper limit
    }
    
    // Validate required string fields
    public static boolean isValidString(String str) {
        return str != null && !str.trim().isEmpty() && str.trim().length() >= 2;
    }
}
