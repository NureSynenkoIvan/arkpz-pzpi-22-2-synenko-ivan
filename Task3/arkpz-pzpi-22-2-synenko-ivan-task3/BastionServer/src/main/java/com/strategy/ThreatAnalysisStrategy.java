package com.strategy;

import com.model.SkyObject;
import com.model.SkyState;

import java.util.List;

public interface ThreatAnalysisStrategy {

    //Returns list of threatening objects.
    public List<SkyObject> analyze(SkyState skyState);
}
