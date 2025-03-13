package org.bee.hms.outpatient;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.*;

// import org.bee.hms.humans.DEPARTMENT;
import org.bee.hms.humans.*;
import org.bee.hms.utils.DateConverter;

public class Outpatient_Humans {
    static List<Patient> all_patients = new ArrayList<>();
    static List<Doctor> all_physicians = new ArrayList<>();

    // ===============patient functs================= //

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
        Date dob = DateConverter.customDate(dateString);
        int age = calculateAge(LocalDate.parse(dateString));
        System.out.print("Enter Contact Number: ");
        String contactNumber = scanner.nextLine();
        for (BloodType s : BloodType.values()) {
            System.out.println(s.ordinal() + " - " + s);
        }
        System.out.print("Enter the number referencing the patient's blood type: ");
        int typeIndex = Integer.parseInt(scanner.nextLine());
        BloodType blood = BloodType.values()[typeIndex];
        Patient patient = new Patient(name, nric, address, nationality, patientGender, age, dob, contactNumber, blood, new ArrayList<Feedback>());
        all_patients.add(patient);
        System.out.print("Patient successfully created!");
        System.out.println(patient);
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
            updateChoice = optionsInput(scan, 0, 3);
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
                        updateInfo = optionsInput(scan, 0, 5);

                        switch (updateInfo) {
                            case 1:
                                System.out.println("Enter your new address: ");
                                info = scan.nextLine().trim();
                                patient.setAddress(info);
                                System.out.println("Address updated successfully.");
                                updateDataset();
                                break;

                            case 2:
                                System.out.println("Enter your nationality: ");
                                info = scan.nextLine().trim();
                                patient.setNationality(info);
                                System.out.println("Nationality updated successfully.");
                                updateDataset();
                                break;

                            case 3:
                                System.out.println("Enter your new contact number: ");
                                info = scan.nextLine().trim();
                                patient.setContactNumber(info);
                                System.out.println("Contact number updated successfully.");
                                updateDataset();
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
     * Prompts the user to enter physician details and creates a new physician
     * object.
     * The method ensures valid input is collected before assigning values.
     *
     * @param scanner The {@link Scanner} object used for input.
     */
    public static void createNewPhysician(Scanner scanner) {
        // String name, String nric, String address, SEX gender, String nationality,
        // Integer age, Date dateOfBirth, String contactNumber, DEPARTMENT department,
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
        Date dob = DateConverter.customDate(dateString);
        int age = calculateAge(LocalDate.parse(dateString));
        System.out.print("Enter Contact Number: ");
        String contactNumber = scanner.nextLine();
        for (DEPARTMENT s : DEPARTMENT.values()) {
            System.out.println(s.ordinal() + " - " + s);
        }
        System.out.print("Enter the number referencing the physician's department: ");
        int typeIndex = Integer.parseInt(scanner.nextLine());
        DEPARTMENT department = DEPARTMENT.values()[typeIndex];
        // Need to fix
        Doctor physician = new Doctor(name, nric, address, physicianGender, nationality, age, dob, contactNumber,
                department, new ArrayList<OutpatientCase>());
        all_physicians.add(physician);
        System.out.print("Physician successfully created!\n");
        System.out.println(physician);
    }

    /**
     * Calculates the age of a person based on their birth date.
     * 
     * @param birthDate The {@link LocalDate} representing the person's date of
     *                  birth.
     * @return The calculated age in years. Returns 0 if the birth date is null.
     */
    public static int calculateAge(LocalDate birthDate) {
        if (birthDate != null) {
            return Period.between(birthDate, LocalDate.now()).getYears();
        } else {
            return 0;
        }
    }
}
