package weather_store.function;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import weather_store.dao.WeatherDAO;
import weather_store.dto.UserDTO;
import weather_store.dto.WeatherDTO;

/**
 * ����ڰ� ������ ��ȣ������ ���� ������ ����ϴ� Ŭ���� �Դϴ�.
 * 
 * @author �Ž¿�
 *
 */
public class WeatherServeService implements Service {
	private UserDTO dto;

	public WeatherServeService(UserDTO dto) {
		this.dto = dto;
	}

	@Override
	public void execute(Scanner sc) {
		WeatherDAO dao = WeatherDAO.getInstance();

		Map<Long, String> codeList = dao.faveriteLocal(dto.getId());
		codeList.entrySet().forEach(t -> {
			List<WeatherDTO> list = dao.weatherServe(t.getKey());
			System.out.println("======================  ������� : [[" + t.getValue() + "]]");
			list.forEach(x -> {
				StringBuilder sb = new StringBuilder();
				if (x.getDay() == 0) {
					sb.append("���� : ");
				} else if (x.getDay() == 1) {
					sb.append("���� : ");
				} else if (x.getDay() == 2) {
					sb.append("�� : ");
				}
				sb.append(x.getHour());
				sb.append("\t��\t ��� : ");
				sb.append(x.getTemp());
				sb.append("��\t����Ȯ�� : ");
				sb.append(x.getPop());
				sb.append("%  <");
				sb.append(x.getWfKOR());
				sb.append(">\tǳ�� : ");
				sb.append(x.getWdKOR());
				sb.append("|");
				sb.append(x.getWs());
				sb.append("m/s\t������ : ");
				sb.append(x.getR06());
				sb.append("mm");
				System.out.println(sb.toString());
			});
			System.out.println("-----------------------------------------------------------------------------------");
		});
	}// end of execute;
}
