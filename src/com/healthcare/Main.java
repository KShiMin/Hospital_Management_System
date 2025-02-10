package com.healthcare;
import java.util.ArrayList;
import java.util.List;

import com.healthcare.billing.Payment;
import com.healthcare.insurance.Insurance;
import com.healthcare.models.Invoice;
import com.healthcare.models.Patient;
import com.healthcare.models.Service;

import java.time.LocalDate;

/**
 * The Main class handles patient billing, insurance claim submission, 
 * and invoice management within a healthcare system.
 */
public class Main {

    /**
     * Calculates the tax rate based on the patient's nationality.
     * 
     * @param nationality The nationality of the patient.
     * @return The tax rate (0% for Singaporean or Permanent Resident, 9% otherwise).
     */
    private static int calculateTaxRate(String nationality) {
        return (nationality.equals("Singaporean") || nationality.equals("Permanent Resident")) ? 0 : 9;
    }

    /**
     * Submits insurance claims for a given patient if the associated policy has an invoice.
     * Ensures that the patient is not null before processing claims.
     * 
     * @param patient The patient whose insurance policies need to be processed.
     * @throws IllegalArgumentException if the patient is null.
     */
    private static void submitInsuranceClaims(Patient patient) {
        if (patient == null) {
            throw new IllegalArgumentException("Error: Patient cannot be null.");
        }

        for (Insurance policy : patient.getPolicies()) {
            try {
                if (policy.getInvoice() != null) { // Ensure invoice is not null
                    policy.submitClaim();
                } else {
                    System.err.println("Warning: Insurance policy " + policy.getPolicyID() + " has no invoice linked.");
                }
            } catch (Exception e) {
                System.err.println("Error processing insurance claim for policy " + policy.getPolicyID() + ": " + e.getMessage());
            }
        }
    }


    /**
     * Prints details of all invoices in the given list.
     * 
     * @param invoices The list of invoices to be printed.
     */
    private static void printInvoices(List<Invoice> invoices) {
        for (Invoice invoice : invoices) {
            invoice.printInvoice();
            System.out.println("");
        }
    }

    /**
     * The main method initializes the system, processes patient invoices, 
     * submits insurance claims, and prints invoice details.
     * 
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        csvHandler dh = new csvHandler();
        List<Invoice> invoiceList = new ArrayList<>();

        // Load test data
        List<Patient> patientList = dh.setPatient();
        List<Service> serviceList = dh.setService();

        // Retrieve services
        Service s1 = serviceList.get(0); // First service
        Service s2 = serviceList.get(1); // Second service
        Service s3 = serviceList.get(2); // Third service
        
        // ===== Invoice 1 (Patient 1) =====
        int taxRateP1 = calculateTaxRate(patientList.get(3).getNationality());
        Invoice invoice1 = new Invoice("INV001", LocalDate.now(), patientList.get(0), "Pay by next week", 10, taxRateP1);

        try {
            // Set services for the invoice
            s1.setServiceDate(LocalDate.now());
            s1.setQuantity(1); // Quantity for service 1
            s2.setServiceDate(LocalDate.now());
            s2.setQuantity(2); // Quantity for service 2

            // Add services to the invoice and set billing details
            invoice1.addService(s1);
            invoice1.addService(s2);
            invoice1.calculateBill();

        } catch (IllegalArgumentException e) {
            System.err.println("Error adding services to invoice: " + e.getMessage());
        }

        // Create Policy and Payment Method
        Insurance policy1 = new Insurance("POL123", patientList.get(0), "HealthCare Inc.", invoice1,
                10000.0, 80.0, 10000.0, LocalDate.now().minusYears(1)); // Policy details

        Payment payment1 = new Payment("Credit Card", "Pending", LocalDate.now().plusWeeks(2));

        patientList.get(0).addPolicy(policy1);
        policy1.setInvoice(invoice1);
        invoice1.setInsuranceClaim(policy1);
        invoice1.setPayment(payment1);

        // Submit insurance claims for Patient 1
        submitInsuranceClaims(patientList.get(0)); 

        // ===== Invoice 2 (Patient 2) =====
        int taxRateP2 = calculateTaxRate(patientList.get(3).getNationality());
        Invoice invoice2 = new Invoice("INV002", LocalDate.now(), patientList.get(3), "Immediate payment required", 15, taxRateP2);
        
        try{
            // Set services for the invoice
            LocalDate prevServiceDate = LocalDate.now().minusDays(2);
            s1.setServiceDate(prevServiceDate);
            s3.setServiceDate(prevServiceDate);
            s3.setQuantity(3); // Quantity for service 3

            // Add services to the invoice and set billing details
            invoice2.addService(s1);
            invoice2.addService(s3);
            invoice2.calculateBill();
        } catch (IllegalArgumentException e) {
                System.err.println("Error adding services to invoice: " + e.getMessage());
        }

        // Create 2nd Policy and Payment Method
        Insurance policy2 = new Insurance("POL124", patientList.get(3), "LifeSecure Inc.", invoice2,
                10000.0, 80.0, 10000.0, LocalDate.now().minusYears(1)); // Policy details
        
        Payment payment2 = new Payment("Cash", "Paid", LocalDate.now());

        patientList.get(3).addPolicy(policy2);
        policy2.setInvoice(invoice2); 
        invoice2.setInsuranceClaim(policy2); 
        invoice2.setPayment(payment2);

        // Submit insurance claims for Patient 2
        submitInsuranceClaims(patientList.get(3));

        // ===== Save and Print Invoices =====
        // Add invoices to the list and save them to a file
        invoiceList.add(invoice1);
        invoiceList.add(invoice2);
        dh.saveInvoicesToFile(invoiceList);

        // Print invoice details
        printInvoices(invoiceList);
    }
}
