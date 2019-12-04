package model;

import java.util.Observable;

public class MoneyCount extends Observable{
	private final int initialAmount = 500;
	private int curAmount;
	
	public MoneyCount() {
		curAmount = initialAmount;
	}
	
	public int getInitialAmount() {
		return initialAmount;
	}
	
	public int getCurAmount() {
		return curAmount;
	}
	
	public void use(int amount) {
		if(affordable(amount)) {
			curAmount -= amount;
		}
		setChanged();
		notifyObservers();
	}
	
	public boolean affordable(int amount) {
		return curAmount > amount;
	}
}
