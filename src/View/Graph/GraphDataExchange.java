package View.Graph;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class GraphDataExchange {
    private ChartConnector barchrt;
    private ChartConnector linechrt;
    private DefaultTableModel tableValues;
    private String Xlabel;
    private String Ylabel;
    private String label;
    private JFrame mainFrame;
    private RefreshDataChart refreshDataInChart;
    // Arraylist which contains all types of chart
    private ArrayList<ChartConnector> graph;

    // Constructor with initial visual type either bar or line
    public GraphDataExchange(Chart visual, DefaultTableModel table, String label, String Xlabel, String Ylabel, JFrame mainFrame) {
            this.graph = new ArrayList<>();
            this.refreshDataInChart =  new RefreshDataChart(this);
            if(visual == Chart.BAR){
            this.label = label;
            this.barchrt = new BarChart(label, mainFrame);
            graph.add(this.barchrt);
            this.mainFrame = mainFrame;
            updateTable(table);
            this.Xlabel = Xlabel;
            this.Ylabel = Ylabel;


        }
            if(visual == Chart.LINE){
                this.createLineChart(table,label,Xlabel,Ylabel, mainFrame);
            }
    }

    public boolean setDataforChart(Chart ch){
        System.out.println(tableValues);
        ChartConnector currentChart = new BarChart("Mock");

        // for the bar chart
        if(ch == Chart.BAR){
            currentChart = this.barchrt;
            // According to the graph, data wil be update for the chart
            if (this.tableValues.getRowCount() > 0) {
                for(int i=0; i < this.tableValues.getRowCount(); i++) {
                    System.out.println(this.tableValues.getValueAt(i,2).toString());
                    // Updating the data
                    if(!tableValues.getValueAt(i,2).toString().equals("--")) {
                        currentChart.setDataset(Double.parseDouble(this.tableValues.getValueAt(i, 2).toString()),
                                this.tableValues.getValueAt(i, 1).toString(), "");
                    }
                }
                return true;
            }
        }
        // for the line chart
        else if (ch == Chart.LINE){
            currentChart = this.linechrt;
            for(int i=0; i < this.tableValues.getColumnCount(); i++) {
                // value (y value), row (series on graph), column (x value)
                currentChart.setDataset(Double.parseDouble(this.tableValues.getValueAt(0,i).toString()),
                        this.tableValues.getValueAt(1, 0).toString(), String.valueOf(i+1) );
            }
        }

        return false;
    }
    // Giving the value for the table with the appropriate condition
    private boolean updateTable(DefaultTableModel table){

        if (table.getRowCount() > 0) {
            this.tableValues = table;
            return true;
        }
        return false;
    }

    // Specific Function to show the barchart
    public void showBarChart(){
         this.barchrt.createChart();
        ((BarChart)this.barchrt).setSize(1000, 1000);
        ((BarChart)this.barchrt).setLocationRelativeTo(null);
        ((BarChart)this.barchrt).setDefaultCloseOperation(((BarChart)this.barchrt).EXIT_ON_CLOSE);
        ((BarChart)this.barchrt).setVisible(true);
    }

    public void showLineChart(){
        this.linechrt.createChart();
        ((LineChart)this.linechrt).setSize(1000, 1000);
        ((LineChart)this.linechrt).setLocationRelativeTo(null);
        ((LineChart)this.linechrt).setDefaultCloseOperation(((LineChart)this.linechrt).EXIT_ON_CLOSE);
        ((LineChart)this.linechrt).setVisible(true);
    }

    /***
     * It is function that refreshes the current graph that is open at a moment.
     */
    public void updataChart(){

        for(ChartConnector ch: graph) {
            if(ch.isVisible())
                this.refreshDataInChart.refreshChart(ch);
        }
    }

    public void createLineChart(DefaultTableModel table, String label, String Xlabel, String Ylabel, JFrame mainFrame){
        this.label = label;
        this.linechrt = new LineChart(label, mainFrame);
        graph.add(this.linechrt);
        this.mainFrame = mainFrame;
        updateTable(table);
        this.Xlabel = Xlabel;
        this.Ylabel = Ylabel;
//        this.refreshDataInChart =  new RefreshDataChart(this);
    }
    public void creatBarChart(DefaultTableModel table, String label, String Xlabel, String Ylabel, JFrame mainFrame){
            this.label = label;
            this.barchrt = new BarChart(label, mainFrame);
            graph.add(this.barchrt);
            this.mainFrame = mainFrame;
            updateTable(table);
            this.Xlabel = Xlabel;
            this.Ylabel = Ylabel;
//        this.refreshDataInChart =  new RefreshDataChart(this);
    }
    public boolean isAnyVisibleChart(){
        for(ChartConnector ch: graph) {
            if(ch.isVisible()){
                return true;
            }
        }
        return false;
    }
}
