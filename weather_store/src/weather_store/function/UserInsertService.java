package weather_store.function;

import java.util.Scanner;

import weather_store.dao.UserDAO;
import weather_store.dto.UserDTO;

public class UserInsertService implements Service{

	@Override
	public void Excute(Scanner sc) {
		
		System.out.println("------------------------------------");
		System.out.println("회원가입");
		System.out.println("------------------------------------");
		
		System.out.println("아이디 :");
		String id = sc.nextLine();
		System.out.println("비밀번호 : ");
		String pw = sc.nextLine();
		System.out.println("이름 : ");
		String name = sc.nextLine();
		System.out.println("주소 : ");
		String addr = sc.nextLine();
		
		int check = UserDAO.getInstance().userInsert(new UserDTO(id, pw, name, addr));
		
		if(check == 0) {
			System.out.println("회원가입 실패");
		} else {
			System.out.println("회원가입 성공");
		}
		
	} // end of method
} // end of class
