package model;

public abstract class Display {
	
	private int idDisplay;
	private String nameContent;
	private int idCategory;
	private String image;
	private double price;
	private int available;
	
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
}
