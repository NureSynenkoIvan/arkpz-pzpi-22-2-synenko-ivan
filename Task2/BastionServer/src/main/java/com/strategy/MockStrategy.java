package com.strategy;

import com.model.SkyObject;
import com.model.SkyState;

import java.util.ArrayList;
import java.util.List;

public class MockStrategy implements ThreatAnalysisStrategy {

    @Override
    public List<SkyObject> analyze(SkyState skyState) {
        List<SkyObject> threateningObjects = new ArrayList<SkyObject>();
        for ( SkyObject radarObject : skyState.skyObjects) {
            if (isThreat(radarObject)) {
                threateningObjects.add(radarObject);
            }
        }
        return threateningObjects;
    }


    public boolean isThreat(SkyObject skyObject) {
        if (! skyObject.hasTransponder) {
            if (skyObject.speed > 100) {
                return true;
            }
        }
        return false;
    }

}
