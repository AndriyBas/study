package com.oyster.gabka;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by bamboo on 29.04.14.
 */
public class GabkaForm extends JFrame implements ActionListener {
    private JPanel rootPanel;
    private JPanel leftPanel;
    private JTextField mTextFieldDex;
    private JTextField mTextFieldHex;
    private JTextField mTextFieldBin;
    private JButton mButtonGo;
    private JRadioButton mRadioButtonDec;
    private JRadioButton mRadioButtonHex;
    private JRadioButton mRadioButtonBin;

    private JRadioButton[] mJRadioButtons = new JRadioButton[4];


    public GabkaForm(final String name) {
        super(name);

        setContentPane(rootPanel);

        setPreferredSize(new Dimension(400, 200));

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        init();

        pack();
        setVisible(true);
    }

    private void init() {
        mJRadioButtons[0] = new JRadioButton("8");
        mJRadioButtons[0].setActionCommand("8");
        mJRadioButtons[0].setSelected(true);

        mJRadioButtons[1] = new JRadioButton("16");
        mJRadioButtons[1].setActionCommand("16");

        mJRadioButtons[2] = new JRadioButton("32");
        mJRadioButtons[2].setActionCommand("32");

        mJRadioButtons[3] = new JRadioButton("64");
        mJRadioButtons[3].setActionCommand("64");

        ButtonGroup group = new ButtonGroup();
        group.add(mJRadioButtons[0]);
        group.add(mJRadioButtons[1]);
        group.add(mJRadioButtons[2]);
        group.add(mJRadioButtons[3]);

        JPanel layoutPanel = new JPanel();
        layoutPanel.setLayout(new BoxLayout(layoutPanel, BoxLayout.Y_AXIS));

        layoutPanel.add(mJRadioButtons[0], BorderLayout.WEST);
        layoutPanel.add(mJRadioButtons[1], BorderLayout.WEST);
        layoutPanel.add(mJRadioButtons[2], BorderLayout.WEST);
        layoutPanel.add(mJRadioButtons[3], BorderLayout.WEST);

        ButtonGroup g = new ButtonGroup();
        mRadioButtonDec.setSelected(true);
        g.add(mRadioButtonDec);
        g.add(mRadioButtonHex);
        g.add(mRadioButtonBin);


        mButtonGo.addActionListener(this);

        rootPanel.add(leftPanel, BorderLayout.CENTER);
        rootPanel.add(layoutPanel, BorderLayout.EAST);
        rootPanel.add(mButtonGo, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (mRadioButtonDec.isSelected()) {
            String binSet = null;
            String hexSet = null;
            switch (getChoosen()) {
                case 0:
                    byte data = Byte.parseByte(mTextFieldDex.getText());
                    binSet = String.format("%8s",
                            Integer.toBinaryString((data + 256) % 256)).replace(' ', '0');// output -> "10000010"
                    hexSet = String.format("%2s",
                            Integer.toHexString((data + 256) % 256)).replace(' ', '0');
                    break;
                case 1:
                    int dataShort = Short.parseShort(mTextFieldDex.getText());

                    binSet = String.format("%16s",
                            Integer.toBinaryString((dataShort + 65536) % 65536)).replace(' ', '0');
                    hexSet = String.format("%4s",
                            Integer.toHexString((dataShort + 65536) % 65536)).replace(' ', '0');
                    break;
                case 2:
                    long dataInt = Integer.parseInt(mTextFieldDex.getText());

                    binSet = String.format("%32s",
                            Long.toBinaryString((dataInt + 4294967296L) % 4294967296L)).replace(' ', '0');
                    hexSet = String.format("%8s",
                            Long.toHexString((dataInt + 4294967296L) % 4294967296L)).replace(' ', '0');
                    break;
                case 3:
                    long dataLong = Long.parseLong(mTextFieldDex.getText());

                    binSet = String.format("%64s",
                            Long.toBinaryString(dataLong).replace(' ', '0'));

                    hexSet = String.format("%16s",
                            Long.toHexString(dataLong)).replace(' ', '0');
                    break;
            }

            mTextFieldBin.setText(binSet);
            mTextFieldHex.setText(hexSet);

//            mTextFieldBin.setText(Integer.toBinaryString(Integer.parseInt(mTextFieldDex.getText())));
            return;
        }

        if (mRadioButtonHex.isSelected()) {

            long i = Long.valueOf(mTextFieldHex.getText(), 16);

//            int ii = Integer.parseInt(mTextFieldHex.getText());
            System.out.println(i);


            return;
        }



    }


    private int getChoosen() {
        for (int i = 0; i < 4; i++) {
            if (mJRadioButtons[i].isSelected()) {
                return i;
            }
        }
        return 0;
    }

}
