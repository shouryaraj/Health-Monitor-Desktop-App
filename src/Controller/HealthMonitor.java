package Controller;

import Model.Vitals.BloodPressure;
import Model.Vitals.Cholesterol;
import Controller.Server.EMR;
import Model.Vitals.SmokingStatus;
import View.Graph.Chart;
import Controller.Renderer.ColumnColorRenderer;
import View.Graph.GraphDataExchange;
import Model.Patient;
import Model.PatientList;
import Model.Practitioner;
import Controller.Renderer.JComboBoxCellRenderer;
import View.Statistics;
import View.TimeMonitor;
import View.UpdateThePatientData;
import View.patientDetails;
//import Controller.Renderer.JComboBoxCellRenderer;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**Main controller class to monitor the patient for the practitioner
 *
 */
public class HealthMonitor extends JFrame {
    private JPanel PanelOfPatient;
    private JPanel LeftPanel;
    private JPanel RightPanel;
    private JTable monitorTable;
    private JList listOfPatient;
    private JLabel nameOfDoctor;
    public JScrollPane scrollJTable;
    private JTable vitalDetailTable;
    private JButton patientDetail;
    private JButton delete;
    private JButton nButton;
    private JTextField threshold;
    private JTextField Jtimer;
    private JButton cholestrolThresholdButton;
    private JButton stopTimer;
    private JLabel pa;
    private JButton barChart;
    private JButton lineChart;
    private JButton statisticsButton;
    private JButton overTimeButton;
    private JButton systolicButton;
    private JTextField systolicField;
    private JButton diastolicButton;
    private JTextField diastolicField;
    private Practitioner practitioner;
    private PatientList patient;
    private DefaultListModel listPatientModel;
    private DefaultTableModel model = new DataModel(this);
    private int rowValueFromTable = -1;
    private EMR server;
    private double cholestrolThresholdValue = 10.0;
    private double systolicThresholdValue = 110.0;
    private double diastolicThresholdValue = 90.0;
    private ColumnColorRenderer columnRender;
    private Timer timer;
    private GraphDataExchange chrt;
    private Patient p;

    public HealthMonitor(String title, Practitioner practitioner, EMR server) {
        super(title);
        this.server = server;
        // Setting the layout of the frame
        setBounds(300, 100, 1000, 1000);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        setPreferredSize(new Dimension(1800, 800));
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        RightPanel.setPreferredSize(new Dimension(500, 300));

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Instruction how to close the program
        this.pack();
        setLocationRelativeTo(null);

        this.setContentPane(PanelOfPatient);  // the name of the panel that is mentioned in the GUI form

        // Stop timer should be false
        stopTimer.setEnabled(false);


        // Creating the Table Model to display the Cholestrol level
        this.newTableIntializing();

        this.practitioner = practitioner;

        // Setting the Practinoner Name
        this.labellingTheDoctorName();

        // Show initial blood pressure threshold values
        systolicField.setText(String.valueOf(systolicThresholdValue));
        diastolicField.setText(String.valueOf(diastolicThresholdValue));


        // Creating the Patient List Model to display on the JList panel i.e, Patient List
        patient = practitioner.getPatients();
        listPatientModel = new DefaultListModel();
        listOfPatient.setModel(listPatientModel);

        // Selected Row in the JTable of the Monitor
        vitalDetailTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                // do some actions here, for example
                // print first column value from selected row
                if (!event.getValueIsAdjusting()) {
                    rowValueFromTable = vitalDetailTable.getSelectedRow();
                }
            }
        });
        // Adding the patient to monitor in the Dynamic Table so click function from the Jlist
        listOfPatient.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // Check for the trigger value once "e.getValueIsAdjusting"
                if (!e.getValueIsAdjusting()) {
                    // personNumber that is index in the list of patient that's on display
                    int personNumber = listOfPatient.getSelectedIndex();

                    // It deselects it again but not to select the back again to monitor same at a time.
                    listOfPatient.clearSelection();

                    // for the empty list
                    if (personNumber >= 0) {
                        // Loop through the table to cross check for the same patient monitoring.
                        for (int i = 0; i < vitalDetailTable.getRowCount(); i++) {
                            // Checking for the same patient that is already in the Row
                            if (vitalDetailTable.getValueAt(i, 0) == patient.get(personNumber).getPatientID()) {
                                // throw an dialog box to inform the user
                                JOptionPane.showMessageDialog(null, "You can't add same person " +
                                                "twice at a time to monitor.", "InfoBox: " + "Error",
                                        JOptionPane.INFORMATION_MESSAGE);
                                return;
                            }
                        }
                        // Checking for the cholesterol value before Adding to the scrollJTable
                        if (cholestrolData(patient.get(personNumber)) && bloodPressureData(patient.get(personNumber))) {
                            smokingStatusData(patient.get(personNumber));
                            setJtableData(patient.get(personNumber));
                            // Calculating the average after the addition of the new monitor patient
                            averageCholestrolDataRender(columnRender);
                        }

                    }
                }
            }
        });

        // Delete Button For the Event Listener
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // check if the JTable is empty.
                if (model.getRowCount() > 0) {
                    // check the delete button pressed without selection of the row in Jtable
                    if (rowValueFromTable != -1) {
                        // remove selected row from the model
                        model.removeRow(rowValueFromTable);
                        // recalculating the average Cholestrol data from the rest of the data
                        averageCholestrolDataRender(columnRender);

                        JOptionPane.showMessageDialog(null, "Selected row deleted successfully");
                    } else {
                        JOptionPane.showMessageDialog(null, "Select Patient to Delete");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "There is no patient for monitoring");
                }
            }
        });

        // Patient Detail third page launch
        patientDetail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // check for selected row first
                if (model.getRowCount() > 0) {
                    if (rowValueFromTable != -1) {
                        Object selectedId = model.getValueAt(rowValueFromTable, 0);
                        for (int i = 0; i < patient.length(); i++) {
                            Patient p = patient.get(i);
                            if (p.getPatientID() == selectedId.toString()) {
                                patientDetails pat = new patientDetails("Details", p, HealthMonitor.this);
                                pat.setVisible(true);
                                setVisible(false);
                                break;
                            }

                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Select Patient to see Details");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "There is no patient for monitoring");
                }

            }
        });

        // Statistics page
        statisticsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check that at least one patient is being monitored
                if (model.getRowCount() > 0) {
                    // Create list with number of patient with above average cholesterol and number of patients who are non-smokers
                    ArrayList<Integer> stats = new ArrayList<>();
                    stats.add(0);  // above average cholesterol
                    stats.add(0);  // number of non-smokers
                    ArrayList<Integer> sysVals = new ArrayList<>(); // list with all systolic values
                    ArrayList<Integer> diaVals = new ArrayList<>(); // list with all diastolic values
                    double aveCholVal = averageCholestrol(); // get average value of cholesterol
                    // Loop through each patient being monitored
                    for (int i = 0; i < vitalDetailTable.getRowCount(); i++) {
                        p = patient.get(i);
                        // Loop through each patient in the whole list
                        for (int j = 0; j < patient.length(); j++) {
                            p = patient.get(j);
                            // Find the patient object
                            if (p.getPatientID() == vitalDetailTable.getValueAt(i, 0).toString()) {
                                break;
                            }
                        }
                        // If this patient has above average cholesterol, increment the number who are above average
                        if ((Double) p.getCholesterol().getMeasurement() > aveCholVal) {
                            stats.set(0, stats.get(0) + 1);
                        }
                        // Add this patient's systolic and diastolic values to the lists
                        sysVals.add(p.getBloodPressure().getSystolicValue());
                        diaVals.add(p.getBloodPressure().getDiastolicValue());
                        // If this patient is not a smoker, increment the number who are non-smokers
                        if (p.smokingStatus().getMeasurement()) {
                            stats.set(1, stats.get(1) + 1);
                        }
                    }
                    Statistics pat = new Statistics("Details", stats, sysVals, diaVals, HealthMonitor.this, systolicThresholdValue, diastolicThresholdValue);
                    pat.setVisible(true);
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "There is no patient for monitoring");
                }
            }
        });

        // Monitor Over Time page
        overTimeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ArrayList<String> patientSystolicInfoList = new ArrayList<>();

                for (int i = 0; i < vitalDetailTable.getRowCount(); i++) {
                    String currentPatientID = vitalDetailTable.getValueAt(i, 0).toString();
                    Patient currentPatient = patient.getPatientWithID(currentPatientID);
                    String patientInfo = previousNSystolicBloodPressureData(currentPatient, 5);
                    patientSystolicInfoList.add(patientInfo);
                }


                TimeMonitor pat = new TimeMonitor("Monitor Over Time", patientSystolicInfoList, HealthMonitor.this);
                pat.setVisible(true);
                setVisible(false);

            }
        });

        // N button for the execution of the timer
        nButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NButtonFunction();

            }
        });

        // Threshold button to stop the average calculation and update according the given threshold value
        cholestrolThresholdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Error handling for empty input
                if (!threshold.getText().equals("")) {
                    // Catch error if there is no numerical value
                    try {
                        // Value should be a Double
                        cholestrolThresholdValue = Double.parseDouble(threshold.getText());
                        averageCholestrolDataRender(columnRender);
                        // Updating cell at runtime the columns according to the threshold value
                        for (int x = 0; x <= vitalDetailTable.getRowCount(); x++) {
                            model.fireTableCellUpdated(x, 1);
                            model.fireTableCellUpdated(x, 2);
                        }

                    } catch (Exception b) {
                        JOptionPane.showMessageDialog(null, "Please provide the numerical value");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please provide the numerical value");
                }


            }


        });

        stopTimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.stop();
                stopTimer.setEnabled(false);
                Jtimer.setText("");
            }
        });

        barChart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(chrt == null) {
                    chrt = new GraphDataExchange(Chart.BAR, model, "Cholesterol Monitor", "Patient Name", "Cholesterol Value", HealthMonitor.this);
                    chrt.setDataforChart(Chart.BAR);
                    chrt.showBarChart();
                }
                else{
                    if(!chrt.isAnyVisibleChart()) {
                        chrt.creatBarChart(model, "Cholesterol Monitor", "Patient Name", "Cholesterol Value", HealthMonitor.this);
                        chrt.setDataforChart(Chart.BAR);
                        chrt.showBarChart();
                    }
                      else{
                        JOptionPane.showMessageDialog(null, "Close the other Graph chart");
                    }
                }

            }
        });

        lineChart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check that at least one patient is being monitored
                if (model.getRowCount() > 0) {
                    // Check that a patient has been selected
                    if (rowValueFromTable != -1) {
                        // Create default table model
                        DefaultTableModel lineChartModel = new DefaultTableModel();
                        // get selected patient id
                        Object selectedId = model.getValueAt(rowValueFromTable, 0);
                        int n = 5;
                        // loop through patients
                        for (int i = 0; i < patient.length(); i++) {
                            Patient p = patient.get(i);
                            // if patient object is select patient
                            if (p.getPatientID() == selectedId.toString()) {
                                // get last 5 blood pressure readings for this patient
                                ArrayList<BloodPressure> bloodPressures = previousNBloodPressureData(p, n);
                                n = bloodPressures.size();  // sometimes not all readings are valid so size(bloodPressures) =\= 5
                                Object[] bps = new Object[n];  // make object list of blood pressure values
                                for (int j = 0; j < n; j++) {
                                    bps[j] = bloodPressures.get(j).getSystolicValue();  // putting systolic values into object list

                                    lineChartModel.addColumn(bps[j]);  // Allocate column space (could be any value)
                                }
                                lineChartModel.addRow(bps);  // Add blood pressure values
                                lineChartModel.addRow(new Object[]{p.getName()});  // Add patient's name
                                // break because we found the right patient
                                break;

                            }
                        }
                        // put the default table model into the line graph
                        if(chrt == null) {

                            chrt = new GraphDataExchange(Chart.LINE, lineChartModel, "Blood Pressure Monitor", "Patient Name", "Cholesterol Value", HealthMonitor.this);
                            chrt.setDataforChart(Chart.LINE);
                            chrt.showLineChart();
                        }
                        else{
                            if(!chrt.isAnyVisibleChart()) {
                                chrt.createLineChart(lineChartModel, "Blood Pressure Monitor", "Patient Name", "Cholesterol Value", HealthMonitor.this);
                                chrt.setDataforChart(Chart.LINE);
                                chrt.showLineChart();
                            }
                            else{
                                JOptionPane.showMessageDialog(null, "Close the other Graph chart");
                            }

                        }

                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Select Patient to view");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "There is no patient for monitoring");
                }
            }
        });

        systolicButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!systolicField.getText().equals("")) {
                    systolicThresholdValue = Double.parseDouble(systolicField.getText());

                    HealthMonitor.this.columnRender.setMinSystolicBP(systolicThresholdValue);
                    for (int x = 0; x <= vitalDetailTable.getRowCount(); x++) {
                        model.fireTableCellUpdated(x, 4);
                        model.fireTableCellUpdated(x, 5);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please provide the numerical value");
                }
            }
        });

        diastolicButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!diastolicField.getText().equals("")) {
                    diastolicThresholdValue = Double.parseDouble(diastolicField.getText());
                    HealthMonitor.this.columnRender.setMinDiastolicBP(diastolicThresholdValue);
                    for (int x = 0; x <= vitalDetailTable.getRowCount(); x++) {
                        model.fireTableCellUpdated(x, 4);
                        model.fireTableCellUpdated(x, 5);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please provide the numerical value");
                }
            }
        });
    }

    /**
     * Helper function for the Table Initializing
     */

    private void newTableIntializing() {
        // Variable names to avoid the magic numbers
        int IDCol = 0;
        int nameCol = 1;
        int cholestrolCol = 2;
        int timeCol = 3;
        int systolicBpCol = 4;
        int diastolicBpCol = 5;
        int bpTimeCol = 6;
        int monitorOptionCol = 7;


        String[] columnNames = {"ID", "Name", "Total Cholestrol", "Time", "Systolic Blood Pressure", "Diastolic Blood Pressure", "Time", "Monitor Option"};

        // Initalizing the coloumn and row model is the Data structure to implement in the Table
        model = new DefaultTableModel(0, 0) {
            // Non-editable Table
            public boolean isCellEditable(int row, int column) {
                if (column == monitorOptionCol) {
                    return true;
                }
                return false;//This causes all cells to be not editable

            }
        };

        // Setting the Coloumn heading
        model.setColumnIdentifiers(columnNames);
        vitalDetailTable.setModel(model);


        // Adjusting the Coloumn size
//        vitalDetailTable.setPreferredSize(new Dimension(300,300));
        vitalDetailTable.getColumnModel().getColumn(IDCol).setPreferredWidth(150);
        vitalDetailTable.getColumnModel().getColumn(nameCol).setPreferredWidth(200);
        vitalDetailTable.getColumnModel().getColumn(cholestrolCol).setPreferredWidth(230);
        vitalDetailTable.getColumnModel().getColumn(timeCol).setPreferredWidth(400);
        vitalDetailTable.getColumnModel().getColumn(systolicBpCol).setPreferredWidth(230);
        vitalDetailTable.getColumnModel().getColumn(diastolicBpCol).setPreferredWidth(230);
        vitalDetailTable.getColumnModel().getColumn(bpTimeCol).setPreferredWidth(400);
        vitalDetailTable.getColumnModel().getColumn(monitorOptionCol).setPreferredWidth(230);
        vitalDetailTable.setRowHeight(40);
        vitalDetailTable.setAutoResizeMode(vitalDetailTable.AUTO_RESIZE_LAST_COLUMN);

        // Making the class for the fucntion  to highlight the colour
        //This Class function renders the Cholestrol Column to change the colour
        this.columnRender = new ColumnColorRenderer(Color.RED);

        //Added the static value of Column data that can be shared multiple in the classes
        // It would be easier to extend the functionality
        this.columnRender.setCholestrolColumn(cholestrolCol);
        this.columnRender.setNameColumn(nameCol);
        this.columnRender.setSystolicBPColumn(systolicBpCol);
        this.columnRender.setDiastoicBPColumn(diastolicBpCol);

        //The default minimum value for the minDiastoic and systolic value
        this.columnRender.setMinDiastolicBP(diastolicThresholdValue);
        this.columnRender.setMinSystolicBP(systolicThresholdValue);

        // Adding the Class at appropriate column location of Cholestrol level and Name Coloumn
        vitalDetailTable.getColumnModel().getColumn(cholestrolCol).setCellRenderer(columnRender);
        vitalDetailTable.getColumnModel().getColumn(nameCol).setCellRenderer(columnRender);
        vitalDetailTable.getColumnModel().getColumn(systolicBpCol).setCellRenderer(columnRender);
        vitalDetailTable.getColumnModel().getColumn(diastolicBpCol).setCellRenderer(this.columnRender);
//        TableColumn table = vitalDetailTable.getColumnModel().getColumn(selectionCol);

        TableColumn tmpColum = this.vitalDetailTable.getColumnModel().getColumn(monitorOptionCol);
        String[] DATA = new String[]{"Hello", "Blood Pressure", "Both"};
        JComboBox comboBox1 = new JComboBox(DATA);

       // Creating the class that helps to share the JcomboBox across the row
        actionBox box = new actionBox(comboBox1);

        // Creating Item Listener for the selection of the choice in the Jcoloumn box
        ItemListener action = new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent event) {
                // Execute when item is selected
                if (event.getStateChange() == ItemEvent.SELECTED) {

                    Object item = event.getItem();
                    // getting the shared jcomboBox and use it to select the choice
                    JComboBox comboBox = box.getCombox();

                    // This function only excute only when selectedrow is selected
                    if (HealthMonitor.this.vitalDetailTable.getSelectedRow() > -1) {
                        // Initialised value, that doesn't create any side effects
                        Patient p = practitioner.getPatients().get(0);

                        // getting the selected row patient
                        for (int j = 0; j < practitioner.getPatients().length(); j++) {
                            p = practitioner.getPatients().get(j);
                            if (HealthMonitor.this.vitalDetailTable.getValueAt(HealthMonitor.this.vitalDetailTable.getSelectedRow(), IDCol) == p.getPatientID()) {
                                break;
                            }
                        }


                        // Check for the selected item is equal to the cholesterol
                        if (item.equals("Cholestrol")) {
                            // fetching the cholesterol value, it can be optimized if the selected item was the cholesterol
                            cholestrolData(p);


                            // Firing the changes in the table value including the cholesterol values plus the updated value index
                            // It has to show the red colour for the average cholestrol value
                            HealthMonitor.this.vitalDetailTable.getModel().setValueAt(p.getCholesterol().toStringVitals(), HealthMonitor.this.vitalDetailTable.getSelectedRow(), cholestrolCol);
                            HealthMonitor.this.vitalDetailTable.getModel().setValueAt(p.getCholesterol().toStringTime(), HealthMonitor.this.vitalDetailTable.getSelectedRow(), timeCol);
                            HealthMonitor.this.vitalDetailTable.getModel().setValueAt("--", HealthMonitor.this.vitalDetailTable.getSelectedRow(), diastolicBpCol);
                            HealthMonitor.this.vitalDetailTable.getModel().setValueAt("--", HealthMonitor.this.vitalDetailTable.getSelectedRow(), systolicBpCol);
                            HealthMonitor.this.vitalDetailTable.getModel().setValueAt("--", HealthMonitor.this.vitalDetailTable.getSelectedRow(), bpTimeCol);
                            // Updating the cholesterol value just after the new updated values in the Row
                            System.out.println(averageCholestrol() + "  Average Value of the cholestrol ");
                            System.out.println(HealthMonitor.this.vitalDetailTable.getSelectedRow());
                            HealthMonitor.this.averageCholestrolDataRender(columnRender);
                            comboBox.setSelectedIndex(-1);
//                            for (int x = 0; x < HealthMonitor.this.vitalDetailTable.getColumnCount() - 1; x++) {
//
//
////                                model.fireTableCellUpdated(HealthMonitor.this.vitalDetailTable.getSelectedRow(), x);
//                            }
//

                        }
                        // Check for the selected item is equal to the blood pressure
                        else if (item.equals("Blood Pressure")) {
                            // fetching the Blood Pressure data value, it can be optimized if the selected item was the cholesterol

                            bloodPressureData(p);


                            // Firing the changes in the table value including the cholesterol values plus the updated value index
                            // It has to show the red colour for the average cholestrol value
                            HealthMonitor.this.vitalDetailTable.getModel().setValueAt(p.getBloodPressure().getSystolicValueString(), HealthMonitor.this.vitalDetailTable.getSelectedRow(), systolicBpCol);
                            HealthMonitor.this.vitalDetailTable.getModel().setValueAt(p.getBloodPressure().getDiastolicValueString(), HealthMonitor.this.vitalDetailTable.getSelectedRow(), diastolicBpCol);
                            HealthMonitor.this.vitalDetailTable.getModel().setValueAt(p.getBloodPressure().getEffectiveTimeDate().toString(), HealthMonitor.this.vitalDetailTable.getSelectedRow(), bpTimeCol);
                            HealthMonitor.this.vitalDetailTable.getModel().setValueAt("--", HealthMonitor.this.vitalDetailTable.getSelectedRow(), cholestrolCol);
                            HealthMonitor.this.vitalDetailTable.getModel().setValueAt("--", HealthMonitor.this.vitalDetailTable.getSelectedRow(), timeCol);

                            HealthMonitor.this.averageCholestrolDataRender(columnRender);
                            System.out.println(averageCholestrol() + "  Average Value of the chol from THE BP >>>>>> ");
                            System.out.println(HealthMonitor.this.vitalDetailTable.getSelectedRow());
                            comboBox.setSelectedIndex(-1);
//                            model.fireTableRowsUpdated(HealthMonitor.this.vitalDetailTable.getSelectedRow(), HealthMonitor.this.vitalDetailTable.getSelectedRow());

                        }
                        // Check for the item is equal to the both
                        else if (item.equals("Both")) {

                            bloodPressureData(p);
                            cholestrolData(p);


                            // Firing the changes in the table value including the cholesterol values plus the updated value index
                            // It has to show the red colour for the average cholestrol value
                            HealthMonitor.this.vitalDetailTable.getModel().setValueAt(p.getCholesterol().toStringVitals(), HealthMonitor.this.vitalDetailTable.getSelectedRow(), cholestrolCol);
                            HealthMonitor.this.vitalDetailTable.getModel().setValueAt(p.getCholesterol().toStringTime(), HealthMonitor.this.vitalDetailTable.getSelectedRow(), timeCol);
                            HealthMonitor.this.vitalDetailTable.getModel().setValueAt(p.getBloodPressure().getSystolicValueString(), HealthMonitor.this.vitalDetailTable.getSelectedRow(), systolicBpCol);
                            HealthMonitor.this.vitalDetailTable.getModel().setValueAt(p.getBloodPressure().getDiastolicValueString(), HealthMonitor.this.vitalDetailTable.getSelectedRow(), diastolicBpCol);
                            HealthMonitor.this.vitalDetailTable.getModel().setValueAt(p.getBloodPressure().getEffectiveTimeDate().toString(), HealthMonitor.this.vitalDetailTable.getSelectedRow(), bpTimeCol);
                            HealthMonitor.this.averageCholestrolDataRender(columnRender);
                            System.out.println(averageCholestrol() + "  Average Value of the chol from THE BOTH .............. >>>>>> ");
                            System.out.println(HealthMonitor.this.vitalDetailTable.getSelectedRow());
                            comboBox.setSelectedIndex(-1);
//                            model.fireTableRowsUpdated(HealthMonitor.this.vitalDetailTable.getSelectedRow(), HealthMonitor.this.vitalDetailTable.getSelectedRow());

                        }

                        // Fire up the change in the table after updating the data on the table
                            model.fireTableDataChanged();
                        // Changing for current opened chart
                        if (chrt != null){
                            chrt.updataChart();
                        }




                    }

//                    System.out.println("Interesting Jcombo: " + String.valueOf(HealthMonitor.this.vitalDetailTable.getSelectedRow()));

                }

            }



        };


        // Adding default cell editor to the the last column that is monitor option
        this.vitalDetailTable.getColumnModel().

            getColumn(monitorOptionCol).

            setCellRenderer(new JComboBoxCellRenderer(comboBox1, action, box));



    }


    /*
    *@Helper fucntion for the Nbutton to implement the timer functionality as per users input to update the data
     */
    private void NButtonFunction(){
        // Loading the text from the N input
        String value = Jtimer.getText();
        int milisecond = 1000;
        // try catch for the wrong and non-numerical value
        try {

            // Coverting the value into integer to make it use for int comparison
            int intValue = Integer.parseInt(value);
            // N value should be greater than 30 and non empty.
            if (!value.equals("") && intValue >= 10) {

                // Intialize the timer to execute it
                this.timer = new Timer(Integer.parseInt(value) * milisecond, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        // Call the Timer Function to update each row again by updating the values also
                        timerN();

                        //
                        if (chrt != null) {
                            // Refreshing all charts that are present in the data exchange
                            chrt.updataChart();
                        }
                        // Recalculating the average value from the updated values
                        averageCholestrolDataRender(columnRender);
                        System.out.println("I am TIMER THE GREAT!");
                    }
                });
                stopTimer.setEnabled(true);
                timer.setRepeats(true); // Only execute once
                timer.start(); // Go go go!

            } else {
                JOptionPane.showMessageDialog(null, "Number of second should be more than 10 seconds");
            }


        } catch (Exception x) {

            JOptionPane.showMessageDialog(null, "Wrong Input");
        }
    }

    /**
    *@param p Patient class to get the Cholestrol data as per the call
     * @return boolean Value if the for the cholesterol entry as per the server response
     */

    public boolean cholestrolData(Patient p){
        UpdateThePatientData patient = new UpdateThePatientData(p);
        try {
            // Server calling for the cholestrolData
            Cholesterol patientCholesterol = server.getPatientCholesterolData(p.getPatientID());
            patient.updateCholestrolValue(patientCholesterol);

        } catch (Exception e) {
           return false;
        }
        return true;
    }

    /**
     *@param p Patient class to get the blood pressure data as per the call
     * @return boolean Value if the for the blood pressure entry as per the server response
     */

    public boolean bloodPressureData(Patient p){
        UpdateThePatientData patient = new UpdateThePatientData(p);
        try {
            // Server calling for the bloodPressureData
            BloodPressure patientBloodPressure = server.getPatientBloodPressureData(p.getPatientID());
            patient.updateBloodPressure(patientBloodPressure);

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Gest previous N BP values
     *@param p Patient class to get the blood pressure data as per the call
     *@param n number of previous values
     * @return ArrayList of BP
     */

    public  ArrayList<BloodPressure> previousNBloodPressureData(Patient p,int n){
        ArrayList<BloodPressure> previousBP = new ArrayList<>();
        try {
            // Server calling for the previous blood pressure info
            previousBP = server.getPreviousPatientBloodPressureData(p.getPatientID(),n);
            return previousBP;
        } catch (Exception e) {
            return previousBP;
        }
    }

    /**
     * Gest previous N BP values
     *@param p Patient class to get the blood pressure data as per the call
     *@param n number of previous values
     * @return ArrayList of BP as a string
     */
    public String previousNSystolicBloodPressureData(Patient p,int n){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        String retVal = p.getName() + ": ";
        try {
            retVal.concat(p.getName() + ": ");
            // Server calling for the previous blood pressure info
            ArrayList<BloodPressure> bpData = server.getPreviousPatientBloodPressureData(p.getPatientID(),n);
            for (int i = 0 ; i < bpData.size(); i++){
                String systolicVal = bpData.get(i).getSystolicValueString();
                String issueDate = sdf.format(bpData.get(i).getEffectiveTimeDate()).toString();
                retVal = retVal + systolicVal + " (" + issueDate +"), ";
            }
            return retVal;
        } catch (Exception e) {
            return retVal;
        }
    }

    public boolean smokingStatusData(Patient p){
        UpdateThePatientData patient = new UpdateThePatientData(p);
        try {
            // Server calling for the smokingStatusData
            SmokingStatus patientSmokingData = server.getPatientSmokingData(p.getPatientID());
            patient.updateSmokingDetails(patientSmokingData);

        } catch (Exception e) {
            return false;
        }
        return true;
    }



    /**
     * timer function exectues after N seconds as per user input and operates deletes all the row. Fetch the fresh data
     * from the server and update the table again
     *
     */
    public void timerN(){
        Patient p;
        ArrayList<Object> IdArray = new ArrayList<>();
        // adding the patient Id in teh IdArray
        for(int i = 0; i <this.model.getRowCount(); i++)
        {
            IdArray.add(this.vitalDetailTable.getValueAt(i,0));
//
        }
        // Getting the patient object from the Patient ID using the practitioner data
        for (int i =0; i <IdArray.size(); i++) {
            // Checking for the selected vitals that has to be updated for the cholesterol
            if (!this.vitalDetailTable.getValueAt(i, 2).toString().equals("--")) {

                // Finding the patient in the practitioner list
                for (int j = 0; j < practitioner.getPatients().length(); j++) {
                    p = practitioner.getPatients().get(j);
                    if (IdArray.get(i) == p.getPatientID()) {
                        UpdateThePatientData patient = new UpdateThePatientData(p);

                        try {
                            // Updating the data from the server
                            Cholesterol patientCholesterol = server.getPatientCholesterolData(p.getPatientID());
                            patient.updateCholestrolValue(patientCholesterol);

                            // Data doesn't update so frequently so updated using mock data
                            // Mock data to verify the working of the Jtimer for the Random ID value
                            if (i == (int) (Math.random() * (this.model.getRowCount() - 0 + 1) + 0)) {
                                double weight = (int) (Math.random() * (250 - 100 + 1)) + 100;
                                Date myDate = new Date(2014, 02, 11);
                                System.out.println(weight + "-______Weight ");
                                Cholesterol c = new Cholesterol(weight, myDate);
                                patient.updateCholestrolValue(c);
                                this.vitalDetailTable.setValueAt(p.getCholesterol().toStringVitals(), i, 2);
                                this.vitalDetailTable.setValueAt(p.getCholesterol().toStringTime(), i, 3);


                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    ;
                }
                ;
            }

            // Checking for the selected vitals that has to be updated for the Bloodpressure
            if (!this.vitalDetailTable.getValueAt(i, 4).toString().equals("--")) {
                if (!this.vitalDetailTable.getValueAt(i, 2).toString().equals("--")) {
                    for (int j = 0; j < practitioner.getPatients().length(); j++) {
                        p = practitioner.getPatients().get(j);
                        if (IdArray.get(i) == p.getPatientID()) {
                            UpdateThePatientData patient = new UpdateThePatientData(p);
                            try {
                                BloodPressure patientBloodPressure = server.getPatientBloodPressureData(p.getPatientID());
                                patient.updateBloodPressure(patientBloodPressure);
                                // Mock data to verify the working of the Jtimer for the Random ID value
                                if (i == (int) (Math.random() * (this.model.getRowCount() - 0 + 1) + 0)) {
                                    int weight = (int) (Math.random() * (250 - 100 + 1)) + 100;
                                    Date myDate = new Date(2014, 02, 11);
                                    System.out.println(weight + "-______Weight ");
                                    BloodPressure bp = new BloodPressure(weight, weight - 20, myDate, p.getPatientID());
                                    patient.updateBloodPressure(bp);
                                    this.vitalDetailTable.setValueAt(p.getBloodPressure().getSystolicValueString(), i, 4);
                                    this.vitalDetailTable.setValueAt(p.getBloodPressure().getDiastolicValueString(), i, 5);


                                }


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        ;
                    }
                    ;

                }
            }
        }

        // Firing the change in each cell after updating the model data in timer except the last column

        for(int x=0; x < this.vitalDetailTable.getRowCount()-1; x++ ){
            for (int y=0; y< this.vitalDetailTable.getColumnCount()-2; y++){
                model.fireTableCellUpdated(x,y);
            }
        }






    }

    /**
     *
     * @param p Get the patient class and sets the values at Jtable by adding to the data model
     * @return
     */
    public boolean setJtableData(Patient p) {

        if (p != null) {
            try {
                // Adding the row with the details
                this.model.addRow(new Object[]{p.getPatientID(), p.getName(), p.getCholesterol().toStringVitals(), p.getCholesterol().toStringTime(),
                        p.getBloodPressure().getSystolicValueString(),p.getBloodPressure().getDiastolicValueString(),p.getBloodPressure().getEffectiveTimeDate().toString()});
            }
            catch (Exception e){
                //JOptionPane.showMessageDialog(null, "Could not retrive all information");
            }


        }
        return false;
    }

    /**
     * refreshPatientList executes when patient list updates on the Jlist panel i.e, left side panel
     */

    public void refreshPatientList(){
        listPatientModel.removeAllElements();
        Patient p;
        for (int i =0; i< patient.length(); i++){

            p = patient.get(i);
//            System.out.println("Adding person to the list" + p.getName());
            listPatientModel.addElement(p.getName());
        }//8494189
    }

    /**
     *
     * @param forCholestrolColumn  Coloumn Render class fucntion to render the cholestrol coloumn for the specific change
     */
    private void averageCholestrolDataRender(ColumnColorRenderer forCholestrolColumn) {
        Double cholestrolValue = 0.0;
        Double sum = 0.0;
        Double averageCholestrolValue = 0.0;
        if (this.vitalDetailTable.getRowCount() >= 2) {
            // Threshold value from use should be greater than 10 or threshold value should be empty
            if (this.cholestrolThresholdValue <= 10 || threshold.getText().equals("")) {
                // for cholestrol column set average to render the cell according the value
                averageCholestrolValue = averageCholestrol();

                forCholestrolColumn.setAverageNumber(averageCholestrolValue);

            }
            // If the thresholdvalue is more than 10 and not empty then execute threhold as a average value
            else {
                averageCholestrolValue = this.cholestrolThresholdValue;

                forCholestrolColumn.setAverageNumber(averageCholestrolValue);
                // for Name column set average to render the cell according the value
//                forNameColumn.setAverageNumber(averageCholestrolValue);
//            System.out.println(averageCholestrolValue);


            }
        }
        else{
            averageCholestrolValue = Double.POSITIVE_INFINITY;
            // for cholestrol column set average to render the cell according the value
            forCholestrolColumn.setAverageNumber(averageCholestrolValue);
            // for Name column set average to render the cell according the value
//            forNameColumn.setAverageNumber(averageCholestrolValue);
//            System.out.println(averageCholestrolValue);
        }
    }
    private double averageCholestrol() {
        Double averageCholestrolValue = 0.0;
        Double cholestrolValue = 0.0;
        Double sum = 0.0;
        Double countOfCholestrolValue = 0.0;
        if (this.vitalDetailTable.getRowCount() > 0) {
            // Calculating the average value by looping through each value
            for (int i = 0; i < this.vitalDetailTable.getRowCount(); i++) {
//                System.out.println(vitalDetailTable.getValueAt(i, 2));
                if(!this.vitalDetailTable.getValueAt(i, 2).toString().equals("--")) {
                    cholestrolValue = Double.parseDouble(this.vitalDetailTable.getValueAt(i, 2).toString());
                    sum = sum + cholestrolValue;
                    countOfCholestrolValue++;
                }

            }

            // Calculating the average cholestrol value
            averageCholestrolValue = sum / countOfCholestrolValue;


        }
        return averageCholestrolValue;
    }
    /**
     * labelling the Doctor Name at the top of the monitor
     */
    public void labellingTheDoctorName(){
        this.nameOfDoctor.setText("Dr."+this.practitioner.getFirstName() + " ID: "+ this.practitioner.getPractitionerID());
    }

    public JScrollPane getScrollJTable() {
        return scrollJTable;
    }

    // Main function that runs the UI function
    public static void main(String[] arg) {
    }
}



