package io.danlaihk.webApp.json.hsiApi;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.danlaihk.webApp.json.JsonObject;


import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IndexChart extends JsonObject {

    @JsonProperty(value="indexLevels-5y")
    private List<List<Object>> pts = new ArrayList<>();


    public List<List<Object>> getPts() {
        return pts;
    }

    public void setPts(List<List<Object>> pts) {
        this.pts = pts;
    }
}



