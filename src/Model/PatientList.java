package Model;
import java.util.ArrayList;

/**
 * List of patients
 */
public class PatientList implements Container {
    private ArrayList<Patient> patients;

    public PatientList(){
        this.patients = new ArrayList<Patient>();
    }

    @Override
    public Iterator createIterator() {
        return new PatientIterator(this);
    }

    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    public int length() {
        return patients.size();
    }

   public Patient get(int index) {
        return patients.get(index);
    }

    public Patient getPatientWithID(String id){
        for (int i =0; i < patients.size(); i++){
            if (patients.get(i).getPatientID().equals(id))
            {
                return patients.get(i);
            }
        }
        return new Patient("null","null","null");
    }

}
