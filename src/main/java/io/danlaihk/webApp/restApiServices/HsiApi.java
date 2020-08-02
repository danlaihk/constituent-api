package io.danlaihk.webApp.restApiServices;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.danlaihk.webApp.json.Underlying;
import io.danlaihk.webApp.json.hsiApi.ConData;
import io.danlaihk.webApp.json.hsiApi.Constituent;
import io.danlaihk.webApp.json.hsiApi.IndexChart;
import io.danlaihk.webApp.json.hsiApi.Jdata;
import org.springframework.web.client.RestTemplate;

import java.util.*;

public class HsiApi {
    private RestTemplate rTemp;
    private ObjectMapper mapper = new ObjectMapper();
    public HsiApi(RestTemplate template){
        this.rTemp = template;
    }

    public List<Underlying> getJdata() throws Exception{
        List<Underlying> uList = new ArrayList<>();
        String[] langArr = {"chi", "eng"};
        Map<String, Underlying> uMap = new HashMap<>();
        for(String lang: langArr){

            String url = "https://www.hsi.com.hk/data/"+lang+"/rt/index-series/hsi/constituents.do";

            Jdata q = rTemp.getForObject(url, Jdata.class);




            List<Constituent> list = q.getIndexSeriesList().get(0).getIndexList().get(0).getConstituentContent();

            for(Constituent con: list){
                String key = con.getCode();
                if(!uMap.containsKey(key)){
                    Underlying underlying = new Underlying(con.getCode());


                    if(lang.equals("chi")){

                        underlying.setCname(con.getConstituentName());
                    }else if(lang.equals("eng")){
                        underlying.setName(con.getConstituentName());
                    }

                    uMap.put(key, underlying);
                }else {
                    Underlying underlying = uMap.get(key);


                    if(lang.equals("chi")){

                        underlying.setCname(con.getConstituentName());
                    }else if(lang.equals("eng")){
                        underlying.setName(con.getConstituentName());
                    }
                }
            }

        }

        for(Map.Entry<String, Underlying> entry : uMap.entrySet()) {
            uList.add(entry.getValue());
        }

        // mapper.writerWithDefaultPrettyPrinter().writeValueAsString(uList);
        return uList;
    }

    //get chart data
    public List<List<Object>>  getChartData(){
        List<Underlying> uList = new ArrayList<>();
        String[] langArr = {"chi", "eng"};
        Map<String, Underlying> uMap = new HashMap<>();
        //https://www.hsi.com.hk/data/chi/indexes/00001.00/chart.json
        String url = "https://www.hsi.com.hk/data/chi/indexes/00001.00/chart.json";

        IndexChart i = rTemp.getForObject(url, IndexChart.class);
        return i.getPts();

        //ObjectMapper m = new ObjectMapper();
        //System.out.println(m.writerWithDefaultPrettyPrinter().writeValueAsString(pts));



        // mapper.writerWithDefaultPrettyPrinter().writeValueAsString(uList);

    }

    public List<Underlying> getConData(){
        List<Underlying> uList = new ArrayList<>();
        String[] langArr = {"chi", "eng"};
        Map<String, Underlying> uMap = new HashMap<>();
        for(String lang: langArr){

            String url = "https://www.hsi.com.hk/data/"+lang+"/rt/index-series/hsi/constituents.do";

            ConData q = rTemp.getForObject(url, ConData.class);


            List<Constituent> list = q.getIndexSeriesList().get(0).getIndexList().get(0).getConstituentContent();


            for(Constituent con: list){
                String key = con.getCode();
                if(!uMap.containsKey(key)){
                    Underlying underlying = new Underlying(con.getCode());

                    if(lang.equals("chi")){

                        underlying.setCname(con.getConstituentName());
                    }else if(lang.equals("eng")){
                        underlying.setName(con.getConstituentName());
                    }

                    uMap.put(key, underlying);
                }else {
                    Underlying underlying = uMap.get(key);


                    if(lang.equals("chi")){

                        underlying.setCname(con.getConstituentName());
                    }else if(lang.equals("eng")){
                        underlying.setName(con.getConstituentName());
                    }
                }
            }

        }

        for(Map.Entry<String, Underlying> entry : uMap.entrySet()) {
            uList.add(entry.getValue());
        }

        // mapper.writerWithDefaultPrettyPrinter().writeValueAsString(uList);
        return uList;
    }

}
