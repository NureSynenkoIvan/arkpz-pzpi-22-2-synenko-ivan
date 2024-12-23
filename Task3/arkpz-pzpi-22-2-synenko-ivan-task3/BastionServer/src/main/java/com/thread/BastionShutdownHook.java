package com.thread;

import com.web.JettyServer;

import java.util.concurrent.ExecutorService;

public class BastionShutdownHook  extends Thread {
    JettyServer server;
    ExecutorService executor;
    SaveThread saveThread;

    public BastionShutdownHook(JettyServer server, ExecutorService executor, SaveThread saveThread) {
        this.server = server;
        this.executor = executor;
        this.saveThread = saveThread;
    }

    @Override
    public void run() {
        try {

            System.out.println("Bastion shutdown hook started.");

            //Save everything that's in buffer of SaveThread so no data is lost.
            saveThread.saveForcefully();

            server.stop();
            System.out.println("Server stopped");
            executor.shutdown();
            System.out.println("Executor stopped");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
