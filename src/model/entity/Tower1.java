package model.entity;

public class Tower1 extends Mortal{
	private final String ID = "T1";
	private final int PRICE = 110;
	private final int frames = 1;
	
	public Tower1(String type) {
		super(type);
		this.setHealth(100);
		this.setAttack(10);
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
