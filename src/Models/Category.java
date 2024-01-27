package Models;

public class Category {
	private Integer categoryId;
	private String categoryName;
	private String categoryDescribe;
	private String imagePath;
	private Integer status;
	
	public Category() {
		
	}
	
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getCategoryDescribe() {
		return categoryDescribe;
	}
	public void setCategoryDescribe(String categoryDescribe) {
		this.categoryDescribe = categoryDescribe;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
