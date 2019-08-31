/* -------------------
* LineChartDemo2.java
* -------------------
* (C) Copyright 2002-2005, by Object Refinery Limited.
*
*/

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;
import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.data.time.Day;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.RefineryUtilities;

/**
 * A simple demonstration application showing how to create a line chart using
 * data from an {@link XYDataset}.
 * <p/>
 * IMPORTANT NOTE: THIS DEMO IS DOCUMENTED IN THE JFREECHART DEVELOPER GUIDE.
 * DO NOT MAKE CHANGES WITHOUT UPDATING THE GUIDE ALSO!!
 */
//public class LineChartDemo2 extends ApplicationFrame {
    public class LineChartDemo2 extends JFrame {


    XYSeries series1;
    XYSeriesCollection dataset;

    /**
     * Creates a new demo.
     *
     * @param title the frame title.
     */
    public LineChartDemo2(String title) {
        super(title);
        dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);
    }

    /**
     * Creates a new demo.
     *
     * @param title the frame title.
     */
    public LineChartDemo2(String title, float[] x, float[] y) {
        super(title);
        series1 = new XYSeries("");

        for (int i = 0; i < x.length; i++) {
            series1.add(x[i], y[i]);
        }
        
        dataset = new XYSeriesCollection();

        dataset.addSeries(series1);

        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);
    }

    public void chartModifier() {
        dataset.removeSeries(series1);
        series1 = new XYSeries("");
        double value;
        for (int i = 1; i < 1000; i++) {
            value = (Math.random() - 0.5) * 10;
            series1.add(i, value);
        }
        dataset.addSeries(series1);
    }

    /**
     * Creates a sample dataset.
     *
     * @return a sample dataset.
     */

    //  private XYDataset createDataset() {
    private XYSeriesCollection createDataset() {
        series1 = new XYSeries("");
        double value;
        for (int i = 1; i < 40000; i++) {
            value = (Math.random() - 0.5) * 10;
            series1.add(i, value);
        }
        dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        return dataset;
    }

    /**
     * Creates a chart.
     *
     * @param dataset the data for the chart.
     * @return a chart.
     */
    private static JFreeChart createChart(XYDataset dataset) {
        JFreeChart chart = ChartFactory.createXYAreaChart(
                "Line Chart Demo 2", // chart title
                "X", // x axis label
                "Y", // y axis label
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

    /**
     * Creates a panel for the demo (used by SuperDemo.java).
     *
     * @return A panel.
     */
    public JPanel createDemoPanel() {
        JFreeChart chart = createChart(createDataset());
        return new ChartPanel(chart);
    }

    /**
     * Starting point for the demonstration application.
     *
     * @param args ignored.
     */
    public static void main(String[] args) {
        LineChartDemo2 demo = new LineChartDemo2("Line Chart Demo 2");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }
}