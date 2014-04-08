package com.oyster.kpi.lab;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * @author bamboo
 * @since 3/25/14 5:52 AM
 */
public class MainClass {

    public static void main(String[] args) throws ParseException {

        JSONParser parser = new JSONParser();

        String s = "[0,{\"1\":{\"2\":{\"3\":{\"4\":[5,{\"6\":7}]}}}}]";
        try {
            Object obj = parser.parse(s);
            JSONArray array = (JSONArray) obj;
            System.out.println("The 2nd element of array");
            System.out.println(array.get(1));
            System.out.println();

            JSONObject obj2 = (JSONObject) array.get(1);
            System.out.println("Field \"1\"");
            System.out.println(obj2.get("1"));

            s = "{}";
            obj = parser.parse(s);
            System.out.println(obj);

            s = "[5,]";
            obj = parser.parse(s);
            System.out.println(obj);

            s = "[5,,2]";
            obj = parser.parse(s);
            System.out.println(obj);
        } catch (ParseException pe) {
            System.out.println("position: " + pe.getPosition());
            System.out.println(pe);
        }

        JSONObject obj = new JSONObject();

        obj.put("name", "foo");
        obj.put("num", new Integer(100));
        obj.put("balance", new Double(1000.21));
        obj.put("is_vip", new Boolean(true));

        String str = obj.toJSONString();
        System.out.println(str);

        JSONParser p = new JSONParser();
        JSONObject obj2 = (JSONObject) p.parse(str);
        obj2.put("name", (JSONObject) p.parse("{\"balance\":1000.21,\"num\":100,\"is_vip\":true,\"name\":\"foo\"}"));

        JSONObject o = (JSONObject) obj2.get("name");
//        o.put("name", (JSONObject)p.parse("_ololo_"));
        JSONObject o2 = (JSONObject) o.get("is_vip");


        System.out.println(obj2.toJSONString());

    }

}
