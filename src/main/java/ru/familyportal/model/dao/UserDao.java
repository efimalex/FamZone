package ru.familyportal.model.dao;


import com.googlecode.genericdao.dao.hibernate.GenericDAO;
import ru.familyportal.model.entity.User;

/**
 * Created by Alex Efimov.
 * User: Саня
 * Date: 07.03.12
 * Time: 22:12
 */
public interface UserDao extends GenericDAO<User, Long> {
    User getAppUserByName(String login);

    User createUser(User u);

    boolean userExists(final String userName);

    String activateUser(final String activationKey);

    void updateUser(User u);
}
