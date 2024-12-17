package com.service.database.dao.impl;

import com.mongodb.ErrorCategory;
import com.mongodb.MongoServerException;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.*;
import com.mongodb.client.model.Sorts;

import com.service.database.dao.AbstractDao;
import com.service.database.dao.EmployeeDao;
import com.model.Employee;
import com.model.enums.Role;
import com.model.enums.SortingOrder;
import org.bson.conversions.Bson;
import com.utils.MongoUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EmployeeDaoImpl extends AbstractDao implements EmployeeDao {
    public EmployeeDaoImpl(MongoUtil mongoUtil) {
        super(mongoUtil, Employee.class);
        getCollection().createIndex(Indexes.ascending("phoneNumber"), new IndexOptions().unique(true));
    }

    @Override
    public void save(Employee employee) {
        MongoCollection<Employee> employeeCollection = getCollection();
        try {
            employeeCollection.insertOne(employee);
        } catch (MongoWriteException e) {
            if (e.getError().getCategory() == ErrorCategory.DUPLICATE_KEY) {
                throw new IllegalArgumentException("An employee with this phone number already exists!");
            }
            throw e;
        }
    }

    @Override
    public void update(Employee employee) {
        MongoCollection<Employee> employeeCollection = getCollection();
        Bson findQuery = Filters.eq("phoneNumber", employee.getPhoneNumber());

        employeeCollection.findOneAndReplace(findQuery, employee);
    }

    @Override
    public void edit(Employee employee, Map<String, String> changes) {
        MongoCollection<Employee> employeeCollection = getCollection();
        Bson findQuery = Filters.eq("phoneNumber", employee.getPhoneNumber());

        List<Bson> updates = new ArrayList<>();
        for (Map.Entry<String, String> change : changes.entrySet()) {
            updates.add(Updates.set(change.getKey(), change.getValue()));
        }
        Bson updateQuery = Updates.combine(updates);

        try {
            employeeCollection.findOneAndUpdate(findQuery, updateQuery);
        } catch (MongoServerException e) {
            throw new IllegalArgumentException("An employee with this phone number already exists!");
        }
    }

    @Override
    public List<Employee> getAll(String sortBy, SortingOrder order) {
        Bson sortQuery;
        if (order == SortingOrder.ASC) {
            sortQuery = Sorts.ascending("name");
        } else {
            sortQuery = Sorts.descending("name");
        }

        MongoCollection<Employee> employeeCollection = getCollection();
        return employeeCollection
                .find()
                .sort(sortQuery)
                .into(new ArrayList<>());
    }

    @Override
    public List<Employee> getByRoles(Role[] roles, SortingOrder order) {
        Bson sortQuery;

        if (order == SortingOrder.ASC) {
            sortQuery = Sorts.ascending("name");
        } else {
            sortQuery = Sorts.descending("name");
        }

        Bson filterQuery = Filters.in("role", roles);

        MongoCollection<Employee> employeeCollection = getCollection();
        return employeeCollection
                .find(filterQuery)
                .sort(sortQuery)
                .into(new ArrayList<>());
    }

    @Override
    public void delete(Employee employee) {
        MongoCollection<Employee> deviceCollection = getCollection();
        deviceCollection.deleteOne(
                Filters.eq("phoneNumber", employee.getPhoneNumber()));
    }

    @Override
    public Employee get(String phoneNumber) {
        MongoCollection<Employee> employeeCollection = getCollection();
        Bson findQuery = Filters.eq("phoneNumber", phoneNumber);
        return employeeCollection.find(findQuery).first();
    }
}
