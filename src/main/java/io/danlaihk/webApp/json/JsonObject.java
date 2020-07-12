package io.danlaihk.webApp.json;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonObject {
    private ObjectMapper mapper = new ObjectMapper();

    public String jEncode()throws Exception
    {
        return mapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(this);
    }
}
