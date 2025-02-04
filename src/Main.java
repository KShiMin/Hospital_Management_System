import java.util.Date;

public class Main {
    public static void main(String[] args) {
        // Creating an invoice with discount and tax details
        Invoice invoice = new Invoice(
                "INV12345",
                new Date(),
                "123 Main Street, Tampines, SG",
                "Payment due in 31 days."
        );

        // Setting billing details
        invoice.setBillingDetails(1000, 10, 5);

        // Display invoice details including discount and tax
        invoice.printInvoice();
    }
}
