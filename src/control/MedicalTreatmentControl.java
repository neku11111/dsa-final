/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;
import adt.DynamicArray;
import entity.MedicalHistory;
import entity.Treatment;
import entity.Doctor;
import entity.Patient;
//import patient.Patient;  // if you need patient data
//import doctor.Doctor;    // if you need doctor data
/**
 *
 * @author LENOVO LOQ 15APH8
 */
public class MedicalTreatmentControl {
    private DynamicArray<Treatment> allTreatments;
    private DynamicArray<MedicalHistory> patientHistories;
    private int nextTreatmentId;
    
    private MaintainDoctor doctorManager;
    private PatientManager patientManager;
    
    public MedicalTreatmentControl() {
        this.allTreatments = new DynamicArray<>();
        this.patientHistories = new DynamicArray<>();
        this.nextTreatmentId = 1001;
        this.doctorManager = new MaintainDoctor();
        this.patientManager = new PatientManager();
        initializeTestData(); 
    }
    
    
    public MedicalTreatmentControl(MaintainDoctor doctorManager, PatientManager patientManager) {
        this.allTreatments = new DynamicArray<>();
        this.patientHistories = new DynamicArray<>();
        this.nextTreatmentId = 1001;
        this.doctorManager = doctorManager;
        this.patientManager = patientManager;
        initializeTestData();
    }
    
    // Method to validate if patient ID exists
    public boolean isValidPatientId(String patientId) {
        if (patientId == null || patientId.trim().isEmpty()) {
            return false;
        }
        
        return patientManager.findPatientById(patientId.trim()) != null;
    }
    
    // Method to validate if doctor ID exists
    public boolean isValidDoctorId(String doctorId) {
        if (doctorId == null || doctorId.trim().isEmpty()) {
            return false;
        }
        
        return doctorManager.searchDoctorById(doctorId.trim()) != null;
    }
    
    // Method to get available doctors
    public String getAvailableDoctors() {
        return doctorManager.viewAllDoctors();
    }
    
    // Method to get available patients 
    public String getAvailablePatients() {
        return "Please check the Patient Management system for available patient IDs.";
    }
    
    // Add a new treatment record
    public boolean addTreatment(String patientId, String doctorId, String diagnosis, 
                            String prescription, String treatmentDate, String treatmentType,
                            String symptoms, String notes, double cost) {

   
     if (!MedicalValidator.isValidString(patientId) ||
         !MedicalValidator.isValidString(doctorId) ||
         !MedicalValidator.isValidString(diagnosis)) {
         System.out.println("Error: Patient ID, Doctor ID, and Diagnosis are required fields.");
         return false;
     }

     // Validate Patient ID exists
     if (!isValidPatientId(patientId)) {
         System.out.println("Error: Patient ID '" + patientId + "' does not exist in the system.");
         System.out.println("Please register the patient first or check the correct Patient ID.");
         return false;
     }
     
     // Validate Doctor ID exists
     if (!isValidDoctorId(doctorId)) {
         System.out.println("Error: Doctor ID '" + doctorId + "' does not exist in the system.");
         System.out.println("Available doctors:");
         System.out.println(getAvailableDoctors());
         return false;
     }

     if (!MedicalValidator.isValidTreatmentDate(treatmentDate)) {
         System.out.println("Error: Invalid treatment date. Use YYYY-MM-DD format and ensure date is within reasonable range.");
         return false;
     }

     if (!MedicalValidator.isValidCost(cost)) {
         System.out.println("Error: Cost must be between $0.00 and $10,000.00");
         return false;
     }

     // Generate unique treatment ID
     String treatmentId = "T" + nextTreatmentId++;

     // Create new treatment
     Treatment treatment = new Treatment(treatmentId, patientId, doctorId, diagnosis, 
                                       prescription, treatmentDate, treatmentType, 
                                       symptoms, notes, cost);

     boolean added = allTreatments.add(treatment);

     if (added) {
         
         updatePatientHistory(patientId, treatment);
         System.out.println("Treatment record added successfully with ID: " + treatmentId);
     }

     return added;
 }

    
    
    public DynamicArray<Treatment> getPatientTreatments(String patientId) {
        DynamicArray<Treatment> patientTreatments = new DynamicArray<>();
        
        
        
        if (!isValidPatientId(patientId)) {
            System.out.println("Warning: Patient ID '" + patientId + "' does not exist in the system.");
            return patientTreatments; // Return empty array
        }
        
        for (int i = 0; i < allTreatments.getSize(); i++) {
            Treatment treatment = allTreatments.getEntry(i);
            if (treatment != null && treatment.getPatientId().equals(patientId)) {
                patientTreatments.add(treatment);
            }
        }
        
        return patientTreatments;
    }
    
    public DynamicArray<Treatment> getPatientTreatmentsSorted(String patientId) {
        DynamicArray<Treatment> treatments = getPatientTreatments(patientId);

        for (int i = 0; i < treatments.getSize() - 1; i++) {
            for (int j = 0; j < treatments.getSize() - 1 - i; j++) {
                Treatment t1 = treatments.getEntry(j);
                Treatment t2 = treatments.getEntry(j + 1);

                if (t1 != null && t2 != null) {
                    
                    if (t1.getTreatmentDate().compareTo(t2.getTreatmentDate()) < 0) {
                        treatments.replace(j, t2);
                        treatments.replace(j + 1, t1);
                    }
                }
            }
        }

        return treatments;
    }
    
    // Get medical history for a patient
    public MedicalHistory getPatientHistory(String patientId) {
        if (!isValidPatientId(patientId)) {
            System.out.println("Warning: Patient ID '" + patientId + "' does not exist in the system.");
            return null;
        }
        
        for (int i = 0; i < patientHistories.getSize(); i++) {
            MedicalHistory history = patientHistories.getEntry(i);
            if (history != null && history.getPatientId().equals(patientId)) {
                return history;
            }
        }
        
        MedicalHistory newHistory = new MedicalHistory(patientId);
        patientHistories.add(newHistory);
        return newHistory;
    }
    
    public DynamicArray<Treatment> searchTreatmentsByDiagnosis(String diagnosis) {
        DynamicArray<Treatment> results = new DynamicArray<>();
        
        if (diagnosis == null || diagnosis.trim().isEmpty()) {
            return results;
        }
        
        for (int i = 0; i < allTreatments.getSize(); i++) {
            Treatment treatment = allTreatments.getEntry(i);
            if (treatment != null && 
                treatment.getDiagnosis().toLowerCase().contains(diagnosis.toLowerCase())) {
                results.add(treatment);
            }
        }
        
        return results;
    }
    
    public DynamicArray<Treatment> getTreatmentsByDoctor(String doctorId) {
        DynamicArray<Treatment> doctorTreatments = new DynamicArray<>();
        
        
        if (!isValidDoctorId(doctorId)) {
            System.out.println("Warning: Doctor ID '" + doctorId + "' does not exist in the system.");
            return doctorTreatments; // Return empty array
        }
        
        for (int i = 0; i < allTreatments.getSize(); i++) {
            Treatment treatment = allTreatments.getEntry(i);
            if (treatment != null && treatment.getDoctorId().equals(doctorId)) {
                doctorTreatments.add(treatment);
            }
        }
        
        return doctorTreatments;
    }
    
    // Update an existing treatment
    public boolean updateTreatment(String treatmentId, String diagnosis, String prescription, 
                                  String notes, double cost) {
        
        for (int i = 0; i < allTreatments.getSize(); i++) {
            Treatment treatment = allTreatments.getEntry(i);
            if (treatment != null && treatment.getTreatmentId().equals(treatmentId)) {
                treatment.setDiagnosis(diagnosis);
                treatment.setPrescription(prescription);
                treatment.setNotes(notes);
                treatment.setCost(cost);
                
                Treatment updated = allTreatments.replace(i, treatment);
                return updated != null;
            }
        }
        
        return false;
    }
    
    public Treatment getTreatmentById(String treatmentId) {
        for (int i = 0; i < allTreatments.getSize(); i++) {
            Treatment treatment = allTreatments.getEntry(i);
            if (treatment != null && treatment.getTreatmentId().equals(treatmentId)) {
                return treatment;
            }
        }
        return null;
    }
    
    public DynamicArray<Treatment> getAllTreatments() {
        return allTreatments;
    }
    
    public String getTreatmentStatistics() {
        int totalTreatments = allTreatments.getSize();
        int totalPatients = patientHistories.getSize();
        
        return String.format("Total Treatments: %d | Total Patients with History: %d", 
                           totalTreatments, totalPatients);
    }
    
    private void updatePatientHistory(String patientId, Treatment treatment) {
        MedicalHistory history = getPatientHistory(patientId);
        if (history != null) {
            history.addTreatment(treatment);
        }
    }
    


    // Report 1: Treatment Summary Report
    public void generateTreatmentSummaryReport() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("           TREATMENT SUMMARY REPORT");
        System.out.println("=".repeat(60));

        if (allTreatments.isEmpty()) {
            System.out.println("No treatment data available for reporting.");
            System.out.println("=".repeat(60));
            return;
        }

        int totalTreatments = allTreatments.getSize();
        double totalRevenue = 0.0;
        double maxCost = 0.0;
        double minCost = Double.MAX_VALUE;

        DynamicArray<String> diagnoses = new DynamicArray<>();
        DynamicArray<Integer> diagnosisCount = new DynamicArray<>();

        for (int i = 0; i < allTreatments.getSize(); i++) {
            Treatment treatment = allTreatments.getEntry(i);
            if (treatment != null) {
                double cost = treatment.getCost();
                totalRevenue += cost;

                if (cost > maxCost) maxCost = cost;
                if (cost < minCost) minCost = cost;

                String diagnosis = treatment.getDiagnosis();
                if (diagnosis != null) {  
                    int index = findDiagnosisIndex(diagnoses, diagnosis);
                    if (index == -1) {
                        diagnoses.add(diagnosis);
                        diagnosisCount.add(1);
                    } else {
                        Integer currentCount = diagnosisCount.getEntry(index);
                        if (currentCount != null) {  
                            diagnosisCount.replace(index, currentCount + 1);
                        }
                    }
                }
            }
        }

        
        if (minCost == Double.MAX_VALUE) {
            minCost = 0.0;
        }

        double averageCost = totalTreatments > 0 ? totalRevenue / totalTreatments : 0.0;

        System.out.printf("Report Period: All Time%n");

        System.out.println("\n--- TOP DIAGNOSES ---");
        System.out.printf("%-25s %s%n", "Diagnosis", "Count");
        System.out.println("-".repeat(40));

        for (int i = 0; i < diagnoses.getSize(); i++) {
            String diagnosis = diagnoses.getEntry(i);
            Integer count = diagnosisCount.getEntry(i);
            if (diagnosis != null && count != null) {
                String displayDiagnosis = diagnosis.length() > 25 ? 
                    diagnosis.substring(0, 22) + "..." : diagnosis;
                System.out.printf("%-25s %d%n", displayDiagnosis, count);
            }
        }

        System.out.println("\n" + "=".repeat(60));
    }

    // Report 2: Doctor Performance Report
    public void generateDoctorPerformanceReport() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("           DOCTOR PERFORMANCE REPORT");
        System.out.println("=".repeat(60));

        if (allTreatments.isEmpty()) {
            System.out.println("No treatment data available for reporting.");
            return;
        }

        DynamicArray<String> doctors = new DynamicArray<>();
        DynamicArray<Integer> treatmentCounts = new DynamicArray<>();
        DynamicArray<Double> revenues = new DynamicArray<>();

        for (int i = 0; i < allTreatments.getSize(); i++) {
            Treatment treatment = allTreatments.getEntry(i);
            if (treatment != null) {
                String doctorId = treatment.getDoctorId();
                double cost = treatment.getCost();

                int index = findDoctorIndex(doctors, doctorId);
                if (index == -1) {
                    doctors.add(doctorId);
                    treatmentCounts.add(1);
                    revenues.add(cost);
                } else {
                    treatmentCounts.replace(index, treatmentCounts.getEntry(index) + 1);
                    revenues.replace(index, revenues.getEntry(index) + cost);
                }
            }
        }

        // Display doctor performance
        System.out.printf("%-10s %-12s %-12s %-15s%n", 
                         "Doctor ID", "Treatments", "Revenue", "Avg per Treatment");
        System.out.println("-".repeat(60));

        for (int i = 0; i < doctors.getSize(); i++) {
            String doctorId = doctors.getEntry(i);
            Integer treatments = treatmentCounts.getEntry(i);
            Double revenue = revenues.getEntry(i);

            if (doctorId != null && treatments != null && revenue != null) {
                double avgPerTreatment = revenue / treatments;
                System.out.printf("%-10s %-12d $%-11.2f $%-14.2f%n", 
                                 doctorId, treatments, revenue, avgPerTreatment);
            }
        }

        int topDoctorIndex = 0;
        int maxTreatments = 0;
        for (int i = 0; i < treatmentCounts.getSize(); i++) {
            Integer count = treatmentCounts.getEntry(i);
            if (count != null && count > maxTreatments) {
                maxTreatments = count;
                topDoctorIndex = i;
            }
        }

        if (doctors.getSize() > 0) {
            System.out.println("\n--- TOP PERFORMER ---");
            System.out.printf("Most Active Doctor: %s (%d treatments)%n", 
                             doctors.getEntry(topDoctorIndex), maxTreatments);
        }
    }

    // Report 3: Patient Visit Analysis Report
    public void generatePatientAnalysisReport() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("           PATIENT ANALYSIS REPORT");
        System.out.println("=".repeat(60));

        if (allTreatments.isEmpty()) {
            System.out.println("No treatment data available for reporting.");
            return;
        }

        DynamicArray<String> patients = new DynamicArray<>();
        DynamicArray<Integer> visitCounts = new DynamicArray<>();
        DynamicArray<Double> totalCosts = new DynamicArray<>();

        for (int i = 0; i < allTreatments.getSize(); i++) {
            Treatment treatment = allTreatments.getEntry(i);
            if (treatment != null) {
                String patientId = treatment.getPatientId();
                double cost = treatment.getCost();

                int index = findPatientIndex(patients, patientId);
                if (index == -1) {
                    patients.add(patientId);
                    visitCounts.add(1);
                    totalCosts.add(cost);
                } else {
                    visitCounts.replace(index, visitCounts.getEntry(index) + 1);
                    totalCosts.replace(index, totalCosts.getEntry(index) + cost);
                }
            }
        }

        System.out.printf("%-12s %-8s %-12s %-15s%n", 
                         "Patient ID", "Visits", "Total Cost", "Avg per Visit");
        System.out.println("-".repeat(60));

        int singleVisitPatients = 0;
        int multipleVisitPatients = 0;

        for (int i = 0; i < patients.getSize(); i++) {
            String patientId = patients.getEntry(i);
            Integer visits = visitCounts.getEntry(i);
            Double totalCost = totalCosts.getEntry(i);

            if (patientId != null && visits != null && totalCost != null) {
                double avgPerVisit = totalCost / visits;
                System.out.printf("%-12s %-8d $%-11.2f $%-14.2f%n", 
                                 patientId, visits, totalCost, avgPerVisit);

                if (visits == 1) {
                    singleVisitPatients++;
                } else {
                    multipleVisitPatients++;
                }
            }
        }

        System.out.println("\n--- PATIENT VISIT SUMMARY ---");
        System.out.printf("Total Unique Patients: %d%n", patients.getSize());
        System.out.printf("Single Visit Patients: %d%n", singleVisitPatients);
        System.out.printf("Multiple Visit Patients: %d%n", multipleVisitPatients);

        if (patients.getSize() > 0) {
            double avgVisitsPerPatient = (double) allTreatments.getSize() / patients.getSize();
            System.out.printf("Average Visits per Patient: %.1f%n", avgVisitsPerPatient);
        }
    }

    private int findDiagnosisIndex(DynamicArray<String> diagnoses, String diagnosis) {
        for (int i = 0; i < diagnoses.getSize(); i++) {
            String existing = diagnoses.getEntry(i);
            if (existing != null && existing.equals(diagnosis)) {
                return i;
            }
        }
        return -1;
    }

    private int findDoctorIndex(DynamicArray<String> doctors, String doctorId) {
        for (int i = 0; i < doctors.getSize(); i++) {
            String existing = doctors.getEntry(i);
            if (existing != null && existing.equals(doctorId)) {
                return i;
            }
        }
        return -1;
    }

    private int findPatientIndex(DynamicArray<String> patients, String patientId) {
        for (int i = 0; i < patients.getSize(); i++) {
            String existing = patients.getEntry(i);
            if (existing != null && existing.equals(patientId)) {
                return i;
            }
        }
        return -1;
    }
    
    private void initializeTestData() {
        System.out.println("Loading sample medical data...");

        boolean result;

        result = addTreatment("P001", "D001", "Common Cold", 
                             "Rest and fluids", "2025-01-15", 
                             "General", "Cough fever", "Recovering well", 50.0);
        System.out.println("Added P001 treatment 1: " + result);

        result = addTreatment("P001", "D001", "Follow-up", 
                             "Continue rest", "2025-01-20", 
                             "General", "Much better", "Recovery complete", 30.0);
        System.out.println("Added P001 treatment 2: " + result);

        result = addTreatment("P002", "D002", "Hypertension", 
                             "Medication prescribed", "2025-01-16", 
                             "Cardiology", "High blood pressure", "Monitor daily", 150.0);
        System.out.println("Added P002 treatment: " + result);

        result = addTreatment("P003", "D003", "Diabetes", 
                             "Insulin therapy", "2025-01-18", 
                             "Endocrinology", "High blood sugar", "Diet plan provided", 200.0);
        System.out.println("Added P003 treatment: " + result);

        System.out.println("Total treatments in array: " + allTreatments.getSize());

        System.out.println("\nTreatments in array:");
        for (int i = 0; i < allTreatments.getSize(); i++) {
            Treatment t = allTreatments.getEntry(i);
            if (t != null) {
                System.out.println("Index " + i + ": " + t.getPatientId() + " - " + t.getDiagnosis());
            } else {
                System.out.println("Index " + i + ": null");
            }
        }
    }
}