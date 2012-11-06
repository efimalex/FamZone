package ru.familyportal.model.hibernate;


import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * Created by Alex Efimov.
 * User: Саня
 * Date: 07.03.12
 * Time: 22:20
 */
@Repository
public class BaseDaoImpl<T, ID extends Serializable> extends GenericDAOImpl<T, ID> {
    @Autowired

    public void setSessionFactory(@Qualifier("sessionFactory") SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }
}
