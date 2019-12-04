package model.entity;

public class Enemy3 extends Mortal{
	private int speed;
	private final String ID = "E3";
	
	public Enemy3(String type) {
		super(type);
		this.setHealth(500);
		this.setAttack(50);
		speed = 20;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public String getId() {
		return ID;
	}

}
