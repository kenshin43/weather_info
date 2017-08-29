package weather_store.function;

import java.util.Scanner;

import weather_store.dao.StoreDAO;

public class ProductSaleTotal implements Service {

	@Override
	public void execute(Scanner sc) {
		System.out.println("======================  매출액 조회");
		System.out.println("매출액 :  " + StoreDAO.getInstance().saleTotal());
	}

}
