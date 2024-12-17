package com.web;

import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/api")
public class JettyServerResourceConfig extends ResourceConfig {

    public JettyServerResourceConfig() {
        packages("com.web.resources");
    }
}
