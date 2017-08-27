package weather_store.function;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import weather_store.dao.WeatherDAO;
import weather_store.dto.WeatherDTO;

public class WeatherServeService implements Service {
	private String id;

	public WeatherServeService(String id) {
		this.id = id;
	}

	@Override
	public void execute(Scanner sc) {
		WeatherDAO dao = WeatherDAO.getInstance();

		Map<Long, String> codeList = dao.faveriteLocal(id);
		codeList.entrySet().forEach(t -> {
			List<WeatherDTO> list = dao.weatherServe(t.getKey());
			System.out.println("======================  기상정보 : [[" + t.getValue() + "]]");
			list.forEach(x -> {
				StringBuilder sb = new StringBuilder();
				if (x.getDay() == 0) {
					sb.append("오늘 : ");
				} else if (x.getDay() == 1) {
					sb.append("내일 : ");
				} else if (x.getDay() == 2) {
					sb.append("모래 : ");
				}
				sb.append(x.getHour());
				sb.append("\t시\t 기온 : ");
				sb.append(x.getTemp());
				sb.append("℃\t강수확율 : ");
				sb.append(x.getPop());
				sb.append("%  <");
				sb.append(x.getWfKOR());
				sb.append(">\t풍량 : ");
				sb.append(x.getWdKOR());
				sb.append("|");
				sb.append(x.getWs());
				sb.append("m/s\t강수량 : ");
				sb.append(x.getR06());
				sb.append("mm");
				System.out.println(sb.toString());
			});
			System.out.println("-----------------------------------------------------------------------------------");
		});
	}// end of execute;
}
