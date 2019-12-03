package model.entity;

public class Tower3 extends Mortal{
	private final String ID = "T3";
	private final int PRICE = 210;
	private final int frames = 6;
	
	public Tower3(String type) {
		super(type);
		this.setHealth(160);
		this.setAttack(50);
	}
	
	public String getId() {
		return ID;
	}
	
	public int getPrice() {
		return PRICE;
	}
	
	public int getFrames() {
		return frames;
	}

}
