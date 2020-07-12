package io.danlaihk.webApp.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Underlying extends JsonObject{
    private int id, code;

    private String name, cname, stime, index;
    private ObjectMapper mapper = new ObjectMapper();

    public Underlying(){}
    public Underlying(String code){
        this.code = Integer.parseInt(code);
    }
    public Underlying(int id, int code, String name, String cname, String stime, String index){
        this.id = id;
        this.code = code;
        this.name = name;
        this.cname = cname;
        this.stime = stime;
        this.index = index;
    }

    public String jEncode()throws Exception
    {
        return mapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(this);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getId() {
        return id;
    }



    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStime() {
        return stime;
    }


}
