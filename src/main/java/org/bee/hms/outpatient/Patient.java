package org.bee.hms.outpatient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private ArrayList<Feedback> feedbacks;                  // List of Feedbacks provided by Patient

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

    private static List<Patient> instances = new ArrayList<>();  // List of Patients

    /**
     * Retrieve and return list of all patient available
     * 
     * @return list containing all patients
     */
    public static List<Patient> getAllPatients() {
        return instances;
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
     * Retrieve a list of all feedbacks made by the patient
     * 
     * @return feedbacks made by the patient
     */
    public ArrayList<Feedback> getFeedbacks() {
        return feedbacks;
    }

    /**
     * Updates list of feedbacks made by the patient
     * 
     * @param feedbacks a list of feedbacks made by the patient to be updated to
     */
    public void setFeedbacks(ArrayList<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    /**
     * Updates list of feedbacks made by patients by adding a new feedback
     * 
     * @param feedback new feedback made by patient
     */
    public void addFeedBack(Feedback feedback) {
        if (!feedbacks.contains(feedback)) {
            feedbacks.add(feedback);
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
     * @param feedbacks     the feedbacks made by the patient
     * 
     */
    public Patient(String name, String nric, String address, String nationality, Sex gender, Integer age,
            Date dateOfBirth, String contactNumber, BloodType bloodType, ArrayList<Feedback> feedbacks) {
        super(name, nric, address, nationality, gender, age, dateOfBirth, contactNumber);   // inherited from superclass Person
        setPatientID(count++);              // Auto assign patientID to count, then increment
        this.bloodType = bloodType;
        this.feedbacks = feedbacks;
        instances.add(this);                // Add new Patient Object to the list of Patients (instances)
    }

    /**
     * Searching of Patient by its ID
     * 
     * @param patientID patient ID
     * @return patient matching the ID
     */
    public static Patient searchPatientbyID(int patientID) {
        for (Patient patient : instances) {
            if (patient.getPatientID() == (patientID)) {
                return patient;
            }
        }
        return null;
    }

    /**
     * Searching of Patient by its NRIC
     * 
     * @param patientNRIC patient NRIC
     * @return patient matching the NRIC
     */
    public static Patient searchPatientbyNRIC(String patientNRIC) {
        for (Patient patient : instances) {
            if (patient.getPatientNRIC().equals(patientNRIC)) {
                return patient;
            }
        }
        return null;
    }

}