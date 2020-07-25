package Controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class DataModel extends DefaultTableModel {
    private JFrame mainFrame;
    public DataModel(JFrame frame){
        super();
        this.mainFrame = frame;
    }
}
