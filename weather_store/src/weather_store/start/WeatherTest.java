package weather_store.start;

import java.util.Scanner;

import weather_store.dto.UserDTO;
import weather_store.function.*;
/**
 * WIS�� �����ϴ� ���� Ŭ���� �Դϴ�.
 * @author �Ž¿�
 *
 */
public class WeatherTest {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		UserDTO dto = new UserDTO();
		System.out.println("���� ���� �ý��ۿ� ���Ű��� ȯ���մϴ�!");
		login: while (true) {
			System.out.println("���� : ----------------------------------------------------------------------------");
			System.out.println("1. �α���\t\t2. ȸ������\t0. ���α׷� ����");
			System.out.println("-----------------------------------------------------------------------------------");
			switch (sc.nextLine()) {
			case "1":
				// �α���
				UserLoginService login = new UserLoginService();
				login.execute(sc);
				dto = login.getId();
				break login;
			case "2":
				// ȸ������
				new UserInsertService().execute(sc);
				break;
			case "0":
				System.exit(0);
				break;
			default:
				System.out.println("�׸� �°� �Է��� �ּ���!");
			}

		}

		if (dto.getIsAdmin() != 1) {
			while (true) {
				System.out
						.println("���� : ----------------------------------------------------------------------------");
				System.out.println("1. ���� Ȯ��\t2. ��ȣ ����\t3. ��ǰ ���\t4. ȸ����������\t0. ���α׷� ����");
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
					System.out.println("���񽺸� �����մϴ�.");
					System.exit(0);
					break;
				default:
					System.out.println("�׸� �°� �Է��� �ּ���!");
					break;
				}
			}
		} else {
			System.out.println("������ ���� ���� ]]]]");
			while (true) {
				System.out
						.println("���� : ------------------------------------------------------------------------------");
				System.out.println("1. ����� ���\t2. ���� ���� ������Ʈ\t3. �����\t0. ���α׷� ����");
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
					System.out.println("�������񽺸� �����մϴ�.");
					System.exit(0);
					break;
				default:
					System.out.println("�׸� �°� �Է��� �ּ���!");
					break;
				}
			}
		}

	}// end of main
}// end of class
