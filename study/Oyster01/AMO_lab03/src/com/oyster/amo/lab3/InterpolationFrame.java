package com.oyster.amo.lab3;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author bamboo
 * @since 4/16/14 12:18 AM
 */
public class InterpolationFrame extends JFrame {
    private JPanel rootPanel;
    private JTabbedPane mTabbedPane1;
    private JTextField mTextFieldA;
    private JTextField mTextFieldB;
    private JTextField mTextFieldNum;
    private JComboBox mComboBox1;
    private JButton mButtonGo;
    private JPanel panelGraph;
    private JPanel panelError;
    private JComboBox mComboBoxFunc;

    public InterpolationFrame() {
        super("Інтерполяція");

        setContentPane(rootPanel);

        setPreferredSize(new Dimension(750, 550));
        pack();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setVisible(true);

        mButtonGo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                perform();
            }
        });
    }

    private void perform() {

        int numOfPoints = Integer.parseInt(mTextFieldNum.getText());

        Interpolation.InterpolationType type = null;
        if (mComboBox1.getSelectedIndex() == 0) {
            type = Interpolation.InterpolationType.LINEAR;
        } else {
            type = Interpolation.InterpolationType.SQUARE;
        }

        int a = Integer.parseInt(mTextFieldA.getText());
        int b = Integer.parseInt(mTextFieldB.getText());

        Function function = null;
        switch (mComboBoxFunc.getSelectedIndex()) {
            case 0:
                function = new Function() {
                    @Override
                    public double value(double x) {
                        return Math.sin(x);
                    }
                };
                break;
            case 1:
                function = new Function() {
                    @Override
                    public double value(double x) {
                        return Math.cos(x);
                    }
                };
                break;
            case 2:
                function = new Function() {
                    @Override
                    public double value(double x) {
                        return Math.sin(x * x);
                    }
                };
                break;
            case 3:
                function = new Function() {
                    @Override
                    public double value(double x) {
                        return Math.cos(x * x);
                    }
                };
                break;
        }

        Interpolation interpolation = new Interpolation(function, numOfPoints, type, a, b);

        XYSeries series0 = new XYSeries("Функція");
        XYSeries seriesI = new XYSeries("Інтерполяція");
        XYSeries seriesDiff = new XYSeries("Похибка");
        double tempI;
        double tempO;
        for (int i = 0; i < 50 * b; i++) {
            double value = i / 50.0f;
            tempI = interpolation.interpolate(value);
            tempO = interpolation.getFunction().value(value);
            series0.add(value, tempO);
            seriesI.add(value, tempI);
            seriesDiff.add(value, Math.abs(tempI - tempO));
        }

        XYSeriesCollection dataGraph = new XYSeriesCollection();
        XYSeriesCollection dataError = new XYSeriesCollection();

        dataGraph.addSeries(series0);
        dataGraph.addSeries(seriesI);




        dataError.addSeries(seriesDiff);

        final JFreeChart chartGraph = ChartFactory.createXYLineChart(
                "Декартова система", "X", "Y", dataGraph, PlotOrientation.VERTICAL,
                true, true, false);

        final JFreeChart chartError = ChartFactory.createXYLineChart(
                "Декартова система", "X", "Y", dataError, PlotOrientation.VERTICAL,
                true, true, false);

        final ChartPanel chartPanelGraph = new ChartPanel(chartGraph);
        final ChartPanel chartPanelError = new ChartPanel(chartError);

        mTabbedPane1.removeAll();
        mTabbedPane1.addTab("Графік", chartPanelGraph);
        mTabbedPane1.addTab("Похибка", chartPanelError);

        System.out.println("\nПохибка на кожному проміжку: ");
        double[] error = interpolation.getError();
        for (int i = 0; i < error.length; i++) {
            System.out.format("%2.8f\n", error[i]);
        }

        System.out.println("\nСередня похибка: ");
        System.out.format("%.8f%n", interpolation.getMeanError());
    }

}
