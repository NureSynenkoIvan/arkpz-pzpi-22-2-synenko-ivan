package com.service.database;

import com.config.DatabaseConfig;
import com.service.database.dao.*;
import com.service.database.dao.impl.DeviceDaoImpl;
import com.service.database.dao.impl.EmployeeDaoImpl;
import com.service.database.dao.impl.MessageScenarioDaoImpl;
import com.service.database.dao.impl.SkyStateDaoImpl;
import com.service.database.utils.MongoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DatabaseService {
    private Logger logger = LoggerFactory.getLogger(DatabaseService.class);

    private static final String MONGODUMP_PATH = "C:\\Program Files\\MongoDB\\Tools\\100\\bin\\mongodump.exe";


    private static DatabaseService instance; // Singleton instance
    private MongoUtil mongoUtil;
    private SkyStateDao skyStateDao;
    private EmployeeDao employeeDao;
    private DeviceDao deviceDao;
    private MessageScenarioDao messageScenarioDao;
    private ConfigDao configDao;



    // Private constructor to prevent direct instantiation
    private DatabaseService() {
        mongoUtil = new MongoUtil(DatabaseConfig.getInstance());
        mongoUtil.connect();
        skyStateDao = new SkyStateDaoImpl(mongoUtil);
        employeeDao = new EmployeeDaoImpl(mongoUtil);
        deviceDao = new DeviceDaoImpl(mongoUtil);
        messageScenarioDao = new MessageScenarioDaoImpl(mongoUtil);
    }

    // Public method to provide access to the singleton instance
    public static synchronized DatabaseService getInstance() {
        if (instance == null) {
            instance = new DatabaseService();
        }
        return instance;
    }

    public MongoUtil getMongoUtil() {
        return mongoUtil;
    }

    public SkyStateDao getSkyStateDao() {
        return skyStateDao;
    }

    public EmployeeDao getEmployeeDao() {
        return employeeDao;
    }

    public DeviceDao getDeviceDao() {
        return deviceDao;
    }

    public MessageScenarioDao getMessageScenarioDao() {
        return messageScenarioDao;
    }

    public ConfigDao getConfigDao() {
        return configDao;
    }

    public void dumpSkyStates(String outputPath) {
        // Generate a unique folder name based on the current date and time
        String timestamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
        String currentOutputPath = outputPath + "\\" + timestamp;

        // Create the directory if it doesn't exist
        try {
            Path outputDir = Paths.get(currentOutputPath);
            Files.createDirectories(outputDir);
            logger.info("Created output directory: " + currentOutputPath);
        } catch (IOException e) {
            logger.error("Failed to create output directory: " + currentOutputPath, e);
            throw new RuntimeException("Could not create output directory", e);
        }
        List<String> command = Arrays.asList(
                "C:\\Program Files\\MongoDB\\Tools\\100\\bin\\mongodump.exe",
                "--db", "bastion",
                "--collection", "sky_state",
                "--out", currentOutputPath
        );

        executeSubprocess(currentOutputPath, command);
    }

    public void dumpDatabase(String outputPath) {
        List<String> command = Arrays.asList(
                "C:\\Program Files\\MongoDB\\Tools\\100\\bin\\mongodump.exe",
                "--db", "bastion",
                "--out", outputPath
        );

        executeSubprocess(outputPath, command);
    }

    private void executeSubprocess(String path, List<String> command) {
        ProcessBuilder pb = new ProcessBuilder(command).directory(new File(path));
        logger.info("Executing command: " + pb.command());
        int exitCode = 0;
        try {
            Process process = pb.start();
            exitCode = process.waitFor();
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }

        logger.info("exitCode:" + exitCode);
    }

}