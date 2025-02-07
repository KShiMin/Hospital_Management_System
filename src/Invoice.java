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
    private Patient patient;
    private List<Service> services;

    // Constructor
    public Invoice(String invoiceId, Date billingDate, Patient patient, String additionalNotes) {
        this.invoiceId = invoiceId;
        this.billingDate = billingDate;
        this.patient = patient;
        this.services = new ArrayList<>();
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

    public void addService(Service service) {
        services.add(service);
    }

    public double calculateTotalAmount() {
        totalAmount = 0;
        for (Service service : services) {
            totalAmount += service.calculatePrice();
        }
        return totalAmount;
    }

    public void setBillingDetails(int discount, int taxRate) {
        this.discount = discount;
        this.taxRate = taxRate;
    }

    public int getDiscount() {
        return discount;
    }

    public int getTaxRate() {
        return taxRate;
    }

    // Calculation methods
    public double calculateDiscountAmount() {
        return (totalAmount * discount) / 100.0;
    }
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
        System.out.println("--------------------------------");
        int index = 0;
        for (Service service : services) {
            System.out.printf("%d. \n", index+1);
            System.out.printf("Service Code: %-20s\n", service.getServiceCode());
            System.out.printf("Service Description: %-20s\n", service.getServiceDescript());
            System.out.printf("Service Date: %-20s\n", service.getServiceDate());
            System.out.printf("Quantity: %d\n", service.getQuantity());
            System.out.printf("Unit Price: %.2f\n", service.getUnitPrice());
            index ++;
        }
        System.out.println("--------------------------------");
        System.out.printf("Total Amount (Before Discounts and Tax): $%.2f\n", calculateTotalAmount());
        System.out.printf("Discount (%d%%): -$%.2f\n", discount, calculateDiscountAmount());
        System.out.printf("Tax (%d%%): +$%.2f\n", taxRate, calculateTaxAmount());
        System.out.println("--------------------------------");
        System.out.printf("Grand Total: $%.2f\n", calculateGrandTotal());
        System.out.println("==============================");
    }
}
