package weather_store.function;

import java.util.Scanner;

import weather_store.dao.StoreDAO;

/**
 * ������� ��ȸ�ϴ� Ŭ����
 * @author �Ž¿�
 *
 */
public class ProductSaleTotal implements Service {

	@Override
	public void execute(Scanner sc) {
		System.out.println("======================  ����� ��ȸ");
		System.out.println("����� :  " + StoreDAO.getInstance().saleTotal()+"��");
	}

}
