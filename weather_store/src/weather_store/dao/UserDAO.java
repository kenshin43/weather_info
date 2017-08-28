package weather_store.dao;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import weather_store.dto.UserDTO;
import weather_store.util.DBUtil;
//import weather_store.util.SHA256Util;

/**
 * @author hyunmoYang
 */
public class UserDAO {
	private static UserDAO userDAO;

	private UserDAO() {
	}

	public static UserDAO getInstance() {
		if (userDAO == null) {
			userDAO = new UserDAO();
		}
		return userDAO;
	}

	/**
	 * 회원가입
	 * 
	 * @param user
	 * @return result
	 */
	public int userInsert(UserDTO user) {
		DBUtil db = DBUtil.getInstance();
		Connection con = db.getConnection();
		PreparedStatement ps = null;
		int result = 0;

		try {
			ps = con.prepareStatement(
					"INSERT INTO person (id, pw, name, addr, salt, passwd) VALUES (?, ?, ?, ?, ?, ?)");

			// String salt = SHA256Util.generateSalt();
			// String newPassword = SHA256Util.getEncrypt(user.getPw(), salt);

			ps.setString(1, user.getId());
			ps.setString(2, user.getPw());
			ps.setString(3, user.getName());
			ps.setString(4, user.getAddr());
			ps.setString(5, null);
			ps.setString(6, user.getPasswd());
			result = ps.executeUpdate();

			// System.out.println("salt : " + salt);
			// System.out.println("newPassword : " + newPassword);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e + "=>userInsert fail");
		} finally {
			db.close(con, ps);
		}
		return result;

	} // end of userInsert()

	public UserDTO userInfo(String id) {
		UserDTO dto = new UserDTO();
		DBUtil db = DBUtil.getInstance();
		Connection con = db.getConnection();
		ResultSet rs = null; // select시에 추가해야 할 부분
		PreparedStatement ps = null;

		try {
			ps = con.prepareStatement("SELECT id, name, addr,isadmin FROM person where id=?");
			ps.setString(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setAddr(rs.getString("addr"));
				dto.setIsAdmin(rs.getInt("isadmin"));
			}
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close(rs, con, ps);
		}
		return dto;
	}

	/**
	 * 회원 탈퇴
	 * 
	 * @param id
	 * @return result
	 */
	public int userDelete(String id) {
		DBUtil db = DBUtil.getInstance();
		Connection con = db.getConnection();
		PreparedStatement ps = null;
		int result = 0;

		try {
			ps = con.prepareStatement("DELETE person WHERE id = ?");
			ps.setString(1, id.trim());
			result = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e + "=> userDelete fail");
		} finally {
			db.close(con, ps);
		}
		return result;
	} // end of userDelete()

	/**
	 * 로그인
	 * 
	 * @param id
	 * @param pw
	 * @return uname
	 */

	public static String userLogin(String id, String inputpw) {
		DBUtil db = DBUtil.getInstance();
		Connection con = db.getConnection();
		ResultSet rs = null; // select시에 추가해야 할 부분
		PreparedStatement ps = null;
		String uname = "";

		try {
			ps = con.prepareStatement("SELECT name, pw, salt FROM person WHERE id = ?");

			ps.setString(1, id);
			rs = ps.executeQuery();

			if (rs.next()) {
				String dbPasswd = rs.getString("pw"); // DB에서 가져온 비밀번호
				boolean pass = MessageDigest.isEqual(inputpw.getBytes(), dbPasswd.getBytes()); // true,false
				if (pass == true) {
					uname = rs.getString("name");
				} else {
					System.out.println("아이디/비밀번호를 잘못 입력하셨습니다.");
					uname = null;
				}
			} else {
				System.out.println("id 없음");
			}
		} catch (SQLException se) {
			System.out.println(se + "=> userLogin fail");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close(rs, con, ps);
		}
		return uname;
	} // end of userLogin()

	/**
	 * 관리자 - 회원목록
	 * 
	 * @return list
	 */
	public List<UserDTO> allUsers() {
		List<UserDTO> list = new ArrayList<UserDTO>(); // list는 자료를 담는 주머니 개념
		DBUtil db = DBUtil.getInstance();
		Connection con = db.getConnection();
		ResultSet rs = null; // select시에 추가해야 할 부분
		PreparedStatement ps = null;

		try {
			ps = con.prepareStatement("SELECT id, name, addr, isadmin FROM person");
			rs = ps.executeQuery();
			while (rs.next()) { // while문 rs의 값을 커서로 담은 로우 하나씩 읽는 함수
								// (더이상 값이 없으면 false 반환)
				// HashMap<> hm = new HashMap(); // HashMap은 넣을 자료들을 한번에 묶는 고무줄 개념
				UserDTO user = new UserDTO();
				user.setId(rs.getString("id"));
				user.setName(rs.getString("name"));
				user.setAddr(rs.getString("addr"));
				list.add(user);
			}
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close(rs, con, ps);
		}
		return list;
	} // end of allUsers()'

	public int userUpdate(UserDTO dto) {
		int result = -1;
		DBUtil db = DBUtil.getInstance();
		Connection con = db.getConnection();
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("update person set name = ?,addr = ? where id = ?");
			ps.setString(1, dto.getName());
			ps.setString(2, dto.getAddr());
			ps.setString(3, dto.getId());
			result = ps.executeUpdate();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close(con, ps);
		}
		return result;
	}

} // end of class
