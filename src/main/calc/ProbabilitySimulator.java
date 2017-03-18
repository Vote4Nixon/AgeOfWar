package main.calc;

import java.util.HashMap;
import java.util.Map;

import main.bot.Game;
import main.bot.StrategyHelper;
import main.model.Castle;

public enum ProbabilitySimulator {
	INSTANCE;
	
	public static final int SIMULATIONS = 10000;
	
	private Game game;
	private StrategyHelper helper;
	private Map<Castle, Double> diffs;
	private Map<Castle, Double> conquerProbs;
	private Map<Castle, Double> stealProbs;
	private Map<Castle, Double> fullConquerProbs;
	private Map<Castle, Double> fullStealProbs;
	
	public void initialize() {
		game = Game.INSTANCE;
		helper = StrategyHelper.INSTANCE;
		
		diffs = new HashMap<>();
		conquerProbs = new HashMap<>();
		stealProbs = new HashMap<>();
		fullConquerProbs = new HashMap<>();
		fullStealProbs = new HashMap<>();
		
		// build probabilities
		game.clans().forEach(clan -> {
			clan.castles().forEach(castle -> {
				diffs.put(castle, helper.difficulty(castle.lines(), Game.MAX_DICE) * 100);
				
				conquerProbs.put(castle, 0.0);
				stealProbs.put(castle, 0.0);
				fullConquerProbs.put(castle, 0.0);
				fullStealProbs.put(castle, 0.0);
				
				for(int i = 0; i < SIMULATIONS; i++) {
					if(helper.attack(castle, false, false)) {
						fullConquerProbs.put(castle, fullConquerProbs.get(castle) + 1);
					}
					if(helper.attack(castle, false, true)) {
						conquerProbs.put(castle, conquerProbs.get(castle) + 1);
					}
					if(helper.attack(castle, true, false)) {
						fullStealProbs.put(castle, fullStealProbs.get(castle) + 1);
					}
					if(helper.attack(castle, true, true)) {
						stealProbs.put(castle, stealProbs.get(castle) + 1);
					}
				}
				
				conquerProbs.put(castle, conquerProbs.get(castle) / SIMULATIONS * 100);
				stealProbs.put(castle, stealProbs.get(castle) / SIMULATIONS * 100);
				fullConquerProbs.put(castle, fullConquerProbs.get(castle) / SIMULATIONS * 100);
				fullStealProbs.put(castle, fullStealProbs.get(castle) / SIMULATIONS * 100);
			});
		});
	}
	
	public double difficulty(Castle castle) {
		return diffs.get(castle);
	}
	
	public double probability(Castle castle, boolean full, boolean steal) {
		if(full) {
			return steal ? fullStealProbs.get(castle) : fullConquerProbs.get(castle);
		}
		else {
			return steal ? stealProbs.get(castle) : conquerProbs.get(castle);
		}
	}
}
