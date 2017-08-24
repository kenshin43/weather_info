package weather_store.function;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import weather_store.dao.UserDAO;
import weather_store.dto.UserDTO;

public class UserListService implements Service{

	@Override
	public void Excute(Scanner sc) {
		System.out.println("--------------------");
		System.out.println("회원목록");
		System.out.println("--------------------");
		
		List<UserDTO> list = UserDAO.getInstance().allUsers();
		Iterator<UserDTO> it = list.iterator();
		
		while(it.hasNext()) {
			UserDTO dto = it.next();
			System.out.println(dto.toString());
		}
		
	} // end of execute()
	
	public static void main(String[] args) {
		UserListService u = new UserListService();
		u.Excute(new Scanner(System.in));
	}
} // end of class
