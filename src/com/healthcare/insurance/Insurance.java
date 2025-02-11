package com.healthcare.insurance;
import java.time.LocalDate;

import com.healthcare.models.Invoice;
import com.healthcare.models.Patient;

/**
 * The {@code Insurance} class represents an insurance policy for a patient.
 * It includes details about the policy, coverage limits, claim status,
 * and methods for submitting and processing insurance claims.
 */
public class Insurance {
    // Attributes
    private String PolicyID;
    private String InsuranceCompany;
    private String status;
    private String denialReason;
    private double maxCoverage;
    private double coveragePercentage;
    private double remainingCoverage;
    private double approvedAmount;
    private double coveredAmount;
    private double totalAmount;
    private Invoice invoice;
    private Patient patient;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate claimDate;

    // Status constants
    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_APPROVED = "APPROVED";
    public static final String STATUS_REJECTED = "REJECTED";
    public static final String STATUS_PARTIALLY_APPROVED = "PARTIALLY APPROVED";

    /**
     * Constructs an insurance policy with the specified details.
     * 
     * @param PolicyID           The unique policy identifier.
     * @param patient            The patient associated with the policy.
     * @param InsuranceCompany   The name of the insurance provider.
     * @param invoice            The invoice linked to this insurance claim.
     * @param maxCoverage        The maximum coverage amount allowed under the policy.
     * @param coveragePercentage The percentage of the total bill covered by insurance.
     * @param remainingCoverage  The remaining coverage amount available for claims.
     * @param startDate          The start date of the policy.
     */
    public Insurance(String PolicyID, Patient patient, String InsuranceCompany, Invoice invoice,
                     double maxCoverage, double coveragePercentage, double remainingCoverage, LocalDate startDate) {
        this.PolicyID = PolicyID;
        this.invoice = invoice;
        this.patient = patient;
        this.status = STATUS_PENDING;
        this.InsuranceCompany = InsuranceCompany;
        this.maxCoverage = maxCoverage;
        this.remainingCoverage = remainingCoverage;
        this.coveragePercentage = coveragePercentage;
        this.startDate = (startDate != null) ? startDate : LocalDate.now().minusYears(2);
        this.endDate = this.startDate.plusYears(10); // Assume a 10-year policy
        this.claimDate = LocalDate.now(); // Claim date is today
    }

    /**
     * Checks if the policy is valid based on the claim date.
     * 
     * @param claimDate The date the claim is being made.
     * @return {@code true} if the policy is valid; {@code false} otherwise.
     */
    public boolean isPolicyValid(LocalDate claimDate) {
        return !claimDate.isBefore(startDate) && !claimDate.isAfter(endDate);
    }

    /**
     * Submits an insurance claim based on the associated invoice.
     * It calculates the covered amount and updates the claim status.
     */
    public void submitClaim() {
        try {
            if (invoice == null) {
                throw new IllegalStateException("Invoice cannot be null");
            }
            this.totalAmount = invoice.getBilling().calculateGrandTotal();
            this.coveredAmount = 0;

            if (isPolicyValid(claimDate)) {
                double policyCovered = getCoverageAmount(totalAmount);
                coveredAmount += policyCovered;
                deductFromCoverageAmount(policyCovered, coveredAmount, totalAmount);
            } else {
                status = STATUS_REJECTED;
                denialReason = "POLICY_EXPIRED";
            }
        } catch (Exception e) {
            System.err.println("Error submitting claim: " + e.getMessage());
        }
    }

    /**
     * Calculates the insurance coverage amount based on the total bill.
     * 
     * @param totalBill The total amount billed.
     * @return The amount covered by the insurance policy.
     */
    public double getCoverageAmount(double totalBill) {
        double coveredAmount = totalBill * (coveragePercentage / 100.0);
        return Math.min(coveredAmount, remainingCoverage);
    }

    /**
     * Deducts the approved claim amount from the remaining insurance coverage.
     * Updates the status of the claim accordingly.
     * 
     * @param amount        The amount to be deducted from coverage.
     * @param coveredAmount The total amount covered by the insurance.
     * @param totalAmount   The total invoice amount.
     */
    public void deductFromCoverageAmount(double amount, double coveredAmount, double totalAmount) {
        if (amount <= remainingCoverage) {
            remainingCoverage -= amount;
        }

        if (coveredAmount == 0) {
            status = STATUS_REJECTED;
            denialReason = "NO_COVERAGE";
        } else if (coveredAmount < totalAmount) {
            status = STATUS_PARTIALLY_APPROVED;
        } else {
            status = STATUS_APPROVED;
        }

        this.approvedAmount = coveredAmount;
    }

     // Getters and Setters

    /**
     * Retrieves the policy ID.
     * 
     * @return The policy ID.
     */
    public String getPolicyID() {
        return PolicyID;
    }

    /**
     * Retrieves the remaining coverage amount available.
     * 
     * @return The remaining coverage amount.
     */
    public double getRemainingCoverage() {
        return remainingCoverage;
    }

    /**
     * Retrieves the name of the insurance company.
     * 
     * @return The insurance company name.
     */
    public String getInsuranceCompany() {
        return InsuranceCompany;
    }

    /**
     * Retrieves the policy's end date.
     * 
     * @return The policy's end date.
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Retrieves the policy's start date.
     * 
     * @return The policy's start date.
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Retrieves the amount approved for the claim.
     * 
     * @return The approved amount.
     */
    public double getApprovedAmount() {
        return approvedAmount;
    }

    /**
     * Retrieves the maximum coverage amount allowed under the policy.
     * 
     * @return The maximum coverage amount.
     */
    public double getMaxCoverage() {
        return this.maxCoverage;
    }

    /**
     * Retrieves the percentage of the bill covered by insurance.
     * 
     * @return The coverage percentage.
     */
    public double getCoveragePercentage() {
        return this.coveragePercentage;
    }

    /**
     * Sets the invoice associated with the insurance policy.
     * 
     * @param invoice The invoice to be linked to the insurance.
     */
    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    /**
     * Retrieves the invoice linked to the insurance claim.
     * 
     * @return The associated invoice.
     */
    public Invoice getInvoice() {
        return this.invoice;
    }
}