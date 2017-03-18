package main.model;

import java.util.Arrays;
import java.util.List;

public class Castle {
	private String title;
	private int value;
	private List<Line> lines;
	
	public Castle(String name, int value, Line... lines) {
		this.title = name;
		this.value = value;
		this.lines = Arrays.asList(lines);
	}
	
	public String title() {
		return title;
	}
	
	public int value() {
		return value;
	}
	
	public List<Line> lines() {
		return lines;
	}
	
	@Override
	public String toString() {
		return lines.stream()
				.map(Line::toString)
				.reduce("", (a, n) -> a + (a == "" ? "" : ",") + n);
	}
}
