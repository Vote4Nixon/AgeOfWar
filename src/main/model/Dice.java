package main.model;

public class Dice {
	private Symbol outcome;
	
	public Dice(int i) {
		outcome = Symbol.values()[i];
	}
	
	public Symbol outcome() {
		return outcome;
	}
	
	@Override
	public String toString() {
		return outcome.shortName();
	}
}
