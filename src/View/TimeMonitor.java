package View;

import Controller.Renderer.ColumnColorRenderer;
import Model.Patient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TimeMonitor extends JFrame implements Panels {
    private JPanel panel1;
    private JTable table1;
    private JPanel header;
    private JLabel Statistics;
    private JButton backButton;
    private JLabel Data;
    private JPanel Body;
    private DefaultTableModel model;
    private ColumnColorRenderer columnRender;
    private JPanel getBodyPanel;
    private JLabel DataLabel;
    private JTextArea textArea1;
    private JFrame frame;

    public TimeMonitor(String title, ArrayList<String> PatientInfoList, JFrame linkFrame){
        super(title);
        setBounds(300, 100, 1000, 1000);

        setPreferredSize(new Dimension(1500, 800));
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Instruction how to close the program
        setContentPane(panel1);  // the name of the panel that is mentioned in the GUI form

        this.pack();
        setLocationRelativeTo(null);
        this.frame = linkFrame;



        table1.getColumnModel();

        // Initalizing the column and row model is the Data structure to implement in the Table
        model = new DefaultTableModel(0, 0) {
            // Non-editable Table
            public boolean isCellEditable(int row, int column) {
                return false;//This causes all cells to be not editable

            }
        };
        String[] columnNames = {"Patient"};

        // Setting the Column heading
        model.setColumnIdentifiers(columnNames);
        table1.setModel(model);

        // Adjusting the Column size
//        vitalDetailTable.setPreferredSize(new Dimension(300,300));
        table1.getColumnModel().getColumn(0).setPreferredWidth(150);
        table1.setRowHeight(40);
        table1.setAutoResizeMode(table1.AUTO_RESIZE_LAST_COLUMN);

        for (int i = 0; i < PatientInfoList.size(); i++){
            this.model.addRow(new Object[]{PatientInfoList.get(i)});
        }


        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                linkFrame.setVisible(true);
                setVisible(false);


    }});

    }


    private void newTableIntializing(){

//
//        // Initalizing the column and row model is the Data structure to implement in the Table
//        model = new DefaultTableModel(0, 0) {
//            // Non-editable Table
//            public boolean isCellEditable(int row, int column) {
//                return false;//This causes all cells to be not editable
//
//            }
//        };
//
//        // Setting the Column heading
//        model.setColumnIdentifiers(columnNames);
//        table1.setModel(model);
//
//        // Adjusting the Column size
////        vitalDetailTable.setPreferredSize(new Dimension(300,300));
//        table1.getColumnModel().getColumn(0).setPreferredWidth(150);
//        table1.getColumnModel().getColumn(1).setPreferredWidth(200);
//        table1.setRowHeight(40);
//        table1.setAutoResizeMode(table1.AUTO_RESIZE_LAST_COLUMN);
//
//        // Making the class for the fucntion  to highlight the colour
//        //This Class function renders the Cholestrol Column to change the colour
//        this.columnRender = new ColumnColorRenderer(Color.RED);
//        //Added the static value of Column data that can be shared multiple in the classes
//        // It would be easier to extend the functionality
//        this.columnRender.setCholestrolColumn(0);
//        this.columnRender.setNameColumn(1);
//
//        // Adding the Class at appropriate column location of Cholestrol level and Name Coloumn
//        table1.getColumnModel().getColumn(0).setCellRenderer(columnRender);
//        table1.getColumnModel().getColumn(1).setCellRenderer(columnRender);
////        TableColumn table = vitalDetailTable.getColumnModel().getColumn(selectionCol);
//
//        this.model.addRow(new Object[]{"test","test"});
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
                this.model.addRow(new Object[]{});
            }
            catch (Exception e){
                JOptionPane.showMessageDialog(null, "Could not retrive all information");
            }


        }
        return false;
    }

    public JFrame getLinkFrame(){
        return this.frame;
    }
    public static void main(String[] arg) {
        // Empty
    }


}

