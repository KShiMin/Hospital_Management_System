package org.bee.hms.outpatient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.bee.hms.humans.Department;
import org.bee.hms.medical.ConsultationType;
import org.bee.hms.medical.VisitStatus;
import org.bee.hms.outpatient.OutpatientCase;
import org.bee.hms.outpatient.Treatment;
import org.bee.hms.utils.OpUtils;

public class ClerkPortal {
    
    static List<OutpatientCase> all_outpatientcases = new ArrayList<>();

    public static void showClerkMenu(Scanner scan){
        String outpatientCaseID = "";
        boolean clerkMenu = true;
        while (clerkMenu) {
            System.out.println(
                    "\n----------------------------------------------------------------------------");
            System.out.println("\tWelcome to Clinic Clerk's Portal");
            System.out.println(
                    "----------------------------------------------------------------------------");
            System.out.println("Following Functionalities are available: \n");

            System.out.println("1- View all Outpatient Cases");
            System.out.println("2- Update Field for Outpatient Case");
            System.out.println("3- Create new Outpatient Case");
            System.out.println("4- Create New Drug");
            System.out.println("5- View Billing for Outpatient Case");
            System.out.println("6- Update Bill Payment Status for Outpatient Case");
            System.out.println("7- Create New Patient");
            System.out.println("8- Create New Physician");
            System.out.println("9- Return to previous");
            System.out.println("10- Exit");

            System.out.println(
                    "----------------------------------------------------------------------------");
            int clerkMenu_input = OpUtils.optionsInput(scan, 0, 11);
            if (clerkMenu_input == 10) {
                OpUtils.printExit();
                clerkMenu = true;
                break;
            }
            switch (clerkMenu_input) {
                case 1:
                    viewAllOutpatientCasesClerk();
                    break;
                case 2:
                    System.out.println("Enter the Outpatient Case ID to update: ");
                    outpatientCaseID = scan.nextLine();
                    OutpatientCase outpatientCase = OutpatientCase
                            .searchOutpatientCaseByID(Integer.valueOf(outpatientCaseID));
                    updateOutpatientCaseStatus(outpatientCase, scan);
                    break;
                case 3:
                    createOutpatientCase(scan);
                    // updateDataset();
                    break;
                case 4:
                    createNewDrug(scan);
                    // updateDataset();
                    break;
                case 5:
                    System.out.println("Enter the Outpatient Case ID for the Bill: ");
                    // outpatientCaseID = scan.nextLine();
                    // viewBillingByOutpatientCase(Integer.valueOf(outpatientCaseID), scan);
                    break;
                case 6:
                    System.out.println("Enter the Outpatient Case ID for the Bill to update: ");
                    // outpatientCaseID = scan.nextLine();
                    // updateBillStatus(Integer.valueOf(outpatientCaseID), scan);
                    break;
                case 7:
                    PatientPortal.createNewPatient(scan);
                    // updateDataset();
                    break;
                case 8:
                    PhysicianPortal.createNewPhysician(scan);
                    // updateDataset();
                    break;
                case 9:
                    // clearScreen();
                    clerkMenu = false;
                    break;
            }
        }
    }
    
    /**
     * Creates a new drug object.
     * This method allows the clerk to add a new drug into the inventory for
     * physicians to add to patient's medications.
     * 
     * @param scanner The {@link Scanner} object used for input.
     * 
     */
    public static void createNewDrug(Scanner scanner) {
        boolean addMore = true;

        while (addMore) {
            System.out.print("Enter the Drug Name: ");
            String drugName = scanner.nextLine();

            System.out.print("Enter the Dosage: ");
            String dosage = scanner.nextLine();

            Date expiryDate = null;
            while (true) {
                System.out.print("Enter the Expiry Date (dd-MM-yyyy): ");
                String expiryDateInput = scanner.nextLine().trim();

                expiryDate = OpUtils.customDate(expiryDateInput);

                if (expiryDate != null) {
                    break;
                } else {
                    System.out.println("Invalid date format! Please enter in dd-MM-yyyy format.");
                }
            }

            System.out.print("Enter the Cost Per Unit: ");
            double costPerUnit = Double.parseDouble(scanner.nextLine());

            // Create and add the drug to the list
            Drug newDrug = new Drug(drugName, dosage, expiryDate, costPerUnit);
            // all_drugs.add(newDrug);

            System.out.println("New Drug created and added successfully!");
            System.out.println(newDrug); // Display the newly added drug

            // Ask if the user wants to add more drugs
            System.out.print("Would you like to add another drug? (yes/no): ");
            String response = scanner.nextLine().trim().toLowerCase();
            addMore = response.equals("yes");
        }
    }

    // ===============clerk functs=================

    /**
     * Creates a outpatient case object.
     * This method allows the clerk to create a new outpatient case.
     * 
     * @param scan The {@link Scanner} object used for input.
     * 
     */
    public static void createOutpatientCase(Scanner scan) {
        // Retrieve Patient by ID
        System.out.print("Enter Patient ID: ");
        String patientID = scan.nextLine();
        Patient patient = Patient.searchPatientbyID(Integer.valueOf(patientID));

        if (patient == null) {
            System.out.println("Patient not found. Returning to main menu.");
            return;
        }

        // Retrieve Physician by ID
        System.out.print("Enter Physician ID: ");
        String physicianID = scan.nextLine();
        Physician physician = Physician.searchPhysicianByID(Integer.valueOf(physicianID));

        if (physician == null) {
            System.out.println("Physician not found. Returning to main menu.");
            return;
        }

        // Select Case Type
        System.out.println("Select Case Type:");
        for (ConsultationType type : ConsultationType.values()) {
            System.out.println((type.ordinal() + 1) + " - " + type);
        }
        System.out.print("Enter case type number: ");
        ConsultationType caseType = ConsultationType.values()[scan.nextInt() - 1];
        scan.nextLine();

        // Select Status
        System.out.println("Select Case Status:");
        for (VisitStatus status : VisitStatus.values()) {
            System.out.println((status.ordinal() + 1) + " - " + status);
        }
        System.out.print("Enter status number: ");
        VisitStatus status = VisitStatus.values()[scan.nextInt() - 1];
        scan.nextLine();

        // Appointment Date
        Date appointmentDate = null;
        while (true) {
            System.out.println("Enter Appointment Date (dd-MM-yyyy): ");
            String input = scan.nextLine().trim();

            appointmentDate = OpUtils.customDate(input);

            if (appointmentDate != null) {
                break; // Valid date, exit loop
            } else {
                System.out.println("Invalid date format! Please enter in dd-MM-yyyy format.");
            }
        }

        System.out.println("Appointment Date: " + (appointmentDate != null ? appointmentDate : "Not set"));

        // Medical History, Diagnosis, Reason for Visit3

        System.out.print("Enter Medical History: ");
        String medicalHistory = scan.nextLine();

        System.out.print("Enter Diagnosis: ");
        String diagnosis = scan.nextLine();

        System.out.print("Enter Reason for Visit: ");
        String visitReason = scan.nextLine();

        // Select Department
        System.out.println("Select Department:");
        for (Department dept : Department.values()) {
            System.out.println((dept.ordinal() + 1) + " - " + dept);
        }
        System.out.print("Enter department number: ");
        Department department = Department.values()[scan.nextInt() - 1];
        scan.nextLine();

        // Follow-Up Date
        Date followUpDate = null;
        while (true) {
            System.out.println("Enter Follow-Up Date (dd-MM-yyyy) or press 'Enter' to leave blank: ");
            String input = scan.nextLine().trim();

            if (input.isEmpty()) {
                break;
            }

            followUpDate = OpUtils.customDate(input);

            if (followUpDate != null) {
                break;
            } else {
                System.out.println("Invalid date format! Please enter in dd-MM-yyyy format.");
            }
        }
        System.out.println("Follow-Up Date: " + (followUpDate != null ? followUpDate : "Not set"));

        // Instructions
        System.out.print("Enter Instructions: ");
        String instructions = scan.nextLine();

        // Create Outpatient Case
        OutpatientCase newCase = new OutpatientCase(
                appointmentDate, medicalHistory, caseType, status, department,
                diagnosis, visitReason, new ArrayList<>(), new ArrayList<>(), followUpDate,
                instructions, patient, physician, null,
                new ArrayList<>(), new ArrayList<>());

        while (true) {
            System.out.print("Enter a Current Medication? (yes/no): ");
            if (!scan.nextLine().equalsIgnoreCase("yes")) {
                break;
            }
            System.out.println(
                    "------------------------------------------------------------------------------------------------------------");
            System.out.println("Current Drug List");
            for (Drug drug : Drug.getAllDrugs()) {
                if (drug != null) {
                    System.out.printf("%-15s | %-20s | %-12s | %-10s | %-12.2f \n",
                            drug.getDrugID(),
                            drug.getDrugName(),
                            drug.getDosage(),
                            drug.getExpiryDate(),
                            drug.getCostPerUnit());
                }
            }
            Boolean validReply = true;
            String reply = "";

            while (validReply) {
                System.out.println(
                        "Please select the drug IDs that you wish to add to the prescriptions separated by commas (e.g., 1,2):");
                reply = scan.nextLine();
                if (reply.matches("^[0-9,]+$")) {
                    validReply = false;
                    String[] drugIDs = reply.split(",");

                    for (String id : drugIDs) {
                        Drug drug = Drug.searchDrugByID(Integer.parseInt(id.trim()));
                        if (!newCase.getPrescriptions().contains(drug)) {
                            newCase.getPrescriptions().add(drug);
                        }
                    }
                } else {
                    System.out.println("Invalid input. Please enter drug IDs separated by commas.");
                }
            }
        }
        all_outpatientcases.add(newCase);
        System.out.println("\nOutpatient case created successfully!");
    }

    /**
     * Prints all outpatient case for the clerk to view
     * 
     */
    public static void viewAllOutpatientCasesClerk() {
        List<OutpatientCase> cases = OutpatientCase.getAllOutpatientCases();

        if (cases.isEmpty()) {
            System.out.println("No outpatient cases found.");
            return;
        }

        System.out.printf("%-10s | %-30s | %-10s | %-15s | %-15s | %-30s | %-15s | %-15s | %-10s | %-10s\n",
                "Case ID", "Appointment Date", "Patient ID", "Patient Name", "Status", "Diagnosis",
                "Physician ID", "Physician Name", "Billing ID", "Total Cost");
        System.out.println("-".repeat(190));

        for (OutpatientCase oc : cases) {
            System.out.printf("%-10s | %-30s | %-10s | %-15s | %-15s | %-30s | %-15s | %-15s | %-10s | $%-10.2f\n",
                    oc.getOutpatientCaseID(),
                    oc.getAppointmentDate(),
                    oc.getPatient() != null ? oc.getPatient().getPatientID() : "N/A",
                    oc.getPatient() != null ? oc.getPatient().getName() : "N/A",
                    oc.getStatus(),
                    oc.getDiagnosis(),
                    oc.getPhysician() != null ? oc.getPhysician().getPhysicianID() : "N/A",
                    oc.getPhysician() != null ? oc.getPhysician().getName() : "N/A",
                    oc.getBilling() != null ? oc.getBilling().getBillingID() : "N/A",
                    oc.getBilling() != null ? oc.getBilling().getFinalCost() : 0.0);
        }
    }
    
    /**
     * Updates the status/details of outpatient case object.
     * This method allows the clerk to update the status of an outpatient case e.g
     * from SCHEDULED to IN_PROGRESS etc
     *
     * @param outpatientCase The outpatientCase object to be updated.
     * @param scanner        The {@link Scanner} object used for input.
     * 
     */
    public static void updateOutpatientCaseStatus(OutpatientCase outpatientCase, Scanner scanner) {
        if (outpatientCase == null) {
            System.out.println("Invalid outpatient case.");
            return;
        }

        System.out.println("Updating details for Outpatient Case ID: " + outpatientCase.getOutpatientCaseID());

        // Case Type
        System.out.print("Enter new Case Type (or press Enter to keep current): ");
        for (ConsultationType type : ConsultationType.values()) {
            System.out.println((type.ordinal() + 1) + " - " + type);
        }
        System.out.print("Enter case type number: ");

        // scanner.nextLine();
        String casetype = scanner.nextLine().trim();

        if (!casetype.isEmpty()) {
            try {
                int caseTypeIndex = Integer.parseInt(casetype) - 1;
                if (caseTypeIndex >= 0 && caseTypeIndex < ConsultationType.values().length) {
                    ConsultationType caseType = ConsultationType.values()[caseTypeIndex];
                    outpatientCase.setType(caseType);
                } else {
                    System.out.println("Invalid selection. Keeping current case type.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Keeping current case type.");
            }
        } else {
            System.out.println("Keeping current case type.");
        }

        // Case Status
        // Select Status
        System.out.println("Select Case Status:");
        for (VisitStatus status : VisitStatus.values()) {
            System.out.println((status.ordinal() + 1) + " - " + status);
        }
        System.out.print("Enter New status number (or press Enter to keep current): ");

        scanner.nextLine();
        String caseStatus = scanner.nextLine().trim();

        if (!caseStatus.isEmpty()) {
            try {
                int statusIndex = Integer.parseInt(caseStatus) - 1;
                if (statusIndex >= 0 && statusIndex < VisitStatus.values().length) {
                    VisitStatus newStatus = VisitStatus.values()[statusIndex];
                    outpatientCase.setStatus(newStatus);
                } else {
                    System.out.println("Invalid selection. Keeping current status.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Keeping current status.");
            }
        } else {
            System.out.println("Keeping current status.");
        }

        // Appointment Date
        System.out.print("Enter New Appointment Date (dd-MM-yyyy) (or press Enter to keep current): ");
        String dateInput = scanner.nextLine().trim();

        if (!dateInput.isEmpty()) {
            Date newDate = OpUtils.customDate(dateInput);
            if (newDate != null) {
                outpatientCase.setAppointmentDate(newDate);
            } else {
                System.out.println("Invalid date format. Keeping current appointment date.");
            }
        } else {
            System.out.println("Keeping current appointment date.");
        }

        // Follow-Up Date
        System.out.print("Enter Follow-Up Date (dd-MM-yyyy) (or press Enter to keep current): ");
        String followUp = scanner.nextLine().trim();

        if (!followUp.isEmpty()) {
            Date newFollowUpDate = OpUtils.customDate(followUp);
            if (newFollowUpDate != null) {
                outpatientCase.setFollowUpDate(newFollowUpDate);
            } else {
                System.out.println("Invalid date format. Keeping current follow-up date.");
            }
        } else {
            System.out.println("Keeping current follow-up date.");
        }

        // Treatments
        // Retrieve treatment by ID
        System.out.print("Enter Treatment ID (or press Enter to keep current): ");
        String treatmentID = scanner.nextLine().trim();
        if (!treatmentID.isEmpty()) {
            Treatment treatment = Treatment.searchTreatmentByID(Integer.parseInt(treatmentID));
            if (treatment != null) {
                // Select Treatment Status
                System.out.println("Select the Treatment Status:");
                for (VisitStatus s : VisitStatus.values()) {
                    System.out.println(s.ordinal() + " - " + s);
                }
                System.out.print("Enter status number (or press Enter to keep current): ");
                String statusInput = scanner.nextLine().trim();
                if (!statusInput.isEmpty()) {
                    int statusIndex = Integer.parseInt(statusInput);
                    VisitStatus treatmentStatus = VisitStatus.values()[statusIndex];
                    treatment.setStatus(treatmentStatus);
                    System.out.println("Status updated successfully!");
                } else {
                    System.out.println("Keeping current status.");
                }

                // Start Date
                System.out.print("Enter the Start Date (dd-MM-yyyy) (or press Enter to keep current): ");
                String startDateInput = scanner.nextLine().trim();
                if (!startDateInput.isEmpty()) {
                    Date newStartDate = OpUtils.customDate(startDateInput);
                    if (newStartDate != null) {
                        treatment.setStartDate(newStartDate);
                        System.out.println("Start date updated successfully!");
                    } else {
                        System.out.println("Invalid date format. Keeping current start date.");
                    }
                } else {
                    System.out.println("Keeping current start date.");
                }

                // End Date
                System.out.print("Enter the End Date (dd-MM-yyyy) (or press Enter to keep current): ");
                String endDateInput = scanner.nextLine().trim();
                if (!endDateInput.isEmpty()) {
                    Date newEndDate = OpUtils.customDate(endDateInput);
                    if (newEndDate != null) {
                        treatment.setEndDate(newEndDate);
                        System.out.println("End date updated successfully!");
                    } else {
                        System.out.println("Invalid date format. Keeping current end date.");
                    }
                } else {
                    System.out.println("Keeping current end date.");
                }

                // Treatment Cost
                System.out.print("Enter the Treatment Cost (or press Enter to keep current): ");
                String costInput = scanner.nextLine().trim();
                if (!costInput.isEmpty()) {
                    try {
                        double newCost = Double.parseDouble(costInput);
                        if (newCost >= 0) {
                            treatment.setCost(newCost);
                            System.out.println("Treatment cost updated successfully!");
                        } else {
                            System.out.println("Invalid cost. Keeping current treatment cost.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Keeping current treatment cost.");
                    }
                } else {
                    System.out.println("Keeping current treatment cost.");
                }

            } else {
                System.out.println("Treatment ID not found, keeping current treatment.");
            }
        } else {
            System.out.println("Keeping current treatment.");
        }
    }
}
