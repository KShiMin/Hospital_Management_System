package org.bee.hms.outpatient;

import java.util.Date;

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

    // private static List<Physician> instances = new ArrayList<>();

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
     */
    public Physician(String name, String nric, String address, Sex gender, String nationality,
            Integer age, Date dateOfBirth, String contactNumber, Department department) {
        super(name, nric, address, nationality, gender, age, dateOfBirth, contactNumber);
        setPhysicianID(count++);
        this.department = department;
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
        // stringBuilder.append(String.format("Patient Cases:        %s\n", getPatientCasesString()));
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
}
