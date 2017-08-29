package weather_store.function;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import weather_store.dao.StoreDAO;
import weather_store.dto.UserDTO;
/**
 * ��ǰ�� �Ұ��ϰ� �����ϴ� Ŭ���� �Դϴ�.
 * @author �Ž¿�
 */
public class ProductADService implements Service {
	private UserDTO dto;

	public ProductADService(UserDTO dto) {
		this.dto = dto;
	}

	@Override
	public void execute(Scanner sc) {
		int[] i = new int[1];
		StoreDAO dao = StoreDAO.getInstance();
		System.out.println("======================  ��ǰ�� : " + dto.getName() + "�� ȯ���մϴ�!!");
		Map<Integer, String> cateMap = dao.cateList();
		List<Integer> cateList = new ArrayList<Integer>();
		while (true) {
			i[0] = 1;
			System.out.println("���� : -------------------------------------------------");
			System.out.println("1. ��ǰ�� ����\t2. ī�װ� ����\t3. ����");
			System.out.println("--------------------------------------------------------");
			switch (sc.nextLine()) {
			case "1":
				System.out.println("�� ǰ�� ���� : ------------------------------------------");
				cateMap.entrySet().forEach(t -> {
					System.out.println("----->" + t.getValue() + "�� <<���>>");
					dao.productList(t.getKey()).forEach(x -> {
						System.out.println(x.getProCode() + "|" + x.getProName() + "\t" + x.getPrice() + "��!");
						System.out.println("---------");
						System.out.println(x.getComment());
						System.out.println("---------");
					});
				});
				sale(dao, sc);
				break;
			case "2":
				System.out.println("ī�װ� �� : ------------------------------------------");
				dao.cateList().entrySet().forEach(t -> {
					cateList.add(t.getKey());
					System.out.println(i[0]++ + "|" + t.getValue());
				});
				System.out.println("���� :  ������ �ּ���.");
				int select = intScan(cateList.size(), sc);
				dao.productList(select).forEach(t -> {
					System.out.println(t.getProCode() + "|" + t.getProName() + "\t" + t.getPrice() + "��!");
					System.out.println("---------");
					System.out.println(t.getComment());
					System.out.println("---------");
				});
				sale(dao, sc);
				break;
			case "3":
				return;
			default:
				System.out.println("�ùٸ� ���� �ٽ� �Է��� �ּ���.");
				break;
			}
		}
	}
/**
 * �迭�� ũ�⿡ �´� �Է��� �޾ƿ��� �޼ҵ�
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

	/**
	 * ����ڰ� ������ ��ǰ�� �����ͺ��̽��� �����ϴ� �޼ҵ�
	 * @param dao StoreDAO
	 * @param sc ��ĳ��
	 */
	public void sale(StoreDAO dao, Scanner sc) {
		String name = "";
		System.out.println("�ȳ� :  ������ �̸��� ��ǰ�� �Է��� �ּ���. exit�� �Է��ϸ� ����˴ϴ�.");
		while (true) {
			name = sc.nextLine();
			if (name.equals("exit")) {
				System.out.println("���Ÿ� ����մϴ�.");
				break;
			}
			int price = dao.saleProduct(name);
			if (price == -1) {
				System.out.println("�ȳ� :  �߸� �Է��ϼ̽��ϴ�. �ٽ� �Է��ϰų�, exit�� �Է��Ͽ� ���Ÿ� ����մϴ�.");
			} else {
				System.out.println(dto.getName() + "��! " + name + "��(��) " + price + "���� �����Ͽ����ϴ�!!");
			}
		}
	}
}
