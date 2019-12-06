package model.entity;

public class Tower6 extends Mortal{
	private final String ID = "T6";
	private final double PRICE = 89.5;
	private final int frames = 7;
	
	public Tower6(String type) {
		super(type);
		this.setHealth(352);
		this.setAttack(0);
	}
	
	public String getId() {
		return ID;
	}
	
	public double getPrice() {
		return PRICE;
	}
	
	public int getFrames() {
		return frames;
	}

}
