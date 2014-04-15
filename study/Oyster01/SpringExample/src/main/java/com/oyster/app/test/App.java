package com.oyster.app.test;

import com.oyster.app.model.__Administrator;
import com.oyster.app.model.__Position;
import com.oyster.dao.DAOFilter;
import com.oyster.dao.exception.DAOException;
import com.oyster.dao.impl.DAOCRUDJdbc;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.UUID;

/**
 * class to test the program
 */
public class App {


    public static void main(String[] args) {

/*
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        JFrame loginFrame = new LoginFrame();

        loginFrame.setSize(290, 120);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setResizable(false);

        loginFrame.setVisible(true);

*/

        try {
            runTest();
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    public static void runTest() throws DAOException {
        ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Module.xml");


//        Random r = new Random();
//        CustomerDAO customerDAO = (CustomerDAO) context.getBean("customerDAO");
//        Customer customer = new Customer(r.nextInt(100000), "bas", r.nextInt(50));
//        customerDAO.insert(customer);
//
//        Customer customer1 = customerDAO.findByCustomerId(1);
//        System.out.println(customer1);


        __Position p = new __Position(UUID.randomUUID(), "name", "desc");
//        System.out.println(DAOAnnotationUtils.entityToMap(p));
//        System.out.println(DAOAnnotationUtils.getStoredFields(p.getClass()));

//        System.out.println(DAOAnnotationUtils.getValueList(p));

        DAOCRUDJdbc x = DAOCRUDJdbc.getInstance(context);
        x.insert(p);

        p.setDescription(" _ fuck yeah 2");
        p.setName("ololo ");

        x.update(p);

        x.delete(p);

        List<__Position> list = x.select(__Position.class, new DAOFilter() {
            @Override
            public <T> boolean accept(T entity) {
                __Position p = (__Position) entity;
                return (p.getName().equals("ololo new name"));
            }
        });

        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }


        __Administrator admin = new __Administrator(UUID.randomUUID(), "admin", "root", "email@gmail.com", "password", p.getId(), 102);

        DAOCRUDJdbc adminJdbc = DAOCRUDJdbc.getInstance(context);

        adminJdbc.insert(admin);

        admin.setName("MyAdmin");

        adminJdbc.update(admin);

        DAOCRUDJdbc adminJdbc2 = (DAOCRUDJdbc) context.getBean("DAOJdbc");

        __Administrator ad2 = new __Administrator(UUID.randomUUID(), "admin2", "root2", "email@gmail.com", "password", p.getId(), 102);
        adminJdbc2.insert(ad2);

        adminJdbc2.delete(admin);
        adminJdbc.delete(ad2);
    }
}
