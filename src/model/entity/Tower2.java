package model.entity;

public class Tower2 extends Mortal{
	private final String ID = "T2";
	private final int PRICE = 120;
	
	public Tower2(String type) {
		super(type);
		this.setHealth(90);
		this.setAttack(30);
	}
	
	public String getId() {
		return ID;
	}

}
