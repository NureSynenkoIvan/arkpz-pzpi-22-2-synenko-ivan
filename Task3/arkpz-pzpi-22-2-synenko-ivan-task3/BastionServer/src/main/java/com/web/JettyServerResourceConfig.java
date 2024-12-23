package com.web;

import com.web.resources.AuthenticationFilter;
import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

@ApplicationPath("/api")
public class JettyServerResourceConfig extends ResourceConfig {

    public JettyServerResourceConfig() {
        packages("com.web.resources");
        register(RolesAllowedDynamicFeature.class);
        register(AuthenticationFilter.class);
    }
}
