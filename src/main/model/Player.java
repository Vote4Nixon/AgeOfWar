package main.model;

import java.util.List;

public class Player {
	public int number;
	public List<Castle> conquered;
	
	public Player(int number) {
		this.number = number;
	}
}
