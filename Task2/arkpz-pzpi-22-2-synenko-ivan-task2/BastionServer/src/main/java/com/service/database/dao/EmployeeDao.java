package com.service.database.dao;

import com.model.Device;
import com.model.Employee;
import com.model.enums.Role;
import com.model.enums.SortingOrder;

import java.util.List;
import java.util.Map;

public interface EmployeeDao {
    public void save(Employee employee);

    public void update(Employee employee);

    public void edit(Employee employee, Map<String, String> changes);

    public List<Employee> getAll(String sortBy, SortingOrder order);

    public List<Employee> getByRoles (Role[] roles, SortingOrder order);

    public void delete(Employee employee);

    Employee get(String phoneNumber);
}
