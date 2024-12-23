package com;

import com.events.AlarmEvent;
import com.model.SkyState;
import com.service.notification.NotificationService;
import com.thread.*;
import com.web.JettyServer;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class BastionApplication {
    private static final AtomicBoolean isAlarm = new AtomicBoolean(false);
    private static final int MAX_THREADS = 8;

    JettyServer httpServer;
    ExecutorService mainExecutor;
    ScheduledExecutorService scheduler;

    RadarDataCollectorTread dataCollector;
    RadarAnalyzerThread analyzer;
    RadarViewerThread viewer;
    AlarmThread alarmer;

    SaveThread saver;
    DailyCleanupThread dailyCleaner;

    BlockingQueue<SkyState> viewsQueue;
    BlockingQueue<SkyState> saveQueue;
    BlockingQueue<AlarmEvent> alarmsQueue;

    public BastionApplication() {
        mainExecutor = Executors.newScheduledThreadPool(MAX_THREADS);
        scheduler = Executors.newSingleThreadScheduledExecutor();

        viewsQueue = new LinkedBlockingQueue<>();
        alarmsQueue = new LinkedBlockingQueue<>();
        saveQueue = new LinkedBlockingQueue<>();

        dataCollector = new RadarDataCollectorTread();
        analyzer = new RadarAnalyzerThread(viewsQueue, saveQueue, alarmsQueue);
        viewer = new RadarViewerThread(viewsQueue);
        alarmer = new AlarmThread(alarmsQueue);

        saver = new SaveThread(saveQueue);
        dailyCleaner = new DailyCleanupThread();


        httpServer = new JettyServer(mainExecutor);

    }

    public void start() throws Exception {

        mainExecutor.execute(analyzer);
        mainExecutor.execute(viewer);
        mainExecutor.execute(alarmer);

        scheduler.scheduleAtFixedRate(dataCollector, 10, 3, TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(saver, 10, 10, TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(dailyCleaner, 0, 11, TimeUnit.DAYS);

        httpServer.start();

    }

    public static boolean isAlarm() {
        return isAlarm.get();
    }

    public static boolean startAlarm(AlarmEvent alarmEvent) {
        System.out.println("STARTING ALARM!!!");
        NotificationService.getInstance().sendAlarmNotifications(alarmEvent);
        return isAlarm.compareAndSet(false, true);
    }

    public static boolean stopAlarm() {
        System.out.println("STOPPING ALARM!!!");
        return isAlarm.compareAndSet(true, false);
    }

    public static void main(String[] args) {
        try {
            BastionApplication app = new BastionApplication();
            Runtime.getRuntime().addShutdownHook(new BastionShutdownHook(app.httpServer, app.mainExecutor, app.saver));
            app.start();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
