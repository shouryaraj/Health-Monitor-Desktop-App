package View.Graph;

import View.Graph.*;

public class RefreshDataChart {

    // This function works to refreshes the chart by providing the right details about the graph
    private GraphDataExchange data;
    // Constructor takes the data as the class GraphDataExchange
    public RefreshDataChart(GraphDataExchange data) {
        this.data = data;
    }




    // Refresh chart takes the Chartconnector and refreshes the data of that chart and triggers the chartChanged that is
    // repainting the graph
    public void refreshChart(ChartConnector graph) {
        System.out.println("refresh it!");
//        data.setDataforChart()
        graph.removeDataSet(this);
        if(graph instanceof BarChart){
            this.data.setDataforChart(Chart.BAR);
            ((BarChart) graph).getJfreeChart().fireChartChanged();
        }
        else if(graph instanceof LineChart){
            System.out.println("Hello: Graph change");
            this.data.setDataforChart(Chart.LINE);
            ((LineChart) graph).getJFreeChart().fireChartChanged();
        }
//        ((BarChart) graph).getJfreeChart().getCategoryPlot().setDataset(((BarChart) graph).getJfreeChart().getCategoryPlot().getDataset());

    }


}
