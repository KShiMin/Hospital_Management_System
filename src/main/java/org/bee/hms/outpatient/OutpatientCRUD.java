package org.bee.hms.outpatient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.bee.hms.humans.DEPARTMENT;
import org.bee.hms.humans.Doctor;
import org.bee.hms.humans.Patient;
import org.bee.hms.medical.Medication;
import org.bee.hms.medical.VisitStatus;
import org.bee.hms.billing.*;

public class OutpatientCRUD {
    static List<Patient> all_patients = new ArrayList<>();
    static List<Doctor> all_physicians = new ArrayList<>();
    static List<Feedback> all_feedbacks = new ArrayList<>();
    static List<Medication> all_drugs = new ArrayList<>();
    static List<LabTest> all_labtests = new ArrayList<>();
    static List<Bill> all_billings = new ArrayList<>();
    static List<Treatment> all_treatments = new ArrayList<>();
    static List<OutpatientCase> all_outpatientcases = new ArrayList<>();
    static List<Procedure> all_procedures = new ArrayList<>();

    /**
     * Creates a new feedback for the patient.
     *
     * @param patient The patient that creates the new feedback.
     * @param scan    The {@link Scanner} object used for input.
     * 
     */
    private static void addFeedback(Patient patient, Scanner scan) {
        System.out.print("Rate Medical Care (1-5): ");
        int medicalCareRating = scan.nextInt();
        System.out.print("Rate Service (1-5): ");
        int serviceRating = scan.nextInt();
        scan.nextLine();
        System.out.print("Enter Remarks: ");
        String remarks = scan.nextLine();

        Feedback feedback = new Feedback(patient, medicalCareRating, serviceRating, new Date(), remarks);

        System.out.println("Feedback submitted successfully!");
        all_feedbacks.add(feedback);
    }

    /**
     * Prints all feedback records for a given patient.
     * This method retrieves and displays a list of feedback associated with the
     * specified patient.
     * If no feedback records are found, a message is displayed indicating that no
     * records exist.
     * 
     * @param patient The {@link Patient} whose feedback records are to be
     *                displayed.
     */
    public static void printAllFeedBack(Patient patient) {
        System.out.println(
                "\n------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("\t\t\t\t\t\tList of All Feedbacks");
        System.out.println(
                "------------------------------------------------------------------------------------------------------------------------------");

        List<Feedback> feedbackList = Feedback.getAllFeedbacksByPatient(patient);
        if (feedbackList.isEmpty()) {
            System.out.println("No feedback records found.");
        } else {
            System.out.printf("%-12s | %-12s | %-20s | %-20s | %-30s | %-40s\n",
                    "Feedback ID", "Patient ID", "Medical Care Rating", "Service Rating", "Date",
                    "Remarks");
            System.out.println(
                    "------------------------------------------------------------------------------------------------------------------------------");

            for (Feedback feedback : feedbackList) {
                System.out.printf("%-12s | %-12s | %-20d | %-20d | %-30s | %-40s\n",
                        feedback.getFeedbackID(),
                        feedback.getPatient().getPatientID(),
                        feedback.getMedicalCareRating(),
                        feedback.getServiceRating(),
                        feedback.getDateStamp(),
                        feedback.getRemarks());
            }
        }
    }
    
    
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
        Doctor physician = Physician.searchPhysicianByID(Integer.valueOf(physicianID));

        if (physician == null) {
            System.out.println("Physician not found. Returning to main menu.");
            return;
        }

        // Select Case Type
        System.out.println("Select Case Type:");
        for (CASETYPE type : CASETYPE.values()) {
            System.out.println((type.ordinal() + 1) + " - " + type);
        }
        System.out.print("Enter case type number: ");
        CASETYPE caseType = CASETYPE.values()[scan.nextInt() - 1];
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

            appointmentDate = customDate(input);

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
        for (DEPARTMENT dept : DEPARTMENT.values()) {
            System.out.println((dept.ordinal() + 1) + " - " + dept);
        }
        System.out.print("Enter department number: ");
        DEPARTMENT department = DEPARTMENT.values()[scan.nextInt() - 1];
        scan.nextLine();

        // Follow-Up Date
        Date followUpDate = null;
        while (true) {
            System.out.println("Enter Follow-Up Date (dd-MM-yyyy) or press 'Enter' to leave blank: ");
            String input = scan.nextLine().trim();

            if (input.isEmpty()) {
                break;
            }

            followUpDate = customDate(input);

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
            for (Medication drug : Medication.getAllDrugs()) {
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
                        Medication drug = Medication.searchDrugByID(Integer.parseInt(id.trim()));
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
     * Updates the outpatient case object.
     * This method allows the physician to update the outpatient case for a patient
     *
     * @param outpatientCase The outpatientCase object to be updated.
     * @param scanner        The {@link Scanner} object used for input.
     * 
     */
    public static void updateOutpatientCaseDetails(OutpatientCase outpatientCase, Scanner scanner) {
        if (outpatientCase == null) {
            System.out.println("Invalid outpatient case.");
            return;
        }

        System.out.println("Updating details for Outpatient Case ID: " + outpatientCase.getOutpatientCaseID());

        System.out.println("Enter new medical history (or press Enter to keep current ["
                + outpatientCase.getMedicalHistory() + "]): ");
        String medicalHistory = scanner.nextLine();
        if (!medicalHistory.isEmpty()) {
            outpatientCase.setMedicalHistory(medicalHistory);
        }

        System.out.print(
                "\nEnter new diagnosis (or press Enter to keep current [" + outpatientCase.getDiagnosis() + "]): ");
        String diagnosis = scanner.nextLine();
        if (!diagnosis.isEmpty()) {
            outpatientCase.setDiagnosis(diagnosis);
        }

        System.out.print("\nEnter new visit reason (or press Enter to keep current [" + outpatientCase.getVisitReason()
                + "]): ");
        String visitReason = scanner.nextLine();
        if (!visitReason.isEmpty()) {
            outpatientCase.setVisitReason(visitReason);
        }

        System.out.print("\nEnter new instructions (or press Enter to keep current [" + outpatientCase.getInstructions()
                + "]): ");
        String instructions = scanner.nextLine();
        if (!instructions.isEmpty()) {
            outpatientCase.setInstructions(instructions);
        }

        System.out.print("Select new status:\n");
        VisitStatus[] statuses = VisitStatus.values();
        for (int i = 0; i < statuses.length; i++) {
            System.out.println((i + 1) + " - " + statuses[i]);
        }
        System.out.print("\nEnter status number (or 0 to keep current [" + outpatientCase.getStatus() + "]): ");
        int statusChoice = scanner.nextInt();
        scanner.nextLine();
        if (statusChoice > 0 && statusChoice <= statuses.length) {
            outpatientCase.setStatus(statuses[statusChoice - 1]);
        }

        System.out.println("Outpatient case updated successfully.");
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
        for (CASETYPE type : CASETYPE.values()) {
            System.out.println((type.ordinal() + 1) + " - " + type);
        }
        System.out.print("Enter case type number: ");

        // scanner.nextLine();
        String casetype = scanner.nextLine().trim();

        if (!casetype.isEmpty()) {
            try {
                int caseTypeIndex = Integer.parseInt(casetype) - 1;
                if (caseTypeIndex >= 0 && caseTypeIndex < CASETYPE.values().length) {
                    CASETYPE caseType = CASETYPE.values()[caseTypeIndex];
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
            Date newDate = customDate(dateInput);
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
            Date newFollowUpDate = customDate(followUp);
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
                    Date newStartDate = customDate(startDateInput);
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
                    Date newEndDate = customDate(endDateInput);
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

        /**
     * Prints the details of an outpatient case
     *
     * @param outpatientCase The outpatient case in which the details will be
     *                       printed out like ID, Appointment Date etc.
     * 
     */
    public static void printOutpatientCaseDetails(OutpatientCase outpatientCase) {
        if (outpatientCase == null) {
            System.out.println("Outpatient case not found.");
            return;
        }

        StringBuilder sb = new StringBuilder();

        sb.append("Outpatient Case Details:\n");
        System.out.println("----------------------------------------------------------------------------\n\n");

        sb.append(String.format("Outpatient Case ID:   %s\n", outpatientCase.getOutpatientCaseID()));
        sb.append(String.format("Appointment Date:     %s\n", outpatientCase.getAppointmentDate()));
        sb.append(String.format("Medical History:      %s\n", outpatientCase.getMedicalHistory()));
        sb.append(String.format("Type:                 %s\n", outpatientCase.getType()));
        sb.append(String.format("Status:               %s\n", outpatientCase.getStatus()));
        sb.append(String.format("Department:           %s\n", outpatientCase.getDepartment()));
        sb.append(String.format("Diagnosis:            %s\n", outpatientCase.getDiagnosis()));
        sb.append(String.format("Visit Reason:         %s\n\n", outpatientCase.getVisitReason()));

        // Current Medications
        sb.append("Current Medications:\n\n");
        if (outpatientCase.getCurrentMedications().isEmpty()) {
            sb.append("    None\n\n");
        } else {
            for (Medication med : outpatientCase.getCurrentMedications()) {
                sb.append("    Drug Details:\n");
                sb.append(String.format("              ID:             %s\n", med.getDrugID()));
                sb.append(String.format("              Name:           %s\n", med.getDrugName()));
                sb.append(String.format("              Dosage:         %s\n", med.getDosage()));
                sb.append(String.format("              Expiry Date:    %s\n", med.getExpiryDate()));
                sb.append(String.format("              Cost Per Unit:  %.2f\n\n", med.getCostPerUnit()));
            }
        }

        // Prescriptions
        sb.append("Prescriptions:\n\n");
        if (outpatientCase.getPrescriptions().isEmpty()) {
            sb.append("    None\n\n");
        } else {
            // sb.append(outpatientCase.getPrescriptions());
            for (Medication prescription : outpatientCase.getPrescriptions()) {
                if (prescription != null) {
                    sb.append("    Drug Details:\n");
                    sb.append(String.format("              ID:             %s\n", prescription.getDrugID()));
                    sb.append(String.format("              Name:           %s\n", prescription.getDrugName()));
                    sb.append(String.format("              Dosage:         %s\n", prescription.getDosage()));
                    sb.append(String.format("              Expiry Date:    %s\n", prescription.getExpiryDate()));
                    sb.append(String.format("              Cost Per Unit:  %.2f\n\n", prescription.getCostPerUnit()));
                }
            }
        }

        sb.append(String.format("Follow-Up Date:       %s\n", outpatientCase.getFollowUpDate()));
        sb.append(String.format("Instructions:         %s\n", outpatientCase.getInstructions()));
        sb.append(String.format("Physician ID:         %s\n",
                outpatientCase.getPhysician() != null ? outpatientCase.getPhysician().getPhysicianID() : "N/A"));
        sb.append(String.format("Billing ID:           %s\n\n",
                outpatientCase.getBilling() != null ? outpatientCase.getBilling().getBillingID() : "null"));

        // Treatments
        sb.append("Treatments:\n");
        if (outpatientCase.getTreatments().isEmpty()) {
            sb.append("    None\n\n");
        } else {
            for (Treatment treatment : outpatientCase.getTreatments()) {
                sb.append("    Treatment Details:\n");
                sb.append(String.format("              ID:          %s\n", treatment.getTreatmentID()));
                sb.append(String.format("              Name:        %s\n", treatment.getTreatmentName()));
                sb.append(String.format("              Status:      %s\n", treatment.getStatus()));
                sb.append(String.format("              Start Date:  %s\n", treatment.getStartDate()));
                sb.append(String.format("              End Date:    %s\n", treatment.getEndDate()));
                sb.append("              Notes:\n");
                sb.append(String.format("              Cost:        %.2f\n\n", treatment.getCost()));
            }
        }

        // Lab Tests (Formatted same as treatments)
        sb.append("Lab Tests:\n");
        if (outpatientCase.getLabtests().isEmpty()) {
            sb.append("    None\n");
        } else {
            for (LabTest test : outpatientCase.getLabtests()) {
                sb.append("    Lab Test Details:\n");
                sb.append(String.format("              ID:          %s\n", test.getLabTestID()));
                sb.append(String.format("              Type:        %s\n", test.getType()));
                sb.append(String.format("              Status:      %s\n", test.getStatus()));
                sb.append(String.format("              Date:        %s\n", test.getDateStamp()));
                sb.append(String.format("              Cost:        %.2f\n\n", test.getCost()));
            }
        }

        System.out.println(sb);
    }

}
