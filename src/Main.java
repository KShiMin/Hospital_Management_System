import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class Main {

    private static void displayAllPatient(List<Patient> pd) {
        System.out.printf("%-12s %-20s %-15s %-21s %-50s%n", "Patient ID", "Full Name", "Phone Number", "Nationality", "Billing Address");

        for (Patient p : pd) {
            System.out.printf("%-12s %-20s %-15s %-21s %-50s%n", p.getId(), p.getName(), p.getPhoneNum(), p.getNationality(), p.getBillAdd());
        }
    }

    private static void displayPayment(List<Payment> pd) {
        System.out.println("\n==== Payment Details ====");
        System.out.printf("%-15s %-15s %-15s%n", "Payment Method", "Payment Status", "Payment Due Date");

        for (Payment pm : pd) {
            System.out.printf("%-15s %-15s %-15s%n", pm.getPaymentMethod(), pm.getPaymentStatus(), pm.getDueDate());
        }
    }

    private static void testData(csvHandler c){
        List<Patient> patientList = new ArrayList<>();
        List<Service> serviceList = new ArrayList<>();
        List<Payment> paymentList = new ArrayList<>();

        Patient p1 = new Patient("P01", "Jack", "Neo", "BLK 19 MARSILING LANE #02-305 Singapore 730019", "81123456", "Singaporean");
        Patient p2 = new Patient("P02", "Mary", "Tan", "BLK 213 HOUGANG ST 21 #01-367 Singapore 530213", "92223546", "Singaporean");
        Patient p3 = new Patient("P03", "Tommy", "Arnold", "34 WHAMPOA WEST #05-15 Singapore 330034", "88833316", "Permanent Resident");
        Patient p4 = new Patient("P04", "Dennis", "Anderson", "3 COLEMAN STREET #18-22 Singapore 179804", "97234569", "Foreigner");
        
        Service s1 = new Service("svc123", "Doctor Consult", null, 0, 30.00);
        Service s2 = new Service("svc456", "Blood Test", null, 0, 50.00);
        Service s3 = new Service("svc789", "Medication", null, 0, 15.00);

        patientList.add(p1);
        patientList.add(p2);
        patientList.add(p3);
        patientList.add(p4);

        serviceList.add(s1);
        serviceList.add(s2);
        serviceList.add(s3);

        // Creating Payment
        Payment payment1 = new Payment("Credit Card", "Pending", LocalDate.now(), p1, s1);
        Payment payment2 = new Payment("Cash", "Paid", LocalDate.now(), p2, s2);
        Payment payment3 = new Payment("Insurance", "Pending", LocalDate.now(), p3, s3);

        // Store Payments in Payment List
        paymentList.add(payment1);
        paymentList.add(payment2);
        paymentList.add(payment3);

        // Save paymentList in file
        c.saveToFile(patientList, serviceList, paymentList);

        // Display all payments stored in payment list
        displayPayment(paymentList);

    }

    public static void main(String[] args) {
        
        csvHandler dh = new csvHandler();
        
        // Test
        // testData(dh);
        // List<Patient> patientList = dh.setPatient();
        // List<Service> servicesList = dh.setService();
        // servicesList.get(0).setServiceDate(new Date());
        // servicesList.get(0).setQuantity(1);
        // servicesList.get(0).displayService();
        // displayAllPatient(patientList);
        // System.out.println("\n");
        // patientList.get(0).displayPatientInfo();

        
    }
}
