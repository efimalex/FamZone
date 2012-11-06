package ru.familyportal.service;

/**
 * Created by Alex Efimov.
 * User: Саня
 * Date: 11.05.12
 * Time: 20:21
 */
public interface AuthenticationService {

    boolean login(String username, String password);
}
