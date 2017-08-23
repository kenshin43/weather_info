package weather_store.function;

import java.util.Scanner;

import weather_store.dao.UserDAO;

public class UserDeleteService implements Service{

	@Override
	public void Excute(Scanner sc) {
		// TODO Auto-generated method stub
		System.out.println("------------------------------");
		System.out.println("È¸¿øÅ»Åð");
		System.out.println("------------------------------");
		System.out.println("¾ÆÀÌµð ÀÔ·Â : ");
		String id = sc.nextLine();
		
		int check = UserDAO.getInstance().userDelete(id);
		
		if(check == 0) {
			System.out.println("È¸¿øÅ»Åð ½ÇÆÐ");
		} else {
			System.out.println("È¸¿øÅ»Åð ¼º°ø");
		}
		
	} // end of method
} // end of class
