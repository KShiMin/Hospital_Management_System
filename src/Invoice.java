import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Invoice {
    // Attributes
    private String invoiceId;
    private Date billingDate;
    private String billingAddress;
    private String additionalNotes;
    private double totalAmount;
    private int discount;
    private int taxRate;

    // Constructor
    public Invoice(String invoiceId, Date billingDate, String billingAddress, String additionalNotes) {
        this.invoiceId = invoiceId;
        this.billingDate = billingDate;
        this.billingAddress = billingAddress;
        this.additionalNotes = additionalNotes;
    }

    // Getters and Setters
    public String getInvoiceId() { return invoiceId; }
    public void setInvoiceId(String invoiceId) { this.invoiceId = invoiceId; }

    public Date getBillingDate() { return billingDate; }
    public void setBillingDate(Date billingDate) { this.billingDate = billingDate; }

    public String getBillingAddress() { return billingAddress; }
    public void setBillingAddress(String billingAddress) { this.billingAddress = billingAddress; }

    public String getAdditionalNotes() { return additionalNotes; }
    public void setAdditionalNotes(String additionalNotes) { this.additionalNotes = additionalNotes; }

    public void setBillingDetails(double totalAmount, int discount, int taxRate) {
        this.totalAmount = totalAmount;
        this.discount = discount;
        this.taxRate = taxRate;
    }

    // Calculation methods
    public double calculateDiscountAmount() { return (totalAmount * discount) / 100.0; }
    public double calculateTaxAmount() {
        double amountAfterDiscount = totalAmount - calculateDiscountAmount();
        return (amountAfterDiscount * taxRate) / 100.0;
    }
    public double calculateGrandTotal() {
        double amountAfterDiscount = totalAmount - calculateDiscountAmount();
        return amountAfterDiscount + calculateTaxAmount();
    }

    // Print neatly formatted invoice details
    public void printInvoice() {
        System.out.println("==============================");
        System.out.println("          Invoice Details     ");
        System.out.println("==============================");
        System.out.printf("Invoice ID: %-20s\n", invoiceId);
        System.out.printf("Billing Date: %-20s\n", billingDate);
        System.out.printf("Billing Address: %-20s\n", billingAddress);
        System.out.printf("Additional Notes: %-20s\n", additionalNotes);
        System.out.println("--------------------------------");
        System.out.printf("Total Amount: $%.2f\n", totalAmount);
        System.out.printf("Discount (%d%%): -$%.2f\n", discount, calculateDiscountAmount());
        System.out.printf("Tax (%d%%): +$%.2f\n", taxRate, calculateTaxAmount());
        System.out.println("--------------------------------");
        System.out.printf("Grand Total: $%.2f\n", calculateGrandTotal());
        System.out.println("==============================");
    }

    // For testing
    public static void main(String[] args) {
        List<Invoice> invoiceList = new ArrayList<>();

        Invoice invoice1 = new Invoice("INV12345", new Date(), "123 Main Street, City, Country", "Pay by end of the month.");
        invoice1.setBillingDetails(1000, 10, 5);

        Invoice invoice2 = new Invoice("INV12346", new Date(), "456 Elm Street, City, Country", "Pay within two weeks.");
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
