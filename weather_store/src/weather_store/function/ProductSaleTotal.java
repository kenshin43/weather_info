package weather_store.function;

import java.util.Scanner;

import weather_store.dao.StoreDAO;

/**
 * 매출액을 조회하는 클래스
 * @author 신승엽
 *
 */
public class ProductSaleTotal implements Service {

	@Override
	public void execute(Scanner sc) {
		System.out.println("======================  매출액 조회");
		System.out.println("매출액 :  " + StoreDAO.getInstance().saleTotal()+"원");
	}

}
