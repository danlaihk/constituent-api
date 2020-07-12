package io.danlaihk.webApp.json.hsiApi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.danlaihk.webApp.json.JsonObject;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class IndexSeries extends JsonObject {
    private String seriesName;
    private String seriesCode;
    private List<Index> indexList;



    public String getSeriesCode() {
        return seriesCode;
    }

    public void setSeriesCode(String seriesCode) {
        this.seriesCode = seriesCode;
    }


    public List<Index> getIndexList() {
        return indexList;
    }

    public void setIndexList(List<Index> indexList) {
        this.indexList = indexList;
    }
}
