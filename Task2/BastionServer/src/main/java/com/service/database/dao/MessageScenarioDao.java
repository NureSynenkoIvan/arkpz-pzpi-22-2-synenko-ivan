package com.service.database.dao;

import com.model.Employee;
import com.model.MessageScenario;

import java.util.List;
import java.util.Map;

public interface MessageScenarioDao {
    public void save(MessageScenario scenario);

    public void update(MessageScenario scenario);

    public void edit(MessageScenario scenario, Map<String, String> changes);

    public List<MessageScenario> getAll();

    public void delete(MessageScenario scenario);

    MessageScenario get(String scenarioName);
}
