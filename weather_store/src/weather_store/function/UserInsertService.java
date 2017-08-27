package weather_store.function;

import java.util.Scanner;

import weather_store.dao.UserDAO;
import weather_store.dto.UserDTO;
import weather_store.util.SecurityUtil;

public class UserInsertService implements Service{

	@Override
	public void execute(Scanner sc) {
		
		System.out.println("======================  회원가입");
		System.out.print("아이디 \t:");
		String id = sc.nextLine();
		System.out.print("비밀번호 \t:");
		String pw = sc.nextLine();
		String newpwd=SecurityUtil.encrypt(pw);
		System.out.print("이름 \t:");
		String name = sc.nextLine();
		System.out.print("주소 \t:");
		String addr = sc.nextLine();
		System.out.println("-----------------------------------------------------------------------------------");
		
		int check = UserDAO.getInstance().userInsert(new UserDTO(id, newpwd, name, addr));
		
		if(check == 0) {
			System.out.println("안내 :  회원가입 실패");
		} else {
			System.out.println("안내 :  회원가입 성공");
		}
		
	} // end of method
	
} // end of class
