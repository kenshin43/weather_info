package weather_store.function;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import weather_store.dao.WeatherDAO;
import weather_store.dto.CoordinateDTO;
import weather_store.dto.UserDTO;

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
		System.out.println("======================  선호지역 : " + dto.getName() + "님 ");
		faveriteMap.entrySet().forEach(t -> {
			System.out.println((i[0]++) + " : " + t.getValue());
			faveriteList.add(t.getKey());
		});
		while (true) {
			System.out.println("선택 : ----------------------------------------------------------------------------");
			System.out.println("1. 선호지역 등록\t2. 선호지역 삭제\t3. 선호지역 수정\t4. 메인");
			System.out.println("-----------------------------------------------------------------------------------");
			switch (sc.nextLine()) {
			case "1":
				i[0] = 1;
				if (faveriteMap.size() > 4) {
					System.out.println("안내 : 선호지역 등록갯수를 초과하였습니다.");
				} else {
					System.out.println(
							"선호 지역 등록 : ---------------------------------------------------------------------");
					System.out.println("└원하는 지역을 입력하세요.");
					List<CoordinateDTO> list = dao.coordinateSearch(sc.nextLine());
					if (list.size() == 0) {
						System.out.println("안내 : 해당하는 지역이 존제하지 않습니다.");
					} else {
						list.forEach(t -> {
							System.out.println((i[0]++) + " : " + t.getParentName() + " " + t.getName());
						});
						System.out.println("안내 : 원하는 지역을 선택해 주세요.");
						select = intScan(i[0], sc);
						long localCode = Long.parseLong(list.get(select - 1).getCode());
						int result = dao.coordinateAdd(dto.getId(), localCode);
						if (result == 0) {
							System.out.println("안내 :  등록에 실패하였습니다.");
						} else {
							System.out.println("안내 :  등록에 성공하였습니다.");
						}
						return;
					}
				}
				break;
			case "2": {
				System.out
						.println("선호 지역 삭제 : ---------------------------------------------------------------------");

				System.out.println("└위 목록에서 지우고자 하는 지역의 번호를 입력하세요.");
				select = intScan(faveriteList.size(), sc);
				long localCode = faveriteList.get(select - 1);
				int result = dao.coordinateDelete(dto.getId(), localCode);
				if (result == 0) {
					System.out.println("안내 :  삭제에 실패하였습니다.");
				} else {
					System.out.println("안내 :  삭제에 성공하였습니다.");
				}
				return;
			}
			case "3":
				int result = 0;
				System.out
						.println("선호 지역 변경 : ---------------------------------------------------------------------");
				System.out.println("└위 목록에서 변경하고자 하는 지역의 번호를 입력하세요.");
				select = intScan(faveriteList.size(), sc);
				System.out.println("안내 :  이제 변경을 원하는 지역을 입력해 주세요.");
				List<CoordinateDTO> list = dao.coordinateSearch(sc.nextLine());
				if (list.size() == 0) {
					System.out.println("안내 : 해당하는 지역이 존재하지 않습니다.");
				} else {
					// 먼저 지역을 추가한다음에.
					i[0] = 1;
					list.forEach(t -> {
						System.out.println((i[0]++) + " : " + t.getParentName() + " " + t.getName());
					});
					System.out.println("안내 :  원하는 지역을 선택해 주세요.");
					int selectInsert = intScan(i[0], sc);
					long localCode = Long.parseLong(list.get(selectInsert - 1).getCode());
					result = dao.coordinateAdd(dto.getId(), localCode);

					if (result == 0) {
						// 실패하면 삭제 실패를 고하고 메인으로 이동.
						System.out.println("안내 :  삭제에 실패하였습니다.");
						return;
					} else {
						// 성공하면 삭제를 진행 함.
						localCode = faveriteList.get(select - 1);
						result = dao.coordinateDelete(dto.getId(), localCode);
					}
				}
				if (result == 0) {
					System.out.println("안내 :  변경에 실패하였습니다.");
				} else {
					System.out.println("안내 :  변경에 성공하였습니다.");
				}
				break;
			case "4":
				return;
			default:
				System.out.println("잘못 입력하셨습니다.");
			}
		}

	}// end of main

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
}// end of class
