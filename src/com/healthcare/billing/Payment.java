package com.healthcare.billing;
import java.time.LocalDate;

public class Payment {
    // Variables
    private String paymentMethod;
    private String paymentStatus;
    private LocalDate dueDate;

    // Default constructor
    public Payment() {
        this("None", "None", LocalDate.now());
    }

    // Constructor
    public Payment(String paymentMethod, String paymentStatus, LocalDate dueDate) {
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.dueDate = dueDate;
    }

    // Setters
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    // Getters
    public String getPaymentMethod() {
        return this.paymentMethod;
    }

    public String getPaymentStatus() {
        return this.paymentStatus;
    }

    public LocalDate getDueDate() {
        return this.dueDate;
    }

    // Display Payment Details
    public void displayPaymentInfo() {
        System.out.println("Payment Method: " + this.paymentMethod);
        System.out.println("Payment Status: " + this.paymentStatus);
        System.out.println("Due Date: " + this.dueDate);
    }
}