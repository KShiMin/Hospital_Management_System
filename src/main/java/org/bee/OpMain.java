package org.bee;

import java.util.Scanner;

import org.bee.hms.outpatient.ClerkPortal;
import org.bee.hms.outpatient.Patient;
import org.bee.hms.outpatient.PatientPortal;
import org.bee.hms.outpatient.Physician;
import org.bee.hms.outpatient.PhysicianPortal;
import org.bee.hms.utils.OpUtils;

public class OpMain {
    public static void main(String[] args) {
        // Parse the dataset
        // DatasetManager.parseDataset();

        Scanner scan = new Scanner(System.in);
        boolean end = false;
        while (!end) {
            System.out.println("\n###################################################");
            System.out.println("\tWelcome to Outpatient Clinic SG!");
            System.out.println("###################################################");
            System.out.println("Please select a role: \n");
            System.out.println("1- Patient");
            System.out.println("2- Physician");
            System.out.println("3- Clerk");
            System.out.println("4- System Admin");
            System.out.println("5- Exit\n");

            int role_input = OpUtils.optionsInput(scan, 0, 6);
            OpUtils.clearScreen();
            switch (role_input) {
                case 1:
                    // Find the patient (assumes your Patient class has a findByInput method)
                    Patient patient = null;
                    do {
                        System.out.println("Enter Patient ID or NRIC");
                        String patientID = scan.nextLine().trim();

                        if (patientID.matches("\\d+")) { // Check if it's numeric
                            patient = Patient.searchPatientbyID(Integer.valueOf(patientID));

                        } else if (Patient.searchPatientbyNRIC(patientID) != null) {
                            patient = Patient.searchPatientbyNRIC(patientID);
                        }
                        if (patient == null) {
                            System.out.println("You have entered an invalid Patient ID or NRIC");
                            patientID = "";
                        }

                    } while (patient == null);
                    PatientPortal.showPatientMenu(patient, scan);
                    break;
                case 2:
                    // Find the physician
                    Physician physician = null;
                    do {
                        System.out.println("Enter Physician ID or NRIC");
                        String physicianID = scan.nextLine().trim();

                        if (physicianID.matches("\\d+")) {
                            physician = Physician.searchPhysicianByID(Integer.valueOf(physicianID));
                        } else if (Physician.searchPhysicianbyNRIC(physicianID) != null) {
                            physician = Physician.searchPhysicianbyNRIC(physicianID);
                        } else {
                            System.out.println("You have entered an invalid Physician ID or NRIC");
                            physicianID = "";
                        }
                    } while (physician == null);
                    PhysicianPortal.showPhysicianMenu(physician, scan);
                    break;
                case 3:
                    ClerkPortal.showClerkMenu(scan);
                    break;
                case 4:
                    // No database to view yet
                    continue;
                    // AdminPortal.showAdminMenu(scan);
                    // break;
                case 5:
                    OpUtils.printExit();
                    end = true;
                    break;
            }
        }
        scan.close();
    }
}
