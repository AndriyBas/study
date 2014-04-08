package com.oyster.kpi.lab;


import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;


public class Main {

    public void todo() {

        try {
            String inFile = "sonnet.xml";
            String outFile = "sonetOut.xml";
            AppConfig appConfig = AppConfig
                    .getInstance();
            XMLConfigReader reader = new XMLConfigReader(appConfig);
            reader.loadFromFile(inFile);
            XMLConfigWriter writer = new XMLConfigWriter(appConfig);
            writer.save(outFile);
            System.out.println(appConfig.out());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public static void main(String[] args) throws ParserConfigurationException,
            IOException {

    }
}
