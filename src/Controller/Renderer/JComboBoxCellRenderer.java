package Controller.Renderer;

import Controller.actionBox;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ItemListener;

public class JComboBoxCellRenderer extends DefaultTableCellRenderer implements Render{
    JComboBox combo;
    ItemListener action;
    private actionBox box;

    public JComboBoxCellRenderer(JComboBox comboBox, ItemListener action, actionBox box) {
        this.combo = new JComboBox();
        this.action = action;
        this.box = box;
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            combo.addItem(comboBox.getItemAt(i));
        }
    }

    public Component getTableCellRendererComponent(JTable jtable, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component cell = super.getTableCellRendererComponent(jtable, value, isSelected, hasFocus, row, column);
        this.combo.setSelectedItem(jtable.getValueAt(0, column));


        JComboBox newCombo;
        String[] DATA = new String[]{"Both", "Cholestrol", "Blood Pressure"};
        newCombo = new JComboBox(DATA);


//                else
        if (value != null) {
            newCombo.setSelectedItem(value);

        } else if (value == null && row == (jtable.getRowCount() - 1) && (cell.getComponentAt(row, column) instanceof JComboBox)) {
            JComboBox box = (JComboBox) cell.getComponentAt(row, column);
            System.out.println("Hello  " + box.getSelectedItem());

            newCombo.setSelectedItem(box.getSelectedItem());
        } else {
            newCombo.setSelectedItem(newCombo.getItemAt(0));
        }

        this.box.setCombox(newCombo);
        newCombo.addItemListener(this.action);
        TableColumn tmpColum = jtable.getColumnModel().getColumn(7);
        DefaultCellEditor defaultCellEditor = new DefaultCellEditor(box.getCombox());
        tmpColum.setCellEditor(defaultCellEditor);


        return newCombo;


    }

}