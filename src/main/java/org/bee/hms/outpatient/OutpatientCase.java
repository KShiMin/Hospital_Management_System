package org.bee.hms.outpatient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.print.Doc;

import org.bee.hms.billing.Bill;
import org.bee.hms.humans.DEPARTMENT;
import org.bee.hms.humans.Doctor;
import org.bee.hms.humans.Patient;
import org.bee.hms.medical.ConsultationType;
import org.bee.hms.medical.Medication;
import org.bee.hms.medical.VisitStatus;


/**
 * Represents an outpatient case in a hospital system.
 * This class contains details such as the patient, physician, diagnosis,
 * prescribed medications, treatments, billing and lab tests associated with the
 * case.
 */
public class OutpatientCase {

    /** Counter for generating unique outpatient case IDs. */
    private static int count = 0;

    /** Unique identifier for the outpatient case. */
    private int outpatientCaseID;

    /** Date of the outpatient appointment. */
    private Date appointmentDate;

    /** Medical history of the patient. */
    private String medicalHistory;

    /** Type of the outpatient case. */
    private ConsultationType type;

    /** Current status of the outpatient case. */
    private VisitStatus status;

    /** Department handling the outpatient case. */
    private DEPARTMENT department;

    /** Diagnosis given for the outpatient case. */
    private String diagnosis;

    /** Reason for the patient's visit. */
    private String visitReason;

    /** List of current medications prescribed to the patient. */
    private ArrayList<Medication> currentMedications;

    /** List of prescriptions assigned to the patient. */
    private ArrayList<Medication> prescriptions;

    /** Follow-up date for the patient. */
    private Date followUpDate;

    /** Instructions given for the outpatient case. */
    private String instructions;

    /** Patient associated with this outpatient case. */
    private Patient patient;

    /** Physician handling the outpatient case. */
    private Doctor physician;

    /** Billing information for the outpatient case. */
    private Bill billing;

    /** List of treatments assigned to the patient. */
    private ArrayList<Treatment> treatments;

    /** List of lab tests ordered for the patient. */
    private ArrayList<LabTest> labtests;

    /** List of all outpatient case instances. */
    private static List<OutpatientCase> instances = new ArrayList<>();

    /**
     * Retrieves all outpatient cases.
     * 
     * @return A list of all outpatient cases.
     */
    public static List<OutpatientCase> getAllOutpatientCases() {
        return instances;
    }

    /**
     * Returns a string representation of the outpatient case details.
     * 
     * @return formatted outpatient case details
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n\nOutpatient Case Details:\n");
        stringBuilder.append(String.format("Outpatient Case ID:   %s\n", outpatientCaseID));
        stringBuilder.append(String.format("Appointment Date:     %s\n", appointmentDate));
        stringBuilder.append(String.format("Medical History:      %s\n", medicalHistory));
        stringBuilder.append(String.format("Type:                 %s\n", type));
        stringBuilder.append(String.format("Status:               %s\n", status));
        stringBuilder.append(String.format("Department:           %s\n", department));
        stringBuilder.append(String.format("Diagnosis:            %s\n", diagnosis));
        stringBuilder.append(String.format("Visit Reason:         %s\n", visitReason));
        stringBuilder.append(String.format("\nCurrent Medications:  %s\n", getCurrentMedicationsString()));
        stringBuilder.append(String.format("\nPrescriptions:        %s\n", getPrescriptionsString()));
        stringBuilder.append(String.format("Follow-Up Date:       %s\n", followUpDate));
        stringBuilder.append(String.format("Instructions:         %s\n", instructions));
        stringBuilder.append(
                String.format("Patient ID:           %s\n", (patient != null ? patient.getPatientID() : "N/A")));
        stringBuilder.append(
                String.format("Physician ID:         %s\n", (physician != null ? physician.getPhysicianID() : "N/A")));
        stringBuilder.append(String.format("Billing:              %s\n", billing));
        stringBuilder.append(String.format("Treatment:            %s\n", getTreatmentsString()));
        stringBuilder.append(String.format("Lab Tests:            %s", getLabTestsString()));

        String string = stringBuilder.toString();
        return string;
    }

    /**
     * Constructs a default outpatient case.
     */
    public OutpatientCase() {
    }

    /**
     * Constructs an outpatient case with specified details.
     * 
     * @param appointmentDate    Date of the appointment.
     * @param medicalHistory     Patient's medical history.
     * @param type               Type of outpatient case.
     * @param status             Status of the case.
     * @param department         Department handling the case.
     * @param diagnosis          Diagnosis of the patient.
     * @param visitReason        Reason for the visit.
     * @param currentMedications List of current medications.
     * @param prescriptions      List of prescribed medications.
     * @param followUpDate       Follow-up appointment date.
     * @param instructions       Special instructions for the patient.
     * @param patient            The patient associated with the case.
     * @param physician          The physician handling the case.
     * @param billing            Billing information for the case.
     * @param treatments         List of treatments provided.
     * @param labtests           List of lab tests conducted.
     */
    public OutpatientCase(Date appointmentDate, String medicalHistory,
            CASETYPE type, VisitStatus status, DEPARTMENT department, String diagnosis,
            String visitReason, ArrayList<Medication> currentMedications, ArrayList<Medication> prescriptions,
            Date followUpDate, String instructions, Patient patient, Doctor physician, Bill billing,
            ArrayList<Treatment> treatments, ArrayList<LabTest> labtests) {
        setOutpatientCaseID(count++);
        this.appointmentDate = appointmentDate;
        this.medicalHistory = medicalHistory;
        this.type = type;
        this.status = status;
        this.department = department;
        this.diagnosis = diagnosis;
        this.visitReason = visitReason;
        this.currentMedications = (currentMedications != null) ? currentMedications : new ArrayList<>();
        this.prescriptions = (prescriptions != null) ? prescriptions : new ArrayList<>();
        this.followUpDate = followUpDate;
        this.instructions = instructions;
        this.patient = patient;
        this.physician = physician;
        this.billing = billing;
        this.treatments = treatments;
        this.labtests = labtests;
        instances.add(this);
    }

    /**
     * Displays the outpatient case details in the console.
     */
    public void displayOutpatientCaseInfo() {
        System.out.println("Outpatient Case ID: " + this.outpatientCaseID);
        System.out.println("Appointment Date: " + this.appointmentDate);
        System.out.println("Patient ID: " + this.patient.getPatientID());
        System.out.println("Patient Name: " + this.patient.getName());
        System.out.println("Medical History: " + this.medicalHistory);
        System.out.println("Current Medications: " + this.currentMedications);
        System.out.println("Assigned Doctor ID: " + this.physician.getPhysicianID());
        System.out.println("Assigned Doctor Name: " + this.physician.getName());
        System.out.println("Visit Type: " + this.visitReason);
        System.out.println("Diagnosis: " + this.diagnosis);
        System.out.println("Treatments: " + treatments);
        System.out.println("Prescriptions: " + prescriptions);
        System.out.println("Lab Tests: " + labtests);
        System.out.println("Follow Up Date: " + this.followUpDate);
        System.out.println("Instructions: " + this.instructions);
        System.out.println("Billing: " + this.billing);
    }

    /**
     * Retrieves the patient associated with the case.
     * 
     * @return The patient.
     */
    public Patient getPatient() {
        return patient;
    }

    /**
     * Sets the patient associated with the case.
     * 
     * @param patient The patient to set.
     */
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    /**
     * Retrieves the physician associated with this outpatient case.
     * 
     * @return the assigned Physician
     */
    public Doctor getPhysician() {
        return physician;
    }

    /**
     * Sets the physician for this outpatient case.
     * 
     * @param physician the physician to assign
     */
    public void setPhysician(Doctor physician) {
        this.physician = physician;
    }

    /**
     * Retrieves the billing record associated with this outpatient case.
     * 
     * @return the corresponding Billing object
     */
    public Bill getBilling() {
        for (Bill bill : Billing.getAllBillings()) {
            if (bill.getOutpatientCase().getOutpatientCaseID() == outpatientCaseID) {
                billing = bill;
            }
        }
        return billing;
    }

    /**
     * Sets the billing record for this outpatient case.
     * 
     * @param billing the Billing object to assign
     */
    public void setBilling(Bill billing) {
        this.billing = billing;
    }

    /**
     * Retrieves the unique outpatient case ID.
     * 
     * @return the outpatient case ID
     */
    public int getOutpatientCaseID() {
        return outpatientCaseID;
    }

    /**
     * Sets the unique outpatient case ID.
     * 
     * @param outpatientCaseID the case ID to assign
     */
    public void setOutpatientCaseID(int outpatientCaseID) {
        this.outpatientCaseID = outpatientCaseID;
    }

    /**
     * Retrieves the appointment date.
     * 
     * @return the appointment date
     */
    public Date getAppointmentDate() {
        return appointmentDate;
    }

    /**
     * Sets the appointment date.
     * 
     * @param appointmentDate the date to assign
     */
    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    /**
     * Retrieves the medical history of the patient.
     * 
     * @return the medical history as a string
     */
    public String getMedicalHistory() {
        return medicalHistory;
    }

    /**
     * Sets the medical history of the patient.
     * 
     * @param medicalHistory the medical history to assign
     */
    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    /**
     * Retrieves the case type.
     * 
     * @return the case type
     */
    public CASETYPE getType() {
        return type;
    }

    /**
     * Sets the case type.
     * 
     * @param type the case type to assign
     */
    public void setType(CASETYPE type) {
        this.type = type;
    }

    /**
     * Retrieves the status of the outpatient case.
     * 
     * @return the case status
     */
    public VisitStatus getStatus() {
        return status;
    }

    /**
     * Sets the status of the outpatient case.
     * 
     * @param status the status to assign
     */
    public void setStatus(VisitStatus status) {
        this.status = status;
    }

    /**
     * Retrieves the department handling the outpatient case.
     * 
     * @return the department
     */
    public DEPARTMENT getDepartment() {
        return department;
    }

    /**
     * Sets the department handling the outpatient case.
     * 
     * @param department the department to assign
     */
    public void setDepartment(DEPARTMENT department) {
        this.department = department;
    }

    /**
     * Retrieves the diagnosis of the outpatient case.
     *
     * @return the diagnosis as a string
     */
    public String getDiagnosis() {
        return diagnosis;
    }

    /**
     * Sets the diagnosis of the outpatient case.
     *
     * @param diagnosis the diagnosis to assign
     */
    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    /**
     * Retrieves the reason for the patient's visit.
     *
     * @return the visit reason as a string
     */
    public String getVisitReason() {
        return visitReason;
    }

    /**
     * Sets the reason for the patient's visit.
     *
     * @param visitReason the visit reason to assign
     */
    public void setVisitReason(String visitReason) {
        this.visitReason = visitReason;
    }

    /**
     * Retrieves the current medications of the patient.
     *
     * @return a list of current medications
     */
    public List<Medication> getCurrentMedications() {
        return currentMedications;
    }

    /**
     * Retrieves the prescriptions assigned to the patient.
     *
     * @return a list of prescriptions
     */
    public List<Medication> getPrescriptions() {
        return prescriptions;
    }

    /**
     * Retrieves the current medications of the patient as a formatted string.
     * 
     * @return a string representation of current medications, or "null" if none
     *         exist
     */
    public String getCurrentMedicationsString() {
        String currentMedList = "";
        if (currentMedications != null) {
            for (Medication med : currentMedications) {
                currentMedList += med;
            }
        } else {
            return "null";
        }
        return currentMedList;
    }

    /**
     * Retrieves the prescriptions assigned to the patient as a formatted string.
     * 
     * @return a string representation of prescriptions, or "null" if none exist
     */
    public String getPrescriptionsString() {
        String prescriptionList = "";
        if (prescriptions != null) {
            for (Medication prescription : prescriptions) {
                prescriptionList += prescription;
            }
        } else {
            return "null";
        }
        return prescriptionList;
    }

    /**
     * Clears all prescriptions for the patient.
     */
    public void clearAllPrescriptions() {
        this.prescriptions = new ArrayList<Medication>();
    }

    /**
     * Removes a drug from both current medications and prescriptions.
     * 
     * @param drug the drug to remove
     */
    public void removeDrug(Medication drug) {
        currentMedications.remove(drug);
        prescriptions.remove(drug);
    }

    /**
     * Retrieves all drugs associated with the patient, combining current
     * medications and prescriptions.
     * 
     * @return a list of all drugs
     */
    public List<Medication> getAllDrugs() {
        List<Medication> allDrugs = new ArrayList<>();
        allDrugs.addAll(currentMedications);
        allDrugs.addAll(prescriptions);
        return allDrugs;
    }

    /**
     * Adds a drug to the patient's prescriptions.
     * 
     * @param drug the drug to add
     */
    public void addDrugToPrescriptions(Medication drug) {
        this.prescriptions.add(drug);
    }

    /**
     * Retrieves the follow-up date for the outpatient case.
     * 
     * @return the follow-up date
     */
    public Date getFollowUpDate() {
        return followUpDate;
    }

    /**
     * Sets the follow-up date for the outpatient case.
     * 
     * @param followUpDate the follow-up date to assign
     */
    public void setFollowUpDate(Date followUpDate) {
        this.followUpDate = followUpDate;
    }

    /**
     * Retrieves the instructions associated with this outpatient case.
     * 
     * @return the instructions
     */
    public String getInstructions() {
        return instructions;
    }

    /**
     * Sets the instructions for this outpatient case.
     * 
     * @param instructions the instructions to assign
     */
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    /**
     * Retrieves the list of treatments for this outpatient case.
     * 
     * @return a list of treatments
     */
    public ArrayList<Treatment> getTreatments() {
        return treatments;
    }

    /**
     * Retrieves the treatments as a formatted string.
     * 
     * @return a string representation of treatments, or "null" if none exist
     */
    public String getTreatmentsString() {
        String treatmentList = "";
        if (treatments != null) {
            for (Treatment treatment : treatments) {
                treatmentList += treatment;
            }
        } else if (treatments == null || treatments.isEmpty()) {
            return "null";
        }
        if (treatmentList.equals("")) {
            return "null";
        }
        return treatmentList;
    }

    /**
     * Sets the treatments for this outpatient case.
     * 
     * @param treatments the list of treatments to assign
     */
    public void setTreatments(ArrayList<Treatment> treatments) {
        this.treatments = treatments;
    }

    /**
     * Adds a treatment to this outpatient case if it is not already included.
     * 
     * @param treatment the treatment to add
     */
    public void addTreatment(Treatment treatment) {
        if (!treatments.contains(treatment)) {
            treatments.add(treatment);
        }
    }

    /**
     * Removes a treatment from this outpatient case.
     * 
     * @param treatment the treatment to remove
     */
    public void removeTreatment(Treatment treatment) {
        treatments.remove(treatment);
    }

    /**
     * Retrieves the list of lab tests for this outpatient case.
     * 
     * @return a list of lab tests
     */
    public List<LabTest> getLabtests() {
        return labtests;
    }

    /**
     * Sets the list of lab tests for this outpatient case.
     * 
     * @param labtests the list of lab tests to assign
     */
    public void setLabtests(ArrayList<LabTest> labtests) {
        this.labtests = labtests;
    }

    /**
     * Retrieves the lab tests as a formatted string.
     * 
     * @return a string representation of lab tests, or "null" if none exist
     */
    public String getLabTestsString() {
        String labtestList = "";
        if (labtests != null) {
            for (LabTest labtest : labtests) {
                labtestList += labtest;
            }
        } else if (labtests == null || labtests.isEmpty()) {
            return "null";
        }
        if (labtestList.equals("")) {
            return "null";
        }
        return labtestList;
    }

    /**
     * Adds a lab test to this outpatient case if it is not already included.
     * 
     * @param labTest the lab test to add
     */
    public void addLabTest(LabTest labTest) {
        if (!labtests.contains(labTest)) {
            labtests.add(labTest);
        }
    }

    /**
     * Removes a lab test from this outpatient case.
     * 
     * @param labTest the lab test to remove
     */
    public void removeLabTest(LabTest labTest) {
        labtests.remove(labTest);
    }

    /**
     * Searches for an outpatient case by ID.
     * 
     * @param outpatientCaseID The ID to search for.
     * @return The matching outpatient case or null if not found.
     */
    public static OutpatientCase searchOutpatientCaseByID(int outpatientCaseID) {
        for (OutpatientCase caseInstance : instances) {
            if (caseInstance.getOutpatientCaseID() == (outpatientCaseID)) {
                return caseInstance;
            }
        }
        return null;
    }

    /**
     * Retrieves all outpatient cases assigned to a specific physician.
     * 
     * @param physician The physician whose cases are to be retrieved.
     * @return A list of outpatient cases associated with the given physician.
     */
    public static List<OutpatientCase> getAllCasesByPhysician(Doctor physician) {
        List<OutpatientCase> allOutpatientCases = new ArrayList<>();
        for (OutpatientCase i : instances) {
            if (i.getPhysician().equals(physician)) {
                allOutpatientCases.add(i);
            }
        }
        return allOutpatientCases;
    }
}