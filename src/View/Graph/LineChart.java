package View.Graph;

import javax.swing.*;

//import com.sun.javaws.util.JfxHelper;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LineChart extends JFrame implements ChartConnector {

    private static final long serialVersionUID = 1L;
    private DefaultCategoryDataset dataset;
    private JFreeChart chart;
    private JPanel basePanel;
    private JButton back;
    public JButton NTimer;
    private RefreshDataChart refresh;
    private Timer timer;
    private JFrame mainFrame;



    public LineChart(String appTitle) {
        super(appTitle);
        this.dataset = new DefaultCategoryDataset();
        this.basePanel = new JPanel();
        this.back = new JButton(); // Dummy Button
        add(this.basePanel);

        // Create Dataset
    }
    public LineChart(String appTitle, JFrame mainFrame){
        super(appTitle);
        this.mainFrame = mainFrame;

        this.dataset = new DefaultCategoryDataset();
        this.basePanel = new JPanel();
        this.back = new JButton(); // Dummy Button
        add(this.basePanel);
    }

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
    //Create chart
    public void createChart(){
        this.chart=ChartFactory.createLineChart(
                "Systolic Blood Pressure Monitor", //Chart Title
                "Latest Readings", // Category axis
                "Pressure (mmHg)", // Value axis
                dataset,
                PlotOrientation.VERTICAL,
                true,true,false
        );

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

    public JFreeChart getJFreeChart(){
        return this.chart;
    }

    /***
     * It creates the Back button function for the graph
     * @return It returns the JButton with the functionality of going back to the mainframe
     */
    public JButton createBackButton(){
        JButton backButton = new JButton("Back");
        backButton.addActionListener( new ActionListener(){
              public void actionPerformed(ActionEvent e){
                  LineChart.this.mainFrame.setVisible(true);
                  LineChart.this.setVisible(false);
              }
        });
        return backButton;
    }

    public static void main(String[] args) throws Exception {

        SwingUtilities.invokeLater(() -> {
            LineChart chart = new LineChart("Line Chart");
            chart.setAlwaysOnTop(true);
            chart.pack();
            chart.setSize(600, 400);
            chart.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            chart.setVisible(true);
        });
    }
}
