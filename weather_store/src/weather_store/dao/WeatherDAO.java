package weather_store.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import weather_store.dto.WeatherDTO;
import weather_store.town.TownCodeService;
import weather_store.town.WeatherRSS;
import weather_store.util.DBUtil;

public class WeatherDAO {
	private static WeatherDAO weatherDAO;

	private WeatherDAO() {
	}

	public static WeatherDAO getInstance() {
		if (weatherDAO == null) {
			weatherDAO = new WeatherDAO();
		}
		return weatherDAO;
	}// end of getInstance();

	public int coordinateSQLInsert() {
		int status = -1;
		TownCodeService tcs = new TownCodeService();
		status = tcs.outputSQL();
		return status;
	}

	public int weatherSQLInsert() {
		int status = -1;
		WeatherRSS r = new WeatherRSS();

		List<List<Map<String, String>>> result = r.getAllTownForecast();

		// r.insertData(result); // 최초 실행 한번만 실행할 것.
		status = r.updateData(result); // 기상청으로부터 가져온 정보를 DB에 업데이트

		System.out.println("작업 완료");
		return status;
	}

	public List<Long> faveriteLocal(String id) {
		List<Long> list = new ArrayList<Long>();
		DBUtil db = DBUtil.getInstance();
		Connection con = db.getConnection();
		String sql = "select local_code from INTERESTAREA where id = ?";
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(rs.getLong(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<WeatherDTO> weatherServe(long localCode) {
		List<WeatherDTO> list = new ArrayList<>();
		int x = 0;
		int y = 0;
		DBUtil db = DBUtil.getInstance();
		Connection con = db.getConnection();
		String sql = "select * from coordinates where local_code = ?";
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, localCode);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				x = rs.getInt("x");
				y = rs.getInt("y");
			}
			sql = "select * from weather_info where x=? and y=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, x);
			pstmt.setInt(2, y);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				WeatherDTO weather = new WeatherDTO();
				weather.setX(rs.getInt("x"));
				weather.setY(rs.getInt("y"));
				weather.setDataSeq(rs.getInt("data_seq"));
				weather.setHour(rs.getInt("day"));
				weather.setTemp(rs.getInt("temp"));
				weather.setTmx(rs.getInt("tmx"));
				weather.setTmn(rs.getInt("tmn"));
				weather.setSky(rs.getInt("sky"));
				weather.setPty(rs.getInt("pty"));
				weather.setWfKOR(rs.getString("wfkor"));
				weather.setWfEN(rs.getString("wfen"));
				weather.setPop(rs.getInt("pop"));
				weather.setReh(rs.getInt("reh"));
				weather.setWs(rs.getInt("ws"));
				weather.setWd(rs.getInt("wd"));
				weather.setWdKOR(rs.getString("wdkor"));
				weather.setWdEN(rs.getString("wden"));
				weather.setR12(rs.getInt("r12"));
				weather.setS12(rs.getInt("s12"));
				weather.setR06(rs.getInt("r06"));
				weather.setS06(rs.getInt("s06"));
				list.add(weather);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
} // end of class
