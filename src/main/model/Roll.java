package main.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Roll {
	private List<Dice> dice;
	private int size;
	private Map<Symbol, Integer> symbolCounts;
	private int infantryCount;
	
	public Roll() {
		size = 0;
		dice = new ArrayList<>();
		symbolCounts = new HashMap<>();
		infantryCount = 0;
		
		for(Symbol s : Symbol.values()) {
			symbolCounts.put(s, 0);
		}
	}
	
	public static Roll createRandom(int size) {
		Random rand = new Random();
		Roll r = new Roll();
		
		for(int i = 0; i < size; i++) {
			r.addDice(rand.nextInt(Symbol.values().length));
		}
		
		return r;
	}
	
	public static Roll createFixed(int... dice) {
		Roll r = new Roll();
		
		for(int i = 0; i < dice.length; i++) {
			r.addDice(dice[i]);
		}
		
		return r;
	}
	
	public void addDice(int i) {
		Dice d = new Dice(i);
		
		dice.add(d);
		size += 1;
		symbolCounts.put(d.outcome(), symbolCounts.get(d.outcome()) + 1);
		infantryCount += d.outcome().infantryCount();
	}
	
	public int symbolCount(Symbol s) {
		return symbolCounts.get(s);
	}
	
	public int infantryCount() {
		return infantryCount;
	}
	
	public int size() {
		return size;
	}
	
	@Override
	public String toString() {
		return dice.stream()
				.map(Dice::toString)
				.reduce("", (a, n) -> a + n);
	}
}
