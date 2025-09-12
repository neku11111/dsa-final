/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

/**
 *
 * @author Chow Kai Feng
 */
import entity.Doctor;
import adt.DynamicArray;
import adt.DynamicArrayInterface;


public class MaintainDoctor {

    private DynamicArrayInterface<Doctor> doctorList;
    
    public MaintainDoctor() {
        doctorList = new DynamicArray<>();
        
        doctorList.add(new Doctor("D001", "Dr. Ali", "General", true, "Mon-Fri 9am-5pm", "012-3456789", "Room 101", 50.0));
        doctorList.add(new Doctor("D002", "Dr. Siti", "Pediatrician", false, "Mon, Wed, Fri 9am-1pm", "013-9876543", "Room 102", 80.0));
        doctorList.add(new Doctor("D003", "Dr. Lim", "Cardiology", true, "Tue-Fri 10am-4pm", "016-2233445", "Room 201", 120.0));
        doctorList.add(new Doctor("D004", "Dr. Tan", "General", true, "Tue-Thu 10am-3pm", "019-7788990", "Room 103", 60.0));

    }

    //Add Doctor Function
    public void addDoctor(Doctor doctor) {
        doctorList.add(doctor);
    }

    //Search Doctor By ID Function
    public Doctor searchDoctorById(String doctorID) {
        for (int i = 0; i < doctorList.size(); i++) {
            Doctor d = doctorList.getEntry(i);
            if (d.getDoctorID().equalsIgnoreCase(doctorID.trim())) {
                return d;
            }
        }
        return null;
    }

    //Update Availability Function
    public boolean updateAvailability(String doctorID, boolean availability) {
        Doctor d = searchDoctorById(doctorID);
        if (d != null) {
            d.setAvailability(availability);
            return true;
        }
        return false;
    }

    //update Schedule Function
    public boolean updateSchedule(String doctorID, String schedule) {
        Doctor d = searchDoctorById(doctorID);
        if (d != null) {
            d.setSchedule(schedule);
            return true;
        }
        return false;
    }

    //Remove Doctor Function
    public boolean removeDoctor(String doctorID) {
        for (int i = 0; i < doctorList.size(); i++) {
            Doctor d = doctorList.getEntry(i);
            if (d.getDoctorID().equalsIgnoreCase(doctorID.trim())) {
                doctorList.remove(i);
                return true;
            }
        }
        return false;
    }

    //View All Doctors
    public String viewAllDoctors() {
        if (doctorList.isEmpty()) {
            return "No doctors available.";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < doctorList.size(); i++) {
            sb.append(doctorList.getEntry(i)).append("\n");
        }
        return sb.toString();
    }

    //Generate Availability & Workload Report Function
    public String generateAvailabilityWorkloadReport() {
    int total = doctorList.size();
    int availableCount = 0, notAvailableCount = 0;

    StringBuilder sb = new StringBuilder("=== Doctor Availability & Workload Report ===\n");

    for (int i = 0; i < total; i++) {
        Doctor d = doctorList.getEntry(i);
        if (d != null) {
            if (d.isAvailable()) availableCount++;
            else notAvailableCount++;
        }
    }

    sb.append("Total Doctors: ").append(total).append("\n")
      .append("Available: ").append(availableCount).append("\n")
      .append("Not Available: ").append(notAvailableCount).append("\n\n");

    sb.append("Doctor Details:\n");
    for (int i = 0; i < total; i++) {
        Doctor d = doctorList.getEntry(i);
        if (d != null) {
            int workDays = d.getSchedule().split("[, ]+").length; // 简单统计排班天数
            sb.append(d.getDoctorID()).append(" - ").append(d.getName())
              .append(" (").append(d.getSpecialization()).append(") | ")
              .append("Status: ").append(d.isAvailable() ? "Available" : "Not Available")
              .append(" | Work Days: ").append(workDays)
              .append(" | Schedule: ").append(d.getSchedule())
              .append("\n");
        }
    }

    return sb.toString();
}

    //Generate Specialization Schedule Report Function
    public String generateSpecializationScheduleReport() {
    StringBuilder sb = new StringBuilder("=== Doctor Specialization & Schedule Report ===\n");

    java.util.Map<String, StringBuilder> specMap = new java.util.HashMap<>();

    for (int i = 0; i < doctorList.size(); i++) {
        Doctor d = doctorList.getEntry(i);
        if (d != null) {
            specMap.putIfAbsent(d.getSpecialization(), new StringBuilder());
            specMap.get(d.getSpecialization())
                   .append("  - ").append(d.getDoctorID())
                   .append(" ").append(d.getName())
                   .append(" | Schedule: ").append(d.getSchedule())
                   .append("\n");
        }
    }

    for (var entry : specMap.entrySet()) {
        sb.append(entry.getKey()).append(":\n")
          .append(entry.getValue());
    }

    return sb.toString();
}
}