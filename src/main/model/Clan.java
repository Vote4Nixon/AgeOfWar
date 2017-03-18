package main.model;

import java.util.Arrays;
import java.util.List;

public class Clan {
	private String title;
	private int value;
	private List<Castle> castles;
	
	public Clan(String title, int value, Castle... castles) {
		this.title = title;
		this.value = value;
		this.castles = Arrays.asList(castles);
	}
	
	public String title() {
		return title;
	}
	
	public int value() {
		return value;
	}
	
	public List<Castle> castles() {
		return castles;
	}
	
	@Override
	public String toString() {
		return castles.stream()
				.map(Castle::toString)
				.reduce("", (a, n) -> a + (a == "" ? "" : ";") + n);
	}
}
