package ru.familyportal.model.hibernate;

import com.googlecode.genericdao.search.Search;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import ru.familyportal.model.dao.UserDao;
import ru.familyportal.model.entity.Preferences;
import ru.familyportal.model.entity.User;

import java.util.List;

/**
 * Created by Alex Efimov.
 * User: Саня
 * Date: 07.03.12
 * Time: 22:25
 */
@Repository
public class UserDaoImpl extends BaseDaoImpl<User, Long> implements UserDao {

    private String activatedUserName = null;

    public User getAppUserByName(String login) {
        User u = null;
        if (login != null) {
            Search s = new Search(User.class);
            s.addFilterEqual("userName", login);
            //s.setResultMode(Search.RESULT_LIST);
            List list = search(s);
            if (list != null && !list.isEmpty()) u = (User) list.get(0);
        }
        return u;
    }

    public User createUser(User u) {
        String userName = u.getUserName();
        if (userExists(userName)) throw new DataIntegrityViolationException(userName);
        u.setCreatedOn(System.currentTimeMillis());
        save(u);
        Preferences prefs = u.getPreferences();
        if (prefs == null) {
            prefs = new Preferences();
            prefs.setUserId(u.getUserId());
            prefs.setProfilePolicy("private");
            prefs.setGender("secret");
            //getHibernateTemplate().save(prefs);
            u.setPreferences(prefs);
        }
        save(u);
        return u;
    }

    public boolean userExists(final String userName) {
        if (userName == null) return false;
        if (getAppUserByName(userName)==null) return false;
        Long userId = getAppUserByName(userName).getUserId();

        return (userId != null && userId.longValue() > 0);
    }

    public String activateUser(final String activationKey) {
        //Ищем имя пользователя по activationKey
        Search s = new Search(User.class);
        s.setDistinct(true);
        s.addFilterEqual("active", false);
        s.addFilterEqual("activationKey", activationKey);
        List list = search(s);
        User u = null;
        if (list != null && !list.isEmpty()) u = (User) list.get(0);
        if (u != null) {
            activatedUserName = u.getUserName();
        }
        //Ищем пользователя по userName
        if (activatedUserName != null) {
            User activatedUser = getAppUserByName(activatedUserName);
            activatedUser.setActive(true);
            activatedUser.setActivationKey(null);
            save(activatedUser);
        }
        return activatedUserName;
    }


    public void updateUser(User u) {
        if (u != null) save(u);
    }
}
