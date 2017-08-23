package weather_store.start;

import weather_store.dao.WeatherDAO;

public class WeatherTest {

	public static void main(String[] args) {
		WeatherDAO dao = WeatherDAO.getInstance();
		dao.coordinateSQLInsert();
	}// end of main

}// end of class
