package io.danlaihk.webApp.json.hsiApi;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.danlaihk.webApp.json.JsonObject;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Jdata extends JsonObject {

    private String requestDate;
    private List<IndexSeries> indexSeriesList;

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public List<IndexSeries> getIndexSeriesList() {
        return indexSeriesList;
    }

    public void setIndexSeriesList(List<IndexSeries> indexSeriesList) {
        this.indexSeriesList = indexSeriesList;
    }
}

