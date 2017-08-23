package weather_store.function;

import java.util.Scanner;

import weather_store.dao.UserDAO;
import weather_store.dto.UserDTO;

public class UserLoginService implements Service{

	@Override
	public void Excute(Scanner sc) {
		// TODO Auto-generated method stub
		System.out.println("---------------------");
		System.out.println("로그인");
		System.out.println("---------------------");
		System.out.println("아이디 : ");
		String id = sc.nextLine();
		System.out.println("비밀번호 : ");
		String pw = sc.nextLine();
		
		String uname = UserDAO.getInstance().userLogin(id, pw);
		
		System.out.println(uname + "님 로그인하였습니다");
		
	} // end of method
} // end of class
