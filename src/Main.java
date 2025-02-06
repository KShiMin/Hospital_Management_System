import java.util.ArrayList;
import java.util.List;

public class Main {

    private static void displayAllPatient(List<Patient> pd) {
        System.out.printf("%-12s %-20s %-15s %-21s %-50s%n", "Patient ID", "Full Name", "Phone Number", "Nationality", "Billing Address");

        for (Patient p : pd) {
            System.out.printf("%-12s %-20s %-15s %-21s %-50s%n", p.getId(), p.getName(), p.getPhoneNum(), p.getNationality(), p.getBillAdd());
        }
    }

    private static void testPatientData(csvHandler c){
        List<Patient> patientList = new ArrayList<>();
        
        Patient p1 = new Patient("P01", "Jack", "Neo", "BLK 19 MARSILING LANE #02-305 Singapore 730019", "81123456", "Singaporean");
        Patient p2 = new Patient("P02", "Mary", "Tan", "BLK 213 HOUGANG ST 21 #01-367 Singapore 530213", "92223546", "Singaporean");
        Patient p3 = new Patient("P03", "Tommy", "Arnold", "34 WHAMPOA WEST #05-15 Singapore 330034", "88833316", "Permanent Resident");
        Patient p4 = new Patient("P04", "Dennis", "Anderson", "3 COLEMAN STREET #18-22 Singapore 179804", "97234569", "Foreigner");
        
        patientList.add(p1);
        patientList.add(p2);
        patientList.add(p3);
        patientList.add(p4);

        c.savePatient(patientList);
    }

    public static void main(String[] args) {
        
        csvHandler dh = new csvHandler();
        
        // Generation of test datas
        // testPatientData(dh);
        List<Patient> patientList = dh.setPatient();

        displayAllPatient(patientList);
        System.out.println("\n");
        patientList.get(0).displayPatientInfo();
        
    }
}
