package com;

import com.events.AlarmEvent;
import com.model.SkyState;
import com.thread.*;
import com.web.JettyServer;
import com.web.SkyStateQueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class BastionApplication {
    private static final AtomicBoolean isAlarm = new AtomicBoolean(false);
    private static final int MAX_THREADS = 8;
    JettyServer httpServer;
    ExecutorService executorService;

    RadarAnalyzerThread analyzer;
    RadarViewerThread viewer;
    AlarmThread alarmer;

    SaveThread saver;
    DailyCleanupThread dailyCleaner;

    BlockingQueue<SkyState> viewsQueue;
    BlockingQueue<SkyState> saveQueue;
    BlockingQueue<AlarmEvent> alarmsQueue;

    public BastionApplication() {

        executorService = Executors.newScheduledThreadPool(MAX_THREADS);
        viewsQueue = new LinkedBlockingQueue<>();
        alarmsQueue = new LinkedBlockingQueue<>();
        saveQueue = new LinkedBlockingQueue<>();
        analyzer = new RadarAnalyzerThread(viewsQueue, saveQueue, alarmsQueue);
        viewer = new RadarViewerThread(viewsQueue);
        alarmer = new AlarmThread(alarmsQueue);

        saver = new SaveThread(saveQueue);
        dailyCleaner = new DailyCleanupThread();


        httpServer = new JettyServer(executorService);

    }

    public void start() throws Exception {
        executorService.execute(analyzer);
        executorService.execute(viewer);
        executorService.execute(alarmer);

        saver.start();
        dailyCleaner.start();

        httpServer.start();

    }

    public static boolean isAlarm() {
        return isAlarm.get();
    }

    public static boolean startAlarm() {
        return isAlarm.compareAndSet(false, true);
    }

    public static void main(String[] args) {
        try {
            BastionApplication app = new BastionApplication();
            Runtime.getRuntime().addShutdownHook(new BastionShutdownHook(app.httpServer, app.executorService));
            app.start();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
