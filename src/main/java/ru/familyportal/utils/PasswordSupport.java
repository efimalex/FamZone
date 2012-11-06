package ru.familyportal.utils;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


import javax.validation.constraints.Pattern;
import java.security.MessageDigest;

/**
 * Created by Alex Efimov.
 * User: Саня
 * Date: 10.03.12
 * Time: 21:22
 */
public class PasswordSupport {
    private static final char[] HEX_CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};



    public static final String tempPassword() {
        java.util.Random rand = new java.util.Random();
        int[] aNums = new int[8];
        for (int n = 0; n < aNums.length; n++) aNums[n] = rand.nextInt(9) + 1;
        char[] ach1 = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'};
        char[] ach2 = new char[]{'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U'};
        char[] ach3 = new char[]{'v', 'w', 'x', 'y', 'z', 'V', 'W', 'X', 'Y', 'Z'};
        char[] ach4 = new char[]{'k', '$', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u'};
        char[] ach5 = new char[]{'$', '%', '!', '#', '$', '%', '!', '#', '$', '%'};
        return (ach4[aNums[7]] + String.valueOf(aNums[2]) + ach1[aNums[3]] + String.valueOf(aNums[0]) + ach3[aNums[5]] + ach2[aNums[4]] + ach4[aNums[6]] + ach5[aNums[1]]);
    }

    public static final String getMD5Hash(final String msg) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.reset();
            byte[] md5Hash = md5.digest(msg.getBytes());
            char[] buffer = new char[32];
            for (int i = 0; i < md5Hash.length; i++) {
                int low = (int) (md5Hash[i] & 0x0f);
                int high = (int) ((md5Hash[i] & 0xf0) >> 4);
                buffer[i * 2] = HEX_CHARS[high];
                buffer[i * 2 + 1] = HEX_CHARS[low];
            }
            return new String(buffer);
        } catch (Exception exc) {
            throw new RuntimeException(exc);
        }
    }

    public static String getBCryptPasswdHash(final String str) {
        try {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            return passwordEncoder.encode(str);
        } catch (Exception exc) {
            throw new RuntimeException(exc);
        }
    }

    @NotEmpty
    @Length(min = 8, max = 12)
    @Pattern(regexp = "(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$", message = "Password is not secure enough!")
    private String password = null;

    private String confirm = null;

    public String getPasswordHash() {
        return getMD5Hash(getPassword());
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String value) {
        this.password = (value != null && value.trim().length() > 0) ? value.trim() : null;
    }

    public String getPasswordConfirm() {
        return confirm;
    }

    public void setPasswordConfirm(String confirm) {
        this.confirm = confirm;
    }

    public boolean isConfirmed() {
        return (confirm != null && confirm.equals(password));
    }

    public void clear() {
        this.password = null;
        this.confirm = null;
    }

    @Override
    public String toString() {
        return new StringBuilder("PasswordSupport[")
                .append("password=").append(password).append(", confirm=")
                .append(confirm).append("]").toString();
    }
}
