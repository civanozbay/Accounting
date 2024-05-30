package com.accounting.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;



import java.io.IOException;
import java.util.Set;

@Configuration
public class AuthSuccessHandler implements AuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        System.out.println("Authentication Success for user: " + authentication.getName() + " with roles: " + roles.toString());

        if (roles.contains("Root User")) {
            response.sendRedirect("/companies/list");
        }
        if (roles.contains("Admin")) {
            response.sendRedirect("/users/list");
        }
        if (roles.contains("Manager")) {
            response.sendRedirect("/dashboard");
        }
        if (roles.contains("Employee")) {
            response.sendRedirect("/dashboard");
        }
    }
}
