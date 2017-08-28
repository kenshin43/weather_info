package weather_store.function;

import java.util.List;
import java.util.Scanner;

import weather_store.dao.UserDAO;
import weather_store.dto.UserDTO;

public class UserListService implements Service {
	private UserDTO dto;

	public UserListService() {
		// TODO Auto-generated constructor stub
	}

	public UserListService(UserDTO dto) {
		this.dto = dto;
	}

	@Override
	public void execute(Scanner sc) {
		System.out.println("======================  로그인");

		List<UserDTO> list = UserDAO.getInstance().allUsers();
		list.forEach(x -> {
			StringBuilder sb = new StringBuilder();
			sb.append("아이디 :");
			sb.append(x.getId());
			sb.append("\t이름 : ");
			sb.append(x.getName());
			sb.append("\t주소 : ");
			sb.append(x.getAddr());
			System.out.println(sb.toString());
		});
		System.out.println("-----------------------------------------------------------------------------------");
	}
} // end of execute()
