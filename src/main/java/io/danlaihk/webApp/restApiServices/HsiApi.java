package io.danlaihk.webApp.restApiServices;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.danlaihk.webApp.json.Underlying;
import io.danlaihk.webApp.json.hsiApi.Constituent;
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
    /*
    public Map<String, Underlying> getJdata(RestTemplate rTemp){


        Map<String, Underlying> uMap = new HashMap<>();
        try{
            String[] langArr = {"chi", "eng"};


            for(String lang: langArr){

                String url = "https://www.hsi.com.hk/data/"+lang+"/rt/index-series/hsi/constituents.do";

                Jdata q = rTemp.getForObject(url, Jdata.class);

                //System.out.println("The api uri is:"); //
                //System.out.println(url); //
                //stime
                //System.out.println(q.getRequestDate()); //



                List<Constituent> list = q.getIndexSeriesList().get(0).getIndexList().get(0).getConstituentContent();

                for(Constituent con: list){
                    String key = con.getCode();
                    if(!uMap.containsKey(key)){
                        Constituent underlying = new Constituent(con.getCode());


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


        }catch (Exception e){
            System.out.println(e.toString());
        }
        return uMap;
    }
    
     */

}
