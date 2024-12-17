package com.service.database;

import com.config.DatabaseConfig;
import com.config.DevicesConfig;
import com.service.database.dao.*;
import com.service.database.dao.impl.DeviceDaoImpl;
import com.service.database.dao.impl.EmployeeDaoImpl;
import com.service.database.dao.impl.MessageScenarioDaoImpl;
import com.service.database.dao.impl.SkyStateDaoImpl;
import com.utils.MongoUtil;

public class DatabaseService {

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

    public void dumpSkyStates() {

    }
}