package weather_store.function;

import java.util.List;
import java.util.Scanner;

import weather_store.dao.UserDAO;
import weather_store.dto.UserDTO;

public class UserListService implements Service {
	private String id = "";

	public UserListService() {
		// TODO Auto-generated constructor stub
	}

	public UserListService(String id) {
		this.id = id;
	}

	@Override
	public void execute(Scanner sc) {
		System.out.println("==================회원목록==================");

		List<UserDTO> list = UserDAO.getInstance().allUsers();
		list.forEach(x -> {
			StringBuilder sb = new StringBuilder();
			sb.append("ID :");
			sb.append(x.getId());
			sb.append("\t이름 : ");
			sb.append(x.getName());
			sb.append("\t주소");
			sb.append(x.getAddr());
			System.out.println(sb.toString());
		});
		System.out.println("--------------------------------------------");

	} // end of execute()

	public static void main(String[] args) {
		UserListService u = new UserListService();
		u.execute(new Scanner(System.in));
	}
} // end of class
