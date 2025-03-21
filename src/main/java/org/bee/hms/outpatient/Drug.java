package org.bee.hms.outpatient;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents a drug with attributes such as ID, name, dosage, expiry date, and
 * cost per unit
 * 
 * This class stores details about the drug, a list of drugs available at the
 * outpatient clinic for Physicians to prescribe to Patients.
 * 
 * Each drug entry is uniquely identified by a {@code drugID}, which is
 * automatically
 * assigned using a static counter.
 * 
 * Instances of this class are stored in a static list {@code instances} for
 * retrieval
 * and management.
 */
public class Drug {
    private static int count = 0;

    private int drugID;
    private String drugName;
    private String dosage;
    private Date expiryDate;
    private Double costPerUnit;

    /**
     * Returns a formatted string representation of the drug details.
     *
     * @return A formatted string containing drug details.
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n\nDrug Details:\n");
        stringBuilder.append(String.format("Drug ID:              %s\n", drugID));
        stringBuilder.append(String.format("Name:                 %s\n", drugName));
        stringBuilder.append(String.format("Dosage:               %s\n", dosage));
        stringBuilder.append(String.format("Expiry Date:          %s\n", expiryDate));
        stringBuilder.append(String.format("Cost Per Unit:        $ %.2f\n", costPerUnit));

        String string = stringBuilder.toString();
        return string;
    }

    /**
     * A static list that stores all instances of the class
     * The list keeps track of all drug objects created in the system.
     */
    private static List<Drug> instances = new ArrayList<>();

    /**
     * Retrieves a list of all drug instances.
     *
     * @return A list of all drug objects.
     */
    public static List<Drug> getAllDrugs() {
        return instances;
    }

    /**
     * Constructs a new Drug object with the specified drug name, dosage, expiry
     * date, and cost per unit.
     * The drug ID is automatically generated and assigned using a static counter.
     * The newly created Drug instance is added to the list of instances.
     *
     * @param drugName    The name of the drug. Cannot be null or empty.
     * @param dosage      The dosage of the drug. Cannot be null or empty.
     * @param expiryDate  The expiry date of the drug. Cannot be null and must be a
     *                    future date.
     * @param costPerUnit The cost per unit of the drug. Must be a positive value.
     */
    public Drug(String drugName, String dosage, Date expiryDate, Double costPerUnit) {
        setDrugID(count++);
        this.drugName = drugName;
        this.dosage = dosage;
        this.expiryDate = expiryDate;
        this.costPerUnit = costPerUnit;

        instances.add(this);
    }

    /**
     * Gets the drug ID.
     *
     * @return The drug ID.
     */
    public int getDrugID() {
        return drugID;
    }

    /**
     * Sets the drug ID.
     *
     * @param drugID The drug ID to set.
     */
    public void setDrugID(int drugID) {
        this.drugID = drugID;
    }

    /**
     * Gets the drug name.
     *
     * @return The drug name.
     */
    public String getDrugName() {
        return drugName;
    }

    /**
     * Sets the drug name.
     *
     * @param drugName The new drug name to set.
     */
    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    /**
     * Gets the dosage of the drug.
     *
     * @return The dosage of the drug.
     */
    public String getDosage() {
        return dosage;
    }

    /**
     * Sets the dosage of the drug.
     *
     * @param dosage The dosage to set for the drug.
     */
    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    /**
     * Gets the expiry date of the drug.
     *
     * @return The expiry date of the drug.
     */
    public Date getExpiryDate() {
        return expiryDate;
    }

    /**
     * Sets the expiry date of the drug.
     *
     * @param expiryDate The date to set as the expiry date of the drug.
     */
    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    /**
     * Gets the costs of the drug
     *
     * @return The cost of the drug.
     */
    public Double getCostPerUnit() {
        return costPerUnit;
    }

    /**
     * Sets the cost of the drug
     *
     * @param costPerUnit The cost of the drug.
     */
    public void setCostPerUnit(Double costPerUnit) {
        this.costPerUnit = costPerUnit;
    }

    /**
     * Searches for a drug entry by its ID.
     *
     * @param drugID The ID of the drug to search for.
     * @return The drug object with the matching ID, or null if not found.
     */
    public static Drug searchDrugByID(int drugID) {
        for (Drug drug : instances) {
            if (drug.getDrugID() == drugID) {
                return drug;
            }
        }
        return null;
    }
}
