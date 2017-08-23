package weather_store.dto;

public class UserDTO {
	private String id="";
	private String pw="";
	private String name="";
	private String addr="";
	private int isAdmin;
	
	public UserDTO(String id, String pw, String name, String addr, int isAdmin) {
		super();
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.addr = addr;
		this.isAdmin = isAdmin;
	}

	public String getId() {
		return id;
	}

	public String getPw() {
		return pw;
	}

	public String getName() {
		return name;
	}

	public String getAddr() {
		return addr;
	}

	public int getIsAdmin() {
		return isAdmin;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public void setIsAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
	}

	@Override
	public String toString() {
		return "PersonDTO [id=" + id + ", pw=" + pw + ", name=" + name + ", addr=" + addr + ", isAdmin=" + isAdmin
				+ "]";
	}
	
	
	
}
