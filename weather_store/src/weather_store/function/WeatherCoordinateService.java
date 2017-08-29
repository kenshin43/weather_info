package weather_store.function;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import weather_store.dao.WeatherDAO;
import weather_store.dto.CoordinateDTO;
import weather_store.dto.UserDTO;
/**
 * ��ȣ������ �����ϴ� Ŭ���� �Դϴ�.
 * ��ȣ���� ��� �� ��ȣ���� ����, ��ȣ���� ���� ����� �����մϴ�.
 * @author �Ž¿�
 *
 */
public class WeatherCoordinateService implements Service {
	private UserDTO dto;

	public WeatherCoordinateService(UserDTO dto) {
		this.dto = dto;
	}

	@Override
	public void execute(Scanner sc) {
		int select = 0;
		int[] i = new int[1];
		i[0] = 1;
		WeatherDAO dao = WeatherDAO.getInstance();
		Map<Long, String> faveriteMap = dao.faveriteLocal(dto.getId());
		List<Long> faveriteList = new ArrayList<Long>();
		System.out.println("======================  ��ȣ���� : " + dto.getName() + "�� ");
		faveriteMap.entrySet().forEach(t -> {
			System.out.println((i[0]++) + " : " + t.getValue());
			faveriteList.add(t.getKey());
		});
		while (true) {
			System.out.println("���� : ----------------------------------------------------------------------------");
			System.out.println("1. ��ȣ���� ���\t2. ��ȣ���� ����\t3. ��ȣ���� ����\t4. ����");
			System.out.println("-----------------------------------------------------------------------------------");
			switch (sc.nextLine()) {
			case "1":
				i[0] = 1;
				if (faveriteMap.size() > 4) {
					System.out.println("�ȳ� : ��ȣ���� ��ϰ����� �ʰ��Ͽ����ϴ�.");
				} else {
					System.out.println(
							"��ȣ ���� ��� : ---------------------------------------------------------------------");
					System.out.println("�����ϴ� ������ �Է��ϼ���.");
					List<CoordinateDTO> list = dao.coordinateSearch(sc.nextLine());
					if (list.size() == 0) {
						System.out.println("�ȳ� : �ش��ϴ� ������ �������� �ʽ��ϴ�.");
					} else {
						list.forEach(t -> {
							System.out.println((i[0]++) + " : " + t.getParentName() + " " + t.getName());
						});
						System.out.println("�ȳ� : ���ϴ� ������ ������ �ּ���.");
						select = intScan(i[0], sc);
						long localCode = Long.parseLong(list.get(select - 1).getCode());
						int result = dao.coordinateAdd(dto.getId(), localCode);
						if (result == 0) {
							System.out.println("�ȳ� :  ��Ͽ� �����Ͽ����ϴ�.");
						} else {
							System.out.println("�ȳ� :  ��Ͽ� �����Ͽ����ϴ�.");
						}
						return;
					}
				}
				break;
			case "2": {
				System.out
						.println("��ȣ ���� ���� : ---------------------------------------------------------------------");

				System.out.println("���� ��Ͽ��� ������� �ϴ� ������ ��ȣ�� �Է��ϼ���.");
				select = intScan(faveriteList.size(), sc);
				long localCode = faveriteList.get(select - 1);
				int result = dao.coordinateDelete(dto.getId(), localCode);
				if (result == 0) {
					System.out.println("�ȳ� :  ������ �����Ͽ����ϴ�.");
				} else {
					System.out.println("�ȳ� :  ������ �����Ͽ����ϴ�.");
				}
				return;
			}
			case "3":
				int result = 0;
				System.out
						.println("��ȣ ���� ���� : ---------------------------------------------------------------------");
				System.out.println("���� ��Ͽ��� �����ϰ��� �ϴ� ������ ��ȣ�� �Է��ϼ���.");
				select = intScan(faveriteList.size(), sc);
				System.out.println("�ȳ� :  ���� ������ ���ϴ� ������ �Է��� �ּ���.");
				List<CoordinateDTO> list = dao.coordinateSearch(sc.nextLine());
				if (list.size() == 0) {
					System.out.println("�ȳ� : �ش��ϴ� ������ �������� �ʽ��ϴ�.");
				} else {
					// ���� ������ �߰��Ѵ�����.
					i[0] = 1;
					list.forEach(t -> {
						System.out.println((i[0]++) + " : " + t.getParentName() + " " + t.getName());
					});
					System.out.println("�ȳ� :  ���ϴ� ������ ������ �ּ���.");
					int selectInsert = intScan(i[0], sc);
					long localCode = Long.parseLong(list.get(selectInsert - 1).getCode());
					result = dao.coordinateAdd(dto.getId(), localCode);

					if (result == 0) {
						// �����ϸ� ���� ���и� ���ϰ� �������� �̵�.
						System.out.println("�ȳ� :  ������ �����Ͽ����ϴ�.");
						return;
					} else {
						// �����ϸ� ������ ���� ��.
						localCode = faveriteList.get(select - 1);
						result = dao.coordinateDelete(dto.getId(), localCode);
					}
				}
				if (result == 0) {
					System.out.println("�ȳ� :  ���濡 �����Ͽ����ϴ�.");
				} else {
					System.out.println("�ȳ� :  ���濡 �����Ͽ����ϴ�.");
				}
				break;
			case "4":
				return;
			default:
				System.out.println("�߸� �Է��ϼ̽��ϴ�.");
			}
		}

	}// end of main

	/**
	 * �迭�� ũ�⿡ �´� ���� ����ڰ� �Է��ϵ��� �ϴ� �޼ҵ�
	 * @param size �迭�� ũ��
	 * @param sc ��ĳ��
	 * @return
	 */
	public int intScan(int size, Scanner sc) {
		int select = 0;
		while (true) {
			try {
				select = sc.nextInt();
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println("�ȳ� :  ���ڸ� �Է��� �ּ���.");
			}
			if (select <= size) {
				break;
			} else {
				System.out.println("�ȳ� :  ������ �°� �Է��� �ּ���.");
			}
		}
		return select;
	}
}// end of class
