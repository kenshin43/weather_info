package weather_store.dto;
/**
 * @author hyunmoYang
 */
public class UserDTO {
	private String id="";
	private String pw="";
	private String name="";
	private String addr="";
	private int isAdmin;
	private String salt="";
	private String passwd="";
	
	// 
	public UserDTO() {
	}
	public UserDTO(String id, String pw) {
		super();
		this.id = id;
		this.pw = pw;
	}
	
	// 
	public UserDTO(String id, String pw, String name, String addr) {
		super();
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.addr = addr;
	}
	

	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
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

	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	@Override
	public String toString() {
		return "PersonDTO [id=" + id + ", pw=" + pw + ", name=" + name + ", addr=" + addr + ", isAdmin=" + isAdmin
				+ "]";
	}
	
	
	
}
