package weather_store.function;

import java.util.Scanner;

import weather_store.dao.UserDAO;

public class UserLoginService implements Service {
	private String id = "";

	public UserLoginService() {
		// TODO Auto-generated constructor stub
	}

	public UserLoginService(String id) {
		this.id = id;
	}

	@Override
	public void Excute(Scanner sc) {
		// TODO Auto-generated method stub
		System.out.println("==========로그인==========");
		System.out.print("아이디 : ");
		String id = sc.nextLine();
		System.out.print("비밀번호 : ");
		String pw = sc.nextLine();
		System.out.println("--------------------------");
		String uname = UserDAO.getInstance().userLogin(id, pw);

		if (uname == null) {
			System.out.println("로그인 안됨");
		} else {
			System.out.println(uname + "님 로그인하였습니다");
		}
		//17852234146409559328391519111482959478466616965071007697097780981345734625575
	} // end of method

	public static void main(String[] args) {
		UserLoginService u = new UserLoginService();
		u.Excute(new Scanner(System.in));
	}
} // end of class
