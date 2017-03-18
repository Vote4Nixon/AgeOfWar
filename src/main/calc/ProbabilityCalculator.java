package main.calc;

import java.util.HashMap;
import java.util.Map;

import main.bot.Game;
import main.model.Line;
import main.model.Roll;

public enum ProbabilityCalculator {
	INSTANCE;
	
	private Game game;
	private Map<Line, Map<Integer, Integer>> matchCounts;
	private Map<Line, Map<Integer, Double>> singleProbs;
	private Map<Line, Map<Integer, Double>> cumulativeProbs;

	public void initialize() {
		game = Game.INSTANCE;
		
		// create match counts
		matchCounts = new HashMap<>();
		for(Line line : game.lines()) {
			Map<Integer, Integer> lineCounts = new HashMap<Integer, Integer>();
			
			for(int i = 1; i <= Game.MAX_DICE; i++) {
				lineCounts.put(i, 0);
			}
			for(Roll r : game.rolls()) {
				if(line.overlapCount(r) > 0) {
					lineCounts.put(r.size(), lineCounts.get(r.size()) + 1);
				}
			}
			
			matchCounts.put(line, lineCounts);
		}
		
		// build probabilities
		singleProbs = new HashMap<>();
		cumulativeProbs = new HashMap<>();
		matchCounts.forEach((line, counts) -> {
			Map<Integer, Double> singleLineProbs = new HashMap<>();
			Map<Integer, Double> cumulativeLineProbs = new HashMap<>();
			
			for(int size = 1; size <= Game.MAX_DICE; size++) {
				double singleProb = getProb(counts.get(size), size);
				double cumulativeProb = 0;
				
				for(int i = size; i >= 1; i--) {
					double mult = 1;
					
					for(int j = size; j > i; j--) {
						mult *= 1 - getProb(counts.get(j), j);
					}
					
					cumulativeProb += getProb(counts.get(i), i) * mult;
				}

				singleLineProbs.put(size, singleProb * 100);
				cumulativeLineProbs.put(size, cumulativeProb * 100);
			}
			
			singleProbs.put(line, singleLineProbs);
			cumulativeProbs.put(line, cumulativeLineProbs);
		});
	}
	
	private double getProb(int count, int size) {
		return count / Math.pow(6, size);
	}
	
	public double singleProb(Line line, int size) {
		return singleProbs.get(line).get(size);
	}
	
	public double cumulativeProb(Line line, int size) {
		return cumulativeProbs.get(line).get(size);
	}
}
