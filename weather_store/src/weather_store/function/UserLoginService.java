package weather_store.function;

import java.util.Scanner;

import weather_store.dao.UserDAO;
import weather_store.dto.UserDTO;
import weather_store.util.SecurityUtil;

public class UserLoginService implements Service {
	private UserDTO dto;
	private String id;

	public UserLoginService() {
	}

	@Override
	public void execute(Scanner sc) {
		// TODO Auto-generated method stub
		while (true) {
			System.out.println("======================  로그인");
			System.out.print("아이디 \t:");
			id = sc.nextLine();
			System.out.print("비밀번호 \t:");
			String pw = sc.nextLine();
			String newpwd = SecurityUtil.encrypt(pw);
			System.out.println("-----------------------------------------------------------------------------------");
			try {
			if (UserDAO.userLogin(id, newpwd).equals(newpwd)) {
				System.out.println("안내 :  로그인에 실패하였습니다.");
			} else {
				break;
			}
			} catch(NullPointerException e) {
				System.out.println("안내 :  로그인에 실패하였습니다.");
			}
		}
	} // end of method

	public UserDTO getId() {
		dto = UserDAO.getInstance().userInfo(id);
		System.out.println("안내 :  로그인에 성공하였습니다. 환영합니다 " + dto.getName() + "님!");
		return dto;
	}

} // end of class
