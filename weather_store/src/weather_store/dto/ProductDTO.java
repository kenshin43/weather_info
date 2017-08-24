package weather_store.dto;

public class ProductDTO {
	private int proCode;
	private String proName;
	private int price;
	private String comment;
	private int cateCode;

	public ProductDTO() {
	}

	public ProductDTO(int proCode, String proName, int price, String comment, int cateCode) {
		super();
		this.proCode = proCode;
		this.proName = proName;
		this.price = price;
		this.comment = comment;
		this.cateCode = cateCode;
	}

	public int getProCode() {
		return proCode;
	}

	public void setProCode(int proCode) {
		this.proCode = proCode;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getCateCode() {
		return cateCode;
	}

	public void setCateCode(int cateCode) {
		this.cateCode = cateCode;
	}

	@Override
	public String toString() {
		return "ProductDTO [proCode=" + proCode + ", proName=" + proName + ", price=" + price + ", comment=" + comment
				+ ", cateCode=" + cateCode + "]";
	}

} // end of main