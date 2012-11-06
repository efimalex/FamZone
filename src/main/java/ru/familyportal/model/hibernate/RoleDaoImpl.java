package ru.familyportal.model.hibernate;

import com.googlecode.genericdao.search.Search;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import ru.familyportal.model.dao.RoleDao;
import ru.familyportal.model.entity.Role;

import java.util.Collection;
import java.util.List;

/**
 * Created by Alex Efimov.
 * User: Саня
 * Date: 03.05.12
 * Time: 22:13
 */
@Repository
public class RoleDaoImpl extends BaseDaoImpl<Role, Long> implements RoleDao {

    public boolean roleExists(final String roleName) {
        if (roleName == null) return false;
        if (getRole(roleName)==null) return false;
        Long roleId = getRole(roleName).getRoleId();
        return (roleId != null && roleId.longValue() > 0);
    }

    public Role getRole(String roleName) {
        Role r = null;
        if (roleName != null) {
            Search s = new Search(Role.class);
            s.addFilterEqual("name", roleName);
            //s.setResultMode(Search.RESULT_LIST);
            List list = search(s);
            if (list != null && !list.isEmpty()) r = (Role) list.get(0);
        }
        return r;
    }

    public Role createRole(String roleName, String description) {
        if (roleExists(roleName)) throw new DataIntegrityViolationException(roleName);
        Role r = new Role();
        r.setName(roleName);
        r.setDescription(description);
        save(r);
        return r;
    }

    public void updateRole(Role role) {
        if (role != null) {
            save(role);
        }
    }

    public boolean deleteRole(Role r) {
        if (r == null) return false;
        boolean wasDeleted = false;
        try {
            remove(r);
            wasDeleted = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return wasDeleted;
    }
}
