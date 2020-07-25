package View;
import Controller.HealthMonitor;
import Controller.Server.EMR;
import Model.Patient;
import Model.PatientList;
import Model.Practitioner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Login extends JFrame{
    public JPanel mainPanel;
    private JTextField enterNumericValueTextField;
    private JPanel idDetails;
    private JButton enterButton;
    private JPanel TextBox;
    private JPanel LiftFromBottom;
    private JPanel Heading;
    private JLabel HeadingLabel;
    private JProgressBar progressBar1;
    private JList ListOfPeople;
    private JButton buttonNewSave;
    private JButton buttonSaveExisting;
    private JTextField textName;
    private JTextField textEmail;
    private EMR server;
    private Patient patient;
    private Practitioner prac;

    public String getPractitionerID() {
        return PractitionerID;
    }

    public void setPractitionerID(String practitionerID) {
        PractitionerID = practitionerID;
    }

    private JTextField textID;
    private DefaultListModel listPatientModel;
    private String PractitionerID;

    // constructor to initialise the value
    public Login(String title, EMR server){
        super(title);
        // setting the value for the display panel
        setBounds(500,100,1000,1000);
        setPreferredSize(new Dimension(800, 500));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Instruction how to close the program
        this.setContentPane(mainPanel);  // the name of the panel that is mentioned in the GUI form
        this.pack();
        this.server = server;

        // Enter button when user clicks the button
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // value from the text field
               String practitionersID = enterNumericValueTextField.getText();
               if(practitionersID != ""){

              try {
                       prac = server.getPractitionerWithID(practitionersID);
                   } catch (Exception e1) {
                       JOptionPane.showMessageDialog(null, "Wrong Practitioner ID", "InfoBox: " + "Error",
                               JOptionPane.INFORMATION_MESSAGE);
                   }
                   PatientList patientList = new PatientList();
                   patientList.createIterator();
                   List<String> list = null;
                   try {
                       list = server.getPractitionerPatientList(practitionersID);
                   } catch (Exception e1) {
                       e1.printStackTrace();
                   }

                   for(int i =0; i<list.size(); i++){
                       try {
                           patient = server.getPatientWithID(list.get(i));
                       } catch (Exception e1) {
                           e1.printStackTrace();
                       }
                       patientList.addPatient(patient);
                   }
                   // Adding the patient List in the practitioner class
                   prac.setPatients(patientList);
                   HealthMonitor healthMonitor = new HealthMonitor("HealthMonitor", prac, server);
                   healthMonitor.setVisible(true);
                   healthMonitor.refreshPatientList();
                   dispose();
               }
               else{
                   JOptionPane.showMessageDialog(null, "There is no input for ID", "InfoBox: " + "Error", JOptionPane.INFORMATION_MESSAGE);
               }
            }
        });
    }

    // Main function that runs the UI function
    public static void main(String[] arg){
    }
}

