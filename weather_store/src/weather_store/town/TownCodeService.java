package weather_store.town;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import weather_store.dto.CoordinateDTO;
import weather_store.util.DBUtil;

public class TownCodeService {

	// 최상 노드 URL
	private final String topURL = "http://www.kma.go.kr/DFSROOT/POINT/DATA/top.json.txt";
	// 중간 노드 URL
	private final String mdlURL = "http://www.kma.go.kr/DFSROOT/POINT/DATA/mdl.%s.json.txt";
	// 최하 노드 URL
	private final String leafURL = "http://www.kma.go.kr/DFSROOT/POINT/DATA/leaf.%s.json.txt";

	// KEY정보
	private final String KEY_CODE = "code";
	private final String KEY_VALUE = "value";
	private final String KEY_X = "x";
	private final String KEY_Y = "y";

	// 각각의 노드를 저장할 리스트
	private List<CoordinateDTO> topList = new ArrayList<CoordinateDTO>();
	private List<CoordinateDTO> mdlList = new ArrayList<CoordinateDTO>();
	private List<CoordinateDTO> leafList = new ArrayList<CoordinateDTO>();

	// 생성자
	public TownCodeService() {
		excute();
	}

	public void excute() {

		JSONArray jsonArray = null;

		// 최고 노드
		jsonArray = getJSON(topURL);
		parseJSON(topList, jsonArray, null, null, false);

		System.out.println("=========");

		// 중간 노드
		for (CoordinateDTO dto : topList) {
			jsonArray = getJSON(String.format(mdlURL, dto.getCode()));
			parseJSON(mdlList, jsonArray, dto.getCode(), dto.getName(), false);
		}

		System.out.println("=========");

		// 최하 노드
		for (CoordinateDTO dto : mdlList) {
			jsonArray = getJSON(String.format(leafURL, dto.getCode()));
			parseJSON(leafList, jsonArray, dto.getCode(), dto.getName(), true);
		}
	}

	public void setSQL(List<CoordinateDTO> list, PreparedStatement pstmt) {
		for (CoordinateDTO dto : list) {
			try {
				pstmt.setLong(1, Long.parseLong(dto.getCode()));
				pstmt.setString(2, dto.getName());
				pstmt.setInt(3, Integer.parseInt(dto.getParentCode() == null ? "0" : dto.getParentCode()));
				pstmt.setString(4, dto.getParentName());
				pstmt.setInt(5, Integer.parseInt(dto.getGridX()==null?"0":dto.getGridX()));
				pstmt.setInt(6, Integer.parseInt(dto.getGridY()==null?"0":dto.getGridY()));
				pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
	}

	public int outputSQL() {
		int result = -1;
		String sql = "insert into COORDINATES values(?,?,?,?,?,?)";
		DBUtil db = DBUtil.getInstance();
		Connection con = db.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sql);
			setSQL(topList, pstmt);
			setSQL(mdlList, pstmt);
			setSQL(leafList, pstmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		db.close(con, pstmt);
		System.out.println("결과:" + topList.size() + "개 /" + mdlList.size() + "개 /" + leafList.size() + "개");
		return result;
	}

	/**
	 * 지정된 URL을 기초로 JSON형식으로 취득
	 */
	private JSONArray getJSON(String urlStr) {
		URL url = null;
		HttpURLConnection conn = null;
		StringBuffer jsonHtml = new StringBuffer();

		try {
			url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();

			if (conn != null) {
				conn.setConnectTimeout(10000); // 접속 응답 대기 시간 , 10초
				conn.setUseCaches(false); // 캐쉬기능 사용안함.

				if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
					BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

					String line = null;
					while ((line = br.readLine()) != null) {
						jsonHtml.append(line + "\n");
					}
					br.close();
				}
				conn.disconnect();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("1." + jsonHtml.toString());
		JSONArray jsonObj = (JSONArray) JSONValue.parse(jsonHtml.toString());

		return jsonObj;
	}

	/**
	 * 파싱된 노드 리스트 -> TownDTO 형식으로 변환
	 * 
	 * @param list
	 *            변환결과를 담을 리스트
	 * @param array
	 *            현재 노드 리스트
	 * @param parentCode
	 *            부모 노드 코드
	 * @param parentName
	 *            부모 노드 이름
	 * @param isLast
	 *            최하노드인가?
	 */
	private List<CoordinateDTO> parseJSON(List<CoordinateDTO> list, JSONArray array, String parentCode,
			String parentName, boolean isLast) {

		JSONObject data = null;
		CoordinateDTO town = null;
		for (int i = 0; i < array.size(); i++) {
			data = (JSONObject) array.get(i);

			if (!isLast) {
				// 최상, 중간 노드
				town = new CoordinateDTO(data.get(KEY_CODE).toString(), data.get(KEY_VALUE).toString(), parentCode,
						parentName);

			} else {
				// 최하 노드
				town = new CoordinateDTO(data.get(KEY_CODE).toString(), data.get(KEY_VALUE).toString(), parentCode,
						parentName, data.get(KEY_X).toString(), data.get(KEY_Y).toString());

			}
			System.out.println(town);
			list.add(town);
		}
		return list;
	}

}
