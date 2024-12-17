package com.web.codec;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.SkyObject;

public class FasterXMLCodecStrategy implements RadarCodecStrategy {
    private final ObjectMapper mapper = new ObjectMapper();




    @Override
    public SkyObject[] decodeRadarData(String json) {
        try {
        return mapper.readValue(json, SkyObject[].class);
    } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
