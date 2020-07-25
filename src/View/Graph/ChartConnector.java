package View.Graph;

import org.jfree.data.category.CategoryDataset;

public interface ChartConnector {
    void setDataset(double value, String rowKey, String columnKey);
    void createChart();
    boolean removeDataSet(Object confirm);
    boolean isVisible();

}
