package org.bee.hms.tests;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import org.bee.hms.billing.Payment;
import org.bee.hms.billing.Invoice;

import org.bee.hms.humans.BloodType;
import org.bee.hms.humans.Sex;
import org.bee.hms.humans.Department;

import org.bee.hms.medical.VisitStatus;

import org.bee.hms.outpatient.*;
import org.bee.hms.outpatient.Physician;;

public class OutPatientTest {
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
        ArrayList<Invoice> inovices = new ArrayList<>();
        
        ArrayList<Drug> drugs = new ArrayList<Drug>();
        LocalDate expDate = LocalDate.now().plusYears(1);
        Drug drug1 = new Drug("Aspirin", "100mg", new Date(System.currentTimeMillis() + 1000000000), 0.10);
        Drug drug2 = new Drug("Paracetamol", "500mg", new Date(System.currentTimeMillis() + 1000000000), 0.20);
        drugs.add(drug1);
        drugs.add(drug2);

        Physician phys1 = new Physician(
                "Dr. John Doe", 
                "S1234567A", 
                "123 Main St", 
                Sex.MALE,  
                "American", 
                45, 
                new Date(78, 4, 21),  // May 21, 1978
                "12345678", 
                Department.INTERNAL_MEDICINE

        );


        ArrayList<String> medHistory = new ArrayList<>();
        medHistory.add("Diabetes");
        medHistory.add("Hypertension");
        Patient patient1 = new Patient(
                "Jane Doe", 
                "S7654321B", 
                "456 Elm St", 
                "Singaporean", 
                Sex.FEMALE, 
                30, 
                new Date(93, 2, 15),  // March 15, 1993
                "87654321", 
                BloodType.O_POSITIVE, 
                medHistory
        );

        ArrayList<LabTest> labtests = new ArrayList<>();
        // Create a sample LabTest object (see LabTest.java :contentReference[oaicite:18]{index=18}&#8203;:contentReference[oaicite:19]{index=19})
        LabTest labTest1 = new LabTest(
                LABTYPE.BLOOD, 
                new Date(), 
                VisitStatus.IN_PROGRESS, 
                phys1, 
                "", 
                50.0
        );
        labtests.add(labTest1);


        List<Procedure> procedures = new ArrayList<>();
        ArrayList<Treatment> treats = new ArrayList<>();
        Treatment treatment1 = new Treatment(
                "Physiotherapy", 
                "Initial session", 
                200.0, 
                procedures
        );
        procedures.add(new Procedure(treatment1, PROCEDURE_TYPE.XRAY, phys1, "Room1", VisitStatus.IN_PROGRESS, "", new Date(), 100.00));
        for(Procedure proce : procedures) {
            treatment1.addProcedure(proce);
        }
        treats.add(treatment1);

        ArrayList<Feedback> feedback = new ArrayList<Feedback>();
        OutpatientCase op1 = new OutpatientCase(
            new Date(),
            Department.ANESTHESIOLOGY,
            "Cough",
            drugs,
            new Date(),
            "eat 2 times a day",
            patient1,
            phys1,
            treats,
            labtests,
            feedback
        );

        // Print out the test data to verify the integration
        System.out.println("=== Drug Objects ===");
        for (Drug d: drugs){
            System.out.println(d.toString());
        }
        
        System.out.println("\n=== Physician Object ===");
        System.out.println(phys1.toString());

        
        System.out.println("\n=== Patient Object ===");
        System.out.println(patient1.toString());
        
        System.out.println("\n=== LabTest Object ===");
        for (LabTest labtest: labtests){
            System.out.println(labtest.toString());
        }
        
        System.out.println("\n=== Treatment Object ===");
        for (Treatment treat: treats){
            System.out.println(treat.toString());
        }

        System.out.println("\n=== Outpatient Object ===");
        System.out.println(op1.toString());

        // generate Invoice Object
        System.out.println("\n=== Invoice Object ===");
        Invoice inv1 = new Invoice(op1);
        inv1.generateInvoice();
        inv1.setPayment(new Payment(PAYMENT_TYPE.CREDIT_CARD, PAYMENT_STATUS.PAID));
        inv1.printInvoice();
        inovices.add(inv1);
    }
}
