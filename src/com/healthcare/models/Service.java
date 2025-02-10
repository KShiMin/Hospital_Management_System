package com.healthcare.models;
import java.time.LocalDate;

/**
 * The {@code Service} class represents a service provided to a patient.
 * It contains details such as the service code, description, date, quantity, and unit price.
 */
public class Service {

    // Variables
    private String serviceCode, serviceDescript;
    private LocalDate serviceDate;
    private int quantity;
    private double unitPrice;

    /**
     * Default constructor that initializes a service with default values.
     */
    public Service() {
        this("None", "None", null, 0, 0.00);
    }

    /**
     * Parameterized constructor to initialize a service with specified details.
     * 
     * @param sc The service code.
     * @param sd The service description.
     * @param sdate The date when the service was provided.
     * @param q The quantity of the service.
     * @param pr The unit price of the service.
     */
    public Service(String sc, String sd, LocalDate sdate, int q, double pr) {
        this.serviceCode = sc;
        this.serviceDescript = sd;
        this.serviceDate = sdate;
        this.quantity = q;
        this.unitPrice = pr;
    }

    /**
     * Sets the service code.
     * 
     * @param sc The service code to be set.
     */
    public void setServiceCode(String sc) {
        this.serviceCode = sc;
    }

    /**
     * Sets the service description.
     * 
     * @param sd The service description to be set.
     */
    public void setServiceDescript(String sd) {
        this.serviceDescript = sd;
    }

    /**
     * Sets the service date.
     * 
     * @param sdate The date when the service was provided.
     */
    public void setServiceDate(LocalDate sdate) {
        this.serviceDate = sdate;
    }

    /**
     * Sets the quantity of the service.
     * 
     * @param q The quantity of the service (must be greater than 0).
     * @throws IllegalArgumentException if the quantity is 0 or negative.
     */
    public void setQuantity(int q) {
        if (q > 0) {
            this.quantity = q;
        } else {
            throw new IllegalArgumentException("Error: Quantity must be greater than 0.");
        }
    }

    /**
     * Sets the unit price of the service.
     * 
     * @param pr The unit price of the service (must be greater than 0).
     * @throws IllegalArgumentException if the unit price is 0 or negative.
     */
    public void setUnitPrice(double pr) {
        if (pr > 0) {
            this.unitPrice = pr;
        } else {
            throw new IllegalArgumentException("Error: Unit price must be greater than 0.");
        }
    }

    /**
     * Retrieves the service code.
     * 
     * @return The service code.
     */
    public String getServiceCode() {
        return this.serviceCode;
    }

    /**
     * Retrieves the service description.
     * 
     * @return The service description.
     */
    public String getServiceDescript() {
        return this.serviceDescript;
    }

    /**
     * Retrieves the service date.
     * 
     * @return The date when the service was provided.
     */
    public LocalDate getServiceDate() {
        return this.serviceDate;
    }

    /**
     * Retrieves the quantity of the service.
     * 
     * @return The quantity of the service.
     */
    public int getQuantity() {
        return this.quantity;
    }

    /**
     * Retrieves the unit price of the service.
     * 
     * @return The unit price of the service.
     */
    public double getUnitPrice() {
        return this.unitPrice;
    }

    /**
     * Calculates the total price for the service based on quantity and unit price.
     * 
     * @return The total cost of the service.
     */
    public double calculatePrice() {
        return this.quantity * this.unitPrice;
    }
}
