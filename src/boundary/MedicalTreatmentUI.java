
package boundary;
import entity.MedicalHistory;
import entity.Treatment;
import control.MedicalValidator;
import control.MedicalTreatmentControl;
import control.MaintainDoctor;
import control.PatientManager;
import adt.DynamicArray;
import java.util.Scanner;
//import patient.Patient;  // if you need patient data
//import doctor.Doctor;    // if you need doctor data
/**
 *
 * @author LENOVO LOQ 15APH8
 */
public class MedicalTreatmentUI {
    private MedicalTreatmentControl treatmentControl;
    private Scanner scanner;
    
    public MedicalTreatmentUI() {
        this.treatmentControl = new MedicalTreatmentControl();
        this.scanner = new Scanner(System.in);
    }
    
    public MedicalTreatmentUI(MaintainDoctor doctorManager, PatientManager patientManager) {
        this.treatmentControl = new MedicalTreatmentControl(doctorManager, patientManager);
        this.scanner = new Scanner(System.in);
    }
    
    public void displayMenu() {
    int choice;
    
    do {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("    MEDICAL TREATMENT MANAGEMENT SYSTEM");
        System.out.println("=".repeat(50));
        System.out.println("1. Add New Treatment Record");
        System.out.println("2. View Patient Treatment History");
        System.out.println("3. Search Treatments by Diagnosis");
        System.out.println("4. View Doctor's Treatments");
        System.out.println("5. Update Treatment Record");
        System.out.println("6. View All Treatments");
        System.out.println("7. View System Statistics");
        System.out.println("8. View Cost Analysis");
        System.out.println("9. Treatment Summary Report");    
        System.out.println("10. Doctor Performance Report");  
        System.out.println("11. Patient Analysis Report");   
        System.out.println("12. Exit");                     
        System.out.println("=".repeat(50));
        System.out.print("Enter your choice (1-12): ");
        
        try {
            choice = Integer.parseInt(scanner.nextLine());
            
            switch (choice) {
                case 1:
                    addNewTreatment();
                    break;
                case 2:
                    viewPatientHistory();
                    break;
                case 3:
                    searchTreatmentsByDiagnosis();
                    break;
                case 4:
                    viewDoctorTreatments();
                    break;
                case 5:
                    updateTreatment();
                    break;
                case 6:
                    viewAllTreatments();
                    break;
                case 7:
                    viewStatistics();
                    break;
                case 8:
                    viewCostAnalysis();
                    break;
                case 9:
                    viewTreatmentSummaryReport();
                    break;
                case 10:
                    viewDoctorPerformanceReport();
                    break;
                case 11:
                    viewPatientAnalysisReport();
                    break;
                case 12:
                    System.out.println("Thank you for using Medical Treatment Management System!");
                    break;
                default:
                    System.out.println("Invalid choice! Please enter 1-12."); 
            }
            
            if (choice != 12) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }   
            
        } catch (NumberFormatException e) {
            choice = 0;
            System.out.println("Invalid input! Please enter a number.");
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
        }
        
    } while (choice != 12);
}
    
    private void addNewTreatment() {
        System.out.println("\n--- Add New Treatment Record ---");

        String patientId;
        do {
            System.out.print("Patient ID: ");
            patientId = scanner.nextLine().trim();
            
            if (patientId.isEmpty()) {
                System.out.println("Patient ID cannot be empty. Please enter a valid Patient ID.");
                continue;
            }
            
            if (!treatmentControl.isValidPatientId(patientId)) {
                System.out.println("Error: Patient ID '" + patientId + "' does not exist in the system.");
                System.out.println("Please register the patient first or check the correct Patient ID.");
                System.out.print("Do you want to try again? (y/n): ");
                String retry = scanner.nextLine().trim();
                if (!retry.equalsIgnoreCase("y")) {
                    System.out.println("Operation cancelled.");
                    return;
                }
            }
        } while (!treatmentControl.isValidPatientId(patientId));

        String doctorId;
        do {
            System.out.println("\n--- Available Doctors ---");
            System.out.println(treatmentControl.getAvailableDoctors());
            System.out.print("Doctor ID: ");
            doctorId = scanner.nextLine().trim();
            
            if (doctorId.isEmpty()) {
                System.out.println("Doctor ID cannot be empty. Please enter a valid Doctor ID.");
                continue;
            }
            
            if (!treatmentControl.isValidDoctorId(doctorId)) {
                System.out.println("Error: Doctor ID '" + doctorId + "' does not exist in the system.");
                System.out.print("Do you want to try again? (y/n): ");
                String retry = scanner.nextLine().trim();
                if (!retry.equalsIgnoreCase("y")) {
                    System.out.println("Operation cancelled.");
                    return;
                }
            }
        } while (!treatmentControl.isValidDoctorId(doctorId));

        System.out.print("Diagnosis: ");
        String diagnosis = scanner.nextLine().trim();

        System.out.print("Prescription: ");
        String prescription = scanner.nextLine().trim();

        String treatmentDate;
        do {
            System.out.print("Treatment Date (YYYY-MM-DD): ");
            treatmentDate = scanner.nextLine().trim();
            if (!MedicalValidator.isValidTreatmentDate(treatmentDate)) {
                System.out.println("Invalid date format or date out of range. Please use YYYY-MM-DD format.");
                System.out.println("   Date should be within the last year to next year.");
            }
        } while (!MedicalValidator.isValidTreatmentDate(treatmentDate));

        System.out.print("Treatment Type: ");
        String treatmentType = scanner.nextLine().trim();

        System.out.print("Symptoms: ");
        String symptoms = scanner.nextLine().trim();

        System.out.print("Notes: ");
        String notes = scanner.nextLine().trim();

        double cost = 0.0;
        do {
            System.out.print("Cost ($0.00 - $10,000.00): ");
            try {
                cost = Double.parseDouble(scanner.nextLine().trim());
                if (!MedicalValidator.isValidCost(cost)) {
                    System.out.println("Cost must be between $0.00 and $10,000.00");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid cost format. Please enter a valid number.");
                cost = -1; 
            }
        } while (!MedicalValidator.isValidCost(cost));

        boolean success = treatmentControl.addTreatment(patientId, doctorId, diagnosis, 
                                                      prescription, treatmentDate, treatmentType,
                                                      symptoms, notes, cost);

        if (!success) {
            System.out.println("Failed to add treatment record. Please check input data.");
        }
    }
    
    private void viewPatientHistory() {
        System.out.println("\n--- Patient Treatment History ---");
        String patientId;
        
        do {
            System.out.print("Enter Patient ID: ");
            patientId = scanner.nextLine().trim();
            
            if (patientId.isEmpty()) {
                System.out.println("Patient ID cannot be empty. Please enter a valid Patient ID.");
                continue;
            }
            
            if (!treatmentControl.isValidPatientId(patientId)) {
                System.out.println("Error: Patient ID '" + patientId + "' does not exist in the system.");
                System.out.print("Do you want to try again? (y/n): ");
                String retry = scanner.nextLine().trim();
                if (!retry.equalsIgnoreCase("y")) {
                    System.out.println("Operation cancelled.");
                    return;
                }
            }
        } while (!treatmentControl.isValidPatientId(patientId));

        DynamicArray<Treatment> treatments = treatmentControl.getPatientTreatmentsSorted(patientId);
        MedicalHistory history = treatmentControl.getPatientHistory(patientId);

        if (treatments.isEmpty()) {
            System.out.println("No treatment records found for patient: " + patientId);
            return;
        }

        System.out.println("\n=== Medical History for Patient: " + patientId + " ===");
        System.out.println("\n=== Treatment Records (Most Recent First) ===");

        displayTreatmentList(treatments);
    }
    
    // Search treatments by diagnosis
    private void searchTreatmentsByDiagnosis() {
        System.out.println("\n--- Search Treatments by Diagnosis ---");
        System.out.print("Enter diagnosis keyword: ");
        String diagnosis = scanner.nextLine().trim();
        
        if (diagnosis.isEmpty()) {
            System.out.println("Diagnosis keyword cannot be empty.");
            return;
        }
        
        DynamicArray<Treatment> results = treatmentControl.searchTreatmentsByDiagnosis(diagnosis);
        
        if (results.isEmpty()) {
            System.out.println("No treatments found with diagnosis containing: " + diagnosis);
        } else {
            System.out.println("\n=== Search Results ===");
            displayTreatmentList(results);
        }
    }
    
    // View treatments by doctor with validation
    private void viewDoctorTreatments() {
        System.out.println("\n--- Doctor's Treatments ---");
        String doctorId;
        
        do {
            System.out.println("\n--- Available Doctors ---");
            System.out.println(treatmentControl.getAvailableDoctors());
            System.out.print("Enter Doctor ID: ");
            doctorId = scanner.nextLine().trim();
            
            if (doctorId.isEmpty()) {
                System.out.println("Doctor ID cannot be empty. Please enter a valid Doctor ID.");
                continue;
            }
            
            if (!treatmentControl.isValidDoctorId(doctorId)) {
                System.out.println("Error: Doctor ID '" + doctorId + "' does not exist in the system.");
                System.out.print("Do you want to try again? (y/n): ");
                String retry = scanner.nextLine().trim();
                if (!retry.equalsIgnoreCase("y")) {
                    System.out.println("Operation cancelled.");
                    return;
                }
            }
        } while (!treatmentControl.isValidDoctorId(doctorId));
        
        DynamicArray<Treatment> treatments = treatmentControl.getTreatmentsByDoctor(doctorId);
        
        if (treatments.isEmpty()) {
            System.out.println("No treatments found for doctor: " + doctorId);
        } else {
            System.out.println("\n=== Treatments by Doctor: " + doctorId + " ===");
            displayTreatmentList(treatments);
        }
    }
    
    // Update treatment record
    private void updateTreatment() {
        System.out.println("\n--- Update Treatment Record ---");
        System.out.print("Enter Treatment ID: ");
        String treatmentId = scanner.nextLine().trim();
        
        if (treatmentId.isEmpty()) {
            System.out.println("Treatment ID cannot be empty.");
            return;
        }
        
        Treatment existing = treatmentControl.getTreatmentById(treatmentId);
        if (existing == null) {
            System.out.println("Treatment not found: " + treatmentId);
            return;
        }
        
        System.out.println("\nCurrent treatment details:");
        System.out.println(existing.toString());
        System.out.println("\nEnter new details (press Enter to keep current value):");
        
        System.out.print("Diagnosis [" + existing.getDiagnosis() + "]: ");
        String diagnosis = scanner.nextLine().trim();
        if (diagnosis.isEmpty()) diagnosis = existing.getDiagnosis();
        
        System.out.print("Prescription [" + existing.getPrescription() + "]: ");
        String prescription = scanner.nextLine().trim();
        if (prescription.isEmpty()) prescription = existing.getPrescription();
        
        System.out.print("Notes [" + existing.getNotes() + "]: ");
        String notes = scanner.nextLine().trim();
        if (notes.isEmpty()) notes = existing.getNotes();
        
        System.out.print("Cost [" + existing.getCost() + "]: ");
        String costStr = scanner.nextLine().trim();
        double cost = existing.getCost();
        if (!costStr.isEmpty()) {
            try {
                cost = Double.parseDouble(costStr);
                if (!MedicalValidator.isValidCost(cost)) {
                    System.out.println("Invalid cost range. Keeping current value.");
                    cost = existing.getCost();
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid cost format. Keeping current value.");
            }
        }
        
        boolean success = treatmentControl.updateTreatment(treatmentId, diagnosis, prescription, notes, cost);
        
        if (success) {
            System.out.println("Treatment record updated successfully!");
        } else {
            System.out.println("Failed to update treatment record.");
        }
    }
    
    // View all treatments
    private void viewAllTreatments() {
        System.out.println("\n--- All Treatment Records ---");
        DynamicArray<Treatment> allTreatments = treatmentControl.getAllTreatments();
        
        if (allTreatments.isEmpty()) {
            System.out.println("No treatment records found.");
        } else {
            displayTreatmentList(allTreatments);
        }
    }
    
    // View system statistics
    private void viewStatistics() {
        System.out.println("\n--- System Statistics ---");
        System.out.println(treatmentControl.getTreatmentStatistics());
    }
    
    private void viewCostAnalysis() {
        System.out.println("\n--- Treatment Cost Analysis ---");
        DynamicArray<Treatment> allTreatments = treatmentControl.getAllTreatments();

        if (allTreatments.isEmpty()) {
            System.out.println("No treatment records found.");
            return;
        }

        double totalCost = 0.0;
        double maxCost = 0.0;
        double minCost = Double.MAX_VALUE;

        for (int i = 0; i < allTreatments.getSize(); i++) {
            Treatment treatment = allTreatments.getEntry(i);
            if (treatment != null) {
                double cost = treatment.getCost();
                totalCost += cost;
                if (cost > maxCost) maxCost = cost;
                if (cost < minCost) minCost = cost;
            }
        }

        double averageCost = totalCost / allTreatments.getSize();

        System.out.printf("Total Treatments: %d%n", allTreatments.getSize());
        System.out.printf("Total Cost: $%.2f%n", totalCost);
        System.out.printf("Average Cost: $%.2f%n", averageCost);
        System.out.printf("Highest Cost: $%.2f%n", maxCost);
        System.out.printf("Lowest Cost: $%.2f%n", minCost);
    }
    
    private void displayTreatmentList(DynamicArray<Treatment> treatments) {
        System.out.println("-".repeat(100));
        System.out.printf("%-8s %-8s %-8s %-12s %-20s %-8s%n", 
                         "T-ID", "P-ID", "D-ID", "Date", "Diagnosis", "Cost");
        System.out.println("-".repeat(100));
        
        for (int i = 0; i < treatments.getSize(); i++) {
            Treatment treatment = treatments.getEntry(i);
            if (treatment != null) {
                System.out.printf("%-8s %-8s %-8s %-12s %-20s $%-7.2f%n",
                                treatment.getTreatmentId(),
                                treatment.getPatientId(),
                                treatment.getDoctorId(),
                                treatment.getTreatmentDate(),
                                treatment.getDiagnosis().length() > 20 ? 
                                    treatment.getDiagnosis().substring(0, 17) + "..." : 
                                    treatment.getDiagnosis(),
                                treatment.getCost());
            }
        }
        System.out.println("-".repeat(100));
        System.out.println("Total records: " + treatments.getSize());
    }
    
    private void viewTreatmentSummaryReport() {
        treatmentControl.generateTreatmentSummaryReport();
    }

    private void viewDoctorPerformanceReport() {
        treatmentControl.generateDoctorPerformanceReport();
    }

    private void viewPatientAnalysisReport() {
        treatmentControl.generatePatientAnalysisReport();
    }
    
    public static void main(String[] args) {
        MedicalTreatmentUI ui = new MedicalTreatmentUI();
        ui.displayMenu();
    }
}