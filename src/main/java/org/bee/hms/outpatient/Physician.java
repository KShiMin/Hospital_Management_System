package org.bee.hms.outpatient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bee.hms.humans.Department;
import org.bee.hms.humans.Sex;

/**
 * Represents a physician, which is a specialized type of Person with additional
 * details such as a unique physician ID, department affiliation, and a list of assigned
 * outpatient cases.
 */
public class Physician extends Person {
    private static int count = 0;
    private int physicianID;
    private Department department;
    private ArrayList<OutpatientCase> patientCases;

    private static List<Physician> instances = new ArrayList<>();

    /**
     * Constructs a new Physician instance with the specified details. This constructor
     * assigns a unique physician ID automatically and adds the instance to the list of
     * all physicians.
     *
     * @param name          the name of the physician.
     * @param nric          the NRIC of the physician.
     * @param address       the address of the physician.
     * @param gender        the gender of the physician.
     * @param nationality   the nationality of the physician.
     * @param age           the age of the physician.
     * @param dateOfBirth   the date of birth of the physician.
     * @param contactNumber the contact number of the physician.
     * @param department    the department the physician belongs to.
     * @param patientCases  the list of outpatient cases associated with the physician.
     */
    public Physician(String name, String nric, String address, Sex gender, String nationality,
            Integer age, Date dateOfBirth, String contactNumber, Department department,
            ArrayList<OutpatientCase> patientCases) {
        super(name, nric, address, nationality, gender, age, dateOfBirth, contactNumber);
        setPhysicianID(count++);
        this.department = department;
        this.patientCases = patientCases;

        instances.add(this);
    }

    /**
     * Returns a formatted string representation of the physician's details,
     * including both physician-specific information and personal details inherited
     * from Person.
     *
     * @return a formatted string representing the physician's details.
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n\nPhysician Details:\n");
        stringBuilder.append(String.format("Physician ID:         %s\n", physicianID));
        stringBuilder.append(String.format("Department:           %s\n", department));
        stringBuilder.append(String.format("Patient Cases:        %s\n", getPatientCasesString()));
        stringBuilder.append("\nPersonal Information:\n");
        stringBuilder.append(String.format("Name:                 %s\n", getName()));
        stringBuilder.append(String.format("Gender:               %s\n", getGender()));
        stringBuilder.append(String.format("Age:                  %s\n", getAge()));
        stringBuilder.append(String.format("Date of Birth:        %s\n", getDateOfBirth()));
        stringBuilder.append(String.format("Contact:              %s", getContactNumber()));

        String string = stringBuilder.toString();
        return string;
    }

    /**
     * Retrieves the list of all Physician instances created.
     *
     * @return a list of all physicians.
     */
    public static List<Physician> getAllPhysicians() {
        return instances;
    }

    /**
     * Gets the unique identifier of the physician.
     *
     * @return the physician's ID.
     */
    public int getPhysicianID() {
        return physicianID;
    }

    /**
     * Sets the unique identifier for the physician.
     *
     * @param physicianID the new physician ID.
     */
    public void setPhysicianID(int physicianID) {
        this.physicianID = physicianID;
    }

    /**
     * Gets the department the physician belongs to.
     *
     * @return the department of the physician.
     */
    public Department getDepartment() {
        return department;
    }

    /**
     * Sets the department of the physician.
     *
     * @param department the new department for the physician.
     */
    public void setDepartment(Department department) {
        this.department = department;
    }

    /**
     * Gets the list of outpatient cases associated with the physician.
     *
     * @return an ArrayList of outpatient cases.
     */
    public ArrayList<OutpatientCase> getPatientCases() {
        return patientCases;
    }

    /**
     * Returns a string representation of the physician's patient cases. If no patient
     * cases exist, returns "null".
     *
     * @return a string representing the patient cases.
     */
    public String getPatientCasesString() {
        String patientCaseString = "";
        if (patientCases != null) {
            for (OutpatientCase patientCase : patientCases) {
                patientCaseString += patientCase;
            }
        }
        if (patientCaseString.equals("")) {
            return "null";
        }
        return patientCaseString;
    }

    /**
     * Adds an outpatient case to the physician's list of patient cases.
     *
     * @param patientCase the outpatient case to add.
     */
    public void addPatientCase(OutpatientCase patientCase) {
        this.patientCases.add(patientCase);
    }

    /**
     * Sets the list of outpatient cases for the physician.
     *
     * @param patientCases an ArrayList of outpatient cases.
     */
    public void setPatientCases(ArrayList<OutpatientCase> patientCases) {
        this.patientCases = patientCases;
    }

    /**
     * Searches for a physician by their unique ID.
     *
     * @param id the physician ID to search for.
     * @return the Physician with the matching ID, or null if no such physician exists.
     */
    public static Physician searchPhysicianByID(int id) {
        for (Physician physician : instances) {
            if (physician.getPhysicianID() == (id)) {
                return physician;
            }
        }
        return null;
    }

    /**
     * Searches for a physician by their NRIC.
     *
     * @param nric the NRIC to search for.
     * @return the Physician with the matching NRIC, or null if no such physician exists.
     */
    public static Physician searchPhysicianbyNRIC(String nric) {
        for (Physician physician : instances) {
            if (physician.getNric().equals(nric)) {
                return physician;
            }
        }
        return null;
    }

}
