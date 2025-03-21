package org.bee.hms.outpatient;
// Imports
import java.util.ArrayList;
import java.util.Date;

import org.bee.hms.humans.BloodType;
import org.bee.hms.humans.Sex;

/**
 * Represents a Patient with attributes and methods inherited from Person class and
 * more attributes such as patient ID, blood type and feedbacks
 * 
 * This class stores details about the patient, a person who came to the clinic 
 * for an outpatient case
 * 
 * Each patient entry is uniquely identified by a {@code patientID}, which is
 * automatically
 * assigned using a static counter.
 * 
 * Instances of this class are stored in a static list {@code instances} for
 * retrieval
 * and management.
 */
public class Patient extends Person {
    private static int count = 0;                           // Count for Patient ID Number

    private int patientID;                                  // Patient Unique Identifier
    private BloodType bloodType;                           // Patient Blood Type
    private ArrayList<String> medicalHistory;                  // List of Feedbacks provided by Patient

    /**
     * Returns a formatted string with Patient Details
     * 
     * @return a string with all patient details
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n\nPatient Details:\n");
        stringBuilder.append(String.format("Patient ID:           %s\n", patientID));
        stringBuilder.append(String.format("Blood Type:           %s\n", bloodType));
        stringBuilder.append("\nPersonal Information:\n");
        stringBuilder.append(String.format("Name:                 %s\n", getName()));
        stringBuilder.append(String.format("NRIC:                 %s\n", getNric()));
        stringBuilder.append(String.format("Address:              %s\n", getAddress()));
        stringBuilder.append(String.format("Nationality:          %s\n", getNationality()));
        stringBuilder.append(String.format("Gender:               %s\n", getGender()));
        stringBuilder.append(String.format("Age:                  %s\n", getAge()));
        stringBuilder.append(String.format("Date of Birth:        %s\n", getDateOfBirth()));
        stringBuilder.append(String.format("Contact:              %s", getContactNumber()));

        String string = stringBuilder.toString();
        return string;
    }

    /**
     * Displays a string containing patient information
     * 
     * @return a string with patient information
     */
    public String displayPatientInfo() {
        return "Blood Type : " + bloodType
                + "\nName : " + getName() + "\nNRIC : " + getNric()
                + "\nAddress : " + getAddress() +
                "\nNationality : " + getNationality() + "\nGender : " + getGender() + "\nAge : " + getAge()
                + "\nDate of Birth : " + getDateOfBirth() +
                "\nContact Number : " + getContactNumber() + "\n";
    }

    /**
     * Retrieve patient ID
     * @return patient ID
     */
    public int getPatientID() {
        return patientID;
    }

    /**
     * Retrieve patient NRIC
     * 
     * @return patient nric
     */
    public String getPatientNRIC() {
        return getNric();
    }

    /**
     * Updates patient's ID
     * 
     * @param patientID number for patient ID to be updated to
     */
    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    /**
     * Retrieve blood type of patient
     * @return blood type of patient
     */
    public BloodType getBloodType() {
        return bloodType;
    }

    /**
     * Updates blood type of patient
     * 
     * @param bloodType blood type for patient blood type to be updated to
     */
    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }
    
    /**
     * Retrieves the medical history of the patient.
     * 
     * @return the medical history as a string
     */
    public ArrayList<String> getMedicalHistory() {
        return this.medicalHistory;
    }

    /**
     * Sets the medical history of the patient.
     * 
     * @param medicalHistory the medical history to assign
     */
    public void setMedicalHistory(ArrayList<String> medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    /**
     * Adds the medical history of the patient.
     * 
     * @param medicalHistory the medical history to assign
     */
    public void addMedicalHistory(String medicalHistory) {
        if(!this.medicalHistory.contains(medicalHistory)) {
            this.medicalHistory.add(medicalHistory);
        }
    }


    /**
     * Constructor for creating new Patient Object and adds to list of all patients
     * 
     * @param name          the name of the patient
     * @param nric          the nric of the patient
     * @param address       the address of the patient
     * @param nationality   the nationality of the patient
     * @param gender        the gender of the patient
     * @param age           the age of the patient
     * @param dateOfBirth   the date of birth of the patient
     * @param contactNumber the contact number of the patient
     * @param bloodType     the blood type of the patient
     * @param medicalHistory     the medical history of the patient
     * 
     */
    public Patient(String name, String nric, String address, String nationality, Sex gender, Integer age,
            Date dateOfBirth, String contactNumber, BloodType bloodType, ArrayList<String> medicalHistory) {
        super(name, nric, address, nationality, gender, age, dateOfBirth, contactNumber);   // inherited from superclass Person
        setPatientID(count++);              // Auto assign patientID to count, then increment
        this.bloodType = bloodType;
        this.medicalHistory = medicalHistory;
    }
}