package model.entity;

public class Enemy1 extends Mortal {
	private int speed;
	private final String ID = "E1";
	
	public Enemy1(String type) {
		super(type);
		this.setHealth(300);
		this.setAttack(5);
		speed = 50;
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
