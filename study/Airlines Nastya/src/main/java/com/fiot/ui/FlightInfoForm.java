package com.fiot.ui;

import com.fiot.app.AppConst;
import com.fiot.app.model.Airport;
import com.fiot.app.model.Flight;
import com.fiot.core.controller.CommandExecutor;
import com.fiot.core.controller.command.Context;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by bamboo on 25.05.14.
 */
public class FlightInfoForm extends JFrame {

    private JPanel rootPane;
    private JComboBox mComboBoxDep;
    private JComboBox mComboBoxArr;
    private JFormattedTextField mFormattedTextFieldDep;
    private JFormattedTextField mFormattedTextFieldArr;
    private JComboBox mComboBoxClass;
    private JFormattedTextField mFormattedTextFieldPrice;
    private JButton mButton1;

    private java.util.List<Airport> airports;
    private Flight mFlight;


    public FlightInfoForm(java.util.List<Airport> airportList, Flight flight) {
        super((String) AppConst.APP_CONFIG.getValue("programTitle") + " : Рейс");

        this.airports = airportList;
        this.mFlight = flight;

        add(rootPane);

        int width = (Integer) AppConst.APP_CONFIG.getValue("flightScreenWidth");
        int height = (Integer) AppConst.APP_CONFIG.getValue("flightScreenHeight");

        setPreferredSize(new Dimension(width, height));
        setMinimumSize(new Dimension(width, height));
        init();
        pack();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void init() {
        mComboBoxClass.addItem("Економ");
        mComboBoxClass.addItem("Бізнес");
        mComboBoxClass.addItem("Змішаний");

        for (Airport a : airports) {
            mComboBoxArr.addItem(a);
            mComboBoxDep.addItem(a);
        }

        mButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Airport dep = (Airport) mComboBoxDep.getSelectedItem();
                Airport arr = (Airport) mComboBoxArr.getSelectedItem();

                String depTimeStr = mFormattedTextFieldDep.getText().trim();
                String arrTimeStr = mFormattedTextFieldArr.getText().trim();

                String classType = (String) mComboBoxClass.getSelectedItem();

                String priceStr = mFormattedTextFieldPrice.getText();

                boolean errorOccured = false;

                StringBuilder errorMsg = new StringBuilder("Введіть ");
                if (depTimeStr.length() == 0) {
                    errorMsg.append(" час відправлення ");
                    errorOccured = true;
                }

                if (arrTimeStr.length() == 0) {
                    if (errorOccured) {
                        errorMsg.append(", та");
                    }
                    errorOccured = true;
                    errorMsg.append("  час прибуття");
                }

                if (priceStr.length() == 0) {
                    if (errorOccured) {
                        errorMsg.append(", та");
                    }
                    errorOccured = true;
                    errorMsg.append(" ціну");
                }
                errorMsg.append("!");

                if (!errorOccured) {
                    Date depTime = null;
                    Date arrTime = null;
                    Long price = null;
                    try {
                        depTime = (Date) mFormattedTextFieldDep.getFormatter().stringToValue(
                                depTimeStr);
                        arrTime = (Date) mFormattedTextFieldArr.getFormatter().stringToValue(
                                arrTimeStr);
                        price = (Long) mFormattedTextFieldPrice.getFormatter().stringToValue(
                                priceStr);
                    } catch (final ParseException ex) {
                        ex.printStackTrace();
                        Utils.showErrorDialog(FlightInfoForm.this, "Помилка переведення чисел");
                        return;
                    }

                    if (mFlight == null) {
                        mFlight = new Flight(
                                UUID.randomUUID(),
                                dep.getId(),
                                arr.getId(),
                                depTime.getTime(),
                                arrTime.getTime(),
                                classType,
                                price
                        );
                        mFlight.setDepAirport(dep);
                        mFlight.setArrAirport(arr);
                    } else {
                        mFlight.setDepAirportID(dep.getId());
                        mFlight.setDepAirport(dep);
                        mFlight.setArrAirportID(arr.getId());
                        mFlight.setArrAirport(arr);

                        mFlight.setDepTime(depTime.getTime());
                        mFlight.setArrTime(arrTime.getTime());

                        mFlight.setClassType(classType);
                        mFlight.setPrice(price);
                    }

                    saveAirport();


                } else {
                    JOptionPane.showMessageDialog(
                            FlightInfoForm.this,
                            errorMsg.toString(),
                            "Спробуйте ще раз",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
    }

    private void saveAirport() {

        System.out.println(mFlight);

        Context c = new Context();
        c.put("id", mFlight.getId());
        c.put("dep_air_id", mFlight.getDepAirportID());
        c.put("arr_air_id", mFlight.getArrAirportID());

        c.put("dep_time", mFlight.getDepTime());
        c.put("arr_time", mFlight.getArrTime());

        c.put("class", mFlight.getClassType());
        c.put("price", mFlight.getPrice());

        try {
            CommandExecutor.getInstance().execute("updateFlight", c, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        mFormattedTextFieldDep = new JFormattedTextField(AppConst.DATE_FORMAT);
        mFormattedTextFieldArr = new JFormattedTextField(AppConst.DATE_FORMAT);
        mFormattedTextFieldPrice = new JFormattedTextField(NumberFormat.getCurrencyInstance(Locale.US));
    }

}
