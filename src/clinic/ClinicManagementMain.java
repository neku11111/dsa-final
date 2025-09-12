/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clinic;

/**
 *
 * @author user
 */
import boundary.DoctorManagementUI;
import boundary.MedicalTreatmentUI;
import boundary.PatientManagementUI;
// import clinic.boundary.AppointmentManagementUI;
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


public class ClinicManagementMain {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("    CLINIC MANAGEMENT SYSTEM");
            System.out.println("=".repeat(50));
            System.out.println("Welcome to the Clinic Management System!");
            System.out.println("Streamline your clinic operations in one place.");
            System.out.println("\n" + "=".repeat(50));
            System.out.println("    Main Menu");
            System.out.println("=".repeat(50));
            System.out.println("1. Patient Management");
            System.out.println("2. Doctor Management");
            System.out.println("3. Medical Management");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                choice = -1;
            }

            switch (choice) {
                case 1 -> {
                    PatientManagementUI patientUI = new PatientManagementUI();
                    patientUI.start();
                }
                case 2 -> {
                    DoctorManagementUI ui = new DoctorManagementUI();
                    ui.run();
                }
                case 3 -> {
                    MedicalTreatmentUI ui = new MedicalTreatmentUI();
                    ui.displayMenu();
                }
                case 0 -> System.out.println("Exiting Clinic Management System...");
                default -> System.out.println("X Invalid choice! Try again.");
            }
        } while (choice != 0);
    }
}


