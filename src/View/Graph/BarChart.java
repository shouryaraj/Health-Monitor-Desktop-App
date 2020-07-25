package View.Graph;

import javax.swing.*;

//import com.sun.javaws.util.JfxHelper;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart. renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/***
 * The Bar Chart Function extends the Jframe and implements chartconnector to provide a bar chart visual interface
 */
public class BarChart extends JFrame implements ChartConnector {

    private static final long serialVersionUID = 1L;
    private DefaultCategoryDataset dataset;
    private JFreeChart chart;
    private JPanel basePanel;
    private  JButton back;
    public JButton Ntimer;
    private RefreshDataChart refresh;
    private Timer timer;
    private JFrame mainFrame;
//    private ChartFactory chart;



    public BarChart(String appTitle) {
        super(appTitle);
        this.dataset = new DefaultCategoryDataset();
        this.basePanel = new JPanel();
        this.back = new JButton(); // Dummy Button
        add(this.basePanel);

        // Create Dataset
//        CategoryDataset dataset = createDataset();
    }
    public BarChart(String appTitle, JFrame mainFrame){
        super(appTitle);
        this.mainFrame =mainFrame;

        this.dataset = new DefaultCategoryDataset();
        this.basePanel = new JPanel();
        this.back = new JButton(); // Dummy Button
        add(this.basePanel);
    }

    /***
     * Sets the data for the bar chart
     * @param value: the value for the dataset
     * @param rowKey: row index
     * @param columnKey column index
     */

    public void setDataset(double value, String rowKey, String columnKey) {
        this.dataset.addValue(value, rowKey, columnKey);
    }
    public boolean removeDataSet(Object confirm){
        if (confirm instanceof RefreshDataChart) {
            for(int i = this.dataset.getRowCount()-1; i >= 0; i--)
            {
                this.dataset.removeRow(i);

            }

            return true;
        }
        return false;
    }
    //Creates Bar chart
    public void createChart(){
        this.chart=ChartFactory.createBarChart(
                "Cholestrol Monitor", //Chart Title
                "Patients", // Category axis
                "Cholestrol Unit", // Value axis
                dataset,
                PlotOrientation.VERTICAL,
                true,true,false
        );

        BarRenderer renderer = (BarRenderer) chart.getCategoryPlot().getRenderer();
        renderer.setItemMargin(.2);
//        chart.setAutoRange(true)
        ChartPanel panel = new ChartPanel(this.chart){

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(900, 800);
            }
        };
        this.basePanel.setLayout(null);
        this.basePanel.setBackground(Color.lightGray);
        panel.setBounds(50,10,900, 800);
        this.basePanel.add(panel);


        //  Creating the back button here
        this.back = createBackButton();
        this.back.setBounds(400, 850, 200, 40 );
        this.back.setBackground(Color.RED);
        this.basePanel.add(this.back);
        this.basePanel.setVisible(true);
        pack();

        setLocationRelativeTo(null);
        setVisible(true);

    }

    @Override
    public boolean isVisible() {
        return super.isVisible();
    }





    public JFreeChart getJfreeChart(){
        return this.chart;
    }


    /***
     *
     * @return It returns the Jbutton with the functionality of going back to the mainframe
     */
    public JButton createBackButton(){
        JButton backButton = new JButton("Back");
        backButton.addActionListener( new ActionListener(){
          public void actionPerformed(ActionEvent e){
              BarChart.this.mainFrame.setVisible(true);
    //                ((BarChart) GraphDataExchange.this.barchrt).dispose();
             BarChart.this.setVisible(false);
          }
      }
    );

        return backButton;
    }








    public static void main(String[] args) throws Exception {
    }
}
