public class Patient {
    // Variables
    private String id, first_name, last_name, billing_add, nationality, phone_num;

    
    // Default constructor
    public Patient(){
        this("None", "None", "None", "None", "00000000", "None");
    }

    // Constructor
    public Patient(String id, String first_name, String last_name, String billing_add, String phone_num, String nationality){
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.billing_add = billing_add;
        this.phone_num = phone_num;
        this.nationality = nationality;
    }

    // Setters
    public void setId(String id){
        this.id = id;
    }

    public void setFirstName(String firstName){
        this.first_name = firstName;
    }

    public void setLastName(String lastName){
        this.last_name = lastName;
    }
    
    public void setBillAddress(String add){
        this.billing_add = add;
    }

    public void setPhoneNum(String number){
        this.phone_num = number;
    }

    public void setNationaity(String nationality){
        this.nationality = nationality;
    }

    // Getters
    public String getId(){
        return this.id;
    }

    public String getName(){
        return this.first_name + " " + this.last_name;
    }

    public String getFirstName(){
        return this.first_name;
    }

    public String getLastName(){
        return this.last_name;
    }

    public String getBillAdd(){
        return this.billing_add;
    }

    public String getPhoneNum(){
        return this.phone_num;
    }

    public String getNationality(){
        return this.nationality;
    }

    public void displayPatientInfo(){
        System.out.println("Patient ID: " + this.id);
        System.out.println("Full Name: " + this.first_name + " " + this.last_name);
        System.out.println("Billing Address: " + this.billing_add);
        System.out.println("Nationality: " + this.nationality);
        System.out.println("Phone Number: " +this.phone_num);
    }


}
