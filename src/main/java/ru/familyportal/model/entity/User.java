package ru.familyportal.model.entity;

import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Alex Efimov.
 * User: Саня
 * Date: 07.03.12
 * Time: 19:11
 */

@Entity
@Table(name = "app_user")
public class User implements Serializable {

    private static final long serialVersionUID = 6916816325466379714L;

    private Long userId;
    private Long createdOn;
    private boolean active;
    private String activationKey;
    private boolean temporaryPassword = false;
    private boolean AccountNonExpired;
    private boolean AccountNonLocked;
    private boolean CredentialsNonExpired;

    private String passwordHash;
    private Set<Role> userRoles;
    private Preferences preferences;

    @NotEmpty
    @Length(min = 4, max = 16)
    @Pattern(regexp = "^[a-zA-Z\\d_]{4,12}$", message = "Invalid screen name.")
    private String userName;

    @NotEmpty
    @Length(min = 1, max = 30)
    private String firstName;

    @Length(min = 0, max = 30)
    private String middleName;

    @NotEmpty
    @Length(min = 1, max = 30)
    private String lastName;

    @Email
    @NotEmpty
    private String email;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long value) {
        this.userId = value;
    }

    @NaturalId(mutable = false)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String value) {
        this.userName = value;
    }

    public Long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Long value) {
        this.createdOn = value;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean value) {
        this.active = value;
    }

    public boolean isAccountNonExpired() {
        return AccountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        AccountNonExpired = accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return AccountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        AccountNonLocked = accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return CredentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        CredentialsNonExpired = credentialsNonExpired;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String value) {
        this.firstName = value;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String value) {
        this.middleName = (value != null && value.trim().length() > 0) ? value.trim() : null;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String value) {
        this.lastName = value;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String value) {
        this.email = value;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String value) {
        this.activationKey = value;
    }

    @Column(name = "password", length = 32)
    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String value) {
        this.passwordHash = value;
    }

    public boolean isTemporaryPassword() {
        return temporaryPassword;
    }

    public void setTemporaryPassword(boolean value) {
        this.temporaryPassword = value;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    public Preferences getPreferences() {
        return preferences;
    }

    public void setPreferences(Preferences preferences) {
        this.preferences = preferences;
    }

    public boolean hasRole(String roleName) {
        Set<Role> myRoles = getUserRoles();
        for (Role next : myRoles) {
            if (next.getName().equals(roleName)) {
                return true;
            }
        }
        return false;
    }

    public void removeRole(Role role) {
        getUserRoles().remove(role);
        role.getRoleUsers().remove(this);
    }

    public void addRole(Role role) {
        getUserRoles().add(role);
        role.getRoleUsers().add(this);
    }

    @Transient
    public Role[] getRoles() {
        return (Role[]) getUserRoles().toArray(new Role[0]);
    }

    @ManyToMany(targetEntity = ru.familyportal.model.entity.Role.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "app_user_role", joinColumns = @JoinColumn(name = "userId"), inverseJoinColumns = @JoinColumn(name = "roleId"))
    protected Set<Role> getUserRoles() {
        if (userRoles == null) {
            userRoles = new HashSet<Role>();
        }
        return userRoles;
    }

    protected void setUserRoles(Set<Role> userRoles) {
        this.userRoles = userRoles;
    }

    public String toString() {
        String myFullName = null;
        if (firstName != null) {
            if (lastName != null) {
                if (middleName != null && middleName.length() > 0) {
                    myFullName = firstName + " " + middleName + " " + lastName;
                } else {
                    myFullName = firstName + " " + lastName;
                }
            }
        } else {
            if (lastName != null) {
                myFullName = lastName;
            } else {
                myFullName = userName;
            }
        }
        return myFullName;
    }

    public int hashCode() {
        return (userName != null) ? userName.hashCode() : super.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!getClass().equals(obj.getClass())) return false;
        User x = (User) obj;
        // see: Hibernate In Action, pg. 124-125
        return equals(userName, x.userName); // equals via business key
    }

    protected final boolean equals(final Object left, final Object right) {
        return (left != null) ? left.equals(right) : (right == null);
    }
}
