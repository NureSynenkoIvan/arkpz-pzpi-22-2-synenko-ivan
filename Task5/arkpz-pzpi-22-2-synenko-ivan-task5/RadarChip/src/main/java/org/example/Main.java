package org.example;

import org.example.model.RadarInformation;
import org.example.model.RadarObject;
import org.example.thread.RadarBridge;
import org.example.thread.RadarSimulationThread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

//This is a program that simulates behaviour of a program
//that takes input radar data and transmits it to the server.
public class Main {
    private static final String URL = "http://192.168.1.93:8080/radar/receive";

    public static void main(String[] args) {
        BlockingQueue<RadarInformation> objects = new LinkedBlockingQueue<RadarInformation>();

        RadarSimulationThread simulation = new RadarSimulationThread(objects);
        RadarBridge bridge = new RadarBridge(simulation, objects, URL);

        simulation.start();
        bridge.start();
    }
}