package weather_store.town;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
/**
 * ���� �ڵ带 ������ ���� Ŭ�����Դϴ�.
 * @author �Ž¿�
 *
 */
public class TownCodeService {

	// �ֻ� ��� URL
	private final String topURL = "http://www.kma.go.kr/DFSROOT/POINT/DATA/top.json.txt";
	// �߰� ��� URL
	private final String mdlURL = "http://www.kma.go.kr/DFSROOT/POINT/DATA/mdl.%s.json.txt";
	// ���� ��� URL
	private final String leafURL = "http://www.kma.go.kr/DFSROOT/POINT/DATA/leaf.%s.json.txt";

	// KEY����
	private final String KEY_CODE = "code";
	private final String KEY_VALUE = "value";
	private final String KEY_X = "x";
	private final String KEY_Y = "y";

	// ������ ��带 ������ ����Ʈ
	private List<CoordinateDTO> topList = new ArrayList<CoordinateDTO>();
	private List<CoordinateDTO> mdlList = new ArrayList<CoordinateDTO>();
	private List<CoordinateDTO> leafList = new ArrayList<CoordinateDTO>();

	// ������
	public TownCodeService() {
		excute();
	}

	public void excute() {

		JSONArray jsonArray = null;

		// �ְ� ���
		jsonArray = getJSON(topURL);
		parseJSON(topList, jsonArray, null, null, false);

		System.out.println("=========");

		// �߰� ���
		for (CoordinateDTO dto : topList) {
			jsonArray = getJSON(String.format(mdlURL, dto.getCode()));
			parseJSON(mdlList, jsonArray, dto.getCode(), dto.getName(), false);
		}

		System.out.println("=========");

		// ���� ���
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
				pstmt.setInt(5, Integer.parseInt(dto.getGridX() == null ? "0" : dto.getGridX()));
				pstmt.setInt(6, Integer.parseInt(dto.getGridY() == null ? "0" : dto.getGridY()));
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
		System.out.println("���:" + topList.size() + "�� /" + mdlList.size() + "�� /" + leafList.size() + "��");
		return result;
	}

	/**
	 * ������ URL�� ���ʷ� JSON�������� ���
	 */
	private JSONArray getJSON(String urlStr) {
		URL url = null;
		HttpURLConnection conn = null;
		StringBuffer jsonHtml = new StringBuffer();

		try {
			url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();

			if (conn != null) {
				conn.setConnectTimeout(10000); // ���� ���� ��� �ð� , 10��
				conn.setUseCaches(false); // ĳ����� ������.

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
	 * �Ľ̵� ��� ����Ʈ -> TownDTO �������� ��ȯ
	 * 
	 * @param list
	 *            ��ȯ����� ���� ����Ʈ
	 * @param array
	 *            ���� ��� ����Ʈ
	 * @param parentCode
	 *            �θ� ��� �ڵ�
	 * @param parentName
	 *            �θ� ��� �̸�
	 * @param isLast
	 *            ���ϳ���ΰ�?
	 */
	private List<CoordinateDTO> parseJSON(List<CoordinateDTO> list, JSONArray array, String parentCode,
			String parentName, boolean isLast) {

		JSONObject data = null;
		CoordinateDTO town = null;
		for (int i = 0; i < array.size(); i++) {
			data = (JSONObject) array.get(i);

			if (!isLast) {
				// �ֻ�, �߰� ���
				town = new CoordinateDTO(data.get(KEY_CODE).toString(), data.get(KEY_VALUE).toString(), parentCode,
						parentName);

			} else {
				// ���� ���
				town = new CoordinateDTO(data.get(KEY_CODE).toString(), data.get(KEY_VALUE).toString(), parentCode,
						parentName, data.get(KEY_X).toString(), data.get(KEY_Y).toString());

			}
			System.out.println(town);
			list.add(town);
		}
		return list;
	}

}
