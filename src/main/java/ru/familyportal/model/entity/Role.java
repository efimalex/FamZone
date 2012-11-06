package ru.familyportal.model.entity;

import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Alex Efimov.
 * User: Саня
 * Date: 07.03.12
 * Time: 21:47
 */
@Entity
@Table(name="app_role")
public class Role implements Serializable {

    private static final long serialVersionUID = -5721626411433612745L;

    private Long roleId;

    @NotEmpty
    @Length(min=4,max=30)
    private String name;

    @NotEmpty
    @Length(min=1,max=90)
    private String description;

    private Set<User> roleUsers;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long value) {
        this.roleId = value;
    }

    @NaturalId
    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy="userRoles", targetEntity=User.class)
    public Set<User> getRoleUsers() {
        if (roleUsers == null) {
            roleUsers = new HashSet<User>();
        }
        return roleUsers;
    }

    public void setRoleUsers(Set<User> roleUsers) {
        this.roleUsers = roleUsers;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public int hashCode() {
        return (name != null) ? name.hashCode() : super.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!getClass().equals(obj.getClass())) return false;
        Role x = (Role)obj;
        // see: Hibernate In Action, pg. 124-125
        return equals(name, x.name); // equals via business key
    }

    protected final boolean equals(final Object left, final Object right) {
        return (left != null) ? left.equals(right) : (right == null);
    }
}
