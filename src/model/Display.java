package model;

//display class
public abstract class Display {
	//all content data
	private int type;
	private int idDisplay;
	private String nameContent;
	private int idCategory;
	private String image;
	private double price;
	private int available;
	private String description; 
	
	public int getIdDisplay() {
		return idDisplay;
	}
	public void setIdDisplay(int idDisplay) {
		this.idDisplay = idDisplay;
	}
	public String getNameContent() {
		return nameContent;
	}
	public void setNameContent(String nameContent) {
		this.nameContent = nameContent;
	}
	public int getIdCategory() {
		return idCategory;
	}
	public void setIdCategory(int idCategory) {
		this.idCategory = idCategory;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getAvailable() {
		return available;
	}
	public void setAvailable(int available) {
		this.available = available;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
}
