package ru.familyportal.model.entity;

import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Alex Efimov.
 * User: Саня
 * Date: 02.09.12
 * Time: 22:13
 * <p/>
 * Справочник ролей пользователя*
 */
@Entity
public class RolesSpr implements Serializable {
    private Long roleId;

    @NotEmpty
    @Length(min = 4, max = 30)
    private String name;

    @NotEmpty
    @Length(min = 1, max = 90)
    private String description;


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
        RolesSpr x = (RolesSpr) obj;
        // see: Hibernate In Action, pg. 124-125
        return equals(name, x.name); // equals via business key
    }

    protected final boolean equals(final Object left, final Object right) {
        return (left != null) ? left.equals(right) : (right == null);
    }

}
