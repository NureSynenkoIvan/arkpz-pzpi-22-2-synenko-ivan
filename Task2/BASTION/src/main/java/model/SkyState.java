package model;


import java.util.Date;
import java.util.List;

public class SkyState {
    private Date time;
    private List<SkyObject> skyObjects;

    public SkyState() {

    }

    public SkyState(Date time, List<SkyObject> skyObjects) {
        this.time = time;
        this.skyObjects = skyObjects;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public List<SkyObject> getSkyObjects() {
        return skyObjects;
    }

    public void setSkyObjects(List<SkyObject> skyObjects) {
        this.skyObjects = skyObjects;
    }
}
