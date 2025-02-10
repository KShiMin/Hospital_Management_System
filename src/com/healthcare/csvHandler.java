package com.healthcare;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.healthcare.models.Invoice;
import com.healthcare.models.Patient;
import com.healthcare.models.Service;

import java.time.LocalDate;

/**
 * The {@code csvHandler} class is responsible for handling CSV file operations.
 * It reads patient and service data from CSV files and saves invoice data to a CSV file.
 */
public class csvHandler {

    private List<Patient> patientData = new ArrayList<>();
    private List<Service> serviceData = new ArrayList<>();

    /**
     * Default constructor for the {@code csvHandler} class.
     */
    public csvHandler() {
    }

    /**
     * Reads patient data from the CSV file and initializes the patient list.
     * Each row in the CSV file represents a patient with the following fields:
     * ID, First Name, Last Name, Billing Address, Phone Number, Nationality.
     * 
     * @return A list of {@code Patient} objects.
     */
    public List<Patient> setPatient() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/data/patients.csv"))) {

            String line;

            // Skip the header row
            br.readLine();

            // Process patient data
            while ((line = br.readLine()) != null) {
                String[] pdatas = line.split(",");
                if (pdatas.length < 6) {
                    System.out.println("Skipping invalid row: " + line); // Debugging
                    continue;
                }
                patientData.add(new Patient(pdatas[0], pdatas[1], pdatas[2], pdatas[3], pdatas[4], pdatas[5]));
            }

        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }

        return patientData;
    }

    /**
     * Reads service data from the CSV file and initializes the service list.
     * Each row in the CSV file represents a service with the following fields:
     * Service Code, Service Description, Unit Price.
     * 
     * @return A list of {@code Service} objects.
     */
    public List<Service> setService() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/data/services.csv"))) {

            String line;

            // Skip the header row
            br.readLine();

            // Reads and processes service data
            while ((line = br.readLine()) != null) {
                String[] sdatas = line.split(",");

                double price;
                try {
                    price = Double.parseDouble(sdatas[2]);
                } catch (NumberFormatException e) {
                    price = 0.0; // Default value in case of an invalid number
                }

                // Set serviceDate as null explicitly
                LocalDate serviceDate = null;

                serviceData.add(new Service(sdatas[0], sdatas[1], serviceDate, 0, price));
            }

        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }

        return serviceData;
    }

    /**
     * Saves invoice data to a CSV file.
     * Each row in the CSV file represents an invoice with the following fields:
     * Invoice ID, Billing Date, Billing Address, Patient ID, Total Amount, Discount, Tax Rate, Grand Total.
     * 
     * @param invoices The list of invoices to be saved to the CSV file.
     */
    public void saveInvoicesToFile(List<Invoice> invoices) {
        String filePath = "./data/invoices.csv";

        try (PrintWriter writer = new PrintWriter(filePath, "UTF-8")) {
            writer.println("InvoiceID,BillingDate,BillingAddress,PatientID,TotalAmount,Discount,TaxRate,GrandTotal");
            for (Invoice invoice : invoices) {
                writer.printf("%s,%s,%s,%s,%.2f,%d,%d,%.2f\n",
                        invoice.getInvoiceId(),
                        invoice.getBillingDate(),
                        invoice.getBillingAddress(),
                        invoice.getPatient().getId(),
                        invoice.getBilling().calculateTotalAmount(invoice.getServices()),
                        invoice.getBilling().getDiscount(),
                        invoice.getBilling().getTaxRate(),
                        invoice.getBilling().calculateGrandTotal()
                );
            }

        } catch (IOException e) {
            System.out.println("Error saving invoices: " + e.getMessage());
        }
    }
}
