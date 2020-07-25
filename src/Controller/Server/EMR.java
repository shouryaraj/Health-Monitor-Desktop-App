package Controller.Server;

import Model.*;
import Model.Address.Address;
import Model.Vitals.BloodPressure;
import Model.Vitals.Cholesterol;
import Model.Vitals.SmokingStatus;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Controller class to maintain connection to FHIR server
 */
public class EMR {

    private final String root;
    private final SimpleDateFormat SDF_DATETIME = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
    private final SimpleDateFormat SDF_DATE = new SimpleDateFormat("YYYY-MM-DD");


    /**
     * establishes connection to FHIR server
     * @param root - url of the server to connect to
     */
    public EMR(String root) {
        this.root = root;
    }



    /**
     * obtains Paitent by their ID
     * @param id - ID String
     * @return Patient with relevant data
     * @throws Exception
     */
    public Patient getPatientWithID(String id) throws Exception {
        Patient newPatient;
        String patientURl = root + "Patient/" + id + "?_format=json";
        JSONObject patient = ServerJSON.readJsonFromUrl(patientURl);
        String patientFamilyName = patient.getJSONArray("name").getJSONObject(0).getJSONArray("given").getString(0);
        String patientGivenName = patient.getJSONArray("name").getJSONObject(0).getString("family");
        String patientBirthDateStr = patient.getString("birthDate");
        Date patientBirthDate = SDF_DATE.parse(patientBirthDateStr);
        String patientGender = patient.getString("gender");
        try {
            JSONObject patientAddressJSON = patient.getJSONArray("address").getJSONObject(0);
            String patientStreet = patientAddressJSON.getJSONArray("line").getString(0);
            String patientPost = patientAddressJSON.getString("postalCode");
            String patientExtension = patientAddressJSON.getString("city") + "/" + patientAddressJSON.getString("state") + "/" + patientAddressJSON.getString("country");
            Address patientAddress = new Address(patientStreet,patientPost,patientExtension);
            newPatient = new Patient(patientGivenName,patientFamilyName,patientBirthDate,patientGender, patientAddress, id);
        }
        catch(Exception e){
            newPatient = new Patient(patientGivenName,patientFamilyName,patientBirthDate,patientGender, id);
        }




        return newPatient;
    }


    /**
     * obtains Practitioner by their ID
     * @param id ID String
     * @return Practitioner with relevant info
     * @throws Exception
     */
    public Practitioner getPractitionerWithID(String id) throws Exception {
        String practitionerURl = root + "Practitioner/" + id + "?_format=json";
        JSONObject practitioner = ServerJSON.readJsonFromUrl(practitionerURl);
        String practitionerFamilyName = practitioner.getJSONArray("name").getJSONObject(0).getJSONArray("given").getString(0);
        String practitionerGivenName = practitioner.getJSONArray("name").getJSONObject(0).getString("family");
        String practitionerName = practitionerGivenName + " " + practitionerFamilyName;
        Practitioner newPractitioner = new Practitioner(practitionerGivenName, practitionerFamilyName,id);
        return newPractitioner;
    }


    /**
     * Get latest patient cholesterol data
     * @param id ID string of Patient
     * @return Cholesterol Data
     * @throws Exception
     */
    public Cholesterol getPatientCholesterolData(String id) throws Exception {
        String details = "Observation?patient="+id+"&code=2093-3&_sort=-date&_count=13&_format=json";
        String url = root + details;
        JSONObject json = ServerJSON.readJsonFromUrl(url);

        try{
            JSONArray entry = json.getJSONArray("entry");
            JSONObject resource = entry.getJSONObject(0).getJSONObject("resource");
            JSONObject valueQuantity = resource.getJSONObject("valueQuantity");
            double weight = valueQuantity.getDouble("value");
            String issuedStr = resource.getString("issued");

            Date issuedDate = SDF_DATETIME.parse(issuedStr);

            Cholesterol cholesterolData = new Cholesterol(weight,issuedDate,id);

            return cholesterolData;
        }
        catch (Exception e) {
            return new Cholesterol(-1); //if no value is found
        }

    }

    /**
     * Get latest patient blood pressure data
     * @param id ID string of Patient
     * @return Blood Pressure Data
     * @throws Exception
     */
    public BloodPressure getPatientBloodPressureData(String id) throws Exception {
        String details = "Observation?patient="+id+"&code=55284-4&_sort=-date&_count=13&_format=json";
        String url = root + details;
        JSONObject json = ServerJSON.readJsonFromUrl(url);

        try{
            JSONArray entry = json.getJSONArray("entry");
            JSONObject resource = entry.getJSONObject(0).getJSONObject("resource");
            JSONArray bloodPressureComponents = resource.getJSONArray("component");
            int diastolicBloodPressure = bloodPressureComponents.getJSONObject(0).getJSONObject("valueQuantity").getInt("value");
            int systolicBloodPressure = bloodPressureComponents.getJSONObject(1).getJSONObject("valueQuantity").getInt("value");

            String issuedStr = resource.getString("issued");

            Date issuedDate = SDF_DATETIME.parse(issuedStr);

            BloodPressure bloodPressureData = new BloodPressure(diastolicBloodPressure,systolicBloodPressure,issuedDate,id);

            return bloodPressureData;
        }
        catch (Exception e) {
            return new BloodPressure(-1,-1); //if no value is found

        }

    }

    /**
     * Get latest patient Smoking data
     * @param id ID string of Patient
     * @return Smoking Data
     * @throws Exception
     */
    public SmokingStatus getPatientSmokingData(String id) throws Exception {
        String details = "Observation?patient="+id+"&code=72166-2&_sort=-date&_count=13&_format=json";
        String url = root + details;
        JSONObject json = ServerJSON.readJsonFromUrl(url);

        try{
            Boolean smokerBoolean;
            JSONArray entry = json.getJSONArray("entry");
            JSONObject resource = entry.getJSONObject(0).getJSONObject("resource");

            JSONObject valueCodeableConcept = resource.getJSONObject("valueCodeableConcept");
            String statusText = valueCodeableConcept.getString("text");
            if (statusText.equals("Never smoker")){
                smokerBoolean = true;
            }
            else {
                smokerBoolean = false;
            }


            String issuedStr = resource.getString("issued");

            Date issuedDate = SDF_DATETIME.parse(issuedStr);


            SmokingStatus smokingData = new SmokingStatus(smokerBoolean,issuedDate,id);

            return smokingData;
        }
        catch (Exception e) {
            return new SmokingStatus(false); //if no value is found

        }

    }




    /**
     * Get previous n values of blood pressure data
     * @param id ID string of Patient
     * @param n number Of Previous Values
     * @return List of previous blood pressure data
     * @throws Exception
     */
    public ArrayList<BloodPressure> getPreviousPatientBloodPressureData(String id,int n) throws Exception {
        String details = "Observation?patient="+id+"&code=55284-4&_sort=-date&_count=13&_format=json";
        String url = root + details;
        ArrayList<BloodPressure> previousBloodPressure = new ArrayList<>();
        JSONObject json = ServerJSON.readJsonFromUrl(url);
        try {
            JSONArray entry = json.getJSONArray("entry");
            for (int entryIndex = 0; entryIndex < n; entryIndex++) {
                JSONObject resource = entry.getJSONObject(entryIndex).getJSONObject("resource");
                JSONArray bloodPressureComponents = resource.getJSONArray("component");
                int diastolicBloodPressure = bloodPressureComponents.getJSONObject(0).getJSONObject("valueQuantity").getInt("value");
                int systolicBloodPressure = bloodPressureComponents.getJSONObject(1).getJSONObject("valueQuantity").getInt("value");

                String issuedStr = resource.getString("issued");
                Date issuedDate = SDF_DATETIME.parse(issuedStr);
                BloodPressure bloodPressureData = new BloodPressure(diastolicBloodPressure,systolicBloodPressure,issuedDate,id);
                previousBloodPressure.add(bloodPressureData);
            }

            Collections.sort(previousBloodPressure,Collections.reverseOrder());
            return previousBloodPressure;

        }
        catch (Exception e) {
            return previousBloodPressure; //if no value is found
        }
    }



    /**
     * Get latest patient measurement data
     * @param id Patient ID
     * @param code Measurement Code
     * @return Measurement String
     * @throws Exception
     */
    public String getLatestPatientMeasurement(String id,String code) throws Exception {
        String details = "Observation?patient="+id+"&code="+ code + "&_sort=-date&_count=13&_format=json";
        String url = root + details;
        JSONObject json = ServerJSON.readJsonFromUrl(url);
        String returnString;
        try{
            JSONArray entry = json.getJSONArray("entry");
            JSONObject resource = entry.getJSONObject(0).getJSONObject("resource");
            returnString = resource.toString();


            return returnString;
        }
        catch (Exception e) {
            returnString = "";
            return returnString;
        }

    }


    /**
     * Get a entity's identifier given their id
     * @param id ID of entity
     * @param type type of entity
     * @return String
     * @throws Exception
     */
    public String getIdentifier(String id,String type) throws Exception {
        String URl = root + type + "/" + id + "?_format=json";
        JSONObject json = ServerJSON.readJsonFromUrl(URl);
        JSONObject identifier = json.getJSONArray("identifier").getJSONObject(0);
        String systemIdentifier = identifier.getString("system");
        String valueIdentifier = identifier.getString("value");

        return systemIdentifier + "%7C" + valueIdentifier;
    }


    /**
     * Get all patients of a practitioner
     * @param id - Practitioner ID
     * @return List with all patient ID strings
     * @throws Exception
     */
    public List<String> getPractitionerPatientList(String id) throws Exception {
        String encountersUrl = root +  "Encounter?participant.identifier=" + getIdentifier(id,"Practitioner") + "&_include=Encounter.participant.individual&_include=Encounter.patient&_format=json";

        boolean nextPage = true;
        String nextUrl = encountersUrl;
        int countPage = 0;
        int countPatient = 0;
        List<String> patientList = new ArrayList<String>();


        while (nextPage) {
            JSONObject allEncountersPractitioner = ServerJSON.readJsonFromUrl(nextUrl);

            nextPage = false;
            JSONArray links = allEncountersPractitioner.getJSONArray("link");

            for (int i = 0; i < links.length(); i++) {
                JSONObject link = links.getJSONObject(i);

                if (link.getString("relation").equals("next")) {
                    nextPage = true;
                    nextUrl = link.getString("url");
                    countPage += 1;
                }
            }

            JSONArray allEncounterData = allEncountersPractitioner.getJSONArray("entry");
            for (int i = 0; i < allEncounterData.length(); i++) {
                JSONObject entry = allEncounterData.getJSONObject(i);
                JSONObject item = entry.getJSONObject("resource");
                String patient = item.getJSONObject("subject").getString("reference");

                String patientId = ((patient.split("/"))[1]);

                if(!(patientList.contains(patientId))){
                    patientList.add(patientId);
                }
            }
        }

        return patientList;
    }

    /**
     * obtains list of practitioners
     * @return List with all practitioner strings
     * @throws Exception
     */
    public List<String> getPractitionerList() throws Exception {
        String practitionerURl = "https://fhir.monash.edu/hapi-fhir-jpaserver/fhir/Practitioner?_format=json";
        JSONObject practitioner = ServerJSON.readJsonFromUrl(practitionerURl);
        List<String> practitionerList = new ArrayList<String>();

        JSONArray allPractitionerData = practitioner.getJSONArray("entry");

        for (int i = 0; i < allPractitionerData.length(); i++) {
            JSONObject entry = allPractitionerData.getJSONObject(i);

            JSONObject item = entry.getJSONObject("resource");

            String practitionerID = item.getString("id");

            if (!(practitionerList.contains(practitionerID))) {
                practitionerList.add(practitionerID);
            }
        }
        return practitionerList;

    }
}
