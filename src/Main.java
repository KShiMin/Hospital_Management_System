import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    // For simulation purpose
    private static void savePatient(List<Patient> pb){
        
        PrintWriter writer = null;

        try{
            writer = new PrintWriter("./data/patients.csv", "UTF-8");
            for(Patient p: pb){
                String patientStr = String.format("%s,%s,%s,%s,%s", p.getId(), p.getName(), p.getBillAdd(), p.getNationality(), p.getPhoneNum());
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

    private static void readPatient(){
        try(BufferedReader br = new BufferedReader(new FileReader("./data/patients.csv"))){
            
            String line;
            
            // Prints out all available data
            while((line = br.readLine()) != null){
                String[] words = line.split(",");
                for(String w: words){
                    System.out.println(w);
                }
                // System.out.println(line);
            }

        } catch(IOException e){
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        List<Patient> patientList = new ArrayList<>();
        
        Patient p1 = new Patient("P01", "Jack", "Neo", "BLK 19 MARSILING LANE #02-305 Singapore 730019", "Singaporean", "81123456");
        Patient p2 = new Patient("P02", "May", "Tan", "BLK 213 HOUGANG ST 21 #01-367 Singapore 530213", "Singaporean", "92223546");
        Patient p3 = new Patient("P03", "Tommy", "Arnold", "34 WHAMPOA WEST #05-15 Singapore 330034", "Permanent Resident", "88833316");
        Patient p4 = new Patient("P04", "Dennis", "Anderson", "3 COLEMAN STREET #18-22 Singapore 179804", "Foreigner", "97234569");
        
        patientList.add(p1);
        patientList.add(p2);
        patientList.add(p3);
        patientList.add(p4);
        

        // p1.displayPatientInfo();
        savePatient(patientList);
        readPatient();
    }
}
