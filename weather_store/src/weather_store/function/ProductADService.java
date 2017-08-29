package weather_store.function;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import weather_store.dao.StoreDAO;
import weather_store.dto.UserDTO;
/**
 * 제품을 소개하고 구매하는 클래스 입니다.
 * @author 신승엽
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
		System.out.println("======================  상품샾 : " + dto.getName() + "님 환영합니다!!");
		Map<Integer, String> cateMap = dao.cateList();
		List<Integer> cateList = new ArrayList<Integer>();
		while (true) {
			i[0] = 1;
			System.out.println("선택 : -------------------------------------------------");
			System.out.println("1. 전품목 보기\t2. 카테고리 선택\t3. 메인");
			System.out.println("--------------------------------------------------------");
			switch (sc.nextLine()) {
			case "1":
				System.out.println("전 품목 보기 : ------------------------------------------");
				cateMap.entrySet().forEach(t -> {
					System.out.println("----->" + t.getValue() + "의 <<목록>>");
					dao.productList(t.getKey()).forEach(x -> {
						System.out.println(x.getProCode() + "|" + x.getProName() + "\t" + x.getPrice() + "원!");
						System.out.println("---------");
						System.out.println(x.getComment());
						System.out.println("---------");
					});
				});
				sale(dao, sc);
				break;
			case "2":
				System.out.println("카테고리 별 : ------------------------------------------");
				dao.cateList().entrySet().forEach(t -> {
					cateList.add(t.getKey());
					System.out.println(i[0]++ + "|" + t.getValue());
				});
				System.out.println("정보 :  선택해 주세요.");
				int select = intScan(cateList.size(), sc);
				dao.productList(select).forEach(t -> {
					System.out.println(t.getProCode() + "|" + t.getProName() + "\t" + t.getPrice() + "원!");
					System.out.println("---------");
					System.out.println(t.getComment());
					System.out.println("---------");
				});
				sale(dao, sc);
				break;
			case "3":
				return;
			default:
				System.out.println("올바른 값을 다시 입력해 주세요.");
				break;
			}
		}
	}
/**
 * 배열의 크기에 맞는 입력을 받아오는 메소드
 * @param size 배열의 크기
 * @param sc 스캐너
 * @return
 */
	public int intScan(int size, Scanner sc) {
		int select = 0;
		while (true) {
			try {
				select = sc.nextInt();
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println("안내 :  숫자를 입력해 주세요.");
			}
			if (select <= size) {
				break;
			} else {
				System.out.println("안내 :  범위에 맞게 입력해 주세요.");
			}
		}
		return select;
	}

	/**
	 * 사용자가 구매한 제품을 데이터베이스에 전송하는 메소드
	 * @param dao StoreDAO
	 * @param sc 스캐너
	 */
	public void sale(StoreDAO dao, Scanner sc) {
		String name = "";
		System.out.println("안내 :  구매할 이름의 제품을 입력해 주세요. exit를 입력하면 종료됩니다.");
		while (true) {
			name = sc.nextLine();
			if (name.equals("exit")) {
				System.out.println("구매를 취소합니다.");
				break;
			}
			int price = dao.saleProduct(name);
			if (price == -1) {
				System.out.println("안내 :  잘못 입력하셨습니다. 다시 입력하거나, exit를 입력하여 구매를 취소합니다.");
			} else {
				System.out.println(dto.getName() + "님! " + name + "을(를) " + price + "원에 구매하였습니다!!");
			}
		}
	}
}
