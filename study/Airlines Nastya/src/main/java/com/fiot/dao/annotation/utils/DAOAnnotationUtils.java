package com.fiot.dao.annotation.utils;

import com.fiot.dao.annotation.Primary;
import com.fiot.dao.annotation.Stored;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DAOAnnotationUtils {


    /**
     * @param c клас чи поле для повернення  {@link com.fiot.dao.annotation.Stored} анотації
     * @return ім’я,якщо клас має анотацію Stored
     */
    public static String getStorageName(Class c) {
        Stored t = (Stored) c.getAnnotation(Stored.class);
        return (t != null) ? t.name() : null;
    }


    /**
     * @param c клас, з якого потрібно повернути інформацію
     * @return primary key класу
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
     * повертає значення UUID з поля Primary Key, вважається, що його тип UUID
     *
     * @param instance екземпляр класу з якого потрібно отримати інформацію
     * @param <T>      тип класу
     * @return UUID значення primary key
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
     * встановлює значення UUID з поля Primary Key, вважається, що його тип UUID
     *
     * @param instance екземпляр класу з якого потрібно отримати інформацію
     * @param value    нове значення для встановлення primary key
     * @param <T>      тип класу
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
     * повертає HashMap<String, Field> для поточного класу c
     * String - Stored.name() value
     *
     * @param c клас, з якого отримується інформація про поля позначені Stored
     * @return HashMap <stored name - це поле> для поточного класу
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
     * повертає значення як стрічку
     *
     * @param instance екземпляр
     * @param f        поле для перетворення
     * @param <T>      тип екземпляру
     * @return поле як стрічку
     */
    public static <T> String getStringValue(T instance, Field f) {
        try {
            PropertyDescriptor p = new PropertyDescriptor(f.getName(), instance.getClass());

            ValueConverter valueConverter = getValueConverter(f);
            return valueConverter.toString(p.getReadMethod().invoke(instance, null));
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
     * повертає конвертер даного поля
     *
     * @param field поле
     * @param <T>   тип екземпляру
     * @return конвертер поля
     */
    public static <T extends ValueConverter> T getValueConverter(Field field) {
        Stored s = (Stored) field.getAnnotation(Stored.class);
        Class converterClass = s.converter();
        try {
            ValueConverter res = (ValueConverter) converterClass.newInstance();
            return (T) res;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * мапа Stored.name() - значення поля
     *
     * @param instance екземпляр для конвертації
     * @param <T>      тип екземпляру
     * @return мапа, яка містить збережене ім’я поля - його значення після застосування ValueConverter
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
     * перетворює екземпляр класу в карту значень
     *
     * @param instance екземпляр для конвертування
     * @param <T>      тип екземпляру
     * @return карту значень <ключ - поле>
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
     * перетворює карту значень у екземпляр класу Т
     *
     * @param instanceClass клас екземпляру
     * @param map           карта значень
     * @param <T>           тип екземпляру
     * @return екземпляр із  карти
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
     * конструктор за замовчуванням
     */
    public DAOAnnotationUtils() {
    }

}
