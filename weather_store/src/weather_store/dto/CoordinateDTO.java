package weather_store.dto;
/**
 * 지역 정보를 저장하는 클래스
 * @author 신승엽
 *
 */
public class CoordinateDTO {

	private String code;
	private String name;
	private String parentCode;
	private String parentName;
	private String gridX;
	private String gridY;

	public CoordinateDTO() {
	}

	public CoordinateDTO(String code, String name, String parentCode, String parentName, String gridX, String gridY) {
		super();
		this.code = code;
		this.name = name;
		this.parentCode = parentCode;
		this.parentName = parentName;
		this.gridX = gridX;
		this.gridY = gridY;
	}

	public CoordinateDTO(String code, String name, String parentCode, String parentName) {
		super();
		this.code = code;
		this.name = name;
		this.parentCode = parentCode;
		this.parentName = parentName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getGridX() {
		return gridX;
	}

	public void setGridX(String gridX) {
		this.gridX = gridX;
	}

	public String getGridY() {
		return gridY;
	}

	public void setGridY(String gridY) {
		this.gridY = gridY;
	}

	@Override
	public String toString() {
		return "code=" + code + ", name=" + name + ", parentCode=" + parentCode + ", parentName=" + parentName
				+ ", gridX=" + gridX + ", gridY=" + gridY;
	}

}
