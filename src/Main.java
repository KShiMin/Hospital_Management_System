import java.io.IOException;
import java.io.PrintWriter;

public class Main {
    // For simulation purpose
    private void savePatient(Patient[] pb){
        
        PrintWriter writer = null;

        try{
            writer = new PrintWriter("./data/patients.csv", "UTF-8");
            for(Patient p: pb){
                String patientStr = String.format("%s,%s,%s,%s,%s,%s", p.getId(), p.getFirstName(), p.getLastName(), p.getBillAdd(), p.getNationality(), p.getPhoneNum());
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

    public static void main(String[] args) {
        Patient p1 = new Patient();
    }
}
