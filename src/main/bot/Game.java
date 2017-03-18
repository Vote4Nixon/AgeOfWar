package main.bot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import main.model.Castle;
import main.model.Clan;
import main.model.InfantryLine;
import main.model.Line;
import main.model.Player;
import main.model.Roll;
import main.model.Symbol;
import main.model.SymbolLine;

public enum Game {
	INSTANCE;
	
	public static final int MAX_DICE = 7;

	private List<Clan> clans;
	private Set<Line> lines;
	private List<Roll> rolls;
	private List<Player> players;
	
	public void initialize(int playerCount) {
		// add players
		players = new ArrayList<>();
		for(int i = 0; i < playerCount; i++) {
			players.add(new Player(i + 1));
		}
		
		// add clans
		clans = new ArrayList<>();
		clans.add(new Clan("Oda", 10,
				new Castle("Azuchi", 3, 
						new SymbolLine(Symbol.ARCHERY), 
						new SymbolLine(Symbol.CAVALRY, Symbol.CAVALRY),
						new InfantryLine(5)
						),
				new Castle("Matsumoto", 2, 
						new SymbolLine(Symbol.ARCHERY), 
						new SymbolLine(Symbol.ARCHERY),
						new InfantryLine(7)
						),
				new Castle("Odani", 1, 
						new InfantryLine(10)
						), 
				new Castle("Gifu", 1, 
						new SymbolLine(Symbol.DAIMYO),
						new SymbolLine(Symbol.ARCHERY), 
						new SymbolLine(Symbol.CAVALRY)
						)
				));
		clans.add(new Clan("Tokugawa", 8,
				new Castle("Edo", 3, 
						new SymbolLine(Symbol.ARCHERY, Symbol.CAVALRY),
						new SymbolLine(Symbol.ARCHERY, Symbol.CAVALRY), 
						new InfantryLine(3)
						),
				new Castle("Kiyosu", 2, 
						new SymbolLine(Symbol.DAIMYO), 
						new SymbolLine(Symbol.ARCHERY),
						new SymbolLine(Symbol.CAVALRY), 
						new InfantryLine(3)
						),
				new Castle("Inuyama", 1, 
						new SymbolLine(Symbol.DAIMYO),
						new SymbolLine(Symbol.CAVALRY, Symbol.CAVALRY)
						)
				));
		clans.add(new Clan("Uesugi", 8,
				new Castle("Kasugayama", 4, 
						new SymbolLine(Symbol.ARCHERY, Symbol.ARCHERY),
						new SymbolLine(Symbol.CAVALRY, Symbol.CAVALRY)
						),
				new Castle("Kitanosho", 3, 
						new SymbolLine(Symbol.DAIMYO),
						new SymbolLine(Symbol.ARCHERY, Symbol.CAVALRY), 
						new InfantryLine(6)
						)
				));
		clans.add(new Clan("Mori", 5, 
				new Castle("Gassantoda", 2, 
						new SymbolLine(Symbol.DAIMYO), 
						new InfantryLine(8)
						),
				new Castle("Takahashi", 2, 
						new SymbolLine(Symbol.CAVALRY, Symbol.CAVALRY), 
						new InfantryLine(5),
						new InfantryLine(2)
						)
				));
		clans.add(new Clan("Chosokabe", 4,
				new Castle("Matsuyama", 2, 
						new SymbolLine(Symbol.DAIMYO), 
						new InfantryLine(4), 
						new InfantryLine(4)
						),
				new Castle("Marugame", 1, 
						new SymbolLine(Symbol.DAIMYO, Symbol.DAIMYO),
						new SymbolLine(Symbol.CAVALRY)
						)
				));
		clans.add(new Clan("Kumamoto", 3, 
				new Castle("Shimazu", 3, 
						new SymbolLine(Symbol.DAIMYO, Symbol.DAIMYO),
						new SymbolLine(Symbol.ARCHERY), 
						new SymbolLine(Symbol.CAVALRY), 
						new InfantryLine(4)
						)
				));
		
		// create lines
		lines = new HashSet<>();
		clans.forEach(clan -> {
			clan.castles().forEach(castle -> {
				lines.addAll(castle.lines());
			});
		});
		
		// add rolls
		rolls = new ArrayList<>();
		for (int size = 1; size <= MAX_DICE; size++) {
			int[] dice = new int[size];
			boolean finished = false;

			while (!finished) {
				Roll r = Roll.createFixed(dice);

				for (int i = 0; i < dice.length; i++) {
					if (i == 0) {
						dice[i]++;
					}
					if (dice[i] == Symbol.values().length) {
						if (i < dice.length - 1) {
							dice[i + 1]++;
						}
						finished = dice[dice.length - 1] == Symbol.values().length;
						dice[i] = 0;
					}
				}

				rolls.add(r);
			}
		}
	}
	
	public List<Clan> clans() {
		return clans;
	}
	
	public Set<Line> lines() {
		return lines;
	}
	
	public List<Roll> rolls() {
		return rolls;
	}
}
