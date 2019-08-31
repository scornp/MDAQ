/* --------------------
* RMSDisplayPanel.java
* --------------------
* (C) Copyright 2002-2006, by Object Refinery Limited.
*/
//package demo;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Second;
import org.jfree.ui.RectangleInsets;

public class RMSDisplayPanel extends JPanel {
    private TimeSeries total;

    /**
     * Creates a new application.
     *
     * @param maxAge the maximum age (in milliseconds).
     */
    public RMSDisplayPanel(int maxAge) {
        super(new BorderLayout());
        //  this.total = new TimeSeries("RMSxx", Second.class);
        this.total = new TimeSeries("", Second.class);

        this.total.setMaximumItemAge(maxAge);
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(this.total);
        DateAxis domain = new DateAxis("Time");
        NumberAxis range = new NumberAxis("RMS");
        domain.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 12));
        range.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 12));
        XYItemRenderer renderer = new XYLineAndShapeRenderer(true, false);
        renderer.setSeriesPaint(0, Color.red);
        renderer.setStroke(new BasicStroke(3f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
        XYPlot plot = new XYPlot(dataset, domain, range, renderer);

        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        domain.setAutoRange(true);
        domain.setLowerMargin(0.0);
        domain.setUpperMargin(0.0);
        domain.setTickLabelsVisible(true);
        //   range.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        range.setAutoRange(true);
        range.setAutoTickUnitSelection(true);
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "RMS", // title
                "time", // x-axis label
                "RMS", // y-axis label
                dataset, // data                  
                false, // create legend?
                true, // generate tooltips?
                false // generate URLs?
        );
        chart.setBackgroundPaint(Color.white);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(300, 150));
        add(chartPanel);
    }

    /**
     * Adds an observation to the RMS time series.
     *
     * @param y the total memory used.
     */
    public void addTotalObservation(double y) {
        this.total.add(new Second(), y);
    }
}