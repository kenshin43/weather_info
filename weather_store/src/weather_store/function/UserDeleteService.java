package weather_store.function;

import java.util.Scanner;

import weather_store.dao.UserDAO;

public class UserDeleteService implements Service {
	private String id="";

	public UserDeleteService() {
		// TODO Auto-generated constructor stub
	}

	public UserDeleteService(String id) {
		this.id = id;
	}

	@Override
	public void Excute(Scanner sc) {
		// TODO Auto-generated method stub
		System.out.println("==========È¸¿øÅ»Åð==========");
		System.out.print("¾ÆÀÌµð ÀÔ·Â : ");
		String id = sc.nextLine();
		System.out.println("----------------------------");

		int check = UserDAO.getInstance().userDelete(id);

		if (check == 0) {
			System.out.println("È¸¿øÅ»Åð ½ÇÆÐ");
		} else {
			System.out.println("È¸¿øÅ»Åð ¼º°ø");
		}

	} // end of method

	public static void main(String[] args) {
		UserDeleteService u = new UserDeleteService();
		u.Excute(new Scanner(System.in));
	}
} // end of class
