package com.web;

import com.web.resources.EmployeeResource;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import java.util.Set;

@ApplicationPath("/api")
public class BastionWebApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        return Set.of(EmployeeResource.class, ObjectMapperProvider.class);
    }
}