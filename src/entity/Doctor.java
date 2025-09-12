/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author Chow Kai Feng
 */
/**
 *
 * @author user
 */
public class Doctor {
    private String doctorID;
    private String name;
    private String specialization;
    private boolean availability;
    private String schedule;
    private String phoneNumber;    
    private String roomNumber;      
    private double consultationFee; 

    public Doctor(String doctorID, String name, String specialization,
                  boolean availability, String schedule,
                  String phoneNumber, String roomNumber, double consultationFee) {
        this.doctorID = doctorID;
        this.name = name;
        this.specialization = specialization;
        this.availability = availability;
        this.schedule = schedule;
        this.phoneNumber = phoneNumber;
        this.roomNumber = roomNumber;
        this.consultationFee = consultationFee;
    }

    public String getDoctorID() { return doctorID; }
    public String getName() { return name; }
    public String getSpecialization() { return specialization; }
    public boolean isAvailable() { return availability; }
    public String getSchedule() { return schedule; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getRoomNumber() { return roomNumber; }
    public double getConsultationFee() { return consultationFee; }

    public void setAvailability(boolean availability) { this.availability = availability; }
    public void setSchedule(String schedule) { this.schedule = schedule; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
    public void setConsultationFee(double consultationFee) { this.consultationFee = consultationFee; }

    @Override
    public String toString() {
        return doctorID + " - " + name + " (" + specialization + ") | " +
               (availability ? "Available" : "Not Available") +
               " | Schedule: " + schedule +
               " | Phone: " + phoneNumber +
               " | Room: " + roomNumber +
               " | Fee: RM" + consultationFee;
    }
}
