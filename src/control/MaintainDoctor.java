
package control;


import entity.Doctor;
import adt.DynamicArray;
import adt.DynamicArrayInterface;


public class MaintainDoctor {

    private DynamicArrayInterface<Doctor> doctorList;
    
    public MaintainDoctor() {
        doctorList = new DynamicArray<>();

    }

    public void addDoctor(Doctor doctor) {
        doctorList.add(doctor);
    }
    public Doctor searchDoctorById(String doctorID) {
        for (int i = 0; i < doctorList.size(); i++) {
            Doctor d = doctorList.getEntry(i);
            if (d.getDoctorID().equalsIgnoreCase(doctorID.trim())) {
                return d;
            }
        }
        return null;
    }

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
