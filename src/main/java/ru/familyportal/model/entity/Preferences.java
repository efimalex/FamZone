package ru.familyportal.model.entity;



import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Alex Efimov.
 * User: Саня
 * Date: 07.03.12
 * Time: 21:43
 */
@Entity
@Table(name = "app_user_pref")
public class Preferences implements Serializable {

    private static final long serialVersionUID = 8250370827050080100L;
    protected Long userId;
    protected String profilePolicy = "private";
    protected String gender = "secret";
    protected boolean richEmailFormat = false;
    protected boolean marketingOptIn = false;
    protected String photoImageType;
    protected byte[] abyPhoto;
    protected Integer birthYear;
    protected String displayName;
    protected String description;
    //Страна
    protected String country = "Russian Federation";
    //Регион
    protected String region = "Omsk";
    //Название учреждения здравоохранения
    protected String hospitalName;
    //Вид деятельности пользователя
    //IT специалист, бухгалтерский учет, экономика и т.д.
    protected String kindOfActivity;
    //профессия пользователя
    protected String profession;
    
    @Id
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long value) {
        this.userId = value;
    }

    public String getProfilePolicy() {
        return profilePolicy;
    }

    public void setProfilePolicy(String value) {
        this.profilePolicy = (value != null && value.length() > 0) ? value : "private";
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String value) {
        this.gender = (value != null && value.length() > 0) ? value : "secret";
    }

    public boolean isRichEmailFormat() {
        return richEmailFormat;
    }

    public void setRichEmailFormat(boolean value) {
        this.richEmailFormat = value;
    }

    public boolean isMarketingOptIn() {
        return marketingOptIn;
    }

    public void setMarketingOptIn(boolean value) {
        this.marketingOptIn = value;
    }

    public String getPhotoImageType() {
        return photoImageType;
    }

    public void setPhotoImageType(String value) {
        this.photoImageType = value;
    }

    @Lob
    @Basic(fetch = FetchType.LAZY)
    public byte[] getPhoto() {
        return this.abyPhoto;
    }

    public void setPhoto(byte[] aby) {
        this.abyPhoto = aby;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer value) {
        this.birthYear = value;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String value) {
        this.displayName = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String value) {
        this.country = (value != null && value.length() > 0) ? value : "US";
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String value) {
        this.region = value;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getKindOfActivity() {
        return kindOfActivity;
    }

    public void setKindOfActivity(String kindOfActivity) {
        this.kindOfActivity = kindOfActivity;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public int hashCode() {
        return (userId != null) ? userId.hashCode() : super.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!getClass().equals(obj.getClass())) return false;
        Preferences x = (Preferences) obj;
        return equals(userId, x.userId);
    }

    protected final boolean equals(final Object left, final Object right) {
        return (left != null) ? left.equals(right) : (right == null);
    }
}
