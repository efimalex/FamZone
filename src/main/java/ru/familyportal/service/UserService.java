package ru.familyportal.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.familyportal.model.entity.Role;
import ru.familyportal.model.entity.User;

import java.util.Collection;

/**
 * Created by Alex Efimov.
 * User: Саня
 * Date: 10.03.12
 * Time: 13:41
 */
public interface UserService {
    UserDetails loadUserByUsername(String s) throws UsernameNotFoundException;

    User addAppUser(User user);

    boolean hasUser(String userName);

    void updateUser(User userDto);

    User getUser(String userName);

    String activateUser(String activationKey);

    Role getRole(String roleName);

    boolean deleteRole(Role role);

    Role createRole(String roleName, String description);

    boolean isUserInRole(String userName, String roleName);

    Collection<User> getUsers();

    boolean deleteUser(User user);
}
