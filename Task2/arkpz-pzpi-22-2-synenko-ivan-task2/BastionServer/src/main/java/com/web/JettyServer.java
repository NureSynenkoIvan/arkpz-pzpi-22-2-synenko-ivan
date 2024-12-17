package com.web;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import org.glassfish.jersey.servlet.ServletContainer;

import java.util.concurrent.ExecutorService;

public class JettyServer {
    private static final int PORT = 8080;

    private Server server;
    private ExecutorService executorService;





    public JettyServer(ExecutorService executorService
                       ) {

        this.executorService = executorService;
        server = new Server();

        // Configure connector
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(PORT);
        server.addConnector(connector);

        // Create servlet context handler
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        // Configure Jersey servlet
        ServletHolder jerseyServlet = context.addServlet(
                ServletContainer.class,
                "/*"
        );
        jerseyServlet.setInitOrder(0);
        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.packages",
                "com.web.resources"
        );

        server.setHandler(context);


    }



    public void start() throws Exception {
        System.out.println("Starting Jetty Server...");
        server.start();

        executorService.submit(() -> {
            try {
                server.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }

    public void stop() throws Exception {
        System.out.println("Stopping Jetty Server...");
        server.stop();
    }
}
