import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class csvHandler {
    
    private List<Patient> patientData = new ArrayList<>();
    private List<Service> serviceData = new ArrayList<>();

    // Constructor
    public csvHandler(){
        
    }

    // For simulation purpose
    public void saveToFile(List<Patient> pd, List<Service> sd) {
        List<String> filepaths = new ArrayList<>();
    
        if (pd.size() != 0) {
            filepaths.add("./data/patients.csv");
        }
        if (sd.size() != 0) {
            filepaths.add("./data/services.csv");
        }
    
        if (!filepaths.isEmpty()) {
            try {
                for (String fp : filepaths) {
                    PrintWriter writer = new PrintWriter(fp, "UTF-8");
                    
                    if (fp.equals("./data/patients.csv")) {
                        System.out.println("Saving Patient Data...");
                        for (Patient p : pd) {
                            String patientStr = String.format("%s,%s,%s,%s,%s,%s", p.getId(), p.getFirstName(), p.getLastName(), 
                                p.getBillAdd(), p.getPhoneNum(), p.getNationality());
                            writer.println(patientStr);
                        }
                    } else if (fp.equals("./data/services.csv")) {
                        System.out.println("Saving Service Data...");
                        for (Service s : sd) {
                            String serviceStr = String.format("%s,%s,%s", s.getServiceCode(), s.getServiceDescript(), s.getUnitPrice());
                            writer.println(serviceStr);
                        }
                    }
                    
                    writer.close();
                }
            } catch (IOException e) {
                System.out.println("Error saving file: " + e.getMessage());
            }
        } else {
            System.out.println("No data to save.");
        }
    }

    public List<Patient> setPatient(){
        try(BufferedReader br = new BufferedReader(new FileReader("./data/patients.csv"))){
            
            String line;
            
            // Prints out all available data
            while((line = br.readLine()) != null){
                String[] pdatas = line.split(",");
                patientData.add(new Patient(pdatas[0], pdatas[1], pdatas[2], pdatas[3], pdatas[4], pdatas[5]));
            }

        } catch(IOException e){
            System.out.println("An error occurred: " + e.getMessage());
        }

        return patientData;
    }

    public List<Service> setService(){
        try(BufferedReader br = new BufferedReader(new FileReader("./data/services.csv"))){
            
            String line;
            
            // Prints out all available data
            while((line = br.readLine()) != null){
                String[] sdatas = line.split(",");

                double price;
                try {
                    price = Double.parseDouble(sdatas[2]);
                } catch (NumberFormatException e) {
                    price = 0.0; // Default value in case of an invalid number
                }

                serviceData.add(new Service(sdatas[0], sdatas[1], null, 0, price));
            }

        } catch(IOException e){
            System.out.println("An error occurred: " + e.getMessage());
        }

        return serviceData;       
    }
        
}
