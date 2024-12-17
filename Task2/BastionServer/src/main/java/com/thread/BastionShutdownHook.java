package com.thread;

import com.web.JettyServer;

import java.util.concurrent.ExecutorService;

public class BastionShutdownHook  extends Thread {
    JettyServer server;
    ExecutorService executor;

    public BastionShutdownHook(JettyServer server, ExecutorService executor) {
        this.server = server;
        this.executor = executor;
    }
    @Override
    public void run() {
        try {
            System.out.println("Bastion shutdown hook started.");
            server.stop();
            System.out.println("Server stopped");
            executor.shutdown();
            System.out.println("Executor stopped");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
