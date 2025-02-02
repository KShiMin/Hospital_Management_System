import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Invoice invoice = new Invoice(
                "INV12345",
                new Date(),
                "123 Main Street, Tampines, SG",
                "Payment due in 31 days."
        );

        // Display details
        System.out.println(invoice);
        invoice.printInvoice();
    }
}
