package com.healthcare.billing;

import java.time.LocalDate;

/**
 * Represents a payment associated with a billing transaction.
 * This class stores details about the payment method, status, and due date.
 */
public class Payment {
    // Variables
    private String paymentMethod;
    private String paymentStatus;
    private LocalDate dueDate;

    /**
     * Default constructor that initializes the payment with default values.
     * The default payment method and status are set to "None", and the due date is set to today.
     */
    public Payment() {
        this("None", "None", LocalDate.now());
    }

    /**
     * Constructs a Payment object with the specified payment method, status, and due date.
     * 
     * @param paymentMethod The method of payment (e.g., Credit Card, Cash, etc.).
     * @param paymentStatus The current status of the payment (e.g., Paid, Pending).
     * @param dueDate       The due date for the payment.
     */
    public Payment(String paymentMethod, String paymentStatus, LocalDate dueDate) {
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.dueDate = dueDate;
    }

    /**
     * Sets the payment method.
     * 
     * @param paymentMethod The method of payment to be set.
     */
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    /**
     * Sets the payment status.
     * 
     * @param paymentStatus The status of the payment to be set (e.g., Paid, Pending).
     */
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    /**
     * Sets the due date for the payment.
     * 
     * @param dueDate The due date to be set.
     */
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Retrieves the payment method.
     * 
     * @return The payment method as a string.
     */
    public String getPaymentMethod() {
        return this.paymentMethod;
    }

    /**
     * Retrieves the payment status.
     * 
     * @return The payment status as a string.
     */
    public String getPaymentStatus() {
        return this.paymentStatus;
    }

    /**
     * Retrieves the due date of the payment.
     * 
     * @return The due date as a LocalDate object.
     */
    public LocalDate getDueDate() {
        return this.dueDate;
    }
}
