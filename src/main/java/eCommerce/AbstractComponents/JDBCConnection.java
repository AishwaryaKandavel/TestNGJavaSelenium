package eCommerce.AbstractComponents;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class JDBCConnection {
	
	Properties prop;
	public JDBCConnection(Properties prop) {
		this.prop = prop;
	}

	public List<HashMap<Object, Object>> getDBDataToMap(String queryName) throws SQLException {
		// TODO Auto-generated method stub
		List<HashMap<Object, Object>> data = new ArrayList<HashMap<Object,Object>>();
		
		String host = prop.getProperty("host");
		int port = Integer.parseInt(prop.getProperty("port"));
		String dbName = prop.getProperty("dbName");
		String root = prop.getProperty("root");
		String password = prop.getProperty("password");
		
		Connection conn = DriverManager.getConnection
				("jdbc:mysql://" + host + ":" + port + "/" + dbName, root, password);
		Statement s = conn.createStatement();
		ResultSet rs = s.executeQuery(prop.getProperty(queryName));
		ResultSetMetaData mdata = rs.getMetaData();
		int count = mdata.getColumnCount();
		List<String> columnNames = new ArrayList<String>();
		for(int i=1; i<=count; i++)
			columnNames.add(mdata.getColumnName(i));
		while (rs.next()) {
			HashMap<Object, Object> row = new HashMap<Object, Object>();
			for (String column : columnNames) {
				row.put(column, rs.getString(column));
			}
			data.add(row);
		}
		System.out.println(data);
		return data;
	}
}
