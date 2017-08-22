package weather_store.dto;

public class WeatherDTO {
	private int x;
	private int y;
	private int hour;
	private int dataSeq;
	private int day;
	private int temp;
	private int tmx;
	private int tmn;
	private int sky;
	private int pty;
	private String wfKOR;
	private String wfEN;
	private int pop;
	private int reh;
	private int ws;
	private int wd;
	private String wdKOR;
	private String wdEN;
	private int r12;
	private int s12;
	private int r06;
	private int s06;

	public WeatherDTO(int x, int y, int hour, int dataSeq, int day, int temp, int tmx, int tmn, int sky, int pty,
			String wfKOR, String wfEN, int pop, int reh, int ws, int wd, String wdKOR, String wdEN, int r12, int s12,
			int r06, int s06) {
		super();
		this.x = x;
		this.y = y;
		this.hour = hour;
		this.dataSeq = dataSeq;
		this.day = day;
		this.temp = temp;
		this.tmx = tmx;
		this.tmn = tmn;
		this.sky = sky;
		this.pty = pty;
		this.wfKOR = wfKOR;
		this.wfEN = wfEN;
		this.pop = pop;
		this.reh = reh;
		this.ws = ws;
		this.wd = wd;
		this.wdKOR = wdKOR;
		this.wdEN = wdEN;
		this.r12 = r12;
		this.s12 = s12;
		this.r06 = r06;
		this.s06 = s06;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getDataSeq() {
		return dataSeq;
	}

	public void setDataSeq(int dataSeq) {
		this.dataSeq = dataSeq;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getTemp() {
		return temp;
	}

	public void setTemp(int temp) {
		this.temp = temp;
	}

	public int getTmx() {
		return tmx;
	}

	public void setTmx(int tmx) {
		this.tmx = tmx;
	}

	public int getTmn() {
		return tmn;
	}

	public void setTmn(int tmn) {
		this.tmn = tmn;
	}

	public int getSky() {
		return sky;
	}

	public void setSky(int sky) {
		this.sky = sky;
	}

	public int getPty() {
		return pty;
	}

	public void setPty(int pty) {
		this.pty = pty;
	}

	public String getWfKOR() {
		return wfKOR;
	}

	public void setWfKOR(String wfKOR) {
		this.wfKOR = wfKOR;
	}

	public String getWfEN() {
		return wfEN;
	}

	public void setWfEN(String wfEN) {
		this.wfEN = wfEN;
	}

	public int getPop() {
		return pop;
	}

	public void setPop(int pop) {
		this.pop = pop;
	}

	public int getReh() {
		return reh;
	}

	public void setReh(int reh) {
		this.reh = reh;
	}

	public int getWs() {
		return ws;
	}

	public void setWs(int ws) {
		this.ws = ws;
	}

	public int getWd() {
		return wd;
	}

	public void setWd(int wd) {
		this.wd = wd;
	}

	public String getWdKOR() {
		return wdKOR;
	}

	public void setWdKOR(String wdKOR) {
		this.wdKOR = wdKOR;
	}

	public String getWdEN() {
		return wdEN;
	}

	public void setWdEN(String wdEN) {
		this.wdEN = wdEN;
	}

	public int getR12() {
		return r12;
	}

	public void setR12(int r12) {
		this.r12 = r12;
	}

	public int getS12() {
		return s12;
	}

	public void setS12(int s12) {
		this.s12 = s12;
	}

	public int getR06() {
		return r06;
	}

	public void setR06(int r06) {
		this.r06 = r06;
	}

	public int getS06() {
		return s06;
	}

	public void setS06(int s06) {
		this.s06 = s06;
	}

	@Override
	public String toString() {
		return "Weather [x=" + x + ", y=" + y + ", hour=" + hour + ", dataSeq=" + dataSeq + ", day=" + day + ", temp="
				+ temp + ", tmx=" + tmx + ", tmn=" + tmn + ", sky=" + sky + ", pty=" + pty + ", wfKOR=" + wfKOR
				+ ", wfEN=" + wfEN + ", pop=" + pop + ", reh=" + reh + ", ws=" + ws + ", wd=" + wd + ", wdKOR=" + wdKOR
				+ ", wdEN=" + wdEN + ", r12=" + r12 + ", s12=" + s12 + ", r06=" + r06 + ", s06=" + s06 + "]";
	}

}
