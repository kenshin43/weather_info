package weather_store.function;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import weather_store.dao.UserDAO;
import weather_store.dto.UserDTO;

public class UserListService implements Service{

	@Override
	public void Excute(Scanner sc) {
		System.out.println("--------------------");
		System.out.println("회원목록");
		System.out.println("--------------------");
		
		ArrayList<UserDTO> list = UserDAO.getInstance().allUsers();
		Iterator<UserDTO> it = list.iterator();
		
		while(it.hasNext()) {
			UserDTO dto = it.next();
			System.out.println(dto.toString());
		}
		
	} // end of execute()
} // end of class
