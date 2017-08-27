package weather_store.function;

import java.util.Scanner;

import weather_store.dao.UserDAO;

public class UserDeleteService implements Service {
	private String id="";

	public UserDeleteService(String id) {
		this.id = id;
	}

	@Override
	public void execute(Scanner sc) {
		System.out.println("======================  È¸¿øÅ»Åð");
		System.out.println("-----------------------------------------------------------------------------------");

		int check = UserDAO.getInstance().userDelete(id);

		if (check == 0) {
			System.out.println("¾È³» :  È¸¿øÅ»Åð ½ÇÆÐ");
		} else {
			System.out.println("¾È³» :  È¸¿øÅ»Åð ¼º°ø");
		}
	} // end of method
} // end of class
