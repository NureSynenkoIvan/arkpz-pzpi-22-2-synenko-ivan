package com.web.singleton;

import com.thread.RadarViewerThread;

/// Singleton wrapper used for accessing RadarViewerThread
/// From RadarViewResource (and potentially - from other
/// resources connected with viewing radars).


public class RadarViewerThreadSingleton {
    private static RadarViewerThread instance;

    public static synchronized RadarViewerThread getInstance() {
        if (instance == null) {
            throw new IllegalStateException("RadarViewerThread is not initialized");
        }
        return instance;
    }

    public static synchronized void initialize(RadarViewerThread radarViewerThread) {
        if (instance != null) {
            throw new IllegalStateException("RadarViewerThread is already initialized");
        }
        instance = radarViewerThread;
    }
}