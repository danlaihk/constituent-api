package io.danlaihk.webApp.json.hsiApi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.danlaihk.webApp.json.JsonObject;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Index extends JsonObject {
    private String indexName;
    private List<Constituent> constituentContent;
    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }


    public List<Constituent> getConstituentContent() {
        return constituentContent;
    }

    public void setConstituentContent(List<Constituent> constituentContent) {
        this.constituentContent = constituentContent;
    }
}
