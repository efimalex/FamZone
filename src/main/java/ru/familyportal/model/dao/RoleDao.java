package ru.familyportal.model.dao;

import ru.familyportal.model.entity.Role;

import java.util.Collection;

/**
 * Created by Alex Efimov.
 * User: Саня
 * Date: 03.05.12
 * Time: 22:11
 */
public interface RoleDao {
    boolean roleExists(String name);

    Role getRole(String name);

    Role createRole(String name, String description);

    void updateRole(Role role);

    boolean deleteRole(Role role);
}
