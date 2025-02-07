import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Calendar;

public class Main {

    private static void displayAllPatient(List<Patient> pd) {
        System.out.printf("%-12s %-20s %-15s %-21s %-50s%n", "Patient ID", "Full Name", "Phone Number", "Nationality", "Billing Address");

        for (Patient p : pd) {
            System.out.printf("%-12s %-20s %-15s %-21s %-50s%n", p.getId(), p.getName(), p.getPhoneNum(), p.getNationality(), p.getBillAdd());
        }
    }

    // Generation of Test Data to be stored in csv file for testing purposed
    private static void testData(csvHandler c) {
        List<Patient> patientList = new ArrayList<>();
        List<Service> serviceList = new ArrayList<>();
        
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

        c.saveToFile(patientList, serviceList);
    }

    public static void main(String[] args) {
        csvHandler dh = new csvHandler();
        
        // Call testData() to generate test data
        // testData(dh);

        // Call setPatient() & setService() to get test data of Patient and Service
        List<Patient> patientList = dh.setPatient();
        List<Service> serviceList = dh.setService();
        
        // Give each patient variable name
        Patient p1 = patientList.get(0);
        Patient p2 = patientList.get(3);
        
        // Set each service to a variable name
        Service s1 = serviceList.get(0);
        Service s2 = serviceList.get(1);
        Service s3 = serviceList.get(2);
                                                                                                        

        // Creating invoices for patients
        List<Invoice> invoiceList = new ArrayList<>();
        
        int taxRateP1 = (p1.getNationality().equals("Singaporean") || p1.getNationality().equals("Permanent Resident")) ? 0 : 9;
        Invoice invoice1 = new Invoice("INV001", new Date(), p1, "Pay by next week");

        s1.setServiceDate(new Date());
        s1.setQuantity(1);
        s2.setServiceDate(new Date());
        s2.setQuantity(2);

        invoice1.addService(s1);
        invoice1.addService(s2);
        invoice1.setBillingDetails(10, taxRateP1);

        int taxRateP2 = (p2.getNationality().equals("Singaporean") || p2.getNationality().equals("Permanent Resident")) ? 0 : 9;
        Invoice invoice2 = new Invoice("INV002", new Date(), p2, "Immediate payment required");
        
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -2);
        Date date = cal.getTime();

        s1.setServiceDate(date);
        s1.setQuantity(1);
        s3.setServiceDate(date);
        s3.setQuantity(3);
        
        invoice2.addService(s1);
        invoice2.addService(s3);
        invoice2.setBillingDetails(15, taxRateP2);

        invoiceList.add(invoice1);
        invoiceList.add(invoice2);

        // Display invoices
        for (Invoice invoice : invoiceList) {
            invoice.printInvoice(); 
            System.out.println("\n");
        }
            
        dh.saveInvoicesToFile(invoiceList);
    }
}
