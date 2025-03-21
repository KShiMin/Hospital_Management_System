package org.bee.hms.outpatient;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bee.hms.medical.VisitStatus;

/**
 * Represents a lab test associated with an outpatient case. A lab test includes details such as
 * a unique lab test ID, lab test type, date stamp, status, the associated outpatient case,
 * the physician who ordered or performed the test, remarks, and cost.
 */
public class LabTest {
    /** Static counter to generate unique lab test IDs. */
    private static int count = 0;

    /** Unique identifier for the lab test. */
    private int labTestID;
    
    /** The type of lab test. */
    private LABTYPE type;
    
    /** The date when the lab test was performed or recorded. */
    private Date dateStamp;
    
    /** The current status of the lab test. */
    private VisitStatus status;
    
    /** The outpatient case associated with this lab test. */
    // private OutpatientCase outpatientCase; // parallel importing
    
    /** The physician associated with this lab test. */
    private Physician physician;
    
    /** Remarks or comments regarding the lab test. */
    private String remarks;
    
    /** The cost of the lab test. */
    private Double cost;

    /** 
     * A static list to store all LabTest instances.
     */
    private static List<LabTest> instances = new ArrayList<>();

    /**
     * Constructs a new LabTest instance with the specified details.
     *
     * @param type the type of the lab test.
     * @param dateStamp the date when the lab test was performed or recorded.
     * @param status the current status of the lab test.
     * @param outpatientCase the outpatient case associated with the lab test.
     * @param physician the physician associated with the lab test.
     * @param remarks remarks or comments regarding the lab test.
     * @param cost the cost of the lab test.
     */
    public LabTest(LABTYPE type, Date dateStamp, VisitStatus status,
                   Physician physician, String remarks, Double cost) {
        setLabTestID(count++);
        this.type = type;
        this.dateStamp = dateStamp;
        this.status = status;
        // this.outpatientCase = outpatientCase;
        this.physician = physician;
        this.remarks = remarks;
        this.cost = cost;

        instances.add(this);
    }

    /**
     * Returns a formatted string representation of the lab test details.
     *
     * @return a string containing the lab test details.
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n\nLab Test Details:\n");
        stringBuilder.append(String.format("Lab Test ID:          %s\n", labTestID));
        stringBuilder.append(String.format("Type:                 %s\n", type));
        stringBuilder.append(String.format("Date:                 %s\n", dateStamp));
        stringBuilder.append(String.format("Status:               %s\n", status));
        stringBuilder.append(String.format("Remarks:              %s\n", remarks));
        stringBuilder.append(String.format("Cost:                 $ %.2f", cost));

        return stringBuilder.toString();
    }

    /**
     * Retrieves a list of all LabTest instances.
     *
     * @return a list containing all lab tests.
     */
    public static List<LabTest> getAllLabtests() {
        return instances;
    }

    /**
     * Gets the physician associated with this lab test.
     *
     * @return the physician.
     */
    public Physician getPhysician() {
        return physician;
    }

    /**
     * Sets the physician associated with this lab test.
     *
     * @param physician the physician to set.
     */
    public void setPhysician(Physician physician) {
        this.physician = physician;
    }

    /**
     * Gets the unique lab test ID.
     *
     * @return the lab test ID.
     */
    public int getLabTestID() {
        return labTestID;
    }

    /**
     * Sets the unique lab test ID.
     *
     * @param labTestID the lab test ID to set.
     */
    public void setLabTestID(int labTestID) {
        this.labTestID = labTestID;
    }

    /**
     * Gets the type of the lab test.
     *
     * @return the lab test type.
     */
    public LABTYPE getType() {
        return type;
    }

    /**
     * Sets the type of the lab test.
     *
     * @param type the lab test type to set.
     */
    public void setType(LABTYPE type) {
        this.type = type;
    }

    /**
     * Gets the date stamp of the lab test.
     *
     * @return the date stamp.
     */
    public Date getDateStamp() {
        return dateStamp;
    }

    /**
     * Sets the date stamp of the lab test.
     *
     * @param dateStamp the date stamp to set.
     */
    public void setDateStamp(Date dateStamp) {
        this.dateStamp = dateStamp;
    }

    /**
     * Gets the current status of the lab test.
     *
     * @return the lab test status a {@link STATUS} enum.
     */
    public VisitStatus getStatus() {
        return status;
    }

    /**
     * Sets the current status of the lab test.
     *
     * @param status the lab test status to set.
     */
    public void setStatus(VisitStatus status) {
        this.status = status;
    }

    /**
     * Gets the remarks associated with the lab test.
     *
     * @return the lab test remarks.
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * Sets the remarks for the lab test.
     *
     * @param remarks the remarks to set.
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * Gets the cost of the lab test.
     *
     * @return the lab test cost.
     */
    public Double getCost() {
        return cost;
    }

    /**
     * Sets the cost of the lab test.
     *
     * @param cost the cost to set.
     */
    public void setCost(Double cost) {
        this.cost = cost;
    }

    /**
     * Searches for a lab test by its unique ID.
     *
     * @param id the lab test ID to search for.
     * @return the LabTest with the matching ID, or null if no such lab test exists.
     */
    public static LabTest searchLabTestByID(int id) {
        for (LabTest labtest : instances) {
            if (labtest.getLabTestID() == id) {
                return labtest;
            }
        }
        return null;
    }
}
