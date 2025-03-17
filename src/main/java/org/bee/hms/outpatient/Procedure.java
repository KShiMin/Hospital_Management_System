package org.bee.hms.outpatient;

import java.util.Date;
import java.util.List;

import org.bee.hms.medical.VisitStatus;

import java.util.ArrayList;

/**
 * Represents a procedure performed as part of a treatment. A procedure includes
 * details such as a unique procedure ID, the associated treatment, procedure type,
 * performing physician, room number, status, remarks, date stamp, and cost.
 */
public class Procedure {
    /** Static counter used to generate unique procedure IDs. */
    private static int count = 0;
    
    /** Unique identifier for the procedure. */
    private int procedureID;
    
    /** The treatment associated with this procedure. */
    private Treatment treatment;
    
    /** The type of procedure. */
    private PROCEDURE_TYPE type;
    
    /** The physician who performs the procedure. */
    private Physician physician;
    
    /** The room number where the procedure is performed. */
    private String roomNumber;
    
    /** The current status of the procedure. */
    private VisitStatus status;
    
    /** Remarks or comments about the procedure. */
    private String remarks;
    
    /** The date and time when the procedure was performed. */
    private Date dateStamp;

    
    /** The cost associated with the procedure. */
    private Double cost;
    
    /**
     * A static list that stores all Procedure instances.
     */
    private static List<Procedure> instances = new ArrayList<>();

    /**
     * Constructs a new Procedure instance with the specified details.
     *
     * @param treatment   the treatment associated with the procedure.
     * @param type        the type of the procedure.
     * @param physician   the physician performing the procedure.
     * @param roomNumber  the room number where the procedure is performed.
     * @param status      the current status of the procedure.
     * @param remarks     any remarks regarding the procedure.
     * @param datestamp   the date and time of the procedure.
     * @param cost        the cost of the procedure.
     */
    public Procedure(Treatment treatment, PROCEDURE_TYPE type, Physician physician,
            String roomNumber, VisitStatus status, String remarks, Date datestamp, Double cost) {
        setProcedureID(count++);
        this.treatment = treatment;
        this.type = type;
        this.physician = physician;
        this.roomNumber = roomNumber;
        this.status = status;
        this.remarks = remarks;
        this.dateStamp = datestamp;
        this.cost = cost;

        instances.add(this);
    }

    /**
     * Returns a formatted string representation of the procedure details.
     *
     * @return a string containing the procedure details.
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n\nProcedure Details:\n");
        stringBuilder.append(String.format("Procedure ID:         %s\n", procedureID));
        stringBuilder.append(String.format("Type:                 %s\n", type));
        stringBuilder.append(String.format("Room:                 %s\n", roomNumber));
        stringBuilder.append(String.format("Status:               %s\n", status));
        stringBuilder.append(String.format("Remarks:              %s\n", (remarks.equals("") ? "null" : remarks)));
        stringBuilder.append(String.format("Date:                 %s\n", dateStamp));
        stringBuilder.append(String.format("Cost:                 $ %.2f", cost));

        String string = stringBuilder.toString();
        return string;
    }

    /**
     * Retrieves a list of all Procedure instances.
     *
     * @return a list containing all procedures.
     */
    public static List<Procedure> getAllProcedures() {
        return instances;
    }

    /**
     * Gets the unique procedure ID.
     *
     * @return the procedure ID.
     */
    public int getProcedureID() {
        return procedureID;
    }

    /**
     * Sets the unique procedure ID.
     *
     * @param procedureID the procedure ID to set.
     */
    public void setProcedureID(int procedureID) {
        this.procedureID = procedureID;
    }

    /**
     * Gets the treatment associated with this procedure.
     *
     * @return the treatment.
     */
    public Treatment getTreatment() {
        return treatment;
    }

    /**
     * Sets the treatment associated with this procedure.
     *
     * @param treatment the treatment to set.
     */
    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }

    /**
     * Gets the type of the procedure.
     *
     * @return the procedure type.
     */
    public PROCEDURE_TYPE getType() {
        return type;
    }

    /**
     * Sets the type of the procedure.
     *
     * @param type the procedure type to set.
     */
    public void setType(PROCEDURE_TYPE type) {
        this.type = type;
    }

    /**
     * Gets the physician who performed the procedure.
     *
     * @return the physician.
     */
    public Physician getPhysician() {
        return physician;
    }

    /**
     * Sets the physician who performed the procedure.
     *
     * @param physician the physician to set.
     */
    public void setPhysician(Physician physician) {
        this.physician = physician;
    }

    /**
     * Gets the room number where the procedure was performed.
     *
     * @return the room number.
     */
    public String getRoomNumber() {
        return roomNumber;
    }

    /**
     * Sets the room number where the procedure is performed.
     *
     * @param roomNumber the room number to set.
     */
    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    /**
     * Gets the current status of the procedure.
     *
     * @return the procedure status a {@link STATUS} enum.
     */
    public VisitStatus getStatus() {
        return status;
    }

    /**
     * Sets the current status of the procedure.
     *
     * @param status the procedure status to set.
     */
    public void setStatus(VisitStatus status) {
        this.status = status;
    }

    /**
     * Gets the remarks associated with the procedure.
     *
     * @return the procedure remarks.
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * Sets the remarks for the procedure.
     *
     * @param remarks the remarks to set.
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * Gets the date stamp of the procedure.
     *
     * @return the date stamp.
     */
    public Date getDateStamp() {
        return dateStamp;
    }

    /**
     * Sets the date stamp of the procedure.
     *
     * @param dateStamp the date stamp to set.
     */
    public void setDateStamp(Date dateStamp) {
        this.dateStamp = dateStamp;
    }

    /**
     * Gets the cost of the procedure.
     *
     * @return the procedure cost.
     */
    public Double getCost() {
        return cost;
    }

    /**
     * Sets the cost of the procedure.
     *
     * @param cost the cost to set.
     */
    public void setCost(Double cost) {
        this.cost = cost;
    }

    /**
     * Searches for a procedure by its unique ID.
     *
     * @param id the procedure ID to search for.
     * @return the Procedure with the matching ID, or null if no such procedure exists.
     */
    public static Procedure searchProcedureByID(int id) {
        for (Procedure procedure : instances) {
            if (procedure.getProcedureID() == id) {
                return procedure;
            }
        }
        return null;
    }
}
