package io.danlaihk.webApp.json.hsiApi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.danlaihk.webApp.json.JsonObject;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Constituent extends JsonObject {
    private String code, constituentName;
    private int contributionChange;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getConstituentName() {
        return constituentName;
    }

    public void setConstituentName(String constituentName) {
        this.constituentName = constituentName;
    }

    public int getContributionChange() {
        return contributionChange;
    }

    public void setContributionChange(int contributionChange) {
        this.contributionChange = contributionChange;
    }
}

