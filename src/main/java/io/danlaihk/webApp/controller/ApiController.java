package io.danlaihk.webApp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.danlaihk.webApp.dataservices.ConstituentRepository;
import io.danlaihk.webApp.json.Underlying;

import io.danlaihk.webApp.restApiServices.HsiApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
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
        ConstituentRepository conRepo = new ConstituentRepository(jdbcTemplate);
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

    @PostMapping("/constituent/list") //refresh the list
    public String postConList() throws Exception{
        HsiApi remoteApi = new HsiApi(restTemplate);
        List<Underlying> list = remoteApi.getJdata();
        ConstituentRepository conRepo = new ConstituentRepository(jdbcTemplate);
        conRepo.batchUpdate(list);

        return "Post Success!";
    }



}
