package com.oyster.dao.annotation.utils;

import com.oyster.dao.annotation.Primary;
import com.oyster.dao.annotation.Stored;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

public class DAOAnnotationUtils {


    /**
     * @param c class or field to retrieve {@link com.oyster.dao.annotation.Stored} annotation
     * @return the name() if the class or field has Stored Annotation
     */
    public static String getStorageName(Class c) {
        Stored t = (Stored) c.getAnnotation(Stored.class);
        return (t != null) ? t.name() : null;
    }


    /**
     * @param c class to retrieve info from
     * @return the primary key of the class
     */
    public static Field getPrimaryKey(Class c) {
        Field[] fields = c.getDeclaredFields();
        for (Field field : fields) {
            Primary p = field.getAnnotation(Primary.class);
            if (p != null) {
                return field;
            }
        }
        return null;
    }

    /**
     * retrieves value of UUID from Primary Key Field, assuming it's type is UUID
     *
     * @param instance instance of a class to retrieve info from
     * @param <T>      type of the class
     * @return UUID value of the primary key
     */
    public static <T> UUID getPrimaryKeyValue(T instance) {
        Field pk = getPrimaryKey(instance.getClass());
        PropertyDescriptor p;
        try {
            p = new PropertyDescriptor(pk.getName(), instance.getClass());
            return ((UUID) p.getReadMethod().invoke(instance, null));
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * set UUID to the Field that is Primary Key, assuming that it's type is UUID
     *
     * @param instance instance of a class to retrieve info from
     * @param value    new value to set for primary key
     * @param <T>      type of the class
     */
    public static <T> void setPrimaryKeyValue(T instance, UUID value) {
        Field pk = getPrimaryKey(instance.getClass());
        PropertyDescriptor p;
        try {
            p = new PropertyDescriptor(pk.getName(), instance.getClass());
            p.getWriteMethod().invoke(instance, value);
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * get HashMap<String, Field> for current class c
     * String - Stored.name() value
     *
     * @param c class to get info abour its declared Stored fields
     * @return HashMap <stored name - its field > for current class
     */
    public static HashMap<String, Field> getStoredFields(Class c) {
        HashMap<String, Field> res = new HashMap<String, Field>();
        Field[] fields = c.getDeclaredFields();
        for (Field field : fields) {
            Stored p = field.getAnnotation(Stored.class);
            if (p != null) {
                res.put(p.name(), field);
            }
        }
        return res;
    }


    /**
     * @param instance
     * @param f
     * @param <T>
     * @return
     */
    public static <T> String getStringValue(T instance, Field f) {
        try {
            PropertyDescriptor p = new PropertyDescriptor(f.getName(), instance.getClass());

            ValueConverter valueConverter = getValueConverter(f);
            return valueConverter.toString(p.getReadMethod().invoke(instance, null));

            /*Class fieldClass = p.getPropertyType();
            if (fieldClass.getCanonicalName().equals("int")) {
                return ((Integer) p.getReadMethod().invoke(instance, null)).toString();
            }
            if (fieldClass.getCanonicalName().equals("double")) {
                return ((Double) p.getReadMethod().invoke(instance, null)).toString();
            }
            Object value = p.getReadMethod().invoke(instance, null);

            if (value == null) return "null";

            if (fieldClass.equals(String.class) || fieldClass.equals(UUID.class)) return "\"" + value.toString() + "\"";


            String prefix = ""; // (isReference(f)) ? "^"+f.getType().getCanonicalName()+" : " : "";
            return (value != null) ? prefix + value.toString() : prefix + "NULL";*/
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param instance
     * @param f
     * @param <T>
     * @return
     */
    public static <T> T getFieldValue(T instance, Field f) {
        PropertyDescriptor p;
        try {
            p = new PropertyDescriptor(f.getName(), instance.getClass());
            return ((T) p.getReadMethod().invoke(instance, null));
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * @param instance
     * @param <T>
     * @return
     */
    public static <T> Properties getValueList(T instance) {
        Properties res = new Properties();
        Class instanceClass = instance.getClass();
        HashMap<String, Field> storedFields = getStoredFields(instanceClass);
        for (String storedName : storedFields.keySet()) {
            Field f = storedFields.get(storedName);
            res.setProperty(storedName, getStringValue(instance, f));
        }
        return res;
    }

    /**
     * @param field
     * @param <T>
     * @return
     */
    public static <T extends ValueConverter> T getValueConverter(Field field) {
        Stored s = (Stored) field.getAnnotation(Stored.class);
        Class converterClass = s.converter();
        try {
            ValueConverter res = (ValueConverter) converterClass.newInstance();
            return (T) res;
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * form map of Stored.name() - Fields value applied to ValueConverter
     *
     * @param instance instance to convert
     * @param <T>      type of the instance
     * @return map that contains stored name of the field - it's value after applying ValueConverter
     */
    public static <T> Map<String, String> getConvertedStoredFields(T instance) {
        HashMap<String, Field> mapStrField = getStoredFields(instance.getClass());
        HashMap<String, String> mapStrStr = new HashMap<String, String>();
        for (String key : mapStrField.keySet()) {
            mapStrStr.put(key, getStringValue(instance, mapStrField.get(key)));
        }
        return mapStrStr;
    }

    /**
     * @param instance instance to convert
     * @param <T>      type of the instance
     * @return
     */
    public static <T> Map entityToMap(T instance) {

        PropertyDescriptor p;
        Map res = new HashMap<String, Object>();
        HashMap<String, Field> storedFields = getStoredFields(instance.getClass());
        try {
            for (String storedName : storedFields.keySet()) {
                Field f = storedFields.get(storedName);

                p = new PropertyDescriptor(f.getName(), instance.getClass());
                Class returnType = p.getReadMethod().getReturnType();
                Object value = p.getReadMethod().invoke(instance, null);
                ValueConverter c = getValueConverter(f);
                res.put(storedName, c.toString(value));
            }
            return res;
        } catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * @param instanceClass
     * @param map
     * @param <T>           type of the instance
     * @return
     */
    public static <T> T convertedMapToEntity(Class instanceClass, Map map) {
        PropertyDescriptor p;
        HashMap<String, Field> storedFields = getStoredFields(instanceClass);
        try {
            T instance = (T) instanceClass.newInstance();

            for (String storedName : storedFields.keySet()) {
                Object strValue = map.get(storedName);
                Field f = storedFields.get(storedName);
                ValueConverter c = getValueConverter(f);
                p = new PropertyDescriptor(f.getName(), instanceClass);
                p.getWriteMethod().invoke(instance, c.toValue(strValue.toString()));
            }
            return instance;
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param instanceClass
     * @param map
     * @param <T>           type of the instance
     * @return
     */
    public static <T> T mapToEntity(Class instanceClass, Map map) {

        PropertyDescriptor p;
        HashMap<String, Field> storedFields = getStoredFields(instanceClass);
        try {
            T instance = (T) instanceClass.newInstance();

            for (String storedName : storedFields.keySet()) {
                Object strValue = map.get(storedName);
                Field f = storedFields.get(storedName);
                ValueConverter c = getValueConverter(f);
                p = new PropertyDescriptor(f.getName(), instanceClass);
                p.getWriteMethod().invoke(instance, c.toValue(c.toString(strValue)));
            }
            return instance;
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * empty constructor
     */
    public DAOAnnotationUtils() {
    }

}
