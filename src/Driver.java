import Controller.Server.EMR;
import View.Login;

import javax.swing.*;


public class Driver {
    /**
     * Main class to drive the application
     * @param args
     */


    public static void main(String[] args) throws Exception{

        EMR serverConnection = new EMR("https://fhir.monash.edu/hapi-fhir-jpaserver/fhir/");
//
//        SmokingStatus testSmoke = serverConnection.getPatientSmokingData("330491");
//        System.out.println(testSmoke.toString());
//
//        ArrayList<BloodPressure> testBP = serverConnection.getPreviousPatientBloodPressureData("330491",5);
//        System.out.println(testBP);


        // Intializing the UI to get the Practitioner ID to extract the Patient details
        JFrame frame = new Login("MyCholestrolMonitor", serverConnection);
        frame.setVisible(true);

        //Good IDs:
        // 1195557
        // 547689
    }
}
