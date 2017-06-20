# BA101G2 專案規範

Eclipse專案編碼 UTF-8
資料庫連線帳密:
目前是servlet/123456，考慮之後統一為ba101g2/ba101g2
Server(獨立貓和嵌入貓)的context.xml內加入:/<Resource auth="Container" driverClassName="oracle.jdbc.driver.OracleDriver" maxActive="20" maxIdle="10" maxWait="-1" name="jdbc/G2DB" password="ba101g2" type="javax.sql.DataSource" url="jdbc:oracle:thin:@localhost:1521:XE" username="ba101g2"//> 
------------------------------------
專案的WebContent的WEB-INF的web.xml內的:
/</welcome-file-list>下面添加:
<resource-ref>
		<description>DB Connection</description>
		<res-ref-name>jdbc/G2DB</res-ref-name>
		<res-type>javax.sql.DataSource</res-type>
		<res-auth>Container</res-auth>
	/</resource-ref>

