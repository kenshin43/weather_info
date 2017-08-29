package weather_store.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil {

	private static final String DRIVER_NAME;
	private static final String CON_URL;
	private static final String USER_NAME;
	private static final String USER_PASSWORD;
	private static DBUtil singletone;

	static {
		Properties prop = new Properties();
		try {
			prop.load(new FileReader("jdbc.properties"));
		} catch (FileNotFoundException e1) {
			System.out.println("지정된 파일을 찾을 수 없습니다.");
		} catch (IOException e) {
			System.out.println("IOExeption");
		}
		DRIVER_NAME = prop.getProperty("driver");
		CON_URL = prop.getProperty("url");
		USER_NAME = prop.getProperty("user");
		USER_PASSWORD = prop.getProperty("password");
	}

	private DBUtil() {
		try {
			Class.forName(DRIVER_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static DBUtil getInstance() {
		if (singletone == null) {
			singletone = new DBUtil();
		}
		return singletone;
	}

	public Connection getConnection() {
		Connection con = null;
		try {
			con = DriverManager.getConnection(CON_URL, USER_NAME, USER_PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	public void close(ResultSet rs, Connection con, PreparedStatement pstmt) {
		try {
			rs.close();
			close(con, pstmt);
		} catch (SQLException e) {
		}
	}

	public void close(Connection con, PreparedStatement pstmt) {
		try {
			con.close();
			pstmt.close();
		} catch (SQLException e) {
		}
	}
}
