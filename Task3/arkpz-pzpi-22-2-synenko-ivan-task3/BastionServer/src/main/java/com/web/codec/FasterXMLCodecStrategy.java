package com.web.codec;

import com.config.LocationConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.SkyObject;
import com.model.types.Coordinates;

public class FasterXMLCodecStrategy implements RadarCodecStrategy {
    private final LocationConfig locationConfig = LocationConfig.getInstance();
    private final ObjectMapper mapper = new ObjectMapper();


    @Override
    public SkyObject[] decodeRadarData(String json) throws IllegalArgumentException {
        try {
            SkyObject[] objects = mapper.readValue(json, SkyObject[].class);


            /*
            FOR LATER RELEASE: SHIFT TO COORDINATE SYSTEM
            Coordinates zeroPoint = locationConfig.getCoordinates();

            for (SkyObject object : objects) {
                object.shiftToCoordinateSystem(zeroPoint);

            }

                        return objects;
            */
            return objects;
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
