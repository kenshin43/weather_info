package weather_store.function;

import java.util.List;
import java.util.Scanner;

import weather_store.dao.WeatherDAO;
import weather_store.dto.WeatherDTO;

public class WeatherServeService implements Service {

	@Override
	public void Excute(Scanner sc) {
		WeatherDAO dao = WeatherDAO.getInstance();

		System.out.println("==============³¯¾¾Á¤º¸================");
		List<Long> codeList = dao.faveriteLocal(sc.nextLine());
		codeList.forEach(t->{
			System.out.println(t);
			List<WeatherDTO> list = dao.weatherServe(t);
			list.forEach(System.out::println);
		});
	}// end of Excute;
	public static void main(String[] args) {
		WeatherServeService w = new WeatherServeService();
		w.Excute(new Scanner(System.in));
	}
}
