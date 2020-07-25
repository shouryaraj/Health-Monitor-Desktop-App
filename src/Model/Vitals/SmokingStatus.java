package Model.Vitals;
import java.util.Date;

public class SmokingStatus extends BiologicalParameter{
    public final String SMOKING_CODE = "72166-2";

    private Boolean smokingStatus;
    private Date effectiveDateTime;
    private String patientID;

    public SmokingStatus(Boolean newSmokingStatus, Date newEffectiveDateTime, String newPatientID) {
        this.smokingStatus = newSmokingStatus;
        this.effectiveDateTime = newEffectiveDateTime;
        this.patientID = newPatientID;
    }

    public SmokingStatus(Boolean newSmokingStatus, Date newEffectiveDateTime) {
        this.smokingStatus = newSmokingStatus;
        this.effectiveDateTime = newEffectiveDateTime;
        this.patientID = "";
    }

    public SmokingStatus(Boolean newSmokingStatus) {
        this.smokingStatus = newSmokingStatus;
        this.effectiveDateTime = null;
    }
    @Override
    public String toStringTime() {
        return null;
    }
    /**
     * @return int: cholesterol level
     */
    @Override
    public Boolean getMeasurement() {
        return this.smokingStatus;
    }
    @Override
    public Object getEffectiveTimeDate(){
        return this.getEffectiveTimeDate();
    }

    @Override
    public String toString() {
        return  "Status: " + this.smokingStatus + " Issued: " + this.effectiveDateTime;
    }


    public String getSMOKING_CODE() {
        return SMOKING_CODE;
    }
}


