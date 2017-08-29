package weather_store.start;

import java.util.Scanner;

import weather_store.dto.UserDTO;
import weather_store.function.*;

public class WeatherTest {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		UserDTO dto = new UserDTO();
		System.out.println("날씨 정보 시스템에 오신것을 환영합니다!");
		login: while (true) {
			System.out.println("선택 : ----------------------------------------------------------------------------");
			System.out.println("1. 로그인\t\t2. 회원가입\t0. 프로그램 종료");
			System.out.println("-----------------------------------------------------------------------------------");
			switch (sc.nextLine()) {
			case "1":
				// 로그인
				UserLoginService login = new UserLoginService();
				login.execute(sc);
				dto = login.getId();
				break login;
			case "2":
				// 회원가입
				new UserInsertService().execute(sc);
				break;
			case "0":
				System.exit(0);
				break;
			default:
				System.out.println("항목에 맞게 입력해 주세요!");
			}

		}

		if (dto.getIsAdmin() != 1) {
			while (true) {
				System.out
						.println("선택 : ----------------------------------------------------------------------------");
				System.out.println("1. 날씨 확인\t2. 선호 지역\t3. 상품 목록\t4. 회원정보변경\t0. 프로그램 종료");
				System.out
						.println("-----------------------------------------------------------------------------------");
				switch (sc.nextLine()) {
				case "1":
					new WeatherServeService(dto).execute(sc);
					break;
				case "2":
					new WeatherCoordinateService(dto).execute(sc);
					break;
				case "3":
					new ProductADService(dto).execute(sc);
					break;
				case "4":
					new UserUpdateService(dto).execute(sc);
					break;
				case "5":
					new UserDeleteService(dto).execute(sc);
					break;
				case "0":
					System.out.println("서비스를 종료합니다.");
					System.exit(0);
					break;
				default:
					System.out.println("항목에 맞게 입력해 주세요!");
					break;
				}
			}
		} else {
			System.out.println("관리자 모드로 입장 ]]]]");
			while (true) {
				System.out
						.println("선택 : ------------------------------------------------------------------------------");
				System.out.println("1. 사용자 목록\t2. 날씨 정보 업데이트\t3. 매출액\t0. 프로그램 종료");
				System.out
						.println("-----------------------------------------------------------------------------------");
				switch (sc.nextLine()) {
				case "1":
					new UserListService().execute(sc);
					break;
				case "2":
					new WeatherInfoUpdate().execute(sc);
					break;
				case "3":
					new ProductSaleTotal().execute(sc);
					break;
				case "0":
					System.out.println("관리서비스를 종료합니다.");
					System.exit(0);
					break;
				default:
					System.out.println("항목에 맞게 입력해 주세요!");
					break;
				}
			}
		}

	}// end of main
}// end of class
