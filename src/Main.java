import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {

    // Save patient data to file
    private static void savePatient(List<Patient> pb) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("./data/patients.csv", "UTF-8");
            for (Patient p : pb) {
                String patientStr = String.format("%s,%s,%s,%s,%s", p.getId(), p.getName(), p.getBillAdd(), p.getNationality(), p.getPhoneNum());
                writer.println(patientStr);
            }
        } catch (IOException e) {
            System.out.println("Error saving patient: " + e.getMessage());
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    // Read patient data from file
    private static void readPatient() {
        try (BufferedReader br = new BufferedReader(new FileReader("./data/patients.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split(",");
                for (String w : words) {
                    System.out.println(w);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        List<Patient> patientList = new ArrayList<>();

        // Creating patient objects
        Patient p1 = new Patient("P01", "Jack", "Neo", "BLK 19 MARSILING LANE #02-305 Singapore 730019", "Singaporean", "81123456");
        Patient p2 = new Patient("P02", "May", "Tan", "BLK 213 HOUGANG ST 21 #01-367 Singapore 530213", "Singaporean", "92223546");
        Patient p3 = new Patient("P03", "Tommy", "Arnold", "34 WHAMPOA WEST #05-15 Singapore 330034", "Permanent Resident", "88833316");
        Patient p4 = new Patient("P04", "Dennis", "Anderson", "3 COLEMAN STREET #18-22 Singapore 179804", "Foreigner", "97234569");

        patientList.add(p1);
        patientList.add(p2);
        patientList.add(p3);
        patientList.add(p4);

        // Save and read patient data
        savePatient(patientList);
        readPatient();

        // Creating invoices for patients
        List<Invoice> invoiceList = new ArrayList<>();

        int taxRateP1 = (p1.getNationality().equals("Singaporean") || p1.getNationality().equals("Permanent Resident")) ? 0 : 9;
        Invoice invoice1 = new Invoice("INV001", new Date(), p1, "Pay by next week");
        invoice1.addService(new Service("svc123", "Doctor Consult", new Date(), 1, 30.00));
        invoice1.setBillingDetails(10, taxRateP1);

        int taxRateP2 = (p2.getNationality().equals("Singaporean") || p2.getNationality().equals("Permanent Resident")) ? 0 : 9;
        Invoice invoice2 = new Invoice("INV002", new Date(), p2, "Immediate payment required");
        invoice2.addService(new Service("svc456", "Blood Test", new Date(), 2, 50.00));
        invoice2.addService(new Service("svc789", "Medication", new Date(), 1, 15.00));
        invoice2.setBillingDetails(15, taxRateP2);

        invoiceList.add(invoice1);
        invoiceList.add(invoice2);

        // Display invoices
        for (Invoice invoice : invoiceList) {
            invoice.printInvoice();
        }
    }
}
