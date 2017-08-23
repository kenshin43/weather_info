package weather_store.town;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import weather_store.util.DBUtil;

public class WeatherRSS {

	// http://www.kma.go.kr/wid/queryDFS.jsp?gridx=59&gridy=125
	private String rssFeed = "http://www.kma.go.kr/wid/queryDFS.jsp?gridx=%s&gridy=%s"; // 주소
	private boolean bFileWrite = false; // 파일로 출력할지 결정하는 변수.
	private PrintStream out;

	public WeatherRSS() {
	}

	public WeatherRSS(boolean isFileWrite) {
		try {
			if (isFileWrite) {
				this.bFileWrite = true;
				String filepath = "file//TBL_WFORECAST_XY_GROUP_DATA.txt";
				out = new PrintStream(new FileOutputStream(filepath));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void closeOutputStream() { // 출력 스트림 닫기
		if (out != null) {
			out.close();
		}
	}

	/**
	 * 테스트를 위한 메인메소드
	 * 
	 * @throws SQLException
	 */
	public static void main(String[] args) throws Exception {

		WeatherRSS r = new WeatherRSS();

		r.getTownForecast("59", "125"); // 기상청으로부터 해당 동네(X,Y좌표)에 날씨 정보 가져오기

		// 기상청으로부터 모든 동네 날씨 정보가져오기
		List<List<Map<String, String>>> result = r.getAllTownForecast();

		r.insertData(result); // 기상청으로부터 가져온 정보를 DB에 저장. (최초 실행시 한번!?)
		r.updateData(result); // 기상청으로부터 가져온 정보를 DB에 업데이트
		Map<String, String> d = r.getTownForecastFromDB("59", "125"); // DB에서 해당 동네(X,Y 좌표)에 날씨 정보 가져오기.
		System.out.println(d);

		System.out.println("작업 완료");

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
		// String sql = "select * from TBL_WFORECAST_XY_GROUP ";
		String sql = "select * from TBL_WFORECAST_XY_GROUP where rownum<10";

		// DB에 날씨정보를 한번 입력한 후에는 대체 다음 쿼리 대체가능
		// select x, y from TBL_WFORECAST_DATA group by x, y ;

		List<List<Map<String, String>>> result = new ArrayList<>();

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				String x = rs.getString("x");
				String y = rs.getString("y");
				// xy좌표에 해당하는 날씨 예보 리스트 가져오기
				List<Map<String, String>> point = getTownForecast(x, y);
				result.add(point);
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

			db.close(rs, conn, stmt); // Connection 연결 해제.
			closeOutputStream(); // 파일 출력 닫기.
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

		String sql = "INSERT INTO TBL_WFORECAST_DATA (X,Y,HOUR,DATA_SEQ,DAY,TEMP,TMX,TMN,"
				+ "SKY,PTY,WFKOR,WFEN,POP,REH,WS,WD,WDKOR,WDEN,R12,S12,R06,S06)	VALUES "
				+ "(:x,:y,:HOUR, :DATA_SEQ, :day, :TEMP,:TMX,:TMN,:SKY,:PTY,:WFKOR,:WFEN,:POP,:REH,:WS,:WD,:WDKOR,:WDEN,"
				+ ":R12,:S12,:R06,:S06)";

		try {

			pstmt = con.prepareStatement(sql);

			// {x=144, y=123, seq=7, hour=24, day=1, temp=22.0, tmx=24.0, tmn=20.0, sky=4,
			// pty=0,
			// wfKor=흐림, wfEn=Cloudy, pop=30, r12=0.0, s12=0.0, ws=3.0, wd=1, wdKor=북동,
			// wdEn=NE,
			// reh=95, r06=-0.0, s06=0.0}

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

					pstmt.executeUpdate(); // 실행.
				}
			}

		} catch (Exception e) {

		} finally {
			db.close(con, pstmt); // Connection 연결 해제.
		}
	}

	/**
	 * 날씨정보 업데이트 : TBL_WFORECAST_DATA
	 */
	public void updateData(List<List<Map<String, String>>> result) {
		DBUtil db = DBUtil.getInstance();
		Connection con = db.getConnection();

		PreparedStatement pstmt = null;

		String sql = "UPDATE TBL_WFORECAST_DATA SET ";
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
		// sql += " WHERE X = :X and Y = :Y and DATA_SEQ = :SEQ";

		try {
			pstmt = con.prepareStatement(sql);

			// {x=144, y=123, seq=7, hour=24, day=1, temp=22.0, tmx=24.0, tmn=20.0, sky=4,
			// pty=0,
			// wfKor=흐림, wfEn=Cloudy, pop=30, r12=0.0, s12=0.0, ws=3.0, wd=1, wdKor=북동,
			// wdEn=NE,
			// reh=95, r06=-0.0, s06=0.0}

			for (int i = 0; i < result.size(); i++) {

				List<Map<String, String>> list = result.get(i);

				for (Map<String, String> map : list) {
					System.out.println(i + ". " + map);
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
					// pstmt.setString(20, map.get("seq"));
					pstmt.setString(20, map.get("hour"));

					pstmt.executeUpdate(); // 실행.
				}
			}

		} catch (Exception e) {
			System.out.println("업데이트중 오류 발생:" + e.getMessage());
			e.fillInStackTrace();
		} finally {
			db.close(con, pstmt); // Connection 연결 해제.
		}
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
					// <hour>3</hour>
					data.put("hour", "3");
					data.put(dataChild.getName(), dataChild.getTextTrim());
				}
				System.out.println(data); // 콘솔에 출력.
				if (bFileWrite)
					out.println(data);// 파일에 출력

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
		String sql = "SELECT * FROM TBL_WFORECAST_DATA "
				+ "WHERE X = :X AND Y = :Y  AND :HOUR >= HOUR-3 AND  :HOUR < HOUR";

		Connection conn = DBUtil.getConn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int nowHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY); // 현재 시간을 얻기
		System.out.println("현재 시간 : " + nowHour);
		try {
			pstmt = conn.prepareStatement(sql);
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
				// data.put("wdKor",rs.getString("wdKor") ); //풍향 한국어
				// data.put("wdEn",rs.getString("wdEn") ); //풍향 영어
				data.put("reh", rs.getString("reh")); // 습도 %
				data.put("r06", rs.getString("r06")); // 6시간 예상 강수량
				data.put("s06", rs.getString("s06")); // 6시간 예상 적설량

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			DBUtil.close(); // Connection 연결 해제.
		}

		return data;
	}

}