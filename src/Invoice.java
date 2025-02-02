import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Invoice {
    // Attributes
    private String invoiceId;
    private Date billingDate;
    private String billingAddress;
    private String additionalNotes;

    // Constructor
    public Invoice(String invoiceId, Date billingDate, String billingAddress, String additionalNotes) {
        this.invoiceId = invoiceId;
        this.billingDate = billingDate;
        this.billingAddress = billingAddress;
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

    // Method to print invoice details
    public void printInvoice() {
        System.out.println(this.toString());
    }

    // toString method for displaying invoice details
    @Override
    public String toString() {
        return "Invoice Details:\n" +
                "Invoice ID: " + invoiceId + "\n" +
                "Billing Date: " + billingDate + "\n" +
                "Billing Address: " + billingAddress + "\n" +
                "Additional Notes: " + additionalNotes + "\n";
    }

    //for testing
    public static void main(String[] args) {
        // List to store invoices
        List<Invoice> invoiceList = new ArrayList<>();

        // Adding invoices
        Invoice invoice1 = new Invoice("INV12345", new Date(), "123 Main Street, City, Country", "Pay by end of the month.");
        Invoice invoice2 = new Invoice("INV12346", new Date(), "456 Elm Street, City, Country", "Pay within two weeks.");

        invoiceList.add(invoice1);
        invoiceList.add(invoice2);

        System.out.println("Invoices after adding:");
        for (Invoice invoice : invoiceList) {
            invoice.printInvoice();
        }

        // Removing an invoice by ID
        String idToRemove = "INV12345";
        invoiceList.removeIf(invoice -> invoice.getInvoiceId().equals(idToRemove));

        System.out.println("Invoices after removal:");
        for (Invoice invoice : invoiceList) {
            invoice.printInvoice();
        }
    }
}
