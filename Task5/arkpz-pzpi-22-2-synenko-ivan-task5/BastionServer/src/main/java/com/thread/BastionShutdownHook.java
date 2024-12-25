package com.thread;

import com.web.JettyServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.ExecutorService;

public class BastionShutdownHook  extends Thread {
    Logger logger = LoggerFactory.getLogger(BastionShutdownHook.class);

    JettyServer server;
    ExecutorService executor;
    SaveThread saveThread;

    Date creationDate;

    public BastionShutdownHook(JettyServer server, ExecutorService executor, SaveThread saveThread) {
        this.server = server;
        this.executor = executor;
        this.saveThread = saveThread;
        this.creationDate = new Date();
    }

    @Override
    public void run() {
        try {

            logger.info("Bastion shutdown hook started.");

            //Save everything that's in buffer of SaveThread so no data is lost.
            saveThread.saveForcefully();

            server.stop();
            logger.info("Server stopped");
            executor.shutdown();
            logger.info("Executor stopped");

            Date shutdownDate = new Date();

            logger.info("Application started at {}, ended at {}",
                    creationDate.toString(),
                    shutdownDate.toString());
            logger.info("Application ended");
        } catch (Exception e) {
            logger.error("Error shutting down: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
