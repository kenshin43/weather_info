package weather_store.start;

import java.util.Scanner;

import weather_store.function.*;

public class WeatherTest {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String id = null;
		System.out.println("날씨 정보 시스템에 오신것을 환영합니다!");
		login: while (true) {
			System.out.println("선택 : ------------------------------------------------------------------------------");
			System.out.println("1. 로그인\t\t2. 회원가입\t0. 프로그램 종료");
			System.out.println("-----------------------------------------------------------------------------------");
			switch (sc.nextLine()) {
			case "1":
				// 로그인
				UserLoginService login = new UserLoginService();
				login.execute(sc);
				id = login.getId();
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
		while (true) {
			System.out.println("선택 : ------------------------------------------------------------------------------");
			System.out.println("1. 날씨 확인\t2. 선호 지역\t3. 상품 목록\t4. 회원정보변경\t0. 프로그램 종료");
			System.out.println("-----------------------------------------------------------------------------------");
			switch (sc.nextLine()) {
			case "1":
				new WeatherServeService(id).execute(sc);
				break;
			case "2":
				new WeatherCoordinateService(id).execute(sc);
				break;
			case "3":
				new ProductADService(id).execute(sc);
				break;
			case "4":
				new UserDeleteService(id).execute(sc);
				break;
			case "0":
				System.out.println("서비스를 종료합니다.");
				System.exit(0);
				break;
			default:
				break;
			}
		}

	}// end of main
}// end of class
