package com.service.database.dao;

import com.model.SkyState;
import com.model.enums.SortingOrder;

import java.util.Collection;
import java.util.List;

public interface SkyStateDao {
    public void save(SkyState skyState);

    public void saveAll(Collection<SkyState> list);

    public List<SkyState> getAll(SortingOrder order);

    public void delete(SkyState skyState);

    public void deleteAll();
}
