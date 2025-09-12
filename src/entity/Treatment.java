/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author LENOVO LOQ 15APH8
 */
public class Treatment {
    private String treatmentId;
    private String patientId;
    private String doctorId;
    private String diagnosis;
    private String prescription;
    private String treatmentDate;
    private String treatmentType;
    private String symptoms;
    private String notes;
    private double cost;
    
    public Treatment() {
        this.treatmentId = "";
        this.patientId = "";
        this.doctorId = "";
        this.diagnosis = "";
        this.prescription = "";
        this.treatmentDate = "";
        this.treatmentType = "";
        this.symptoms = "";
        this.notes = "";
        this.cost = 0.0;
    }
    
    public Treatment(String treatmentId, String patientId, String doctorId, 
                    String diagnosis, String prescription, String treatmentDate,
                    String treatmentType, String symptoms, String notes, double cost) {
        this.treatmentId = treatmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.diagnosis = diagnosis;
        this.prescription = prescription;
        this.treatmentDate = treatmentDate;
        this.treatmentType = treatmentType;
        this.symptoms = symptoms;
        this.notes = notes;
        this.cost = cost;
    }
    
    // Getters
    public String getTreatmentId() { return treatmentId; }
    public String getPatientId() { return patientId; }
    public String getDoctorId() { return doctorId; }
    public String getDiagnosis() { return diagnosis; }
    public String getPrescription() { return prescription; }
    public String getTreatmentDate() { return treatmentDate; }
    public String getTreatmentType() { return treatmentType; }
    public String getSymptoms() { return symptoms; }
    public String getNotes() { return notes; }
    public double getCost() { return cost; }
    
    // Setters
    public void setTreatmentId(String treatmentId) { this.treatmentId = treatmentId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }
    public void setDoctorId(String doctorId) { this.doctorId = doctorId; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }
    public void setPrescription(String prescription) { this.prescription = prescription; }
    public void setTreatmentDate(String treatmentDate) { this.treatmentDate = treatmentDate; }
    public void setTreatmentType(String treatmentType) { this.treatmentType = treatmentType; }
    public void setSymptoms(String symptoms) { this.symptoms = symptoms; }
    public void setNotes(String notes) { this.notes = notes; }
    public void setCost(double cost) { this.cost = cost; }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Treatment treatment = (Treatment) obj;
        return treatmentId.equals(treatment.treatmentId);
    }
    
    @Override
    public String toString() {
        return String.format("Treatment ID: %s | Patient: %s | Doctor: %s | Date: %s | Diagnosis: %s", 
                           treatmentId, patientId, doctorId, treatmentDate, diagnosis);
    }
}