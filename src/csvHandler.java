import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class csvHandler {
    
    private List<Patient> patientData = new ArrayList<>();

    public csvHandler(){
        
    }

    // For simulation purpose
    public void savePatient(List<Patient> pb){
        
        PrintWriter writer = null;

        try{
            writer = new PrintWriter("./data/patients.csv", "UTF-8");
            for(Patient p: pb){
                String patientStr = String.format("%s,%s,%s,%s,%s,%s", p.getId(), p.getFirstName(), p.getLastName(), p.getBillAdd(), p.getPhoneNum(), p.getNationality());
                writer.println(patientStr);
            }

        } catch(IOException e){
            System.out.println("Error saving patient: " + e.getMessage());

        } finally{

            // Ensure that only writer with an object and not null is closed
            if(writer != null){
                writer.close();
            }
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
    
    public void displayData(){
        
    }
}
