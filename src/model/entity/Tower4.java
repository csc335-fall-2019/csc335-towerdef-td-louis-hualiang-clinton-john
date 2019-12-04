package model.entity;

public class Tower4 extends Mortal{
	private final String ID = "T4";
	private final int PRICE = 245;
	private final int frames = 9;
	public Tower4(String type) {
		super(type);
		this.setHealth(180);
		this.setAttack(65);
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
