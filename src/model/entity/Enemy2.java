package model.entity;

public class Enemy2 extends Mortal{
	private int speed;
	private final String ID = "E2";
	
	public Enemy2(String type) {
		super(type);
		this.setHealth(200);
		this.setAttack(5);
		speed = 80;
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
