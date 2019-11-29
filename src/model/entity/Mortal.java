package model.entity;

public class Mortal extends Entity {
	private int health;
	private int attack;
	
	public Mortal(String type) {
		super(type);
	}
	
	public void beAttacked(int damage) {
		if (health > damage) {
			health -= damage;
		}else {
			health = 0;
		}
	}
	
	protected void setHealth(int health) {
		this.health = health;
	}
	
	protected void setAttack(int attack) {
		this.attack = attack;
	}
	
	public boolean isDead() {
		return health == 0;
	}
	
	public int getHealth() {
		return health;
	}
	
	public int getAttack() {
		return attack;
	}
	
}
