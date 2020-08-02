package io.danlaihk.webApp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;


import io.danlaihk.webApp.dataservices.ConstituentRepository;
import io.danlaihk.webApp.json.ChartData;
import io.danlaihk.webApp.json.Underlying;
import io.danlaihk.webApp.restApiServices.HsiApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    RestTemplate restTemplate;


    @GetMapping(value = {
            "/constituent",
            "/constituent/list",
    })
    public String getConList()throws Exception{
        System.out.println("check");
        ConstituentRepository conRepo = new ConstituentRepository(jdbcTemplate);
        //return user.toString();
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(conRepo.getList());
    }

    @GetMapping("/constituent/code/{code}")
    public String findConByCode(@PathVariable Integer code)throws Exception{
        Underlying underlying;
        ConstituentRepository conRepo = new ConstituentRepository(jdbcTemplate);

        if(code != null){
            underlying = conRepo.findByCode(code);
        }else{
            underlying = new Underlying();
        }

        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(underlying);

    }
    @GetMapping("chart/index/{index}")
    public String findChartByCode(@PathVariable String index)throws Exception{
        ChartData data;
        ConstituentRepository conRepo = new ConstituentRepository(jdbcTemplate);


        if(index != null){
            data = conRepo.findChartByIndex(index);
        }else{
            data = new ChartData();
        }

        return data.jEncode();
        //return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(underlying);

    }

    @PostMapping("/remote/data")
    public String refreshData() throws Exception{
        int chartResult = updateHsiChart();
        int conResult = postConList();

        if(chartResult == 1 && conResult == 1){
            return "Update Data Success!";
        }else{
            if(chartResult != 1){
                System.out.println("chart update failed");
            }else if(conResult != 1){
                System.out.println("con update failed");
            }

            return "Update Failed!";
        }
    }

    @PostMapping("/chart/hsi")
    public int updateHsiChart() throws Exception{
        HsiApi api = new HsiApi(restTemplate);
        List<List<Object>> list =  api.getChartData();

        ConstituentRepository conRepo = new ConstituentRepository(jdbcTemplate);
        conRepo.updateHsiChart(list);
        return 1;
        //return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(list);

    }
    /////////////////////////////////////////////
    @PostMapping("/constituent/list") //refresh the list
    public int postConList() throws Exception{
        HsiApi remoteApi = new HsiApi(restTemplate);
        List<Underlying> list = remoteApi.getConData();

        if(list.size() == 50) {
            ConstituentRepository conRepo = new ConstituentRepository(jdbcTemplate);
            conRepo.batchUpdate(list);
            return 1;
        }else {
            return 0;
        }



    }



}
