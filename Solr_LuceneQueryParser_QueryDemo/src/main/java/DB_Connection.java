

import java.sql.Connection;
import java.sql.DriverManager;

public class DB_Connection {
	final String user ="root";
	final String password = "dpchn";
	public Connection getDbConnection() {
		Connection connection=null;
		try {
			String url = "jdbc:mysql://localhost:3306/solr";
			connection = DriverManager.getConnection(url,user, password );
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

}
