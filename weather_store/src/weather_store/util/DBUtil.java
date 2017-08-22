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

	private static final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
	private static final String CON_URL = "jdbc:oracle:thin:@119.205.160.192:1521:xe";
	private static final String USER_NAME = "test";
	private static final String USER_PASSWORD = "1234";
	private static DBUtil singletone;

	private DBUtil() {
		Properties prop = new Properties();
			try {
				prop.load(new FileReader("src/jdbc.properties"));
			} catch (FileNotFoundException e1) {
				System.out.println("지정된 파일을 찾을 수 없습니다.");
			} catch (IOException e) {
				System.out.println();
			}
		
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

	public static Connection getConnection() {
		Connection con = null;
		try {
			con = DriverManager.getConnection(CON_URL, USER_NAME, USER_PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	public static void close(ResultSet rs, Connection con, PreparedStatement pstmt) {
		try {
			rs.close();
			close(con, pstmt);
		} catch (SQLException e) {
		}
	}

	public static void close(Connection con, PreparedStatement pstmt) {
		try {
			con.close();
			pstmt.close();
		} catch (SQLException e) {
		}
	}
}
