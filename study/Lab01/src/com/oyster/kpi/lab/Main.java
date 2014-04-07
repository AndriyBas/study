package com.oyster.kpi.lab;


public class Main {

    public void todo() {


        String inputFile = "user.json";
        String outputFile = "userOut.json";
        AppConfig appConfig = AppConfig
                .getInstance();
        ConfigReader reader = new JSONConfigReader(appConfig);
        reader.loadFromFile(inputFile);

        appConfig.setValue("name", "KPI");

        ConfigWriter writer = new JSONConfigWriter(appConfig);
        writer.save(outputFile);

        System.out.println(appConfig.out());

    }


    public static void main(String[] args) {
        new Main().todo();
    }
}
