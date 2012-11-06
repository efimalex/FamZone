package ru.familyportal.model.hibernate;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by IntelliJ IDEA.
 * User: efim
 * Date: 22.07.11
 * Time: 10:34
 * To change this template use File | Settings | File Templates.
 */
public class Starter {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("/ru/familyportal/model/dao/applicationContext-Dao.xml");
    }
}
