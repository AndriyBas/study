package com.oyster.fiot;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYShapeAnnotation;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;

/**
 * @author bamboo
 * @since 4/16/14 8:34 PM
 */
public class HalfDividerFrame extends ApplicationFrame {
    private JPanel rootPanel;
    private JTextField mTextFieldLeft;
    private JTextField mTextFieldRight;
    private JButton mFindButton;
    private ChartPanel chartPanelGraph = null;

    private Function function = new Function() {
        @Override
        public double value(double x) {
            return x * (x * x - 1) + 1;
        }
    };

    private double EPS = 0.000000001D;
    private double zeroValue = Double.MAX_VALUE;


    public HalfDividerFrame(String title) {
        super(title);

        setContentPane(rootPanel);

        setPreferredSize(new Dimension(750, 550));

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        mFindButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawGraphic();
            }
        });

        pack();
        setVisible(true);
    }

    private boolean findZero(double left, double right) {
        if (Math.abs(function.value(left)) < EPS) {
            zeroValue = left;
            return true;
        }

        if (right - left < 0.1 * EPS) {
            return false;
        }

        double m = 0.5 * (left + right);

        if (function.value(m) * function.value(left) < 0) {
            return findZero(left, m);
        } else {
            return findZero(m, right);
        }

    }

    private void drawGraphic() {

        double a = Double.parseDouble(mTextFieldLeft.getText());
        double b = Double.parseDouble(mTextFieldRight.getText());

        XYSeries series = new XYSeries("Функція");

        for (double i = a; i < b; i += 0.05) {
            series.add(i, function.value(i));
        }

        XYSeriesCollection dataGraph = new XYSeriesCollection();

//        XYPointerAnnotation annotation = new XYPointerAnnotation("Zero", 0, 0, Math.PI);

        dataGraph.addSeries(series);

        JFreeChart chartGraph = ChartFactory.createXYLineChart(
                "Декартова система", "X", "Y", dataGraph, PlotOrientation.VERTICAL,
                true, true, false);


        if (findZero(a, b)) {

            ValueAxis valueAxis = chartGraph.getXYPlot().getRangeAxis();
            System.out.println(valueAxis.getUpperBound());

            double h = 0.03 * (valueAxis.getUpperBound() - valueAxis.getLowerBound());
            double w = 0.03 * (b - a);

            System.out.println(w + "  ,  " + h);

            XYShapeAnnotation xyShapeAnnotation = new XYShapeAnnotation(
                    new Ellipse2D.Double(zeroValue - 0.5 * w, function.value(zeroValue) - 0.5 * h, w, h),
                    null,
                    null,
                    Color.ORANGE);
            chartGraph.getXYPlot().addAnnotation(xyShapeAnnotation);
        }

        if (chartPanelGraph != null) {
            rootPanel.remove(chartPanelGraph);
        }

        chartPanelGraph = new ChartPanel(chartGraph);
        rootPanel.add(chartPanelGraph, BorderLayout.CENTER);

        pack();
        rootPanel.setVisible(true);
    }

}
