package ru.familyportal.service.transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.familyportal.service.AuthenticationService;

/**
 * Created by Alex Efimov.
 * User: Саня
 * Date: 11.05.12
 * Time: 20:18
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    public boolean login(String username, String password) {
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            username, password));
            if (authenticate.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(
                        authenticate);
                return true;
            }
        } catch (AuthenticationException e) {
        }
        return false;
    }
}
