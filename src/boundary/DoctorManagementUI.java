/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

/**
 *
 * @author user
 */
import control.MaintainDoctor;
import entity.Doctor;
import java.util.Scanner;

/**
 *
 * @author Chow Kai Feng
 */



public class DoctorManagementUI {

    private MaintainDoctor maintainDoctor;
    private Scanner sc;
    private int nextId;

    public DoctorManagementUI() {
        maintainDoctor = new MaintainDoctor();
        sc = new Scanner(System.in);
        nextId = 5;
    }

    public void run() {
        int choice;
        do {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("    DOCTOR MANAGEMENT SYSTEM");
            System.out.println("=".repeat(50));
            System.out.println("1. Add Doctor");
            System.out.println("2. View All Doctors");
            System.out.println("3. Search Doctor by ID");
            System.out.println("4. Update Availability");
            System.out.println("5. Update Schedule");
            System.out.println("6. Remove Doctor");
            System.out.println("7. Generate Doctor Availability & Workload Report");
            System.out.println("8. Generate Doctor Specialization & Schedule Report");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");

            choice = readInt();

            switch (choice) {
                case 1 -> addDoctorUI();
                case 2 -> viewAllDoctorsUI();
                case 3 -> searchDoctorUI();
                case 4 -> updateAvailabilityUI();
                case 5 -> updateScheduleUI();
                case 6 -> removeDoctorUI();
                case 7 -> System.out.println(maintainDoctor.generateAvailabilityWorkloadReport());
                case 8 -> System.out.println(maintainDoctor.generateSpecializationScheduleReport());
                case 0 -> System.out.println("Exiting Doctor Management Module...");
                default -> System.out.println("X Invalid choice! Try again.");
            }
        } while (choice != 0);
    }

    private void addDoctorUI() {
        String id = generateDoctorID();
        System.out.print("Enter Name: ");
        String name = sc.nextLine().trim();
        System.out.print("Enter Specialization: ");
        String spec = sc.nextLine().trim();
        String phone = inputPhoneNumber();
        String room = inputRoomNumber(); 
        double fee = inputConsultationFee();

        Doctor d = new Doctor(id, name, spec, true, "Mon-Fri 9am-5pm", phone, room, fee);
        maintainDoctor.addDoctor(d);
        System.out.println("Doctor added successfully!");
    }
    
    private String generateDoctorID() {
        String id = String.format("D%03d", nextId); // D001, D002, ...
        nextId++;
        return id;
    }
    
    private String inputPhoneNumber() {
    String phone;
    while (true) {
        System.out.print("Enter Phone Number (e.g., 012-3456789): ");
        phone = sc.nextLine().trim();
        if (phone.matches("^01[0-9]-\\d{7,8}$")) {
            return phone;
        }
        System.out.println("X Invalid phone number! Must be like 012-3456789 or 019-12345678.");
    }
    }
    
    private String inputRoomNumber() {
    String room;
    while (true) {
        System.out.print("Enter Room Number (e.g., R101): ");
        room = sc.nextLine().trim();
        if (room.matches("^R\\d{3}$")) {
            return room;
        }
        System.out.println("X Invalid room number! Must be in format R### (e.g., R101).");
    }
    }
    
    private double inputConsultationFee() {
    double fee;
    while (true) {
        System.out.print("Enter Consultation Fee (> 0): ");
        try {
            fee = Double.parseDouble(sc.nextLine().trim());
            if (fee > 0) {
                return fee;
            }
            System.out.println("X Fee must be greater than 0.");
        } catch (NumberFormatException e) {
            System.out.println("X Invalid number! Please enter a valid fee.");
        }
    }
    }

    private void viewAllDoctorsUI() {
        System.out.println(maintainDoctor.viewAllDoctors());
    }

    private void searchDoctorUI() {
        String id = inputDoctorID();
        Doctor d = maintainDoctor.searchDoctorById(id);
        if (d != null) {
            System.out.println(d);
        } else {
            System.out.println("X Doctor with ID " + id + " not found.");
        }
    }

    private void updateAvailabilityUI() {
        String id = inputDoctorID();
        System.out.print("Enter availability (true/false): ");
        boolean available = Boolean.parseBoolean(sc.nextLine().trim());
        if (maintainDoctor.updateAvailability(id, available)) {
            System.out.println("Availability updated.");
        } else {
            System.out.println("X Doctor not found.");
        }
    }

    private void updateScheduleUI() {
        String id = inputDoctorID();
        System.out.print("Enter new Schedule: ");
        String schedule = sc.nextLine().trim();
        if (maintainDoctor.updateSchedule(id, schedule)) {
            System.out.println("Schedule updated.");
        } else {
            System.out.println("X Doctor not found.");
        }
    }

    private void removeDoctorUI() {
        String id = inputDoctorID();
        if (maintainDoctor.removeDoctor(id)) {
            System.out.println("Doctor removed.");
        } else {
            System.out.println("X Doctor not found.");
        }
    }

    private String inputDoctorID() {
        String id;
        while (true) {
            System.out.print("Enter Doctor ID (e.g., D001): ");
            id = sc.nextLine().trim();
            if (id.matches("^[D][0-9]{3}$")) {
                return id;
            }
            System.out.println("X Invalid format! Doctor ID must be 1 uppercase letter + 3 digits (e.g., D001).");
        }
    }

    private int readInt() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("X Invalid number! Enter again: ");
            }
        }
    }
    
    public static void main(String[] args) {
        DoctorManagementUI ui = new DoctorManagementUI();
        ui.run();
    }
}
