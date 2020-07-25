package Model;
public abstract class Actor {
    private String IdNumber;
    private String Name;
    private String Surname;
    private Gender gender;


    abstract public void setFirstName(String practitionerFirstName);
    abstract public void setSurname(String Surname);
    abstract public void setId(String ID);

}
