package com.web.codec;

import com.model.SkyObject;

public interface RadarCodecStrategy {

    SkyObject[] decodeRadarData(String json);
}
