package com.strategy;

import com.model.SkyObject;
import com.model.SkyState;

import java.util.List;

public interface ThreatAnalysisStrategy {
    public List<SkyObject> analyze(SkyState skyState);
}
