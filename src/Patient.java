public class Patient {
    // Variables
    private Integer id;
    protected String first_name;
    protected String last_name;
    private String billing_add;
    private Integer phone_num;
    protected String nationality;
    protected String sex;
    private String email;

    // Getter & Setters
    public void setId(Integer nric){
        this.id = nric;
    };

    public void setName(String firstName, String lastName){
        this.first_name = firstName;
        this.last_name = lastName;
    };

    public void setBillAddress(String add){
        this.billing_add = add;
    };

    public void setPhoneNum(Integer number){
        this.phone_num = number;
    };

    public void setNationaity(String nationality){
        this.nationality = nationality;
    };

    public void setSex(String sex){
        this.sex = sex;
    };

}
