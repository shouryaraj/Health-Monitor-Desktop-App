package Model;

/**
 * Practitioner class has an ID and a list of patients
 */
public class Practitioner extends Actor{
    private String practitionerID;
    private String practitionerFirstName;
    private String practitionerSurname;
    private String practitionerPost;
    private PatientList patients;




    public String getFirstName() {
        return practitionerFirstName;
    }
    @Override
    public void setFirstName(String practitionerFirstName) {
        this.practitionerFirstName = practitionerFirstName;
    }

    public String getSurname() {
        return practitionerSurname;
    }
    @Override
    public void setSurname(String practitionerSurname) {
        this.practitionerSurname = practitionerSurname;
    }
    @Override
    public void setId(String practitionerID) {
        this.practitionerID = practitionerID;
    }



    public Practitioner(String practitionerID) {
        this.practitionerID = practitionerID;
    }

    public String getPractitionerID() {
        return practitionerID;
    }




    public String getPractitionerPost() {
        return practitionerPost;
    }

    public void setPractitionerPost(String practitionerPost) {
        this.practitionerPost = practitionerPost;
    }

    public PatientList getPatients() {
        return patients;
    }

    public void setPatients(PatientList patients) {
        this.patients = patients;
    }

    public Practitioner(String nameF, String nameS, String post, String practitionerID){
        this.practitionerFirstName = nameF;
        this.practitionerSurname = nameS;
        this.practitionerPost = post;
        this.practitionerID = practitionerID;

    }

    public Practitioner(String nameF, String nameS, String practitionerID){
        this.practitionerFirstName = nameF;
        this.practitionerSurname = nameS;
        this.practitionerPost = null;
        this.practitionerID = practitionerID;

    }
    public Practitioner(String practitionerID, String practitionerFName,String practitioneSurnameName, String practitionerPost,PatientList patient) {
        this.practitionerID = practitionerID;
        this.practitionerFirstName = practitionerFName;
        this.practitionerSurname = practitioneSurnameName;
        this.practitionerPost = practitionerPost;
        this.patients = patient;
    }

}