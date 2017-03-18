package main.bot;

import java.util.ArrayList;
import java.util.List;

import main.calc.ProbabilityCalculator;
import main.model.Castle;
import main.model.Line;
import main.model.Player;
import main.model.Roll;
import main.model.Symbol;
import main.model.SymbolLine;

public enum StrategyHelper {
	INSTANCE;
	
	public Castle bestCastle(List<Castle> castles, List<Player> players) {
		return null;
	}
	
	public Line mostDifficultLine(List<Line> lines, int size) {
		double currDiff = difficulty(lines, size);
		Line line = null;
		
		for(int i = 0; i < lines.size(); i++) {
			List<Line> tempLines = new ArrayList<>(lines);
			tempLines.remove(i);
			
			double newDiff = difficulty(tempLines, size - lines.get(i).required());
			
			if(newDiff >= currDiff) {
				currDiff = newDiff;
				line = lines.get(i);
			}
		}
		
		return line;
	}
	
	public Line leastDifficultLine(List<Line> lines, Roll roll) {
		double currDiff = difficulty(lines, roll.size() - 1);
		Line line = null;
		
		for(int i = 0; i < lines.size(); i++) {
			int overlap = lines.get(i).overlapCount(roll);
			
			if(overlap > 0) {
				List<Line> tempLines = new ArrayList<>(lines);
				tempLines.remove(i);
				
				double newDiff = difficulty(tempLines, roll.size() - overlap);
				
				if(newDiff <= currDiff) {
					currDiff = newDiff;
					line = lines.get(i);
				}
			}
		}
		
		return line;
	}
	
	public boolean attack(Castle c, boolean steal, boolean removeHardest) {
		List<Line> lines = new ArrayList<>(c.lines());
		int initSize = Game.MAX_DICE;
		
		if(steal) {
			lines.add(new SymbolLine(Symbol.DAIMYO));
		}
		
		if(removeHardest) {
			Line hardestLine = mostDifficultLine(lines, initSize);
			
			if(hardestLine != null) {
				lines.remove(hardestLine);
				initSize -= hardestLine.required();
			}
		}
		
		Roll roll = Roll.createRandom(initSize);
		int required = required(lines);
		
		while(lines.size() > 0 && roll.size() >= required) {
			Line bestLine = leastDifficultLine(lines, roll);

			if(bestLine == null) {
				roll = Roll.createRandom(roll.size() - 1);
			}
			else {
				lines.remove(bestLine);
				required -= bestLine.required();
				roll = Roll.createRandom(roll.size() - bestLine.overlapCount(roll));
			}
		}
		
		return lines.size() == 0;
	}
	
	public double difficulty(List<Line> lines, int size) {
		ProbabilityCalculator calc = ProbabilityCalculator.INSTANCE;
		
		if(lines.size() == 0) {
			return 0;
		}
		
		int required = required(lines);
		
		if(size < required) {
			return 1;
		}
		
		double value = 1 - (required - 1) / (double)size;
		
		for(Line line : lines) {
			value *= calc.cumulativeProb(line, size) / 100;
		}
		
		return 1 - value;
	}
	
	private int required(List<Line> lines) {
		return lines.stream()
				.map(Line::required)
				.reduce(0, (a, r) -> a + r);
	}
}
