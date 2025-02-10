package com.healthcare.models;
import java.util.ArrayList;
import java.util.List;

import com.healthcare.insurance.Insurance;

/**
 * The {@code Patient} class represents a patient in a healthcare system.
 * It stores patient details such as ID, name, billing address, phone number, nationality,
 * and their associated insurance policies.
 */
public class Patient {
    // Variables
    private String id, first_name, last_name, billing_add, nationality, phone_num;
    private List<Insurance> policies = new ArrayList<>(); // Insurance policies list

    /**
     * Default constructor that initializes a patient with default values.
     */
    public Patient() {
        this("None", "None", "None", "None", "00000000", "None");
    }

    /**
     * Parameterized constructor to initialize a patient with specified details.
     *
     * @param id The unique identifier for the patient.
     * @param first_name The patient's first name.
     * @param last_name The patient's last name.
     * @param billing_add The billing address of the patient.
     * @param phone_num The patient's phone number.
     * @param nationality The nationality of the patient.
     */
    public Patient(String id, String first_name, String last_name, String billing_add, String phone_num, String nationality) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.billing_add = billing_add;
        this.phone_num = phone_num;
        this.nationality = nationality;
    }

    /**
     * Adds an insurance policy to the patient's insurance list.
     *
     * @param policy The insurance policy to be added.
     */
    public void addPolicy(Insurance policy) {
        policies.add(policy);
    }

    /**
     * Retrieves the list of insurance policies associated with the patient.
     *
     * @return A list of the patient's insurance policies.
     */
    public List<Insurance> getPolicies() {
        return policies;
    }

    // Setters

    /**
     * Sets the patient's ID.
     *
     * @param id The new patient ID.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Sets the patient's first name.
     *
     * @param firstName The new first name of the patient.
     */
    public void setFirstName(String firstName) {
        this.first_name = firstName;
    }

    /**
     * Sets the patient's last name.
     *
     * @param lastName The new last name of the patient.
     */
    public void setLastName(String lastName) {
        this.last_name = lastName;
    }

    /**
     * Sets the patient's billing address.
     *
     * @param add The new billing address.
     */
    public void setBillAddress(String add) {
        this.billing_add = add;
    }

    /**
     * Sets the phone number after validating its format.
     * 
     * @param number The phone number to be set.
     * @throws IllegalArgumentException if the number is null, contains non-numeric characters, or is of invalid length.
     */
    public void setPhoneNum(String number) {
        if (number == null) {
            throw new IllegalArgumentException("Error: Phone number cannot be null.");
        }
        
        // Ensure it contains only digits and has a valid length (between 8 and 15)
        if (!number.matches("\\d{8,15}")) {
            throw new IllegalArgumentException("Error: Invalid phone number format. Must be 8 to 15 digits.");
        }

        this.phone_num = number;
    }

    /**
     * Sets the patient's nationality.
     *
     * @param nationality The new nationality.
     */
    public void setNationaity(String nationality) {
        this.nationality = nationality;
    }

    // Getters

    /**
     * Retrieves the patient's ID.
     *
     * @return The patient's ID.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Retrieves the full name of the patient.
     *
     * @return The patient's full name (first name + last name).
     */
    public String getName() {
        return this.first_name + " " + this.last_name;
    }

    /**
     * Retrieves the patient's first name.
     *
     * @return The patient's first name.
     */
    public String getFirstName() {
        return this.first_name;
    }

    /**
     * Retrieves the patient's last name.
     *
     * @return The patient's last name.
     */
    public String getLastName() {
        return this.last_name;
    }

    /**
     * Retrieves the patient's billing address.
     *
     * @return The billing address of the patient.
     */
    public String getBillAdd() {
        return this.billing_add;
    }

    /**
     * Retrieves the patient's phone number.
     *
     * @return The patient's phone number.
     */
    public String getPhoneNum() {
        return this.phone_num;
    }

    /**
     * Retrieves the patient's nationality.
     *
     * @return The nationality of the patient.
     */
    public String getNationality() {
        return this.nationality;
    }
}
