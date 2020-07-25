package Model;

import Model.Address.Address;
import Model.Vitals.BloodPressure;
import Model.Vitals.Cholesterol;
import Model.Vitals.SmokingStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

/**
 * Patient class has a name, surname, birth date, gender, address and patient ID
 */
public class Patient {
    private String name;
    private String surname;
    private Date birthDate;
    private Gender gender;
    private Address address;
    private String patientID;
    private BloodPressure bloodPressure;
    private SmokingStatus smokingStatus;

    public Cholesterol getCholesterol() {
        return cholesterol;
    }

    public void setCholesterol(Cholesterol cholesterol) {
        this.cholesterol = cholesterol;
    }

    public void setBloodPressure(BloodPressure patientBloodPressure) {
        this.bloodPressure = patientBloodPressure;
    }

    public BloodPressure getBloodPressure() {
        return bloodPressure;
    }

    public void setSmokingStatus(SmokingStatus smokingStatus) {
        this.smokingStatus = smokingStatus;
    }

    public SmokingStatus smokingStatus() {
        return smokingStatus;
    }

    private Cholesterol cholesterol;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public Patient(String name, String surname, Date birthDate, String gender, Address address, String patientID) {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        if (gender.equals("male")){
            this.gender = Gender.MALE;
        } else{
            this.gender =Gender.FEMALE;
        }
        this.address = address;
        this.patientID = patientID;
    }

    public Patient(String name, String surname, Date birthDate, String gender,  String patientID) {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        if (gender.equals("male")){
            this.gender = Gender.MALE;
        } else{
            this.gender =Gender.FEMALE;
        }
        this.address = null;
        this.patientID = patientID;
    }


    public Patient(String name, String surname, String patientID) {
        this.name = name;
        this.surname = surname;
        this.birthDate = null;
        this.gender = null;
        this.address = null;
        this.patientID = patientID;
    }

    /**
     *
     * @return The integer value of the age
     */

    public int getAge(){
        Date today = new Date();
        Period period = Period.between(convertDateToLocalDate(getBirthDate()),convertDateToLocalDate(today));
        return period.getYears();
    }
    /**
     *
     * @param date Date that has to converted according to the local date time
     * @return converted date respect to the local t
     */

    public LocalDate convertDateToLocalDate(Date date){
        LocalDateTime inputDate = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        LocalDate convDate = inputDate.toLocalDate();
        return convDate;
    }



    @Override
    public String toString() {
        String patientID = this.patientID;
        String name = this.name + " " + this.surname;
        return "PatientID: " + patientID + " Name: " + name;
    }



}
