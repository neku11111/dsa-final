/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author Jun Yat
 */
public class Patient {
    private String id;
    private String ic;   
    private String name;
    private int age; 
    private String gender;
    private String contact;
    private String address;
    private String medicalHistory;
    private int priority = 5;

    public Patient(String id, String ic, String name, int age, String gender, String contact, String address, String medicalHistory) {
        this.id = id;
        this.ic = ic;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.contact = contact;
        this.address = address;
        this.medicalHistory = medicalHistory;
    }

    public String getId() {
        return id;
    }

    public String getIc() {
        return ic;
    }

    public String getName() {
        return name;
    }

    public int getAge() {  
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getContact() {
        return contact;
    }

    public String getAddress() {
        return address;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setIc(String ic) {
        this.ic = ic;
    }
    
    public void setName (String name){
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }
    
    public void setGender (String gender){
        this.gender = gender;
    }
    
    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }
    
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "ID='" + id + '\'' +
                ", IC='" + ic + '\'' +
                ", Name='" + name + '\'' +
                ", Age=" + age +
                ", Gender='" + gender + '\'' +
                ", Contact='" + contact + '\'' +
                ", Address='" + address + '\'' +
                ", Medical History='" + medicalHistory + '\'' +
                '}';
    }
}
