package com.oyster.amo.lab05;

import org.jfree.ui.ApplicationFrame;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * @author bamboo
 * @since 4/16/14 11:13 PM
 */
public class GausFrame extends ApplicationFrame {
    private JPanel rootPanel;
    private JComboBox mComboBox1;
    private JButton mSolveButton;

    private JScrollPane mScrollPane;
    private JTable mTable;
    private AbstractTableModel mTableModel;

    private Object[][] data;
    private ArrayList<Object[][]> cache = new ArrayList<>();

    public GausFrame(final String title) {
        super(title);

        setContentPane(rootPanel);

        setPreferredSize(new Dimension(500, 400));

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        mSolveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int n = mComboBox1.getSelectedIndex() + 2;

                double[][] a = new double[n][n + 1];
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        a[i][j] = Double.parseDouble(mTableModel.getValueAt(i, j).toString());
                    }
                    a[i][n] = Double.parseDouble(mTableModel.getValueAt(i, n + 1).toString());
                }

/*
                for (int i = 0; i < a.length; i++) {
                    for (int j = 0; j < a[i].length; j++) {
                        System.out.print(a[i][j] + " ");
                    }
                    System.out.println();
                }
                System.out.println();

*/

                GausSolver solver = new GausSolver(a);


                double[] res = solver.solve();
                for (int i = 0; i < res.length; i++) {
                    mTableModel.setValueAt(res[i], i, n + 3);
                }

                mTableModel.fireTableDataChanged();


            }
        });

        for (int i = 0; i < 6; i++) {
            cache.add(new Object[i][i + 4]);
        }

        mComboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawGraphic(mComboBox1.getSelectedIndex() + 2);
            }
        });

        mComboBox1.setSelectedIndex(1);
        drawGraphic(3);


        pack();
        setVisible(true);
    }

    private void drawGraphic(int n) {

        if (mScrollPane != null) {
            rootPanel.remove(mScrollPane);
        }

        mTableModel = new MyTableModel(n);
        mTable = new JTable(mTableModel);
        mScrollPane = new JScrollPane(mTable);

        rootPanel.add(mScrollPane, BorderLayout.CENTER);

        pack();

    }

    class MyTableModel extends AbstractTableModel {

        ArrayList<String> columnName = new ArrayList<String>();
        Object[][] data;


        public MyTableModel(int n) {
            for (int i = 0; i < n; i++) {
                columnName.add("a_" + (i + 1));
            }
            columnName.add("    ");
            columnName.add(" b ");
            columnName.add("    ");
            columnName.add("x");

            data = cache.get(n);
        }

        @Override
        public int getRowCount() {
            return data.length;
        }

        @Override
        public int getColumnCount() {
            return columnName.size();
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return data[rowIndex][columnIndex];
        }

        @Override
        public String getColumnName(int column) {
            return columnName.get(column);
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            data[rowIndex][columnIndex] = aValue;
        }

        @Override
        public boolean isCellEditable(int row, int col) {
            if (col < data.length || col == data.length + 1) {
                return true;
            } else {
                return false;
            }
        }
    }

}
