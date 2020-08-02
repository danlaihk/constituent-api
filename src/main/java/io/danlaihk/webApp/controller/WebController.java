package io.danlaihk.webApp.controller;

import io.danlaihk.webApp.dataservices.ConstituentRepository;
import io.danlaihk.webApp.json.Underlying;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Controller
public class WebController {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/")
    public String getIndex(Model model){
        List<Underlying> conlist = getConList();
        Map<String, String> ulist = new HashMap<>();
        for(Underlying u: conlist){
            ulist.put(String.valueOf(u.getCode()),  encodeUtf8(u.getCname()) + " " + u.getName() );
        }
        model.addAttribute("ulist", ulist);
        return "index";
    }
    private String encodeUtf8(String input){
        byte[] bytes = input.getBytes(StandardCharsets.UTF_8);

        String utf8EncodedString = new String(bytes, StandardCharsets.UTF_8);
        return utf8EncodedString;
    }
    public List<Underlying> getConList(){
        ConstituentRepository conRepo = new ConstituentRepository(jdbcTemplate);
        return conRepo.getList();
    }

}
