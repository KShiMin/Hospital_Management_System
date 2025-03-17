package org.bee.hms.outpatient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.bee.hms.humans.Department;
import org.bee.hms.humans.Sex;
import org.bee.hms.medical.VisitStatus;
import org.bee.hms.outpatient.LabType;
import org.bee.hms.outpatient.LabTest;
import org.bee.hms.outpatient.OutpatientCase;
import org.bee.hms.outpatient.Treatment;
import org.bee.hms.utils.OpUtils;

public class PhysicianPortal {
    
    static List<Physician> all_physicians = new ArrayList<>();
    static List<LabTest> all_labtests = new ArrayList<>();
    static List<Treatment> all_treatments = new ArrayList<>();
    static List<Procedure> all_procedures = new ArrayList<>();

    /**
     * Prompts the user to enter physician details and creates a new physician
     * object.
     * The method ensures valid input is collected before assigning values.
     *
     * @param scanner The {@link Scanner} object used for input.
     */
    public static void createNewPhysician(Scanner scanner) {
        // String name, String nric, String address, Sex gender, String nationality,
        // Integer age, Date dateOfBirth, String contactNumber, Department department,
        // ArrayList<OutpatientCase> patientCases
        System.out.print("Enter Physician Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter NRIC: ");
        String nric = scanner.nextLine();
        System.out.print("Enter Physician's address: ");
        String address = scanner.nextLine();
        System.out.print("Enter Physician's nationality: ");
        String nationality = scanner.nextLine();
        Boolean validNumber = true;
        Sex physicianGender = Sex.MALE;
        while (validNumber) {
            System.out.println("Select Gender:\n1.Male\n2.Female");
            String gender = scanner.nextLine();
            try {
                if (Integer.parseInt(gender) == 1) {
                    physicianGender = Sex.MALE;
                } else if (Integer.parseInt(gender) == 2) {
                    physicianGender = Sex.FEMALE;
                }
                validNumber = false;
            } catch (Exception e) {
                System.out.println("Wrong Input. Please Try again!");
            }
        }
        System.out.print("Enter Date of Birth (yyyy-MM-dd): ");
        String dateString = scanner.nextLine();
        Date dob = OpUtils.customDate(dateString);
        int age = OpUtils.calculateAge(LocalDate.parse(dateString));
        System.out.print("Enter Contact Number: ");
        String contactNumber = scanner.nextLine();
        for (Department s : Department.values()) {
            System.out.println(s.ordinal() + " - " + s);
        }
        System.out.print("Enter the number referencing the physician's department: ");
        int typeIndex = Integer.parseInt(scanner.nextLine());
        Department department = Department.values()[typeIndex];
        Physician physician = new Physician(name, nric, address, physicianGender, nationality, age, dob, contactNumber,
                department, new ArrayList<OutpatientCase>());
        all_physicians.add(physician);
        System.out.print("Physician successfully created!\n");
        System.out.println(physician);
    }

    public static void showPhysicianMenu(Physician physician, Scanner scan) {
        boolean physicianMenu = true;
        while (physicianMenu) {
            System.out.println(
                    "\n----------------------------------------------------------------------------");
            System.out.println("\tWelcome to Physician's Portal, " + physician.getName());
            System.out.println(
                    "----------------------------------------------------------------------------");
            System.out.println("Following Functionalities are available: \n");

            System.out.println("1- View all/ update Outpatient Cases");
            System.out.println("2- View all/ update Treatment List By Outpatient Case");
            System.out.println("3- View all/ update Lab Test List By Outpatient Case");
            System.out.println("4- View all/ update Drug List By Outpatient Case");
            System.out.println("5- Return to the Main menu");
            System.out.println("6- Exit");

            System.out.println(
                    "----------------------------------------------------------------------------");
            int physicianTask_input = OpUtils.optionsInput(scan, 0, 7);
            if (physicianTask_input == 6) {
                OpUtils.printExit();
                System.exit(0);
                break;
            }
            String outpatientCaseID = "";
            OutpatientCase outpatientCase = null;
            switch (physicianTask_input) {
                case 1:
                    boolean opcPhysicianMenu = true;
                    while (opcPhysicianMenu) {
                        if (physician.getPatientCases().isEmpty()) {
                            System.out.println("No outpatient cases found.");
                            break;
                        }
                        viewAllOutpatientCases(physician);

                        System.out.println("-".repeat(190));

                        System.out.println("Following Functionalities are available: \n");
                        System.out.println("1- View/ update Outpatient Case Particulars");
                        System.out.println("2- Return to previous");
                        System.out.println("3- Exit");
                        System.out.println("-".repeat(190));

                        int opcPhysician_input = OpUtils.optionsInput(scan, 0, 4);
                        if (opcPhysician_input == 3) {
                            OpUtils.printExit();
                            System.exit(0);
                            break;
                        }

                        switch (opcPhysician_input) {
                            case 1:
                                System.out.println("Enter the Outpatient Case ID: ");
                                outpatientCaseID = scan.nextLine();

                                outpatientCase = OutpatientCase.searchOutpatientCaseByID(Integer.valueOf(outpatientCaseID));
                                OutpatientCase.printOutpatientCaseDetails(outpatientCase);

                                boolean validResp = false;
                                while (!validResp) {
                                    System.out.print("Do you wish to update the details? (yes/no): ");
                                    String response = scan.next().trim().toLowerCase();
                                    if (response.equals("yes")) {
                                        OutpatientCase.updateOutpatientCaseDetails(outpatientCase, scan);
                                        // updateDataset();
                                        validResp = true;
                                    } else if (response.equals("no")) {
                                        validResp = true;
                                    }
                                }
                                break;
                            case 2:
                                OpUtils.clearScreen();
                                opcPhysicianMenu = false;
                                break;
                        }
                    }
                    break;
                case 2:
                    if (physician.getPatientCases().isEmpty()) {
                        System.out.println("No outpatient cases found.");
                        break;
                    }
                    viewAllOutpatientCases(physician);

                    System.out.println("Enter the Outpatient Case ID: ");
                    outpatientCaseID = scan.nextLine();

                    outpatientCase = OutpatientCase
                            .searchOutpatientCaseByID(Integer.valueOf(outpatientCaseID));
                    boolean treatmentPhysicianMenu = true;
                    if (outpatientCase != null) {
                        while (treatmentPhysicianMenu) {
                            System.out.println(
                                    "\n----------------------------------------------------------------------------");
                            System.out.println("Following Functionalities are available: \n");

                            System.out.println("1- View/ Update Treatment Particulars");
                            System.out.println("2- Create a new Treatment");
                            System.out.println("3- Return to previous");
                            System.out.println("4- Exit");

                            System.out.println(
                                    "----------------------------------------------------------------------------");

                            int treatmentPhysician_input = OpUtils.optionsInput(scan, 0, 5);
                            if (treatmentPhysician_input == 4) {
                                OpUtils.printExit();
                                System.exit(0);
                                break;
                            }
                            switch (treatmentPhysician_input) {
                                case 1:
                                    System.out.println("Displaying all Treatment Plan for Case "
                                            + outpatientCase.getOutpatientCaseID());
                                    printTreatmentList(outpatientCase);

                                    System.out.print("Do you want to modify a treatment? (yes/no): ");
                                    String response = scan.nextLine().trim().toLowerCase();

                                    if (response.equals("yes")) {
                                        System.out.print("Enter the Treatment ID to update: ");
                                        String treatmentID = scan.nextLine();

                                        Treatment treatment = Treatment
                                                .searchTreatmentByID(Integer.valueOf(treatmentID));
                                        updateTreatmentDetails(treatment, scan);
                                        // updateDataset();
                                    }
                                    break;
                                case 2:
                                    createNewTreatment(outpatientCase, scan);
                                    // updateDataset();
                                    break;
                                case 3:
                                    OpUtils.clearScreen();
                                    treatmentPhysicianMenu = false;
                                    break;
                            }
                        }
                        break;
                    } else {
                        System.out.println("Outpatient Case ID not found. Please try again.");
                    }
                    break;
                case 3:
                    if (physician.getPatientCases().isEmpty()) {
                        System.out.println("No outpatient cases found.");
                        break;
                    }
                    viewAllOutpatientCases(physician);
                    System.out.println("Enter the Outpatient Case ID: ");
                    outpatientCaseID = scan.nextLine();

                    outpatientCase = OutpatientCase
                            .searchOutpatientCaseByID(Integer.valueOf(outpatientCaseID));

                    boolean labtestPhysicianMenu = true;
                    if (outpatientCase != null) {
                        while (labtestPhysicianMenu) {
                            System.out.println("Displaying all Lab Tests for Case"
                                    + outpatientCase.getOutpatientCaseID());
                            printLabTestList(outpatientCase);
                            System.out.println(
                                    "\n----------------------------------------------------------------------------");
                            System.out.println("Following Functionalities are available: \n");

                            System.out.println("1- Update Lab Test Particulars");
                            System.out.println("2- Create a new Lab Test");
                            System.out.println("3- Return to previous");
                            System.out.println("4- Exit");

                            System.out.println(
                                    "----------------------------------------------------------------------------");

                            int labtestPhysician_input = OpUtils.optionsInput(scan, 0, 5);
                            if (labtestPhysician_input == 4) {
                                OpUtils.printExit();
                                System.exit(0);
                                break;
                            }
                            switch (labtestPhysician_input) {
                                case 1:
                                    System.out.println("Enter the Lab Test ID to update: ");
                                    String labTestID = scan.nextLine();

                                    LabTest labTest = LabTest
                                            .searchLabTestByID(Integer.valueOf(labTestID));
                                    updateLabTestDetails(labTest, scan);
                                    // updateDataset();
                                    break;
                                case 2:
                                    createNewLabTest(outpatientCase, scan);
                                    // updateDataset();
                                    break;
                                case 3:
                                    labtestPhysicianMenu = false;
                                    break;
                            }
                        }
                    }
                    break;
                case 4:
                    if (physician.getPatientCases().isEmpty()) {
                        System.out.println("No outpatient cases found.");
                        break;
                    }
                    viewAllOutpatientCases(physician);
                    System.out.println("Enter the Outpatient Case ID: ");

                    outpatientCaseID = scan.nextLine();

                    outpatientCase = OutpatientCase
                            .searchOutpatientCaseByID(Integer.valueOf(outpatientCaseID));
                    boolean drugPhysicianMenu = true;
                    if (outpatientCase == null) {
                        System.out
                                .println("Outpatient Case ID not found. Please try again.");
                        break;
                    } else {
                        while (drugPhysicianMenu) {
                            System.out.println(
                                    "\n----------------------------------------------------------------------------");

                            System.out.println("Following Functionalities are available: \n");
                            System.out.println("1- View all Medications");
                            System.out.println("2- Update Drugs for Outpatient Case");
                            System.out.println("3- Clear Prescriptions for Outpatient Case");
                            System.out.println("4- Return to previous");
                            System.out.println("5- Exit");

                            System.out.println(
                                    "----------------------------------------------------------------------------");

                            int drugPhysician_input = OpUtils.optionsInput(scan, 0, 6);
                            if (drugPhysician_input == 5) {
                                OpUtils.printExit();
                                System.exit(0);
                                break;
                            }
                            switch (drugPhysician_input) {
                                case 1:
                                    printDrugListByOutpatientCase(outpatientCase);
                                    break;
                                case 2:
                                    printDrugListByOutpatientCase(outpatientCase);
                                    updateDrugListByOutpatientCase(outpatientCase, scan);
                                    // updateDataset();
                                    break;
                                case 3:
                                    clearPrescriptionsByOutpatientCase(outpatientCase);
                                    // updateDataset();
                                    break;
                                case 4:
                                    drugPhysicianMenu = false;
                                    break;
                            }
                        }
                    }
                    break;
                case 5:
                    OpUtils.clearScreen();
                    physicianMenu = false;
                    break;
            }
        }
    }

    /**
     * Prints the all the outpatient cases that is linked to the physician
     *
     * @param physician The physician in which the outpatient cases are assigned to
     * 
     */
    public static void viewAllOutpatientCases(Physician physician) {
        List<OutpatientCase> cases = physician.getPatientCases();

        System.out.printf("%-8s | %-32s | %-10s | %-15s | %-20s | %-15s | %-20s | %-15s | %-10s | %-10s \n",
                "Case ID", "Appointment Date", "Patient ID", "Patient Name", "Type", "Status", "Diagnosis",
                "Physician Name", "Billing ID", "Total Cost");
        System.out.println("-".repeat(190));

        for (OutpatientCase oc : cases) {
            System.out.printf("%-8s | %-32s | %-10s | %-15s | %-20s | %-15s | %-20s | %-15s | %-10s | $%-10.2f\n",
                    oc.getOutpatientCaseID(),
                    oc.getAppointmentDate(),
                    oc.getPatient() != null ? oc.getPatient().getPatientID() : "N/A",
                    oc.getPatient() != null ? oc.getPatient().getName() : "N/A",
                    oc.getType(),
                    oc.getStatus(),
                    oc.getDiagnosis(),
                    oc.getPhysician() != null ? oc.getPhysician().getName() : "N/A",
                    oc.getBilling() != null ? oc.getBilling().getBillingID() : "N/A",
                    oc.getBilling() != null ? oc.getBilling().getFinalCost() : 0.0);
        }

    }

    /**
     * Prints the treatment list for the respective outpatient case.
     *
     * @param outpatientCase The outpatientCase that is related to the
     *                       treatments.
     * 
     */
    public static void printTreatmentList(OutpatientCase outpatientCase) {
        List<Treatment> treatments = Treatment.getAllTreatmentsByCase(outpatientCase);
        if (treatments.isEmpty()) {
            System.out.println("No treatments found.");
        } else {
            System.out.println(
                    "---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("%-12s | %-20s | %-25s | %-12s | %-30s | %-30s | %-30s | %-10s | %-30s\n",
                    "Treatment ID", "Outpatient Case ID", "Treatment Name", "Status", "Start Date", "End Date", "Notes",
                    "Cost", "Procedures");
            System.out.println(
                    "---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

            // Print the treatment details
            for (Treatment treatment : treatments) {
                StringBuilder procedureDetails = new StringBuilder();
                for (Procedure procedure : treatment.getProcedures()) {
                    procedureDetails.append(procedure.getProcedureID()).append(", ");
                }
                if (procedureDetails.length() > 0) {
                    procedureDetails.setLength(procedureDetails.length() - 2); // Remove last comma and space
                }

                System.out.printf("%-12s | %-20s | %-25s | %-12s | %-30s | %-30s | %-30s | %-10s | %-30s\n",
                        treatment.getTreatmentID(),
                        treatment.getOutpatientCase().getOutpatientCaseID(),
                        treatment.getTreatmentName(),
                        treatment.getStatus(),
                        treatment.getStartDate(),
                        treatment.getEndDate(),
                        treatment.getNotes(),
                        treatment.getCost(),
                        procedureDetails.toString(),
                        "");
            }
        }

    }

    /**
     * Updates the treatment object.
     * This method allows the physician to update the treatment for a patient
     *
     * @param treatment The treatment to be updated.
     * @param scanner   The {@link Scanner} object used for input.
     * 
     */
    public static void updateTreatmentDetails(Treatment treatment, Scanner scanner) {
        if (treatment == null) {
            System.out.println("Treatment not found.");
            return;
        }

        System.out.println("Updating Treatment: " + treatment.getTreatmentID());
        // Update Notes (Treatment Name)
        System.out.print("Enter new treatment name (or press Enter to keep [" + treatment.getTreatmentName() + "]): ");
        String newName = scanner.nextLine();
        if (!newName.isEmpty())
            treatment.setTreatmentName(newName);

        // Update Status
        System.out.println("Select new status: ");
        for (VisitStatus s : VisitStatus.values()) {
            System.out.println(s.ordinal() + " - " + s);
        }
        boolean validNumber = true;

        while (validNumber) {
            System.out.print("Enter status number: ");
            int statusIndex = Integer.parseInt(scanner.nextLine());
            if (statusIndex > (VisitStatus.values().length - 1)) {
                System.out.println("Please select a valid option.");
            } else {
                validNumber = false;
                treatment.setStatus(VisitStatus.values()[statusIndex]);
            }
        }

        // Update Start Date
        System.out
                .print("Enter new Start Date (dd-MM-yyyy) or press Enter to keep [" + treatment.getStartDate() + "]: ");
        String newStartDate = scanner.nextLine();
        if (!newStartDate.isEmpty()) {
            Date parsedStartDate = OpUtils.customDate(newStartDate);
            if (parsedStartDate != null) {
                treatment.setStartDate(parsedStartDate);
            }
        }

        // Update End Date
        System.out.print("Enter new End Date (dd-MM-yyyy) or press Enter to keep [" + treatment.getEndDate() + "]: ");
        String newEndDate = scanner.nextLine();
        if (!newEndDate.isEmpty()) {
            Date parsedEndDate = OpUtils.customDate(newEndDate);
            if (parsedEndDate != null) {
                treatment.setEndDate(parsedEndDate);
            }
        }

        // Update Cost
        System.out.print("Enter new cost (or press Enter to keep [" + treatment.getCost() + "]): ");
        String newCost = scanner.nextLine();
        if (!newCost.isEmpty())
            treatment.setCost(Double.parseDouble(newCost));

        // Update Procedures
        System.out.println("Current Procedures:");
        System.out.println(
                "-------------------------------------------------------------------------------------------------");
        System.out.printf("%-15s | %-15s | %-15s | %-15s | %-20s \n", "Procedure ID", "Type", "Room", "Status",
                "Physician ID");
        System.out.println(
                "-------------------------------------------------------------------------------------------------");

        for (Procedure p : treatment.getProcedures()) {
            System.out.printf("%-15s | %-15s | %-15s | %-15s | %-20s \n",
                    p.getProcedureID(),
                    p.getType(),
                    p.getRoomNumber(),
                    p.getStatus(),
                    treatment.getOutpatientCase().getPhysician().getName());
        }

        System.out.println(
                "-------------------------------------------------------------------------------------------------");

        System.out.print("\nDo you want to modify procedures? (yes/no): ");
        if (scanner.nextLine().equalsIgnoreCase("yes")) {
            System.out.print("Enter the Procedure ID to modify: ");
            String procedureID = scanner.nextLine();

            Procedure procedure = Procedure.searchProcedureByID(Integer.valueOf(procedureID));
            updateProcedureDetails(procedure, scanner);
        }

        System.out.println("Treatment updated successfully!");
    }

    /**
     * Updates the procedure object.
     * This method allows the physician to update the procedure for a specific
     * treatment for a patient
     *
     * @param procedure The procedure to be updated.
     * @param scanner   The {@link Scanner} object used for input.
     * 
     */
    public static void updateProcedureDetails(Procedure procedure, Scanner scanner) {
        if (procedure == null) {
            System.out.println("Procedure not found.");
            return;
        }

        System.out.println("Updating Procedure: " + procedure.getProcedureID());
        for (PROCEDURE_TYPE t : PROCEDURE_TYPE.values()) {
            System.out.println(t.ordinal() + " - " + t);
        }
        System.out.print("Select new procedure type (or press Enter to keep [" + procedure.getType() + "]): ");
        String newType = scanner.nextLine();
        if (!newType.isEmpty()) {
            try {
                int typeIndex = Integer.parseInt(newType);
                if (typeIndex >= 0 && typeIndex < PROCEDURE_TYPE.values().length) {
                    procedure.setType(PROCEDURE_TYPE.values()[typeIndex]);
                    System.out.println("Procedure type updated to: " + PROCEDURE_TYPE.values()[typeIndex]);
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid type entered. Keeping the current type.");
            }
        }

        System.out.print("Enter new room number (or press Enter to keep [" + procedure.getRoomNumber() + "]): ");
        String newRoomNumber = scanner.nextLine();
        if (!newRoomNumber.isEmpty()) {
            procedure.setRoomNumber(newRoomNumber);
        }

        System.out.print("Enter new remarks (or press Enter to keep [" + procedure.getRemarks() + "]): ");
        String newRemarks = scanner.nextLine();
        if (!newRemarks.isEmpty()) {
            procedure.setRemarks(newRemarks);
        }

        // Step 8: Update Cost if needed
        System.out.print("Enter new cost (or press Enter to keep [" + procedure.getCost() + "]): ");
        String newCost = scanner.nextLine();
        if (!newCost.isEmpty()) {
            try {
                procedure.setCost(Double.parseDouble(newCost));
            } catch (NumberFormatException e) {
                System.out.println("Invalid cost entered. Keeping the current cost.");
            }
        }

        System.out.println("Procedure updated successfully!");

    }

    /**
     * Creates a new Treatment object.
     * This method allows the physician to create a new treatment and assign to the
     * outpatient case.
     *
     * @param outpatientCase The outpatient case in which the newly created
     *                       treatment can be added to.
     * @param scanner        The {@link Scanner} object used for input.
     * 
     */
    public static void createNewTreatment(OutpatientCase outpatientCase, Scanner scanner) {
        // Gather treatment details from the user
        System.out.print("Enter the Treatment Name: ");
        String treatmentName = scanner.nextLine();

        System.out.println("Select the Treatment Status:");
        for (VisitStatus s : VisitStatus.values()) {
            System.out.println(s.ordinal() + " - " + s);
        }
        System.out.print("Enter status number: ");
        int statusIndex = Integer.parseInt(scanner.nextLine());
        VisitStatus treatmentStatus = VisitStatus.values()[statusIndex];

        System.out.print("Enter the Start Date (dd-MM-yyyy): ");
        String startDateInput = scanner.nextLine();
        Date startDate = OpUtils.customDate(startDateInput);

        System.out.print("Enter the End Date (dd-MM-yyyy): ");
        String endDateInput = scanner.nextLine();
        Date endDate = OpUtils.customDate(endDateInput);

        System.out.print("Enter the Notes (if any): ");
        String notes = scanner.nextLine();

        System.out.print("Enter the Treatment Cost: ");
        double cost = Double.parseDouble(scanner.nextLine());

        // Create the new Treatment object and associate it with the Outpatient Case
        Treatment newTreatment = new Treatment(outpatientCase, treatmentName, treatmentStatus,
                startDate, endDate, notes, cost, new ArrayList<>());
        newTreatment.setOutpatientCase(outpatientCase); // Link the new treatment to the outpatient case
        outpatientCase.addTreatment(newTreatment); // Optionally, add treatment to the outpatient case's treatment list
        all_treatments.add(newTreatment);

        System.out.println("New Treatment created and linked successfully!");
        System.out.print("\nWould you like to create a new procedure for this new treatment plan? (yes/no)");
        String response = scanner.nextLine().trim().toLowerCase();
        if (response.equals("yes")) {
            createNewProcedure(newTreatment, scanner);
        }
    }

    /**
     * Creates a new Procedure object.
     * This method allows the physician to create a new procedure and assign to the
     * treatment.
     *
     * @param treatment The treament in which the newly created
     *                  procedure can be added to.
     * @param scanner   The {@link Scanner} object used for input.
     * 
     */
    public static void createNewProcedure(Treatment treatment, Scanner scanner) {
        System.out.println("Select Procedure Type:");
        for (PROCEDURE_TYPE type : PROCEDURE_TYPE.values()) {
            System.out.println(type.ordinal() + " - " + type);
        }
        System.out.print("Enter type number: ");
        int typeIndex = Integer.parseInt(scanner.nextLine());
        PROCEDURE_TYPE procedureType = PROCEDURE_TYPE.values()[typeIndex];

        System.out.print("Enter Room Number: ");
        String roomNumber = scanner.nextLine();

        System.out.println("Select Procedure Status:");
        for (VisitStatus status : VisitStatus.values()) {
            System.out.println(status.ordinal() + " - " + status);
        }
        System.out.print("Enter status number: ");
        int statusIndex = Integer.parseInt(scanner.nextLine());
        VisitStatus procedureStatus = VisitStatus.values()[statusIndex];

        System.out.print("Enter Remarks (if any): ");
        String remarks = scanner.nextLine();

        System.out.print("Enter Procedure Date (dd-MM-yyyy): ");
        String dateInput = scanner.nextLine();
        Date procedureDate = OpUtils.customDate(dateInput);

        System.out.print("Enter Procedure Cost: ");
        double cost = Double.parseDouble(scanner.nextLine());

        // Create and link Procedure
        Procedure newProcedure = new Procedure(treatment, procedureType, treatment.getOutpatientCase().getPhysician(),
                roomNumber,
                procedureStatus, remarks, procedureDate, cost);
        System.out.println("Procedure added with ID: " + newProcedure.getProcedureID());
        treatment.addProcedure(newProcedure);
        all_procedures.add(newProcedure);

        System.out.println("\nWould you like to create another procedure? (yes/no)");
        String response = scanner.nextLine().trim().toLowerCase();
        if (response.equals("yes")) {
            createNewProcedure(treatment, scanner);
        }
    }

    /**
     * Prints the list Lab Tests for the respective outpatient case.
     *
     * @param outpatientCase The outpatientCase that is related to the
     *                       Lab Tests.
     * 
     */
    public static void printLabTestList(OutpatientCase outpatientCase) {
        List<LabTest> labTests = new ArrayList<>();
        for (LabTest lt : LabTest.getAllLabtests()) {
            if (lt.getOutpatientCase().equals(outpatientCase)) {
                labTests.add(lt);
            }
        }

        if (labTests.isEmpty()) {
            System.out.println("No lab tests found.");
        } else {
            System.out.println(
                    "--------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("%-12s | %-15s | %-15s | %-12s | %-30s | %-12s | %-12s | %-30s\n",
                    "Lab Test ID", "Outpatient ID", "Lab Type", "Physician ID", "Date", "Status", "Cost", "Remarks");
            System.out.println(
                    "--------------------------------------------------------------------------------------------------------------------------------------------------------");

            // Print all lab test details
            for (LabTest labTest : labTests) {
                System.out.printf("%-12s | %-15s | %-15s | %-12s | %-30s | %-12s | %-12s | %-30s\n",
                        labTest.getLabTestID(),
                        labTest.getOutpatientCase().getOutpatientCaseID(),
                        labTest.getType(),
                        (labTest.getPhysician() != null ? labTest.getPhysician().getPhysicianID() : "N/A"),
                        labTest.getDateStamp(),
                        labTest.getStatus(),
                        labTest.getCost(),
                        labTest.getRemarks());
            }
        }
    }

    /**
     * Updates the lab test object.
     * This method allows the physician to update the details of a labTest
     *
     * @param labTest The labTest to be updated.
     * @param scanner The {@link Scanner} object used for input.
     * 
     */
    public static void updateLabTestDetails(LabTest labTest, Scanner scanner) {
        if (labTest == null) {
            System.out.println("Lab Test not found.");
            return;
        }

        System.out.println("Updating details for Lab Test ID: " + labTest.getLabTestID());

        // Update Lab Type
        System.out.println("Select the new Lab Test Type:");
        for (LabType type : LabType.values()) {
            System.out.println(type.ordinal() + " - " + type);
        }
        System.out.print("Enter type number: ");
        int typeIndex = Integer.parseInt(scanner.nextLine());
        labTest.setType(LabType.values()[typeIndex]);

        // Update Status
        System.out.println("Select the new Status:");
        for (VisitStatus status : VisitStatus.values()) {
            System.out.println(status.ordinal() + " - " + status);
        }
        System.out.print("Enter status number: ");
        int statusIndex = Integer.parseInt(scanner.nextLine());
        labTest.setStatus(VisitStatus.values()[statusIndex]);

        // Update Remarks
        System.out.print("Enter new Cost: ");
        String newCost = scanner.nextLine();
        if (!newCost.isEmpty())
            labTest.setCost(Double.parseDouble(newCost));

        // Update Remarks
        System.out.print("Enter new Remarks: ");
        String remarks = scanner.nextLine();
        labTest.setRemarks(remarks);

        System.out.println("Lab Test details updated successfully!");
        // scanner.close();
    }

    /**
     * Creates a new lab test object.
     * This method allows the physician to create a new labtest and assign it
     * to the respective outpatient case.
     *
     * @param outpatientCase The outpatientCase in which the new lab test will be
     *                       linked to.
     * @param scanner        The {@link Scanner} object used for input.
     * 
     */
    public static void createNewLabTest(OutpatientCase outpatientCase, Scanner scanner) {
        // Select Lab Type
        System.out.println("Select the Lab Test Type:");
        for (LabType type : LabType.values()) {
            System.out.println(type.ordinal() + " - " + type);
        }
        System.out.print("Enter type number: ");
        int typeIndex = Integer.parseInt(scanner.nextLine());
        LabType labType = LabType.values()[typeIndex];

        // Enter Date
        Date dateStamp = null;
        while (true) {
            System.out.print("Enter the Date (dd-MM-yyyy): ");
            String dateInput = scanner.nextLine().trim();

            dateStamp = OpUtils.customDate(dateInput);

            if (dateStamp != null) {
                break;
            } else {
                System.out.println("Invalid date format! Please enter in dd-MM-yyyy format.");
            }
        }

        // Select Status
        System.out.println("Select the Lab Test Status:");
        for (VisitStatus status : VisitStatus.values()) {
            System.out.println(status.ordinal() + " - " + status);
        }
        System.out.print("Enter status number: ");
        int statusIndex = Integer.parseInt(scanner.nextLine());
        VisitStatus labStatus = VisitStatus.values()[statusIndex];

        System.out.print("Enter the Lab Test Cost: ");
        double cost = Double.parseDouble(scanner.nextLine());

        // Enter Remarks
        System.out.print("Enter Remarks (if any): ");
        String remarks = scanner.nextLine();

        // Create and add the new Lab Test
        LabTest newLabTest = new LabTest(labType, dateStamp, labStatus, outpatientCase, outpatientCase.getPhysician(),
                remarks, cost);
        outpatientCase.addLabTest(newLabTest);
        all_labtests.add(newLabTest);

        System.out.println("Lab Test created successfully with ID: " + newLabTest.getLabTestID());
        // scanner.close();
    }

    /**
     * Prints the current medications and prescriptions for the outpatient case
     *
     * @param outpatientCase The outpatientCase that is related to the
     *                       Drug object like the patient's current medications and
     *                       prescriptions.
     * 
     */
    public static void printDrugListByOutpatientCase(OutpatientCase outpatientCase) {
        System.out.println(
                "------------------------------------------------------------------------------------------------------------");

        System.out.println("\tDrug List for Outpatient Cases");
        System.out.println(
                "------------------------------------------------------------------------------------------------------------");

        System.out.println("\nCurrent Medications\n");
        System.out.printf("%-15s | %-15s | %-20s | %-15s | %-30s | %-20s \n",
                "Outpatient ID", "Drug ID", "Drug Name", "Dosage", "Expiry Date", "Cost Per Unit");
        System.out.println(
                "------------------------------------------------------------------------------------------------------------");

        boolean hasDrugs = false;
        for (Drug drug : outpatientCase.getCurrentMedications()) {
            hasDrugs = true;
            if (drug != null) {
                System.out.printf("%-15s | %-15s | %-20s | %-15s | %-30s | $%-20.2f \n",
                        outpatientCase.getOutpatientCaseID(),
                        drug.getDrugID(),
                        drug.getDrugName(),
                        drug.getDosage(),
                        drug.getExpiryDate(),
                        drug.getCostPerUnit());
            }
        }

        System.out.println("\nPrescriptions\n");
        System.out.printf("%-15s | %-15s | %-20s | %-15s | %-30s | %-20s \n",
                "Outpatient ID", "Drug ID", "Drug Name", "Dosage", "Expiry Date", "Cost Per Unit");
        System.out.println(
                "------------------------------------------------------------------------------------------------------------");

        for (Drug drug : outpatientCase.getPrescriptions()) {
            hasDrugs = true;
            if (drug != null) {
                System.out.printf("%-15s | %-15s | %-20s | %-15s | %-30s | $%-20.2f \n",
                        outpatientCase.getOutpatientCaseID(),
                        drug.getDrugID(),
                        drug.getDrugName(),
                        drug.getDosage(),
                        drug.getExpiryDate(),
                        drug.getCostPerUnit());
            }
        }

        if (!hasDrugs) {
            System.out.println("No drugs found for any outpatient cases.");
        }

    }

    /**
     * Clear the prescriptions for the outpatient case
     * This method allows the physicians to clear the prescriptions for an
     * in-progress outpatient case.
     *
     * @param outpatientCase The outpatientCase in which the prescriptions will be
     *                       cleared
     * 
     */
    private static void clearPrescriptionsByOutpatientCase(OutpatientCase outpatientCase) {
        System.out.println("\n----------------------------------------------------------------------------");
        System.out.println("Clearing All Prescription For Outpatient Case");
        System.out.println("----------------------------------------------------------------------------");
        if (outpatientCase.getStatus() == VisitStatus.IN_PROGRESS) {
            outpatientCase.clearAllPrescriptions();
        }
    }

    private static void updateDrugListByOutpatientCase(OutpatientCase outpatientCase, Scanner scanner) {
        System.out.println(
                "------------------------------------------------------------------------------------------------------------");

        if (outpatientCase.getStatus() == VisitStatus.IN_PROGRESS) {
            System.out.println("Would you want to update prescriptions for this outpatient case? (yes/no)\n");
            if (scanner.nextLine().equalsIgnoreCase("yes")) {
                Boolean hasDrugs = false;
                System.out.println("\nPrescriptions\n");
                System.out.printf("%-15s | %-15s | %-20s | %-15s | %-30s | %-20s \n",
                        "Outpatient ID", "Drug ID", "Drug Name", "Dosage", "Expiry Date", "Cost Per Unit");
                System.out.println(
                        "-----------------------------------------------------------------------------------------------------------------------------");

                for (Drug drug : outpatientCase.getPrescriptions()) {
                    hasDrugs = true;
                    if (drug != null) {
                        System.out.printf("%-15s | %-15s | %-20s | %-15s | %-30s | $%-20.2f \n",
                                outpatientCase.getOutpatientCaseID(),
                                drug.getDrugID(),
                                drug.getDrugName(),
                                drug.getDosage(),
                                drug.getExpiryDate(),
                                drug.getCostPerUnit());
                    }
                }

                if (!hasDrugs) {
                    System.out.println("No drugs found for any outpatient cases.");
                }
                System.out.println(
                        "------------------------------------------------------------------------------------------------------------");
                System.out.println("Current Drug List");
                for (Drug drug : Drug.getAllDrugs()) {
                    if (drug != null) {
                        System.out.printf("%-15s | %-20s | %-12s | %-10s | $%-12.2f\n",
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
                            "Please select the drugIDs that you wish to add to the prescriptions seperated by commas e.g 1,2:\n");
                    reply = scanner.nextLine();
                    if (reply.matches("^[0-9,]+$") == true) {
                        validReply = false;
                        if (reply.indexOf(",") != -1) {
                            String[] drugs = reply.trim().split(",");
                            for (String i : drugs) {
                                Drug drug = Drug.searchDrugByID(Integer.valueOf(i));
                                if (outpatientCase.getPrescriptions().contains(drug) == false) {
                                    outpatientCase.getPrescriptions().add(drug);

                                }
                            }
                        } else {
                            Drug drug = Drug.searchDrugByID(Integer.valueOf(reply.trim()));
                            if (outpatientCase.getPrescriptions().contains(drug) == false) {
                                outpatientCase.getPrescriptions().add(drug);
                            }
                        }
                    } else {
                        System.out.println("You have entered the wrong format or input. Try again.");
                    }
                }
            }
        }
    }
}

