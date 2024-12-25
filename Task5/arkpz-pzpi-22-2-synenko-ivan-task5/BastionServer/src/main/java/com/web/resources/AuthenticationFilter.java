package com.web.resources;

import com.model.Employee;
import com.service.database.DatabaseService;
import com.service.database.dao.EmployeeDao;
import com.service.database.utils.HashUtil;
import com.web.exceptions.AuthorisationException;
import jakarta.annotation.Priority;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;

import java.security.Principal;
import java.util.Base64;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static jakarta.ws.rs.Priorities.AUTHENTICATION;


/***************************************
 Filter that restricts  access to every
 resource by setting a SecurityContext.

 For example. If user is administrator,
 and successfully logs in, he's given
 "administrator" role and
 can access all administrator resources.

 Roles are specified in User.getApplicationRole().
 Permissions to access a resource are specified
 in annotations of resource methods.
 **************************************/


@Provider
@Priority(AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    private static final String[] AUTH_EXCLUDED_ENDPOINTS = new String[]{
            "radar/receive",
            "register"
    };

    private static final ConcurrentMap<String, Employee> logins = new ConcurrentHashMap<>();
    private static final EmployeeDao employeeDao = DatabaseService.getInstance().getEmployeeDao();

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String path = requestContext.getUriInfo().getPath();
        for (String endpoint : AUTH_EXCLUDED_ENDPOINTS) {
            if (path.startsWith(endpoint)) {
                return;
            }
        }
        try {
            String authHeader = requestContext.getHeaderString("Authorization");
            if (authHeader == null || !authHeader.startsWith("Basic ")) {
                throw new AuthorisationException("Missing or invalid Authorization header");
            }

            // Decode and validate credentials
            String encodedCredentials = authHeader.substring("Basic ".length());
            String decodedCredentials = new String(Base64.getDecoder().decode(encodedCredentials));

            String[] parts = decodedCredentials.split(":");

            if (parts.length != 2) {
                throw new AuthorisationException("Invalid credentials: must contain 2 parts, login and password");
            }

            String phoneNumber = parts[0];
            String password = parts[1];

            //Get employee from database or from cash if they already logged in during execution time.
            Employee employee;
            if (!logins.containsKey(phoneNumber)) {
                employee = employeeDao.get(phoneNumber);
                validateUser(employee, password);
                logins.put(phoneNumber, employee);
            } else {
                employee = logins.get(phoneNumber);
                validateUser(employee, password);
            }

            // Set SecurityContext that represents rights of user.
            String role = employee.getApplicationRole();
            requestContext.setSecurityContext(new CustomSecurityContext(phoneNumber, role));

        } catch (AuthorisationException e) {
            abortRequest(requestContext, e.getMessage());
        }
    }

    private static void validateUser(Employee employee, String password) throws AuthorisationException {
        if (employee == null) {
            throw new AuthorisationException("User not found");
        }
        if (! HashUtil.checkPassword(password, employee.getPasswordHash())) {
            throw new AuthorisationException("Invalid username or password");
        }
    }


    private void abortRequest(ContainerRequestContext requestContext, String message) {
        requestContext.abortWith(jakarta.ws.rs.core.Response
                .status(jakarta.ws.rs.core.Response.Status.UNAUTHORIZED)
                .entity(message)
                .build());
    }

    private static class CustomSecurityContext implements SecurityContext {
        private final String username;
        private final String role;

        public CustomSecurityContext(String username, String role) {
            this.username = username;
            this.role = role;
        }

        @Override
        public Principal getUserPrincipal() {
            return () -> username;
        }

        @Override
        public boolean isUserInRole(String role) {
            return this.role.equals(role);
        }

        @Override
        public boolean isSecure() {
            return false;
        }

        @Override
        public String getAuthenticationScheme() {
            return "BASIC";
        }
    }
}