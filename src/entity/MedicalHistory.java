/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;
import entity.Treatment;
import adt.DynamicArray;
//import patient.Patient;  // if you need patient data
//import doctor.Doctor;    // if you need doctor data
/**
 *
 * @author LENOVO LOQ 15APH8
 */
public class MedicalHistory {
    private String patientId;
    private DynamicArray<Treatment> treatments;
    private String allergies;
    private String chronicConditions;
    private String bloodType;
    private String emergencyContact;
    
    // Constructor
    public MedicalHistory(String patientId) {
        this.patientId = patientId;
        this.treatments = new DynamicArray<>();
        this.allergies = "";
        this.chronicConditions = "";
        this.bloodType = "";
        this.emergencyContact = "";
    }
    
    // Constructor with all parameters
    public MedicalHistory(String patientId, String allergies, String chronicConditions, 
                         String bloodType, String emergencyContact) {
        this.patientId = patientId;
        this.treatments = new DynamicArray<>();
        this.allergies = allergies;
        this.chronicConditions = chronicConditions;
        this.bloodType = bloodType;
        this.emergencyContact = emergencyContact;
    }
    
    // Methods to manage treatments using DynamicArray methods
    public boolean addTreatment(Treatment treatment) {
        if (treatment != null && treatment.getPatientId().equals(this.patientId)) {
            return treatments.add(treatment);
        }
        return false;
    }
    
    public Treatment getTreatment(int index) {
        return treatments.getEntry(index);
    }
    
    public Treatment removeTreatment(int index) {
        return treatments.remove(index);
    }
    
    public int getTreatmentCount() {
        return treatments.getSize();
    }
    
    public DynamicArray<Treatment> getAllTreatments() {
        return treatments;
    }
    
    public DynamicArray<Treatment> getTreatmentsByDiagnosis(String diagnosis) {
        DynamicArray<Treatment> result = new DynamicArray<>();
        for (int i = 0; i < treatments.getSize(); i++) {
            Treatment treatment = treatments.getEntry(i);
            if (treatment.getDiagnosis().toLowerCase().contains(diagnosis.toLowerCase())) {
                result.add(treatment);
            }
        }
        return result;
    }
    
    public Treatment getLatestTreatment() {
        if (treatments.isEmpty()) return null;
        return treatments.getEntry(treatments.getSize() - 1);
    }
    
    // Getters and Setters
    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }
    
    public String getAllergies() { return allergies; }
    public void setAllergies(String allergies) { this.allergies = allergies; }
    
    public String getChronicConditions() { return chronicConditions; }
    public void setChronicConditions(String chronicConditions) { this.chronicConditions = chronicConditions; }
    
    public String getBloodType() { return bloodType; }
    public void setBloodType(String bloodType) { this.bloodType = bloodType; }
    
    public String getEmergencyContact() { return emergencyContact; }
    public void setEmergencyContact(String emergencyContact) { this.emergencyContact = emergencyContact; }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        MedicalHistory that = (MedicalHistory) obj;
        return patientId.equals(that.patientId);
    }
    
    @Override
    public String toString() {
        return String.format("Patient ID: %s | Treatments: %d | Allergies: %s | Chronic: %s", 
                           patientId, treatments.getSize(), allergies, chronicConditions);
    }
}
