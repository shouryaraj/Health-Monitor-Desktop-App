package Model.Vitals;

import java.util.Date;

public class BloodPressure extends BiologicalParameter implements Comparable<BloodPressure> {
    public static final String BLOOD_PRESSURE_CODE = "55284-4";

    private int diastolicValue;
    private int systolicValue;
    private Date effectiveDateTime;
    private String patientID;


    public BloodPressure(int newDiastolic, int newSystolic, Date newDate, String newPatientID){
        this.diastolicValue = newDiastolic;
        this.systolicValue = newSystolic;
        this.effectiveDateTime = newDate;
        this.patientID = newPatientID;



    }

    public BloodPressure(int newDiastolic, int newSystolic){
        this.diastolicValue = newDiastolic;
        this.systolicValue = newSystolic;
        this.effectiveDateTime = null;
        this.patientID = "";
    }
    /**
     * @return int: blood pressure
     */
    @Override
    public Object getMeasurement() {
        return this.diastolicValue;
    }

    @Override
    public Date getEffectiveTimeDate() {
        return this.effectiveDateTime;
    }

    @Override
    public String toStringTime() {
        return null;
    }

    public int getDiastolicValue() {
        return diastolicValue;
    }

    public int getSystolicValue() {
        return systolicValue;
    }

    public String getDiastolicValueString() {
        return diastolicValue + " mmHg";
    }

    public String getSystolicValueString() {
        return systolicValue + " mmHg";
    }

    @Override
    public String toString() {
        return  "Diastolic: " + this.diastolicValue + " Systolic: " + this.systolicValue + " Issued: " + this.effectiveDateTime;
    }

    @Override
    public int compareTo(BloodPressure o) {
        return this.getEffectiveTimeDate().compareTo(o.getEffectiveTimeDate());
    }
}

