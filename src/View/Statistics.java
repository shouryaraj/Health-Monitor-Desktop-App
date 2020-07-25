package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Statistics extends JFrame implements Panels {
    private JPanel panel1;
    private JPanel header;
    private JLabel Statistics;
    private JButton backButton;
    private JPanel Body;
    private JLabel aboveAverageCholesterol;
    private JLabel highSystolic;
    private JLabel highDiastolic;
    private JLabel nonSmokers;
    private double highSystolicValue;
    private double highDiastolicValue;
    private JFrame frame;

    public Statistics(String title, ArrayList<Integer> stats, ArrayList<Integer> sysVals, ArrayList<Integer> diaVals, JFrame linkFrame, double newSystolicValue, double newDiastolicValue) {
        super(title);
        setBounds(500, 100, 1000, 1000);
        setPreferredSize(new Dimension(1000, 800));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Instruction how to close the program
        this.setContentPane(panel1);  // the name of the panel that is mentioned in the GUI form
        this.pack();
        this.highDiastolicValue = newDiastolicValue;
        this.highSystolicValue = newSystolicValue;
        this.frame = linkFrame;

        aboveAverageCholesterol.setText("No. of monitored patients with above average cholesterol: " + stats.get(0));
        nonSmokers.setText("No. of monitored patients who have never smoked: " + stats.get(1));

        calcHighSystolic(sysVals);
        calcHighDiastolic(diaVals);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                linkFrame.setVisible(true);
                setVisible(false);
            }
        });
    }

    private void calcHighSystolic(ArrayList<Integer> sysVals){
        int highVal = 0;
        for (int i = 0; i < sysVals.size(); i ++) {
            if (sysVals.get(i) > highSystolicValue) { highVal += 1;}
        }
        highSystolic.setText("No. of monitored patients with high systolic blood pressure: " + highVal);
    }

    private void calcHighDiastolic(ArrayList<Integer> diaVals){
        int highVal = 0;
        for (int i = 0; i < diaVals.size(); i ++) {
            if (diaVals.get(i) > highDiastolicValue) { highVal += 1;}
        }
        highDiastolic.setText("No. of monitored patients with high diastolic blood pressure: " + highVal);
    }
    public JFrame getLinkFrame(){
        return this.frame;
    }

    public static void main(String[] arg) {
        // Empty
    }
}
