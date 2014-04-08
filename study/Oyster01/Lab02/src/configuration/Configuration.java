package configuration;


import java.util.Properties;


public class Configuration extends Properties {


    public String getString(String key) {
        // TODO Auto-generated method stub

        return null;
    }


    public int getInteger(String key) {
        // TODO Auto-generated method stub
        return 0;
    }


    public double getDouble(String key) {
        // TODO Auto-generated method stub
        return 0;
    }


    public void appendProperty(String key, String... values) {
        // TODO Auto-generated method stub

    }


    public void appendProperty(String key, int... values) {
        // TODO Auto-generated method stub

    }

    public void appendProperty(String key, double... values) {
        // TODO Auto-generated method stub

    }


    public void appendConfiguration(Configuration... configurations) {
        for (Configuration c : configurations) {
            Object[] keys = c.keySet().toArray();
            for (Object cur : keys) {
                String key = (String) cur;
                String value = c.getProperty(key);
                if (this.containsKey(key)) {
                    this.setProperty(key, this.getProperty(key) + "," + value);
                } else {
                    this.setProperty(key, value);
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuffer result = new StringBuffer("CONFIGURATION:[\r\n");
        for (Object cur : this.keySet()) {
            String key = (String) cur;
            String value = this.getProperty(key);
            result.append(key + " = " + value + "\r\n");
        }
        result.append("]\r\n");
        return result.toString();
    }
}
