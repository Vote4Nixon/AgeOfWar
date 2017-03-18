package main.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SymbolLine extends Line {
	private List<Symbol> symbols;
	private Map<Symbol, Integer> symbolCounts;
	
	public SymbolLine(Symbol... symbols) {
		this.symbols = Arrays.asList(symbols);
		
		symbolCounts = new HashMap<>();
		for(Symbol s : Symbol.values()) {
			symbolCounts.put(s, 0);
		}
		for(Symbol s : symbols) {
			symbolCounts.put(s, symbolCounts.get(s) + 1);
		}
	}
	
	@Override
	public int required() {
		return symbols.size();
	}
	
	@Override
	public int overlapCount(Roll r) {
		int overlap = 0;
		
		for(Symbol s : symbolCounts.keySet()) {
			if(symbolCounts.get(s) > r.symbolCount(s)) {
				return 0;
			}
			overlap += symbolCounts.get(s);
		}
		
		return overlap;
	}
	
	@Override
	public String toString() {
		return symbols.stream()
				.map(Symbol::shortName)
				.reduce("", (a, n) -> a + n);
	}
}
