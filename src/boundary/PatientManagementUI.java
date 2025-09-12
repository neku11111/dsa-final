/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

import entity.Patient;
import control.PatientManager;
import java.util.*;
import adt.DynamicArray;

/**
 *
 * @author Jun Yat
 */
public class PatientManagementUI {

    public Scanner scanner = new Scanner(System.in);

    public void start() {
        PatientManager manager = new PatientManager();
        manager.run();
    }

    public int Menu() {
        System.out.println("\n===== Patient Management System =====");
        System.out.println("1. Register a new patient");
        System.out.println("2. Display Patient Records");
        System.out.println("3. Search Patient ");
        System.out.println("4. Update Patient Records");
        System.out.println("5. Delete Patient");
        System.out.println("6. Manage Queue for Patient");
        System.out.println("7. Generate Reports");
        System.out.println("8. Back To Main Menu");
        System.out.print("Enter your choice: ");

        int choice = -1;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a number between 1-8.");
        }
        return choice;
    }

    public Patient getPatientDetails(DynamicArray<Patient> patientList, String generatedId) {
        System.out.println("\n--- Register New Patient ---");
        String id = generatedId;
        showMessage("Assigned Patient ID: " + id);

        String ic;
        while (true) {
            System.out.print("Enter Patient IC (xxxxxx-xx-xxxx): ");
            ic = scanner.nextLine().trim();
            if (!ic.matches("\\d{6}-\\d{2}-\\d{4}")) {
                System.out.println("Invalid IC format! Correct format: xxxxxx-xx-xxxx (digits only).");
                continue;
            }
            boolean exists = false;
            for (int i = 0; i < patientList.getSize(); i++) {
                if (patientList.getEntry(i).getIc().equalsIgnoreCase(ic)) {
                    exists = true;
                    break;
                }
            }
            if (exists) {
                System.out.println("IC already exists. Enter a unique IC.");
            } else {
                break;
            }
        }

        System.out.print("Enter Patient Name: ");
        String name = scanner.nextLine();

        int age = -1;
        while (age < 0) {
            System.out.print("Enter Age: ");
            try {
                age = Integer.parseInt(scanner.nextLine());
                if (age < 0) {
                    System.out.println("Age cannot be negative.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Enter a valid number.");
            }
        }

        String gender;
        while (true) {
            System.out.print("Enter Gender (M/F): ");
            gender = scanner.nextLine().trim().toUpperCase();
            if (!gender.equals("M") && !gender.equals("F")) {
                System.out.println("Invalid input! Gender must be 'M' or 'F'.");
            } else {
                break;
            }
        }

        String contact;
        while (true) {
            System.out.print("Enter Contact (Ie. 0164470280 // 01110478899): ");
            contact = scanner.nextLine().trim();
            if (contact.matches("01\\d{8}") || contact.matches("011\\d{8}")) {
                break;
            }
            System.out.println("Invalid contact format!");
        }

        System.out.print("Enter Address: ");
        String address = scanner.nextLine();

        System.out.print("Enter Medical History (optional, '-' if none): ");
        String medicalHistory = scanner.nextLine().trim();
        if (medicalHistory.isEmpty()) {
            medicalHistory = "-";
        }

        Patient patient = new Patient(id, ic, name, age, gender, contact, address, medicalHistory);

        System.out.println("Patient Created: " + patient);
        return patient;
    }

    public int showDisplayMenu() {
        System.out.println("\n--- Display Patients Menu ---");
        System.out.println("1. Display all patients (registration order)");
        System.out.println("2. Sort by Name (A -> Z)");
        System.out.println("3. Sort by Name (Z -> A)");
        System.out.println("4. Sort by Registration Time (newest first)");
        System.out.println("5. Filter by Gender");
        System.out.println("6. Back to Main Menu");
        System.out.print("Enter your choice: ");
        int choice = -1;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Enter 1-6.");
        }
        return choice;
    }

    public String getGenderFilter() {
        String gender;
        do {
            System.out.print("Enter gender to filter (M/F): ");
            gender = scanner.nextLine().trim().toUpperCase();
            if (!gender.equals("M") && !gender.equals("F")) {
                System.out.println("Invalid gender!");
            } else {
                break;
            }
        } while (true);
        return gender;
    }

    public void displayPatients(DynamicArray<Patient> list) {
        if (list.isEmpty()) {
            System.out.println("No patients found.");
            return;
        }
        System.out.printf("%-12s || %-13s || %-20s || %-4s || %-6s || %-12s || %-20s || %-20s%n",
                "Patient ID", "IC", "Name", "Age", "Gender", "Contact", "Address", "Medical History");
        System.out.println("---------------------------------------------------------------------------------------------"
                + "---------------------------------");
        for (int i = 0; i < list.getSize(); i++) {
            Patient p = list.getEntry(i);
            System.out.printf("%-12s || %-13s || %-20s || %-4d || %-6s || %-12s || %-20s || %-20s%n",
                    p.getId(), p.getIc(), p.getName(), p.getAge(), p.getGender(),
                    p.getContact(), p.getAddress(), p.getMedicalHistory());
        }
    }

    public int showSearchMenu() {
        System.out.println("\n--- Search Patient Menu ---");
        System.out.println("1. Search by ID or IC");
        System.out.println("2. Search by Name");
        System.out.println("3. Search by Medical History");
        System.out.println("4. Back to Main Menu");
        System.out.print("Enter your choice: ");
        int choice = -1;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Enter 1-4.");
        }
        return choice;
    }

    public String getSearchKeyword(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }

    public String getInput(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }

    public int getIntInput(String message) {
        int value = -1;
        while (true) {
            System.out.print(message);
            try {
                value = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input!");
            }
        }
        return value;
    }

    public void displayPatient(Patient p) {
        System.out.printf("%-12s || %-13s || %-20s || %-4s || %-6s || %-12s || %-20s || %-20s%n",
                "Patient ID", "IC", "Name", "Age", "Gender", "Contact", "Address", "Medical History");
        System.out.println("---------------------------------------------------------------------------------------------"
                + "---------------------------------");
        System.out.printf("%-12s || %-13s || %-20s || %-4d || %-6s || %-12s || %-20s || %-20s%n",
                p.getId(), p.getIc(), p.getName(), p.getAge(), p.getGender(),
                p.getContact(), p.getAddress(), p.getMedicalHistory());
    }

    public String getPatientId(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }

    public boolean confirmDeletion(Patient patient) {
        displayPatient(patient);
        String confirm;
        do {
            System.out.print("\nAre you sure you want to delete this patient? (Y/N): ");
            confirm = scanner.nextLine().trim().toUpperCase();
            if (confirm.equals("Y")) {
                return true;
            } else if (confirm.equals("N")) {
                return false;
            } else {
                System.out.println("Invalid input! Enter Y or N.");
            }
        } while (true);
    }

    public int showQueueMenu() {
        System.out.println("\n--- Patient Queue Menu ---");
        System.out.println("1. Add patient to queue (Normal)");
        System.out.println("2. Add patient to queue with priority");
        System.out.println("3. Serve next patient");
        System.out.println("4. View queue");
        System.out.println("5. Remove patient from queue");
        System.out.println("6. Clear entire queue");
        System.out.println("7. Peek next patient");            // NEW
        System.out.println("8. Change patient priority");       // NEW
        System.out.println("9. Back to Main Menu");
        System.out.print("Enter your choice: ");

        int choice = -1;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Enter 1-9.");
        }
        return choice;
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public int showReportMenu() {
        System.out.println("\n===== Patient Reports =====");
        System.out.println("1. Medical History Report");
        System.out.println("2. Gender Distribution Report");
        System.out.println("3. Age Group Distribution Report");
        System.out.println("4. Back to Main Menu");
        System.out.print("Enter your choice: ");

        int choice = -1;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Enter 1-4.");
        }
        return choice;
    }
}
