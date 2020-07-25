package View;

import Model.*;
import Model.Vitals.BiologicalParameter;
import Model.Vitals.BloodPressure;
import Model.Vitals.Cholesterol;
import Model.Vitals.SmokingStatus;

public class UpdateThePatientData {
    private Patient patient;


    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        if(patient != null) {
            this.patient = patient;
        };
    }

    public UpdateThePatientData(Patient patient) {
        this.setPatient(patient);
    }

    public boolean updateCholestrolValue(BiologicalParameter chol){
        if (chol != null){
            this.patient.setCholesterol((Cholesterol) chol);
            return true;
        }
        return false;
    }
    public boolean updateBloodPressure(BiologicalParameter bp){
        if (bp != null){
            this.patient.setBloodPressure((BloodPressure) bp);
            return true;
        }
        else{
            return false;
        }
    }
    public boolean updateSmokingDetails(BiologicalParameter smoking){
        if(smoking != null){
            this.patient.setSmokingStatus((SmokingStatus) smoking);
            return true;
        }
        else{
            return false;
        }

    }



}
