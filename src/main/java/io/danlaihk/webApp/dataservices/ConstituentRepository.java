package io.danlaihk.webApp.dataservices;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.danlaihk.webApp.json.ChartData;
import io.danlaihk.webApp.json.Underlying;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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
    public void updateHsiChart(List<List<Object>> list){
        if(list.size() > 0){
            jdbcTemplate.execute("TRUNCATE TABLE index_lists");
            //create table if not exists <mytable>;

            //truncate table <mytable>;
            Date date = new Date();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            df.setTimeZone(TimeZone.getTimeZone("Asia/Hong_Kong"));


            String statement = "INSERT INTO index_lists (stime, timestamp, last, index_type) VALUES ('"+df.format(date)+"', ?,?, 'HSI')";


            int[] resultList = jdbcTemplate.batchUpdate(statement, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                    //List<List<Object>>

                    List<Object> pt = list.get(i);
                    preparedStatement.setLong(1, Long.parseLong(pt.get(0).toString()));
                    preparedStatement.setFloat(2, Float.parseFloat(pt.get(1).toString()));

                }

                @Override
                public int getBatchSize() {
                    return list.size();
                }

            });

        }
    }

    public ChartData findChartByIndex(String index){
        List<String> result = jdbcTemplate.query(
                "select stime from index_lists order by stime desc limit 1",
                (rs, rowNum) -> rs.getString("stime")
        );

        String stime = result.get(0);

        String sql = "SELECT timestamp, last FROM index_lists WHERE index_type = '"+index+"' order by timestamp";
        List<List<Object>> pts = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> {
                    return new ArrayList<Object>(){
                        {
                            add(rs.getLong("timestamp"));
                            add(rs.getFloat("last"));
                        }
                    };
                }
        );

        return new ChartData(stime,  pts);
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
    private String encodeUtf8(String input){
        byte[] bytes = input.getBytes(StandardCharsets.UTF_8);

        String utf8EncodedString = new String(bytes, StandardCharsets.UTF_8);
        return utf8EncodedString;
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
                jdbcTemplate.update(statement,  u.getName(), encodeUtf8(u.getCname()), u.getCode());
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
