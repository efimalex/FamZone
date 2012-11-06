package ru.familyportal.service.transactional;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import ru.familyportal.model.entity.Preferences;
import ru.familyportal.model.entity.Role;
import ru.familyportal.model.entity.User;
import ru.familyportal.service.UserService;
import ru.familyportal.utils.PasswordSupport;

import java.io.File;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created by Alex Efimov.
 * User: Саня
 * Date: 26.04.12
 * Time: 22:41
 */
public class UserServiceImplTest extends AbstractServiceTest {
    @Qualifier("userServiceImpl")
    @Autowired
    UserService service;
    @Autowired
    SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static final File toFile(String path) {
        if (path == null) return null;
        path = path.trim();
        if (path.startsWith("$[WEBAPP]/")) {
            String after = path.substring("$[WEBAPP]/".length());
            String realPath = System.getProperty("example.root");
            if (realPath != null) {
                if (!realPath.endsWith("/")) realPath = (realPath+"/");
                path = realPath+after;
            } else {
                path = after;
            }
        }
        return new File(path);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void testLoadUserByUsernameNotFoundEx() {
        service.loadUserByUsername("test");
    }

    @Test
    public void testAddUser() throws Exception {
        assertNotNull(service);

        assertTrue(!service.hasUser("test"));
        User user = new User();
        user.setUserName("test");
        user.setEmail("info@mail.ru");
        user.setFirstName("test");
        user.setLastName("test");
        String passwordHash = PasswordSupport.tempPassword();
       // System.out.println(passwordHash);
        user.setPasswordHash(PasswordSupport.getBCryptPasswdHash(passwordHash));
        user.setTemporaryPassword(true);
        User u = service.addAppUser(user);
        assertNotNull(u);
        try {
            service.addAppUser(user);
            fail("A 'DataIntegrityViolationException' should have been thrown from the createUser method!");
        } catch (DataIntegrityViolationException dve) {}
        assertTrue(service.hasUser(u.getUserName()));

        u.setFirstName("Testy");
        u.setMiddleName("J");
        u.setLastName("McTester");
        u.setEmail("testy@example.com");
        service.updateUser(u);
        User afterUpdate = service.getUser("test");
        assertNotNull(afterUpdate);

        String activationKey = afterUpdate.getActivationKey();
        assertNotNull(activationKey);
        //Здесь проваливается
        String activatedUserId = service.activateUser(activationKey);
        assertEquals(activatedUserId, afterUpdate.getUserName());

        Role testRole1 = service.getRole("testRole1");
        if (testRole1 != null) service.deleteRole(testRole1);
        testRole1 = service.createRole("testRole1", "this is a test role");
        assertNotNull(testRole1);

        getSessionFactory().getCurrentSession().flush();

        afterUpdate.addRole(testRole1);

        getSessionFactory().getCurrentSession().flush();

        assertTrue(testRole1.getRoleUsers().contains(afterUpdate));

      //  byte[] abyPhoto = FacesSupport.toBytes(toFile("test/01.jpg"));

        Preferences p = afterUpdate.getPreferences();

        p.setGender("male");
        p.setMarketingOptIn(true);
        p.setRichEmailFormat(true);
        p.setPhotoImageType("image/x-png");
        //p.setPhoto(abyPhoto);
        service.updateUser(afterUpdate);
        getSessionFactory().getCurrentSession().flush();
        p = afterUpdate.getPreferences();
        assertNotNull(p);
        assertEquals("male",p.getGender());
        assertTrue(p.isMarketingOptIn());
        assertTrue(p.isRichEmailFormat());
        p.setPhotoImageType("image/x-png");
        //p.setPhoto(abyPhoto);
        service.updateUser(afterUpdate);

        getSessionFactory().getCurrentSession().flush();

        p = afterUpdate.getPreferences();
        assertNotNull(p);
        assertEquals("male",p.getGender());
        assertTrue(p.isMarketingOptIn());
        assertTrue(p.isRichEmailFormat());
        p.setGender("female");
        p.setPhotoImageType("image/gif");
        service.updateUser(afterUpdate);
        getSessionFactory().getCurrentSession().flush();
        afterUpdate = service.getUser(afterUpdate.getUserName());
        p = afterUpdate.getPreferences();
        assertNotNull(p);
        assertEquals("female",p.getGender());
        assertEquals("image/gif",p.getPhotoImageType());

        Collection<User> users = service.getUsers();
        assertNotNull(users);
        assertTrue(!users.isEmpty());

        assertTrue(service.deleteUser(afterUpdate));
        assertTrue(service.deleteRole(testRole1));

        getSessionFactory().getCurrentSession().flush();
    }
}
