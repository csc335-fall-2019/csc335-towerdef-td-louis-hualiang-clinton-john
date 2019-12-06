package model.entity;

public class Tower5 extends Mortal{
	private final String ID = "T5";
	private final int PRICE = 335;
	private final int frames = 9;
	
	public Tower5(String type) {
		super(type);
		this.setHealth(200);
		this.setAttack(135);
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
