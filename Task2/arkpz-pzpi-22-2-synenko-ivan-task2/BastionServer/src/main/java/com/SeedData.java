package com;

import com.model.*;
import com.model.enums.DeviceType;
import com.model.enums.Role;
import com.model.enums.SortingOrder;
import com.model.enums.WeekDay;
import com.model.types.Coordinates;
import com.model.types.WorkTime;
import com.mongodb.MongoWriteException;
import com.service.database.DatabaseService;
import com.service.database.dao.DeviceDao;
import com.service.database.dao.EmployeeDao;
import com.service.database.dao.MessageScenarioDao;
import com.service.database.dao.SkyStateDao;
import com.service.database.dao.impl.SkyStateDaoImpl;
import com.utils.MongoUtil;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


//Script for filling database with data.
public class SeedData {
    static DatabaseService dbService = DatabaseService.getInstance();
    static DeviceDao deviceDao = dbService.getDeviceDao();
    ;
    static EmployeeDao employeeDao = dbService.getEmployeeDao();
    ;
    static MessageScenarioDao messageScenarioDao = dbService.getMessageScenarioDao();
    ;

    static List<Device> devices = List.of(
            new Device("Radar1", DeviceType.RADAR, new Coordinates(50.45, 30.52)),
            new Device("Radar2", DeviceType.RADAR, new Coordinates(40.73, -73.93)),
            new Device("Radar3", DeviceType.RADAR, new Coordinates(34.05, -118.25))
    );
    static List<WeekDay> fullWeek = List.of(
            WeekDay.MONDAY,
            WeekDay.TUESDAY,
            WeekDay.WEDNESDAY,
            WeekDay.THURSDAY,
            WeekDay.FRIDAY
    );

    static WorkTime fullWeekWorkTime = new WorkTime(
            fullWeek,
            LocalTime.of(8, 00),
            LocalTime.of(16, 00)
    );

    static List<Employee> employees = List.of(
            new Employee("Boris", "Bilyy", "38123-456-7890", Role.DISPATCHER, "Supervisor",
                    null, fullWeekWorkTime, false, "password1".hashCode()),
            new Employee("Kirylo", "Sarov", "38123-456-7891", Role.USER, "Technician",
                    null, fullWeekWorkTime, false, "password2".hashCode()),
            new Employee("Mykyta", "Tomashko", "38123-456-7892", Role.USER, "Assistant",
                    null, fullWeekWorkTime, false, "password3".hashCode()),
            new Employee("Ilia", "Zhurenko", "38123-456-7893", Role.USER, "Operator",
                    null, fullWeekWorkTime, false, "password4".hashCode()),
            new Employee("Alexandr", "Kushchin", "38123-456-7894", Role.USER, "Manager",
                    null, fullWeekWorkTime, false, "password5".hashCode()),
            new Employee("Erik", "Cantorovich", "38123-456-7895", Role.DISPATCHER, "Supervisor",
                    null, fullWeekWorkTime, false, "password6".hashCode()),
            new Employee("Taras", "Levin", "38123-456-7896", Role.USER, "Technician",
                    null, fullWeekWorkTime, false, "password7".hashCode()),
            new Employee("Vladyslav", "Vovchenko", "38123-456-7897", Role.USER, "Assistant",
                    null, fullWeekWorkTime, false, "password8".hashCode()),
            new Employee("Savely", "Hryhorenko", "38123-456-7898", Role.USER, "Mechanic",
                    null, fullWeekWorkTime, false, "password9".hashCode()),
            new Employee("Ivan", "Synenko", "38123-456-7899", Role.ADMINISTRATOR, "Director",
                    null, fullWeekWorkTime, false, "password10".hashCode())
    );

    static List<MessageScenario> messageScenarios = List.of(
            new MessageScenario("Air Alert",
                    "Увага! Оголошена повітряна тривога! Негайно прослідуйте до укриття",
                    "Повітряна тривога завершена"),
            new MessageScenario("Chemical Alert",
                    "Увага! Хімічна тривога! Зберігайте спокій і негайно прослідуйте до укриття",
                    "Відбій хімічної тривоги")
    );

    public static void main(String[] args) {
        try {
            /*
            devices.forEach(device -> {
                deviceDao.save(device);
                System.out.println("Inserted into db: " + device.toString());
            });*//*
            employees.forEach(employee -> {
                employeeDao.save(employee);
                System.out.println("Inserted into db: " + employee.toString());
            });*/
            /*messageScenarios.forEach(scenario -> {
                messageScenarioDao.save(scenario);
                System.out.println("Inserted into db: " + scenario.toString());
            });*/

        } catch (MongoWriteException e) {
            System.out.println("You're trying to insert test information into DB, while it's already there. \n"
                    + e.getMessage() );
        }
    }


}
