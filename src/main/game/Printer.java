package main.game;

import main.bot.Game;
import main.calc.ProbabilityCalculator;
import main.calc.ProbabilitySimulator;

public class Printer {
	public static void main(String[] args) {
		Game.INSTANCE.initialize(1);
		ProbabilityCalculator.INSTANCE.initialize();
		ProbabilitySimulator.INSTANCE.initialize();
		
		printCalc();
		System.out.println();
		printSim();
	}
	
	private static void printCalc() {
		Game game = Game.INSTANCE;
		ProbabilityCalculator calc = ProbabilityCalculator.INSTANCE;
		
		System.out.println("Single Probabilities Per Line");
		System.out.println("\nA = Archer, C = Cavalry, D = Daimyo, Number = Infantry Count\n");
		System.out.println("\tNumber of Dice");
		System.out.print("Line\t");
		for(int i = 1; i <= Game.MAX_DICE; i++) {
			System.out.print(i + "\t");
		}
		System.out.println();
		
		game.lines().forEach(line -> {
			System.out.print(line + "\t");
			for(int i = 1; i <= Game.MAX_DICE; i++) {
				System.out.print(calc.singleProb(line, i) + "\t");
			}
			System.out.println();
		});
		System.out.println();
		
		System.out.println("Cumulative Probabilities Per Line");
		System.out.println("\nA = Archer, C = Cavalry, D = Daimyo, Number = Infantry Count\n");
		System.out.println("\tNumber of Dice");
		System.out.print("Line\t");
		for(int i = 1; i <= Game.MAX_DICE; i++) {
			System.out.print(i + "\t");
		}
		System.out.println();
		
		game.lines().forEach(line -> {
			System.out.print(line + "\t");
			for(int i = 1; i <= Game.MAX_DICE; i++) {
				System.out.print(calc.cumulativeProb(line, i) + "\t");
			}
			System.out.println();
		});
	}
	
	private static void printSim() {
		Game game = Game.INSTANCE;
		ProbabilitySimulator sim = ProbabilitySimulator.INSTANCE;
		
		System.out.println("Simulated Probabilities Per Castle");
		System.out.println("\nColumns With (F) = conquer/steal the full castle, Columns Without (F) = remove the hardest line, then conquer/steal\n");
		System.out.println("Clan\tCastle\tRequirements\tDifficulty\tConquer\tConquer (F)\tSteal\tSteal (F)");
		game.clans().forEach(clan -> {
			System.out.print(clan.title());
			clan.castles().forEach(castle -> {
				System.out.println("\t" + castle.title() + "\t" + castle + "\t"
						+ sim.difficulty(castle) + "\t" 
						+ sim.probability(castle, false, false) + "\t" 
						+ sim.probability(castle, true, false) + "\t"
						+ sim.probability(castle, false, true) + "\t" 
						+ sim.probability(castle, true, true));
			});
		});
	}
}
