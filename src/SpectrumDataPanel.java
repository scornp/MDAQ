import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RefineryUtilities;
import org.jfree.ui.RectangleInsets;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ  IDEA.
 * User: scornp
 * Date: 05-Mar-2007
 * Time: 13:37:11
 * To change this template use File | Settings | File Templates.
 */
public class SpectrumDataPanel extends JPanel {
    XYSeries series1;
    XYSeriesCollection dataset;
    String title;
    String xAxis;
    String yAxis;
    int sizeOfData;

    public SpectrumDataPanel(String title, String xAxis, String yAxis, int sizeOfData) {
        this.title = title;
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.sizeOfData = sizeOfData;


        int i = 0;
        int N = 4096 * 2;//Integer.parseInt(args[0]);
        Complex[] x = new Complex[N];

        for (i = 0; i < N; i++) {
            if (i < N / 2 - 50 || i > N / 2 + 50) {
                x[i] = new Complex(0, 0);
            } else {
                x[i] = new Complex(1, 0);
            }
        }

        // FFT of original data
        Complex[] y = FFT.fft(x);

        float[] xx, yy;
        float wmin = 0, wmax = 44100 / 2, wdelta;

        wdelta = (wmax - wmin) / (N / 2 - 1);

        xx = new float[N / 2];
        yy = new float[N / 2];

        for (i = 0; i < N / 2; i++) {
            xx[i] = wmin + i * wdelta;
            yy[i] = (float) y[i].Re();
        }
        series1 = new XYSeries("");

        for (i = 0; i < xx.length; i++) {
            series1.add(xx[i], yy[i]);
        }

        dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(300, 150));
        this.add(chartPanel);
    }


    public void setNewDataSeries(float[] xx, float[] yy) {
        dataset.removeSeries(series1);
        series1 = new XYSeries("");
        double value;
        for (int i = 1; i < xx.length; i++) {
            series1.add(xx[i], yy[i]);
        }
        dataset.addSeries(series1);
    }

    /**
     * Creates a chart.
     *
     * @param dataset the data for the chart.
     * @return a chart.
     */
    private JFreeChart createChart(XYDataset dataset) {
        JFreeChart chart = ChartFactory.createXYAreaChart(
                title, // chart title
                xAxis, // x axis label
                yAxis, // y axis label
                dataset, // data
                PlotOrientation.VERTICAL,
                false, // include legend
                true, // tooltips
                false // urls
        );

        chart.setBackgroundPaint(Color.white);
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        return chart;
    }
}
