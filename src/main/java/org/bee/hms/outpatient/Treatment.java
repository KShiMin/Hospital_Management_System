package org.bee.hms.outpatient;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a treatment provided for an outpatient case. A treatment consists of a unique
 * treatment ID, a treatment name, status, start and end dates, notes, cost, and a list of procedures
 * associated with it.
 */
public class Treatment {
    /** Static counter to generate unique treatment IDs. */
    // private static int count = 0;

    /** Unique identifier for the treatment. */
    private int treatmentID;

    /** The name of the treatment. */
    private String treatmentName;

    /** Notes regarding the treatment. */
    private String notes;

    /** The cost of the treatment. */
    private Double cost;

    /** The list of procedures performed as part of the treatment. */
    private List<Procedure> procedures;


    /**
     * Constructs a new Treatment instance with the specified details.
     *
     * @param outpatientCase the outpatient case associated with the treatment.
     * @param treatmentName  the name of the treatment.
     * @param notes          notes regarding the treatment.
     * @param cost           the cost of the treatment.
     * @param procedures     a list of procedures associated with the treatment; if null,
     *                       an empty list is initialized.
     */
    public Treatment(String treatmentName, String notes, Double cost, List<Procedure> procedures) {
        // setTreatmentID(count++);
        this.treatmentName = treatmentName;
        this.notes = notes;
        this.cost = cost;
        this.procedures = (procedures != null) ? procedures : new ArrayList<>();

    }

    /**
     * Returns a formatted string representation of the treatment details.
     *
     * @return a string containing the treatment's details.
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n\nTreatment Details:\n");
        stringBuilder.append(String.format("Treatment ID:         %s\n", treatmentID));
        stringBuilder.append(String.format("Name:                 %s\n", treatmentName));
        stringBuilder.append(String.format("Notes:                %s\n", (notes.equals("") ? "null" : notes)));
        stringBuilder.append(String.format("Costs:                $ %s", cost));

        String string = stringBuilder.toString();
        return string;
    }

    /**
     * Gets the list of procedures associated with this treatment.
     *
     * @return a list of procedures.
     */
    public List<Procedure> getProcedures() {
        return procedures;
    }

    /**
     * Sets the list of procedures for this treatment.
     *
     * @param procedures a list of procedures to be associated with the treatment.
     */
    public void setProcedures(List<Procedure> procedures) {
        this.procedures = procedures;
    }


    /**
     * Gets the unique identifier for this treatment.
     *
     * @return the treatment ID.
     */
    public int getTreatmentID() {
        return treatmentID;
    }

    /**
     * Sets the unique identifier for this treatment.
     *
     * @param treatmentID the treatment ID to set.
     */
    public void setTreatmentID(int treatmentID) {
        this.treatmentID = treatmentID;
    }

    /**
     * Gets the name of the treatment.
     *
     * @return the treatment name.
     */
    public String getTreatmentName() {
        return treatmentName;
    }

    /**
     * Gets the notes associated with the treatment.
     *
     * @return the treatment notes.
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Sets the notes for the treatment.
     *
     * @param notes the treatment notes to set.
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * Gets the cost of the treatment.
     *
     * @return the treatment cost.
     */
    public Double getCost() {
        return cost;
    }

    /**
     * Sets the cost of the treatment.
     *
     * @param cost the treatment cost to set.
     */
    public void setCost(Double cost) {
        this.cost = cost;
    }

    /**
     * Adds a procedure to the treatment's list of procedures if it is not already present.
     *
     * @param procedure the procedure to add.
     */
    public void addProcedure(Procedure procedure) {
        if (!procedures.contains(procedure)) {
            procedures.add(procedure);
        }
    }
}
