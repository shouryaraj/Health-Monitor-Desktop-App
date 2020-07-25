package Controller.Renderer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
// Customize the code to set the background and foreground color for each column of a JTable
public class ColumnColorRenderer extends DefaultTableCellRenderer implements Render {
    Color foregroundColor;
    private Double averageNumber = Double.POSITIVE_INFINITY;
    private Double minSystolicBP;
    private Double minDiastolicBP;
    private static int cholestrolColumn;
    private  static int nameColumn;
    private static int systolicBPColumn;
    private static int diastoicBPColumn;



    public ColumnColorRenderer(Color foregroundColor) {
        super();
        this.foregroundColor = foregroundColor;

    }public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,   boolean hasFocus, int row, int column) {
        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (!value.equals("--") && !value.equals("N/A")) {
            if (column == this.cholestrolColumn) {
                if (Double.parseDouble(value.toString()) >= averageNumber) {
                    cell.setForeground(foregroundColor);
                } else {
                    cell.setForeground(Color.BLACK);
                }
            } else if (column == this.nameColumn) {
                if(!table.getValueAt(row, this.cholestrolColumn).toString().equals("--") && !table.getValueAt(row, this.cholestrolColumn).toString().equals("N/A"))
                    if (Double.parseDouble(table.getValueAt(row, this.cholestrolColumn).toString()) >= averageNumber) {
                        cell.setForeground(foregroundColor);
                    } else {
                        cell.setForeground(Color.BLACK);
                    }
            }
            if(table.getColumnName(column) != "Name"){
                if (column == this.systolicBPColumn) {
                    if (value != null && Double.parseDouble(table.getValueAt(row, this.systolicBPColumn).toString().split(" ", 2)[0]) >= this.minSystolicBP) {
                        cell.setForeground(Color.blue);
                    } else {
                        cell.setForeground(Color.BLACK);
                    }
                }
                if (column == this.diastoicBPColumn) {
                    if (Double.parseDouble(table.getValueAt(row, this.diastoicBPColumn).toString().split(" ", 2)[0]) >= this.minDiastolicBP) {
                        cell.setForeground(Color.blue);
                    } else {
                        cell.setForeground(Color.BLACK);
                    }
                }
            }
        }
        else{
            cell.setForeground(Color.BLACK);
        }



        return cell;

    }

    public Double getAverageNumber() {
        return averageNumber;
    }
    public void setAverageNumber(Double averageNumber) {
        this.averageNumber = averageNumber;
    }
    public void setMinSystolicBP(Double minSystolicBP) {
        this.minSystolicBP = minSystolicBP;
    }
    public Double getMinSystolicBP(Double minSystolicBP) {
        return this.minSystolicBP;
    }

    public void setMinDiastolicBP(Double minDiastolicBP) {
        this.minDiastolicBP = minDiastolicBP;
    }


    public void setCholestrolColumn(int value){
        this.cholestrolColumn = value;
    }

    public void setNameColumn(int nameColumn) {
        this.nameColumn = nameColumn;
    }

    public void setSystolicBPColumn(int systolicBPColumn) {
        this.systolicBPColumn = systolicBPColumn;
    }

    public void setDiastoicBPColumn(int diastoicBPColumn) {
        this.diastoicBPColumn = diastoicBPColumn;
    }
}