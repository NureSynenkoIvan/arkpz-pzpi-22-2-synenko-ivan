package com.web.resources;

import com.model.Employee;
import com.model.enums.Role;
import com.service.database.DatabaseService;
import com.service.database.dao.EmployeeDao;
import com.service.sms.SMSService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/***************************************

 Resource used to register new users.
 Requires sending SMS.

 **************************************/

@Path("/register")
public class RegistrationResource {
    EmployeeDao employeeDao = DatabaseService.getInstance().getEmployeeDao();
    SMSService smsService = new SMSService();
    private static final Map<String, String> verificationCodes = new HashMap<>();
    private static final Map<String, Employee> unregisteredEmployees = new HashMap<>();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerEmployee(Employee employee) {
        //The only way to register should be to register as a USER.
        //Employees should be promoted only by administrator.
        if (!employee.getRole().equals(Role.USER)) {
            employee.setRole(Role.USER);
        }
        // Generate a random verification code
        String verificationCode = generateVerificationCode();
        verificationCodes.put(employee.getPhoneNumber(), verificationCode);
        unregisteredEmployees.put(employee.getPhoneNumber(), employee);

        // Send the code via SMS
        try {
            smsService.sendSMS(employee.getPhoneNumber(), "Ваш код веріфікації: " + verificationCode);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to send verification code: " + e.getMessage())
                    .build();
        }

        return Response.ok("Verification code sent to " + employee.getPhoneNumber()).build();
    }

    @POST
    @Path("/verify")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response verifyEmployee(Map<String, String> requestBody) {
        String phoneNumber = requestBody.get("phoneNumber");
        String code = requestBody.get("code");

        // Check if the code matches
        if (verificationCodes.containsKey(phoneNumber) && verificationCodes.get(phoneNumber).equals(code)) {
            verificationCodes.remove(phoneNumber); // Remove code after verification

            employeeDao.save(unregisteredEmployees.get(phoneNumber));
            return Response.ok("Registration complete").build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED)
                .entity("Invalid verification code")
                .build();
        }
    }

    private String generateVerificationCode() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000)); // Generate a 6-digit code
    }
}
