package com.web.resources;

import com.model.Employee;
import com.service.database.DatabaseService;
import com.service.database.dao.EmployeeDao;
import com.service.database.utils.HashUtil;
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
    private static final ConcurrentMap<String, Employee> logins = new ConcurrentHashMap<>();
    private static final EmployeeDao employeeDao = DatabaseService.getInstance().getEmployeeDao();

    @Override
    public void filter(ContainerRequestContext requestContext) {
        //Unrecognised radars are blocked in RadarReceiver.
        String path = requestContext.getUriInfo().getPath();
        if (path.startsWith("radar/receive") || path.startsWith("register")) {
            return;
        }

        String authHeader = requestContext.getHeaderString("Authorization");
        if (authHeader == null || !authHeader.startsWith("Basic ")) {
            abortRequest(requestContext, "Missing or invalid Authorization header");
            return;
        }

        // Decode and validate credentials
        String encodedCredentials = authHeader.substring("Basic ".length());
        String decodedCredentials = new String(Base64.getDecoder().decode(encodedCredentials));
        String[] parts = decodedCredentials.split(":");
        String phoneNumber = parts[0];
        String password = parts[1];
        Employee employee;

        //Get employee from database or from cash if they logged in.
        if (!logins.containsKey(phoneNumber)) {
            employee = employeeDao.get(phoneNumber);
        } else {
            employee = logins.get(phoneNumber);
        }

        if (parts.length != 2 || !validateUser(employee, password)) {
            abortRequest(requestContext, "Invalid username or password");
            return;
        }

        String role = employee.getApplicationRole();
        logins.put(phoneNumber, employee);

        // Set SecurityContext that represents rights of user.
        requestContext.setSecurityContext(new CustomSecurityContext(phoneNumber, role));
    }

    private boolean validateUser(Employee employee, String password) {
        if (employee != null) {
            if (HashUtil.checkPassword(password, employee.getPasswordHash())){
                return true;
            }
        }
        return false;
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