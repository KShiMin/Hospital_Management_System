import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Invoice {
    // Attributes
    private String invoiceId;
    private Date billingDate;
    private String billingAddress;
    private String additionalNotes;
    private double grandTotal;
    private int discount;
    private int taxRate;
    private Patient patient;

    // Constructor
    public Invoice(String invoiceId, Date billingDate, Patient patient, String additionalNotes) {
        this.invoiceId = invoiceId;
        this.billingDate = billingDate;
        this.patient = patient;
        this.billingAddress = patient.getBillAdd();
        this.additionalNotes = additionalNotes;
    }

    // Getters and Setters
    public String getInvoiceId() {
        return invoiceId;
    }
    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Date getBillingDate() {
        return billingDate;
    }

    public void setBillingDate(Date billingDate) {
        this.billingDate = billingDate;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getAdditionalNotes() {
        return additionalNotes;
    }
    public void setAdditionalNotes(String additionalNotes) {
        this.additionalNotes = additionalNotes;
    }

    public Patient getPatient() {
        return patient;
    }
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setBillingDetails(double grandTotal, int discount, int taxRate) {
        this.grandTotal = grandTotal;
        this.discount = discount;
        this.taxRate = taxRate;
    }

    // Calculation methods
    public double calculateDiscountAmount() {
        return (grandTotal * discount) / 100.0;
    }
    public double calculateTaxAmount() {
        double amountAfterDiscount = grandTotal - calculateDiscountAmount();
        return (amountAfterDiscount * taxRate) / 100.0;
    }
    public double calculateGrandTotal() {
        double amountAfterDiscount = grandTotal - calculateDiscountAmount();
        return amountAfterDiscount + calculateTaxAmount();
    }

    // Print neatly formatted invoice details
    public void printInvoice() {
        System.out.println("==============================");
        System.out.println("      Invoice Details     ");
        System.out.println("==============================");
        System.out.printf("Invoice ID: %-20s\n", invoiceId);
        System.out.printf("Billing Date: %-20s\n", billingDate);
        System.out.printf("Billing Address: %-20s\n", billingAddress);
        System.out.printf("Additional Notes: %-20s\n", additionalNotes);
        System.out.println("--------------------------------");
        System.out.printf("Patient Name: %-20s\n", patient.getName());
        System.out.printf("Phone Number: %-20s\n", patient.getPhoneNum());
        System.out.printf("Nationality: %-20s\n", patient.getNationality());
        System.out.printf("Grand Total (Before Discounts and Tax): $%.2f\n", grandTotal);
        System.out.printf("Discount (%d%%): -$%.2f\n", discount, calculateDiscountAmount());
        System.out.printf("Tax (%d%%): +$%.2f\n", taxRate, calculateTaxAmount());
        System.out.println("--------------------------------");
        System.out.printf("Grand Total: $%.2f\n", calculateGrandTotal());
        System.out.println("==============================");
    }

    // For testing
    public static void main(String[] args) {
        List<Invoice> invoiceList = new ArrayList<>();

        Patient patient1 = new Patient("P001", "John", "Doe", "123 Main Street, City, Country", "American", "12345678");
        Invoice invoice1 = new Invoice("INV12345", new Date(), patient1, "Pay by end of the month.");
        invoice1.setBillingDetails(1000, 10, 5);

        Patient patient2 = new Patient("P002", "Jane", "Smith", "456 Elm Street, City, Country", "British", "87654321");
        Invoice invoice2 = new Invoice("INV12346", new Date(), patient2, "Pay within two weeks.");
        invoice2.setBillingDetails(2000, 15, 8);


        invoiceList.add(invoice1);
        invoiceList.add(invoice2);

        System.out.println("Invoices after adding:");
        for (Invoice invoice : invoiceList) {
            invoice.printInvoice();
        }

        String idToRemove = "INV12345";
        invoiceList.removeIf(invoice -> invoice.getInvoiceId().equals(idToRemove));

        System.out.println("Invoices after removal:");
        for (Invoice invoice : invoiceList) {
            invoice.printInvoice();
        }
    }
}
