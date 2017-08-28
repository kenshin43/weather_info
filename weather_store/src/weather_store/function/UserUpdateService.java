package weather_store.function;

import java.util.Scanner;

import weather_store.dao.UserDAO;
import weather_store.dto.UserDTO;

public class UserUpdateService implements Service {
	private UserDTO dto;
	
	public UserUpdateService(UserDTO dto){
		this.dto = dto;
	}
	

	@Override
	public void execute(Scanner sc) {
		UserDAO dao = UserDAO.getInstance();
		System.out.println("======================  회원정보수정");
		System.out.println("현재 주소\t: "+dto.getAddr());
		System.out.print("변경\t:");
		dto.setAddr(sc.nextLine());
		System.out.println("현재 이름\t: "+dto.getName());
		System.out.print("변경\t:");
		dto.setName(sc.nextLine());
		int result = dao.userUpdate(dto);
		if(result!=-1&&result!=0) {
			System.out.println("안내 :  회원 정보 수정에 성공하였습니다.");
		} else {
			System.out.println("안내 :  회원 정보 수정에 실패하였습니다.");
		}
	}

}
