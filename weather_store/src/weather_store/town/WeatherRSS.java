package weather_store.town;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import weather_store.util.DBUtil;

public class WeatherRSS {

	// http://www.kma.go.kr/wid/queryDFS.jsp?gridx=59&gridy=125
	private String rssFeed = "http://www.kma.go.kr/wid/queryDFS.jsp?gridx=%s&gridy=%s"; // 주소

	public WeatherRSS() {
	}

	/**
	 * 기상청으로부터 모든 동네 날씨 정보를 가져오기. :
	 */
	public List<List<Map<String, String>>> getAllTownForecast() {
		DBUtil db = DBUtil.getInstance();
		Connection conn = db.getConnection();
		Statement stmt = null;
		ResultSet rs = null;

		// 동네 X.Y 좌표 그룹 리스트 조회
		String sql = "select x,y from COORDINATES";

		List<List<Map<String, String>>> result = new ArrayList<>();

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				String x = rs.getString("x");
				String y = rs.getString("y");
				if (!(x.equals("0") && y.equals("0"))) {
					List<Map<String, String>> point = getTownForecast(x, y);
					result.add(point);
				}
			}

			System.out.println("크기:" + result.size());

		} catch (Exception e) {

		} finally {

			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			try {
				conn.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	/**
	 * 날씨 정보 DB입력 : TBL_WFORECAST_DATA
	 */
	public void insertData(List<List<Map<String, String>>> result) {
		DBUtil db = DBUtil.getInstance();
		Connection con = db.getConnection();

		PreparedStatement pstmt = null;

		String sql = "INSERT INTO WEATHER_INFO (X,Y,HOUR,DATA_SEQ,DAY,TEMP,TMX,TMN,"
				+ "SKY,PTY,WFKOR,WFEN,POP,REH,WS,WD,WDKOR,WDEN,R12,S12,R06,S06)	VALUES "
				+ "(:x,:y,:HOUR, :DATA_SEQ, :day, :TEMP,:TMX,:TMN,:SKY,:PTY,:WFKOR,:WFEN,:POP,:REH,:WS,:WD,:WDKOR,:WDEN,"
				+ ":R12,:S12,:R06,:S06)";

		try {

			pstmt = con.prepareStatement(sql);

			for (int i = 0; i < result.size(); i++) {
				List<Map<String, String>> list = result.get(i);
				for (Map<String, String> map : list) {
					System.out.println(i + ". " + map);
					pstmt.setString(1, map.get("x"));
					pstmt.setString(2, map.get("y"));
					pstmt.setString(3, map.get("hour"));
					pstmt.setString(4, map.get("seq"));
					pstmt.setString(5, map.get("day"));
					pstmt.setString(6, map.get("temp"));
					pstmt.setString(7, map.get("tmx"));
					pstmt.setString(8, map.get("tmn"));
					pstmt.setString(9, map.get("sky"));
					pstmt.setString(10, map.get("pty"));
					pstmt.setString(11, map.get("wfKor"));
					pstmt.setString(12, map.get("wfEn"));
					pstmt.setString(13, map.get("pop"));
					pstmt.setString(14, map.get("reh"));
					pstmt.setString(15, map.get("ws"));
					pstmt.setString(16, map.get("wd"));
					pstmt.setString(17, map.get("wdKor"));
					pstmt.setString(18, map.get("wdEn"));
					pstmt.setString(19, map.get("r12"));
					pstmt.setString(20, map.get("s12"));
					pstmt.setString(21, map.get("r06"));
					pstmt.setString(22, map.get("s06"));
					try {
						pstmt.executeUpdate();
					} catch (SQLIntegrityConstraintViolationException e) {
						System.out.println("중복값 배제");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close(con, pstmt); // Connection 연결 해제.
		}
	}

	/**
	 * 날씨정보 업데이트 : TBL_WFORECAST_DATA
	 */
	public int updateData(List<List<Map<String, String>>> result) {
		int status = -1;
		DBUtil db = DBUtil.getInstance();
		Connection con = db.getConnection();

		PreparedStatement pstmt = null;

		String sql = "UPDATE WEATHER_INFO SET ";
		sql += "TEMP      = :TEMP";
		sql += ",TMX      = :TMX";
		sql += ",TMN      = :TMN";
		sql += ",SKY      = :SKY";
		sql += ",PTY      = :PTY";
		sql += ",WFKOR    = :WFKOR";
		sql += ",WFEN     = :WFEN";
		sql += ",POP      = :POP";
		sql += ",REH      = :REH";
		sql += ",WS       = :WS";
		sql += ",WD       = :WD";
		sql += ",WDKOR    = :WDKOR";
		sql += ",WDEN     = :WDEN";
		sql += ",R12      = :R12";
		sql += ",S12      = :S12";
		sql += ",R06      = :R06";
		sql += ",S06      = :S06";
		sql += " WHERE X  = :X and Y = :Y and HOUR = :HOUR";

		try {
			pstmt = con.prepareStatement(sql);
			for (int i = 0; i < result.size(); i++) {
				List<Map<String, String>> list = result.get(i);

				for (Map<String, String> map : list) {
					pstmt.setString(1, map.get("temp"));
					pstmt.setString(2, map.get("tmx"));
					pstmt.setString(3, map.get("tmn"));
					pstmt.setString(4, map.get("sky"));
					pstmt.setString(5, map.get("pty"));
					pstmt.setString(6, map.get("wfKor"));
					pstmt.setString(7, map.get("wfEn"));
					pstmt.setString(8, map.get("pop"));
					pstmt.setString(9, map.get("reh"));
					pstmt.setString(10, map.get("ws"));
					pstmt.setString(11, map.get("wd"));
					pstmt.setString(12, map.get("wdKor"));
					pstmt.setString(13, map.get("wdEn"));
					pstmt.setString(14, map.get("r12"));
					pstmt.setString(15, map.get("s12"));
					pstmt.setString(16, map.get("r06"));
					pstmt.setString(17, map.get("s06"));
					pstmt.setString(18, map.get("x"));
					pstmt.setString(19, map.get("y"));
					pstmt.setString(20, map.get("seq"));
					pstmt.setString(20, map.get("hour"));
					pstmt.executeUpdate(); // 실행.
				}
			}
			status = 1;
		} catch (Exception e) {
			System.out.println("업데이트중 오류 발생:" + e.getMessage());
			e.fillInStackTrace();
		} finally {
			db.close(con, pstmt); // Connection 연결 해제.
		}
		return status;
	}

	/**
	 * 기상청으로부터 해당 좌표의 날씨 정보를 가져오기. (XML)
	 */
	public List<Map<String, String>> getTownForecast(String x, String y) {

		List<Map<String, String>> result = new ArrayList<Map<String, String>>();

		try {
			SAXBuilder parser = new SAXBuilder();
			parser.setIgnoringElementContentWhitespace(true);

			String url = String.format(rssFeed, x, y);
			Document doc = parser.build(url);
			Element root = doc.getRootElement();
			Element channel = root.getChild("body");
			List<Element> list = channel.getChildren("data");

			for (int i = 0; i < list.size(); i++) {

				Element el = (Element) list.get(i);
				String seq = el.getAttributeValue("seq");
				if (seq.equals("8"))
					break; // 24시간 데이터만 추출

				Map<String, String> data = new LinkedHashMap<String, String>();
				data.put("x", x);
				data.put("y", y);
				data.put("seq", seq);
				// data요소의 자식요소들을 하나씩 꺼내서 저장 : (요소이름, 요소의 테스트노드)
				for (Element dataChild : el.getChildren()) {
					data.put(dataChild.getName(), dataChild.getTextTrim());
				}
				result.add(data);
			}
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * DB로부터 해당 좌표의 현재시간 날씨 정보를 가져오기.
	 */
	public Map<String, String> getTownForecastFromDB(String x, String y) {

		Map<String, String> data = new LinkedHashMap<String, String>();
		String sql = "SELECT * FROM WEATHER_INFO " + "WHERE X = :X AND Y = :Y  AND :HOUR >= HOUR-3 AND  :HOUR < HOUR";
		DBUtil db = DBUtil.getInstance();
		Connection con = db.getConnection();

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int nowHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY); // 현재 시간을 얻기
		System.out.println("현재 시간 : " + nowHour);
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, x);
			pstmt.setString(2, y);
			pstmt.setString(3, Integer.toString(nowHour));
			pstmt.setString(4, Integer.toString(nowHour));
			rs = pstmt.executeQuery();

			if (rs.next()) {
				data.put("x", rs.getString("x"));
				data.put("y", rs.getString("y"));
				data.put("hour", rs.getString("hour")); // 동네예보 3시간 단위
				data.put("seq", rs.getString("data_seq")); // 48시간중 몇번째 인지 가르킴
				data.put("day", rs.getString("day")); // 1번째날 (0: 오늘/1: 내일/2: 모레)
				data.put("temp", rs.getString("temp")); // 현재 시간온도
				data.put("tmx", rs.getString("tmx")); // 최고 온도
				data.put("tmn", rs.getString("tmn")); // 최저 온도
				data.put("sky", rs.getString("sky")); // 하늘 상태코드 (1: 맑음, 2: 구름조금, 3:구름많음, 4:흐림)
				data.put("pty", rs.getString("pty")); // 강수 상태코드 (0: 없음, 1: 비, 2: 비/눈, 3: 눈/비, 4: 눈)
				data.put("wfkor", rs.getString("wfKor")); // 날씨 한국어
				data.put("wfEn", rs.getString("wfEn")); // 날씨 영어
				data.put("pop", rs.getString("pop")); // 강수 확률%
				data.put("r12", rs.getString("r12")); // 12시간 예상 강수량
				data.put("s12", rs.getString("s12")); // 12시간 예상 적설량
				data.put("ws", rs.getString("ws")); // 풍속(m/s)
				data.put("wd", rs.getString("wd")); // 풍향 (0~7:북, 북동, 동, 남동, 남, 남서, 서, 북서)
				data.put("wdKor", rs.getString("wdKor")); // 풍향 한국어
				data.put("wdEn", rs.getString("wdEn")); // 풍향 영어
				data.put("reh", rs.getString("reh")); // 습도 %
				data.put("r06", rs.getString("r06")); // 6시간 예상 강수량
				data.put("s06", rs.getString("s06")); // 6시간 예상 적설량
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close(rs, con, pstmt);
		}
		return data;
	}
}