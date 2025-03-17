package org.bee.hms.outpatient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.bee.hms.humans.BloodType;
import org.bee.hms.humans.Sex;
import org.bee.hms.utils.OpUtils;

public class PatientPortal {

    static List<Feedback> all_feedbacks = new ArrayList<>();

    public static void showPatientMenu(Patient patient, Scanner scan) {
        boolean patientMenu = true;
        while (patientMenu) {
            System.out.println("\n----------------------------------------------------------------------------");
            System.out.println("\tWelcome to Patient's Portal, " + patient.getName());
            System.out.println("----------------------------------------------------------------------------");
            System.out.println("Following Functionalities are available: \n");
            System.out.println("1- View/ Update Particulars");
            System.out.println("2- Add Feedback");
            System.out.println("3- View all past feedbacks");
            System.out.println("4- Return to previous");
            System.out.println("5- Exit");
            System.out.println("----------------------------------------------------------------------------");

            int patientTaskinput = OpUtils.optionsInput(scan, 0, 6);
            if (patientTaskinput == 5) {
                // Exit Message - To be changed when implementing with telemedicine
                // Utils.printExit();
                System.exit(0);
            }
            switch (patientTaskinput) {
                case 1:
                    System.out.println("\n----------------------------------------------------------------------------");
                    System.out.println("\t\t\tView Particulars");
                    System.out.println("----------------------------------------------------------------------------\n");
                    // patient.displayHuman(); // uses Insurance's patient class
                    System.out.println(patient.displayPatientInfo());
                    updatePatientParticular(patient);
                    break;
                case 2:
                    addFeedback(patient, scan);
                    break;
                case 3:
                    printAllFeedBack(patient);
                    break;
                case 4:
                    patientMenu = false;
                    break;
            }
        }
    }
    
/**
     * Updates the Patient particulars through a series of prompts.
     *
     * @param patient The patient to be updated.
     * 
     */
    private static void updatePatientParticular(Patient patient) {
        int updateChoice = 0;
        Scanner scan = new Scanner(System.in);
        do {
            System.out.println("\n----------------------------------------------------------------------------");
            System.out.println("Would you like to update your particulars? \n1. Yes \n2. No");
            updateChoice = OpUtils.optionsInput(scan, 0, 3);
            switch (updateChoice) {
                case 1:
                    System.out
                            .println("\n----------------------------------------------------------------------------");
                    System.out.println("Update Particulars");
                    System.out.println("----------------------------------------------------------------------------");

                    int updateInfo;
                    String info;
                    do {
                        System.out.println("\nSelect the information you wish to update:");
                        System.out.println("1. Address\n2. Nationality\n3. Contact Number\n4. Exit Update");
                        updateInfo = OpUtils.optionsInput(scan, 0, 5);

                        switch (updateInfo) {
                            case 1:
                                System.out.println("Enter your new address: ");
                                info = scan.nextLine().trim();
                                // patient.setAddress(info);
                                System.out.println("Address updated successfully.");
                                // updateDataset();
                                break;

                            case 2:
                                System.out.println("Enter your nationality: ");
                                info = scan.nextLine().trim();
                                // patient.setNationality(info);
                                System.out.println("Nationality updated successfully.");
                                // updateDataset();
                                break;

                            case 3:
                                System.out.println("Enter your new contact number: ");
                                info = scan.nextLine().trim();
                                // Need help
                                // patient.setContact(info);
                                patient.setContactNumber(info);
                                System.out.println("Contact number updated successfully.");
                                // updateDataset();
                                break;

                            case 4:
                                System.out.println("Exiting update process.");
                                break;
                        }
                    } while (updateInfo != 4);
                    break;

                case 2:
                    break;
            }

        } while (updateChoice != 1 && updateChoice != 2);
    }

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
                        // not sure how to get patientID since they're in different class
                        feedback.getPatient().getPatientID(),
                        feedback.getMedicalCareRating(),
                        feedback.getServiceRating(),
                        feedback.getDateStamp(),
                        feedback.getRemarks());
            }
        }
    }

    
/**
     * Prompts the user to enter patient details and creates a new patient object.
     * The method ensures valid input is collected before assigning values.
     *
     * @param scanner The {@link Scanner} object used for input.
     */
    public static void createNewPatient(Scanner scanner) {
        // String name, String nric, String address, String nationality, SEX gender,
        // Integer age, Date dateOfBirth, String contactNumber, BLOOD_TYPE bloodType
        System.out.print("Enter Patient Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter NRIC: ");
        String nric = scanner.nextLine();
        System.out.print("Enter Patient's address: ");
        String address = scanner.nextLine();
        System.out.print("Enter Patient's nationality: ");
        String nationality = scanner.nextLine();
        Boolean validNumber = true;
        Sex patientGender = Sex.MALE;
        while (validNumber) {
            System.out.println("Select Gender:\n1.Male\n2.Female");
            String gender = scanner.nextLine();
            try {
                if (Integer.parseInt(gender) == 1) {
                    patientGender = Sex.MALE;
                    validNumber = false;
                } else if (Integer.parseInt(gender) == 2) {
                    patientGender = Sex.FEMALE;
                    validNumber = false;
                }
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
        for (BloodType s : BloodType.values()) {
            System.out.println(s.ordinal() + " - " + s);
        }
        System.out.print("Enter the number referencing the patient's blood type: ");
        int typeIndex = Integer.parseInt(scanner.nextLine());
        BloodType blood = BloodType.values()[typeIndex];
        Patient patient = new Patient(name, nric, address, nationality, patientGender, age, dob, contactNumber, blood,
                new ArrayList<Feedback>());
        // all_patients.add(patient);
        System.out.print("Patient successfully created!");
        System.out.println(patient);
    }
}
