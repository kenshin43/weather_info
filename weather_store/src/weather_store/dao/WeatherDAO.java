package weather_store.dao;

import weather_store.town.TownCodeService;
import weather_store.util.DBUtil;

public class WeatherDAO {
	private static WeatherDAO weatherDAO;

	private WeatherDAO() {
	}

	public static WeatherDAO getInstance() {
		if (weatherDAO == null) {
			weatherDAO = new WeatherDAO();
		}
		return weatherDAO;
	}// end of getInstance();

	public int coordinateSQLInsert() {
		int status = -1;
		TownCodeService tcs = new TownCodeService();
		tcs.outputSQL();
		return 0;
	}

} // end of class
