import java.util.Date;

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

    // toString() method for displaying invoice details
    @Override
    public String toString() {
        return "Invoice{" +
                "Invoice ID='" + invoiceId + '\'' +
                ", Billing Date=" + billingDate +
                ", Billing Address='" + billingAddress + '\'' +
                ", Additional Notes='" + additionalNotes + '\'' +
                '}';
    }

    // Example method to print a formatted invoice
    public void printInvoice() {
        System.out.println("==== Invoice Details ====");
        System.out.println("Invoice ID: " + invoiceId);
        System.out.println("Billing Date: " + billingDate);
        System.out.println("Billing Address: " + billingAddress);
        System.out.println("Additional Notes: " + additionalNotes);
        System.out.println("==========================");
    }
}
