# constituent-api
web api services for constituent 

All data rights reserved by hsi.com.hk <br/>
Educational use only.

**data table structure (MySql) is written in table-structure/constituent**


**************************************************************************
<b>Api Endpoint</b>

Method: Get

http://{{domain.name}}/api/constituent <br/>
http://{{domain.name}}/api/constituent/list <br/><br/>
*Get the list of constituents <br/>
<br/>




http://{{domain.name}}/api/constituent/code/{{code number}} <br/><br/>
*Get the constituent by code number(eg: 700) <br/>

http://{{domain.name}}/api/chart/index/{index} <br/>
<br/><br/>
*Get the indexs' chart data(only available for HSI temporaily)



Mthod: Post<br/>
http://{{domain.name}}/api/constituent/list<br/><br/>
*Renew the list in database from hsi.com.hk<br/>

http://{{domain.name}}/api/chart/hsi<br/><br/>
*update hsi chart data.<br/>


http://{{domain.name}}/api/remote/data<br/><br/>
*update hsi chart data & constituent list at same time.<br/>

**************************************************************************


1.web page sample
![image](https://github.com/danlaihk/constituent-api/blob/master/web%20page.png)

2.api json
![image](https://github.com/danlaihk/constituent-api/blob/master/api%20json.png)

![image](https://github.com/danlaihk/constituent-api/blob/master/post%20remote%20data.png)

3.data table
![image](https://github.com/danlaihk/constituent-api/blob/master/data%20table.png)


