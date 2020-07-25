package MachineLearning;


import Model.Vitals.BloodPressure;
import com.opencsv.CSVWriter;
import Model.Gender;
import Model.Patient;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MLDriver {

    /**
     * Main class to drive the application
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {
        moreDataFromServer serverConnection = new moreDataFromServer("https://fhir.monash.edu/hapi-fhir-jpaserver/fhir/");
        List<java.lang.String> list = serverConnection.getPractitionerList() ;
        String Id;
        File file = new File("E:\\textfile.csv");

        try {
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file);

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile);

            // create a List which contains String array
            List<String[]> data = new ArrayList<String[]>();
            data.add(new String[] { "id", "age" , "gender", "height", "weight","bp_low","bp_high", "glucose", "sodium", "potassium", "Leukocytes" ,"cholestrol" });
            writer.writeAll(data);
            System.out.println("Okay");
            Id = "3689";
            String cholestrol, glucose, height, weight,potassium, sodium, leuco = "";
            String bp_low ="";
            String bp_high ="";
            Patient p;
            BloodPressure bp;
            int countP = 0;
            String age;
            List<String> pList;
            String gender = "0";
            for(int i =0; i < list.size(); i++){
                System.out.println("NEW PRAC..............................................................................................");
                pList = serverConnection.getPractitionerPatientList(list.get(i));
                System.out.println(String.valueOf(list.get(i))+ " Prac ID      " +String.valueOf(pList.size()) + " size of the patient list");
                    for (int j = 0; j < pList.size(); j++) {
                        data = new ArrayList<String[]>();
                        Id = pList.get(j);
                        p = serverConnection.getPatientWithID(Id);
                        System.out.println(countP);

                        if (p.getGender() == Gender.MALE) {
                            gender = "1";
                        } else {
                            gender = "0";
                        }


                        try {

                            cholestrol = serverConnection.getPatientCholesterolData(Id).toStringVitals();
                            System.out.println(cholestrol);
                            if (Double.parseDouble(cholestrol) < 180.0) {
                                cholestrol = "0";

                            } else {
                                cholestrol = "1";
                            }
                            if (p.getAge() < 100) {
                                age = String.valueOf(p.getAge());
                                System.out.println(age);
                                height = String.valueOf(Math.round(Double.parseDouble(serverConnection.getHeight(Id).toStringVitals())));

                                weight = String.valueOf(Math.round(Double.parseDouble(serverConnection.getWeight(Id).toStringVitals())));

                                bp = serverConnection.getPatientBloodPressureData(Id);
                                bp_low = String.valueOf(Math.round(bp.getDiastolicValue()));
                                bp_high = String.valueOf(Math.round(bp.getSystolicValue()));

                                glucose = String.valueOf(Math.round(Double.parseDouble(serverConnection.getGlucose(Id).toStringVitals())));
                                sodium = String.valueOf(Math.round(Double.parseDouble(serverConnection.getSodium(Id).toStringVitals())));
                                potassium = String.valueOf(Math.round(Double.parseDouble(serverConnection.getPotassium(Id).toStringVitals())));
                                leuco = String.valueOf(Math.round(Double.parseDouble(serverConnection.getLeukocytes(Id).toStringVitals())));

                                data.add(new String[]{Id, age, gender, height, weight, bp_low, bp_high, glucose, sodium, potassium, leuco, cholestrol});
                                writer.writeAll(data);
                                countP++;
                            }

                        }
                        catch (Exception e){
                            System.out.println("Missing Value for the vital ");
                        }









                    }
                if(countP > 300){
                    break;
                }







            }




//            writer.writeAll(data);

            // closing writer connection
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


       }

}




