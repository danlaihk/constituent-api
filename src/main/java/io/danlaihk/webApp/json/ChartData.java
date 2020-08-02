package io.danlaihk.webApp.json;

import java.util.List;

public class ChartData extends JsonObject{
    private String stime;
    private List<List<Object>> pts;

    public ChartData(){}
    public ChartData(String stime, List<List<Object>> pts){
        this.stime = stime;
        this.pts = pts;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public List<List<Object>> getPts() {
        return pts;
    }

    public void setPts(List<List<Object>> pts) {
        this.pts = pts;
    }
}
