package org.bee.hms.outpatient;

import java.util.List;
import java.util.Scanner;

public class TreatmentCRUD {
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
        for (STATUS s : STATUS.values()) {
            System.out.println(s.ordinal() + " - " + s);
        }
        boolean validNumber = true;

        while (validNumber) {
            System.out.print("Enter status number: ");
            int statusIndex = Integer.parseInt(scanner.nextLine());
            if (statusIndex > (STATUS.values().length - 1)) {
                System.out.println("Please select a valid option.");
            } else {
                validNumber = false;
                treatment.setStatus(STATUS.values()[statusIndex]);
            }
        }

        // Update Start Date
        System.out
                .print("Enter new Start Date (dd-MM-yyyy) or press Enter to keep [" + treatment.getStartDate() + "]: ");
        String newStartDate = scanner.nextLine();
        if (!newStartDate.isEmpty()) {
            Date parsedStartDate = customDate(newStartDate);
            if (parsedStartDate != null) {
                treatment.setStartDate(parsedStartDate);
            }
        }

        // Update End Date
        System.out.print("Enter new End Date (dd-MM-yyyy) or press Enter to keep [" + treatment.getEndDate() + "]: ");
        String newEndDate = scanner.nextLine();
        if (!newEndDate.isEmpty()) {
            Date parsedEndDate = customDate(newEndDate);
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
        Date startDate = customDate(startDateInput);

        System.out.print("Enter the End Date (dd-MM-yyyy): ");
        String endDateInput = scanner.nextLine();
        Date endDate = customDate(endDateInput);

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
        for (STATUS status : STATUS.values()) {
            System.out.println(status.ordinal() + " - " + status);
        }
        System.out.print("Enter status number: ");
        int statusIndex = Integer.parseInt(scanner.nextLine());
        STATUS procedureStatus = STATUS.values()[statusIndex];

        System.out.print("Enter Remarks (if any): ");
        String remarks = scanner.nextLine();

        System.out.print("Enter Procedure Date (dd-MM-yyyy): ");
        String dateInput = scanner.nextLine();
        Date procedureDate = customDate(dateInput);

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
}
