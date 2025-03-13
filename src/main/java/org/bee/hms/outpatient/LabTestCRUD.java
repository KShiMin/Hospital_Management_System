package org.bee.hms.outpatient;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import org.bee.hms.medical.VisitStatus;

public class LabTestCRUD {
    static List<LabTest> all_labtests = new ArrayList<>();
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
        for (LABTYPE type : LABTYPE.values()) {
            System.out.println(type.ordinal() + " - " + type);
        }
        System.out.print("Enter type number: ");
        int typeIndex = Integer.parseInt(scanner.nextLine());
        labTest.setType(LABTYPE.values()[typeIndex]);

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
        for (LABTYPE type : LABTYPE.values()) {
            System.out.println(type.ordinal() + " - " + type);
        }
        System.out.print("Enter type number: ");
        int typeIndex = Integer.parseInt(scanner.nextLine());
        LABTYPE labType = LABTYPE.values()[typeIndex];

        // Enter Date
        Date dateStamp = null;
        while (true) {
            System.out.print("Enter the Date (dd-MM-yyyy): ");
            String dateInput = scanner.nextLine().trim();

            dateStamp = customDate(dateInput);

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

}
