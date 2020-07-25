package Model.Vitals;

import java.util.Date;

/**
 * Cholesterol class contains the measurement of cholesterol in the patient and the time of the test
 */
public class Cholesterol extends BiologicalParameter{
    public final String CHOLESTEROL_CODE = "2093-3";

    private double cholesterolLevel;
    private Date effectiveDateTime;
    private String patientID;

    public Cholesterol(double newCholesterolLevel, Date newEffectiveDateTime, String newPatientID) {
        this.cholesterolLevel = newCholesterolLevel;
        this.effectiveDateTime = newEffectiveDateTime;
        this.patientID = newPatientID;
    }

    public Cholesterol(double newCholesterolLevel, Date newEffectiveDateTime) {
        this.cholesterolLevel = newCholesterolLevel;
        this.effectiveDateTime = newEffectiveDateTime;
        this.patientID = "";
    }

    public Cholesterol(double newCholesterolLevel) {
        this.cholesterolLevel = newCholesterolLevel;
        this.effectiveDateTime = null;
    }

    /**
     * @return int: cholesterol level
     */
    @Override
    public Object getMeasurement() {
        return this.cholesterolLevel;
    }
    @Override
    public Object getEffectiveTimeDate(){
        return this.getEffectiveTimeDate();
    }

    @Override
    public String toStringTime(){
        String time = this.effectiveDateTime.toString();
        return time;
    }

    public String toStringVitals() {
        String cholesterol = String.valueOf(this.cholesterolLevel);

            String time = this.effectiveDateTime.toString();

        return cholesterol;
    }

    public String getCHOLESTEROL_CODE() {
        return CHOLESTEROL_CODE;
    }
}
