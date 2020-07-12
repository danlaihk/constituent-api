# constituent-api
web api services for constituent 

All data rights reserved by hsi.com.hk <br/>
Educational use only.

data table structure (MySql)


CREATE TABLE `constituent` (
  `id` int(11) NOT NULL,
  `code` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `cname` varchar(100) DEFAULT NULL,
  `stime` datetime NOT NULL,
  `index_type` varchar(50) DEFAULT NULL
);



1.web page sample
![image](https://github.com/danlaihk/constituent-api/blob/master/web%20page.png)

2.api json
![image](https://github.com/danlaihk/constituent-api/blob/master/api%20json.png)

3.data table
![image](https://github.com/danlaihk/constituent-api/blob/master/data%20table.png)


