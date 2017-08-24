package weather_store.function;

import java.util.Scanner;

import weather_store.dao.UserDAO;
import weather_store.dto.UserDTO;

public class UserInsertService implements Service{

	@Override
	public void Excute(Scanner sc) {
		
		System.out.println("==========회원가입==========");
		System.out.print("아이디 : ");
		String id = sc.nextLine();
		System.out.print("비밀번호 : ");
		String pw = sc.nextLine();
		System.out.print("이름 : ");
		String name = sc.nextLine();
		System.out.print("주소 : ");
		String addr = sc.nextLine();
		System.out.println("----------------------------");
		
		int check = UserDAO.getInstance().userInsert(new UserDTO(id, pw, name, addr));
		
		if(check == 0) {
			System.out.println("회원가입 실패");
		} else {
			System.out.println("회원가입 성공");
		}
		
	} // end of method
	
	public static void main(String[] args) {
		UserInsertService u = new UserInsertService();
		u.Excute(new Scanner(System.in));
		
	} // end of main
} // end of class
