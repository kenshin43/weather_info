package weather_store.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import weather_store.dto.ProductDTO;
import weather_store.util.DBUtil;
/**
 * 제품의 정보를 SQL에서 받아와 처리하는 메소드의 모음입니다.
 * @author 신승엽
 *
 */
public class StoreDAO {
	private static StoreDAO dao;

	private StoreDAO() {
	}

	public static StoreDAO getInstance() {
		if (dao == null) {
			dao = new StoreDAO();
		}
		return dao;
	}

	public List<ProductDTO> productList(int cateCode) {
		List<ProductDTO> list = new ArrayList<ProductDTO>();
		DBUtil db = DBUtil.getInstance();
		Connection con = db.getConnection();
		String sql = "select pro_code,pro_name,price,comments,cate_code from product where cate_code = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cateCode);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ProductDTO dto = new ProductDTO();
				dto.setProCode(rs.getInt("pro_code"));
				dto.setProName(rs.getString("pro_name"));
				dto.setPrice(rs.getInt("price"));
				dto.setComment(rs.getString("comments"));
				dto.setCateCode(rs.getInt("cate_code"));
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		db.close(rs, con, pstmt);
		return list;
	}

	public Map<Integer, String> cateList() {
		Map<Integer, String> category = new HashMap<Integer, String>();
		DBUtil db = DBUtil.getInstance();
		Connection con = db.getConnection();
		String sql = "select cate_code,cate_name from category";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				category.put(rs.getInt("cate_code"), rs.getString("cate_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		db.close(rs, con, pstmt);
		return category;
	}

	public int saleProduct(String productName) {
		int price = -1;
		int proCode = -1;
		DBUtil db = DBUtil.getInstance();
		Connection con = db.getConnection();
		String sql = "select price,pro_code from product where pro_name = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, productName);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				proCode = rs.getInt("pro_code");
				price = rs.getInt("price");
			}
			pstmt.execute();
			db.close(con, pstmt);
			con = db.getConnection();
			sql = "insert into sell values (?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, new Date().getTime());
			pstmt.setInt(2, proCode);
			pstmt.execute();
			db.close(con, pstmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return price;
	}

	public int saleTotal() {
		int price = -1;
		DBUtil db = DBUtil.getInstance();
		Connection con = db.getConnection();
		String sql = "select sum(price) from sell s join product p on p.pro_code=s.pro_code ";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				price = rs.getInt(1);
			}
			pstmt.execute();
			db.close(con, pstmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return price;
	}
}// end of class
