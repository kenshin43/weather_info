package weather_store.function;

import java.util.Scanner;

import weather_store.dao.WeatherDAO;
/**
 * 날씨 정보를 최신화 하는 클래스 입니다.
 * 쓰래드를 이용하여 백그라운드로 실행됩니다.
 * @author 신승엽
 *
 */
public class WeatherInfoUpdate implements Service {

	@Override
	public void execute(Scanner sc) {
		System.out.println("======================  날씨 정보 업데이트");
		System.out.println("안내 :  날씨 정보가 백그라운드로 업데이트 됩니다. 작업 완료 시 안내해 드리겠습니다.");
		new Thread(() -> {
			WeatherDAO.getInstance().weatherSQLInsert();
		}).start();
	}

}
