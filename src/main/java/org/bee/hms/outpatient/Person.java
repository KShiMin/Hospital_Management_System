package org.bee.hms.outpatient;
import java.util.Date;

import org.bee.hms.humans.Sex;

/**
 * Represents a person with personal details such as name, NRIC, address,
 * gender, nationality, age, date of birth, and contact number.
 */
public class Person {
    private String name;
    private String nric;
    private String address;
    private Sex gender;
    private String nationality;
    private Integer age;
    private Date dateOfBirth;
    private String contactNumber;

    /**
     * Constructs a new Person instance with the given details.
     * 
     * @param name          The name of the person.
     * @param nric          The NRIC of the person.
     * @param address       The address of the person.
     * @param nationality   The nationality of the person.
     * @param gender        The gender of the person.
     * @param age           The age of the person.
     * @param dateOfBirth   The date of birth of the person.
     * @param contactNumber The contact number of the person.
     */
    public Person(String name, String nric, String address, String nationality, Sex gender, Integer age,
            Date dateOfBirth, String contactNumber) {
        this.name = name;
        this.nric = nric;
        this.address = address;
        this.gender = gender;
        this.nationality = nationality;
        this.age = age;
        this.dateOfBirth = dateOfBirth;
        this.contactNumber = contactNumber;
    }

    /**
     * Returns a formatted string representation of the person's details.
     * 
     * @return A formatted string containing the person's details.
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("Name:                 %s\n", getName()));
        stringBuilder.append(String.format("Gender:               %s\n", getGender()));
        stringBuilder.append(String.format("Age:                  %s\n", getAge()));
        stringBuilder.append(String.format("Date of Birth:        %s\n", getDateOfBirth()));
        stringBuilder.append(String.format("Contact:              %s\n", getContactNumber()));
        stringBuilder.append(String.format("NRIC:                 %s\n", getNric()));
        stringBuilder.append(String.format("Address:              %s\n", getAddress()));
        stringBuilder.append(String.format("Nationality:          %s", getNationality()));

        return stringBuilder.toString();
    }

    /**
     * Displays the person's information in the console.
     */
    public void displayUserInfo() {
        System.out.println("Name: " + name);
        System.out.println("NRIC: " + nric);
        System.out.println("Contact: " + contactNumber);
        System.out.println("Age: " + age);
        System.out.println("Gender: " + gender);
        System.out.println("Address: " + address);
        System.out.println("Nationality: " + nationality);
        System.out.println("Date of birth: " + dateOfBirth);
    }

    /**
     * Gets the name of the person.
     * 
     * @return The name of the person.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the person.
     * 
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the NRIC of the person.
     * 
     * @return The NRIC of the person.
     */
    public String getNric() {
        return nric;
    }

    /**
     * Sets the NRIC of the person.
     * 
     * @param nric The NRIC to set.
     */
    public void setNric(String nric) {
        this.nric = nric;
    }

    /**
     * Gets the address of the person.
     * 
     * @return The address of the person.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the person.
     * 
     * @param address The address to set.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the nationality of the person.
     * 
     * @return The nationality of the person.
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * Sets the nationality of the person.
     * 
     * @param nationality The nationality to set.
     */
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    /**
     * Gets the gender of the person.
     *
     * @return The gender of the person as a {@link SEX} enum.
     */
    public Sex getGender() {
        return gender;
    }

    /**
     * Sets the gender of the person.
     * 
     * @param gender The gender to set.
     */
    public void setGender(Sex gender) {
        this.gender = gender;
    }

    /**
     * Gets the age of the person.
     * 
     * @return The age of the person.
     */
    public Integer getAge() {
        return age;
    }

    /**
     * Sets the age of the person.
     * 
     * @param age The age to set.
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * Gets the date of birth of the person.
     * 
     * @return The date of birth of the person.
     */
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Sets the date of birth of the person.
     * 
     * @param dateOfBirth The date of birth to set.
     */
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Gets the contact number of the person.
     * 
     * @return The contact number of the person.
     */
    public String getContactNumber() {
        return contactNumber;
    }

    /**
     * Sets the contact number of the person.
     * 
     * @param contactNumber The contact number to set.
     */
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
