import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Invoice invoice = new Invoice(
                "INV12345",
                new Date(),
                "123 Main Street, Springfield, USA",
                "Payment due in 30 days."
        );

        // Display details
        System.out.println(invoice);
        invoice.printInvoice();
    }
}
