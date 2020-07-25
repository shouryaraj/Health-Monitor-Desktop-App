package MachineLearning;

import Controller.Server.EMR;


import Controller.Server.ServerJSON;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class moreDataFromServer extends EMR {
    private final String root;
    SimpleDateFormat sdfDateTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
    SimpleDateFormat sdfDate = new SimpleDateFormat("YYYY-MM-DD");


    /**
     * establishes connection to FHIR server
     */
    public moreDataFromServer(String root) {

        super(root);
        this.root = root;
    }

    /**
     * Get latest patient Blood pressure data
     */


    /**
     * Get latest patient Glucose data
     */


    public Glucose getGlucose(String id) throws Exception {
        String details = "Observation?patient="+id+"&code=2339-0&_sort=-date&_count=13&_format=json";
        String url = root + details;
        JSONObject json = ServerJSON.readJsonFromUrl(url);

        JSONArray entry = json.getJSONArray("entry");
        System.out.println(entry);
        JSONObject resource = entry.getJSONObject(0).getJSONObject("resource");
        JSONObject valueQuantity = resource.getJSONObject("valueQuantity");
        double weight = valueQuantity.getDouble("value");
        String issuedStr = resource.getString("issued");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        Date issuedDate = sdf.parse(issuedStr);

        Glucose glucosedata = new Glucose(weight,issuedDate);

        return glucosedata;
    }

    public Height getHeight(String id) throws Exception {
        String details = "Observation?patient="+id+"&code=8302-2&_sort=-date&_count=13&_format=json";
        String url = root + details;
        JSONObject json = ServerJSON.readJsonFromUrl(url);
        Height height;

            JSONArray entry = json.getJSONArray("entry");

            JSONObject resource = entry.getJSONObject(0).getJSONObject("resource");
            JSONObject valueQuantity = resource.getJSONObject("valueQuantity");
            double weight = valueQuantity.getDouble("value");
            String issuedStr = resource.getString("issued");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
            Date issuedDate = sdf.parse(issuedStr);


             height = new Height(weight, issuedDate);

        return height;
    }
    public Weight getWeight(String id) throws Exception {
        String details = "Observation?patient="+id+"&code=29463-7&_sort=-date&_count=13&_format=json";
        String url = root + details;
        JSONObject json = ServerJSON.readJsonFromUrl(url);

        JSONArray entry = json.getJSONArray("entry");

        JSONObject resource = entry.getJSONObject(0).getJSONObject("resource");
        JSONObject valueQuantity = resource.getJSONObject("valueQuantity");
        double weight = valueQuantity.getDouble("value");
        String issuedStr = resource.getString("issued");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        Date issuedDate = sdf.parse(issuedStr);


        Weight w = new Weight(weight, issuedDate);

        return w;
    }
    public Calcium getCalcium(String id) throws Exception {
        String details = "Observation?patient="+id+"&code=49765-1&_sort=-date&_count=13&_format=json";
        String url = root + details;
        JSONObject json = ServerJSON.readJsonFromUrl(url);

        JSONArray entry = json.getJSONArray("entry");

        JSONObject resource = entry.getJSONObject(0).getJSONObject("resource");
        JSONObject valueQuantity = resource.getJSONObject("valueQuantity");
        double weight = valueQuantity.getDouble("value");
        String issuedStr = resource.getString("issued");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        Date issuedDate = sdf.parse(issuedStr);


        Calcium c = new Calcium(weight, issuedDate);

        return c;
    }
    public Potassium getPotassium(String id) throws Exception {
        String details = "Observation?patient="+id+"&code=6298-4&_sort=-date&_count=13&_format=json";
        String url = root + details;
        JSONObject json = ServerJSON.readJsonFromUrl(url);

        JSONArray entry = json.getJSONArray("entry");

        JSONObject resource = entry.getJSONObject(0).getJSONObject("resource");
        JSONObject valueQuantity = resource.getJSONObject("valueQuantity");
        double weight = valueQuantity.getDouble("value");
        String issuedStr = resource.getString("issued");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        Date issuedDate = sdf.parse(issuedStr);


        Potassium p = new Potassium(weight, issuedDate);

        return p;
    }
    public Sodium getSodium(String id) throws Exception {
        String details = "Observation?patient="+id+"&code=2947-0&_sort=-date&_count=13&_format=json";
        String url = root + details;
        JSONObject json = ServerJSON.readJsonFromUrl(url);

        JSONArray entry = json.getJSONArray("entry");

        JSONObject resource = entry.getJSONObject(0).getJSONObject("resource");
        JSONObject valueQuantity = resource.getJSONObject("valueQuantity");
        double weight = valueQuantity.getDouble("value");
        String issuedStr = resource.getString("issued");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        Date issuedDate = sdf.parse(issuedStr);


        Sodium s = new Sodium(weight, issuedDate);

        return s;
    }
    public Leukocytes getLeukocytes(String id) throws Exception {
        String details = "Observation?patient="+id+"&code=6690-2&_sort=-date&_count=13&_format=json";
        String url = root + details;
        JSONObject json = ServerJSON.readJsonFromUrl(url);

        JSONArray entry = json.getJSONArray("entry");

        JSONObject resource = entry.getJSONObject(0).getJSONObject("resource");
        JSONObject valueQuantity = resource.getJSONObject("valueQuantity");
        double weight = valueQuantity.getDouble("value");
        String issuedStr = resource.getString("issued");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        Date issuedDate = sdf.parse(issuedStr);


        Leukocytes L = new Leukocytes(weight, issuedDate);

        return L;
    }

    /**
     * Get all patients of a practitioner
     */
    @Override
    public List<String> getPractitionerPatientList(String id) throws Exception {
        String encountersUrl = root +  "Encounter?participant.identifier=" + getIdentifier(id,"Practitioner") + "&_include=Encounter.participant.individual&_include=Encounter.patient&_format=json";

        boolean nextPage = true;
        String nextUrl = encountersUrl;
        int countPage = 0;
        int countPatient = 0;
        List<String> patientList = new ArrayList<String>();


        while (nextPage && (countPage <= 15)) {
//            System.out.println(String.valueOf(countPage)+ "  Page count");
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
                    countPatient ++;
                    patientList.add(patientId);
                    if((countPatient >= 15)){
                        break;
                    }
                }
            }
        }

        return patientList;
    }

}
