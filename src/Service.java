import java.util.Date;
import java.text.SimpleDateFormat;

public class Service {

    // Variables
    private String serviceCode, serviceDescript;
    private Date serviceDate;

    // Constructors
    public Service(){
        this("None", "None", new Date());
    }

    public Service(String sc, String sd, Date sdate){
        this.serviceCode = sc;
        this.serviceDescript = sd;
        this.serviceDate = sdate;
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

    // Getters
    public String getServiceCode(){
        return this.serviceCode;
    }

    public String getServiceDescript(){
        return this.serviceDescript;
    }

    public Date setServiceDate(){
        return this.serviceDate;
    }
}
