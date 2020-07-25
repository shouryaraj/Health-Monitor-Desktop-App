package View;
import Model.Patient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class patientDetails extends JFrame implements Panels {
    private JPanel panel1;
    private JPanel header;
    private JPanel Body;
    private JLabel patientProperDetials;
    private JLabel patientName;
    private JLabel patientId;
    private JLabel patientAge;
    private JLabel patientGender;
    private JLabel patientAddress;
    private JButton backButton;
    private JFrame Frame;
    public patientDetails(String title, Patient patient, JFrame linkFrame) {
        super(title);
//        this.setLayout(new BorderLayout());
        setBounds(500, 100, 1000, 1000);
        setPreferredSize(new Dimension(1000, 800));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Instruction how to close the program
//        createTable(this.cholestrolTable);
        this.setContentPane(panel1);  // the name of the panel that is mentioned in the GUI form
        this.pack();
        this.Frame = linkFrame;


        patientName.setText("NAME: " + patient.getName());
        patientId.setText("ID: "  + patient.getPatientID());
        patientAge.setText("AGE: " + patient.getAge());
        patientGender.setText("Gender: " + patient.getGender().toString());
        patientAddress.setText("Address: " + patient.getAddress().toString());


        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                linkFrame.setVisible(true);
                setVisible(false);

            }
        });
    }
    @Override
    public JFrame getLinkFrame(){
        return this.Frame;
    }

    public static void main(String[] arg) {



    }

}


