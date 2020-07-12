package io.danlaihk.webApp.dataservices;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.danlaihk.webApp.json.Underlying;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class ConstituentRepository {
    private JdbcTemplate jdbcTemplate;
    private ObjectMapper mapper = new ObjectMapper();

    public ConstituentRepository(JdbcTemplate template){
        this.jdbcTemplate = template;
    }




    public List<Underlying> getList(){
        String statement = "SELECT * from constituent where code is not null order by code;";
        List<Underlying> list = jdbcTemplate.query(
                statement,
                    (rs, rowNum) ->{
                        return new Underlying(
                                rs.getInt("id"),
                                rs.getInt("code"),
                                rs.getString("name"),
                                rs.getString("cname"),
                                rs.getString("stime"),
                                rs.getString("index_type")
                        );
                    }

                );
        return list.size() > 0? list: Collections.emptyList();
    }
    public Underlying findByCode(Integer code){
        String statement = "SELECT * from constituent where code =? order by stime desc limit 1;";
        List<Underlying> list =  jdbcTemplate.query(
                statement, new Object[]{String.valueOf(code)},
                (rs, rowNum) ->{
                    return new Underlying(
                            rs.getInt("id"),
                            rs.getInt("code"),
                            rs.getString("name"),
                            rs.getString("cname"),
                            rs.getString("stime"),
                            rs.getString("index_type")
                    );
                }

        );

        return (list.size() > 0)? list.get(0): new Underlying();
    }
    public void batchUpdate(List<Underlying> list){



        List<Integer> result = jdbcTemplate.query(
                "SELECT count(*) as total from constituent;",
                (rs, rowNum) -> rs.getInt("total"));


        int total = result.get(0);


        if(total > 0){

            String statement = "UPDATE constituent SET " +
                    "stime = NOW(), index_type= 'HSI', name=?, cname=? " +
                    "WHERE code =?;";
            for(Underlying u: list){
                jdbcTemplate.update(statement,  u.getName(),u.getCname(), u.getCode());
            }
        }else {

            String statement = "INSERT INTO constituent (stime, index_type, code, name, cname) VALUES (NOW(), 'HSI', ?,?,?)";


            int[] resultList = jdbcTemplate.batchUpdate(statement, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                    Underlying u = list.get(i);
                    preparedStatement.setInt(1, u.getCode());
                    preparedStatement.setString(2, u.getName());
                    preparedStatement.setString(3, u.getCname());
                }

                @Override
                public int getBatchSize() {
                    return list.size();
                }

            });



        }


    }
}
