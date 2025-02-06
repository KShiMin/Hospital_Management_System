import java.util.Date;

public class Service {

    // Variables
    private String serviceCode, serviceDescript;
    private Date serviceDate;
    private int quantity;
    private double unitPrice;

    // Constructors
    public Service(){
        this("None", "None", null, 0, 0.00);
    }

    public Service(String sc, String sd, Date sdate, int q, double pr){
        this.serviceCode = sc;
        this.serviceDescript = sd;
        this.serviceDate = sdate;
        this.quantity = q;
        this.unitPrice = pr;
    }

    // Setters
    public void setServiceCode(String sc){
        this.serviceCode = sc;
    }

    public void setServiceDescript(String sd){
        this.serviceDescript = sd;
    }

    public void setServiceDate(Date sdate){
        this.serviceDate = sdate;
    }

    public void setQuantity(int q){
        this.quantity = q;
    }

    public void setUnitPrice(double pr){
        this.unitPrice = pr;
    }

    // Getters
    public String getServiceCode(){
        return this.serviceCode;
    }

    public String getServiceDescript(){
        return this.serviceDescript;
    }

    public Date getServiceDate(){
        return this.serviceDate;
    }

    public int getQuantity(){
        return this.quantity;
    }

    public double getUnitPrice(){
        return this.unitPrice;
    }

    public double calculatePrice() {
        return this.quantity * this.unitPrice;
    }

    // Display individual service
    public void displayService() {
        System.out.println("Service Code: " + this.serviceCode);
        System.out.println("Service Description: " + this.serviceDescript);
        System.out.println("Service Date: " + this.serviceDate);
        System.out.println("Service Quantity: " + this.quantity);
        System.out.println("Service Unit Price: " + this.unitPrice);
        System.out.println("Service Price: " + calculatePrice());
    }

}

