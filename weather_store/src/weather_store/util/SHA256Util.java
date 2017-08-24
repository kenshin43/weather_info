//package weather_store.util;
//
//import java.math.BigInteger;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.Random;
//
//public class SHA256Util {
//	public static String getEncrypt(String source, String salt) {
//		return getEncrypt(source, salt.getBytes());
//	}
//
//	public static String getEncrypt(String source, byte[] salt) {
//
//		String result = "";
//
//		byte[] a = source.getBytes();
//		byte[] bytes = new byte[a.length + salt.length];
//
//		System.arraycopy(a, 0, bytes, 0, a.length);
//		System.arraycopy(salt, 0, bytes, a.length, salt.length);
//
//		try {
//			MessageDigest md = MessageDigest.getInstance("SHA-256");
//			md.update(bytes);
//
//			byte[] byteData = md.digest();/*
//
//			BigInteger bi = new BigInteger(1, byteData);
//			String hex = bi.toString();
//
//			
//			System.out.println("encry : "+hex);
//			int paddingLength = (byteData.length * 2) - hex.length();
//			if (paddingLength > 0) {
//				return String.format("%0" + paddingLength + "d", 0) + hex;
//			} else {
//				return hex;
//			}
//
//			// StringBuffer sb = new StringBuffer();
//			// for (int i = 0; i < byteData.length; i++) {
//			// sb.append(Integer.toString((byteData[i] & 0xFF) + 256, 16).substring(1));
//			// }
//			//
//			// result = sb.toString();
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
//
//		return null;
//	}
//
//	public static byte[] fromHex(String hex) throws NoSuchAlgorithmException {
//		byte[] bytes = new byte[hex.length() / 2];
//		for (int i = 0; i < bytes.length; i++) {
//			bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
//		}
//		return bytes;
//	}
//
//	public static String generateSalt() {
//		Random random = new Random();
//
//		byte[] salt = new byte[8];
//		random.nextBytes(salt);
//
//		StringBuffer sb = new StringBuffer();
//		for (int i = 0; i < salt.length; i++) {
//			// byte 값을 Hex 값으로 바꾸기.
//			sb.append(String.format("%02x", salt[i]));
//		}
//
//		return sb.toString();
//	}
//	
//	public static boolean decodePwd(ResultSet rs, String pwd) throws NoSuchAlgorithmException, SQLException {
//		boolean result = false;
//		String dbpw = "";
//		byte[] dbInpw = null;
//		MessageDigest sha1Receiver = MessageDigest.getInstance("SHA-256");
//		if (rs.next()) {
//			String salt = rs.getString("salt");
//			byte [] bytes = new byte[salt.length()/2];
//			for (int i = 0; i < bytes.length; i++) {
//				bytes[i] = (byte)Integer.parseInt(salt.substring(2 * i, 2 * i + 2), 16);
//			}
//			sha1Receiver.update(bytes);
//			dbpw = rs.getString("pw");
//			dbInpw = new byte[dbpw.length()/2];
//			for (int i = 0; i < dbInpw.length; i++) {
//				dbInpw[i] = (byte)Integer.parseInt(dbpw.substring(2 * i, 2 * i + 2), 16);
//			}
//		}
//		byte[] sha1ReceiverText = sha1Receiver.digest(pwd.getBytes());
//		result = MessageDigest.isEqual(dbInpw, sha1ReceiverText);
//		return result;
//	}
//}
//*/