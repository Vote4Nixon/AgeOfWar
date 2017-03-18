package main.model;

public class InfantryLine extends Line {
	private int count;
	
	public InfantryLine(int count) {
		this.count = count;
	}
	
	@Override
	public int required() {
		int maxInfantryCount = Symbol.THREE_INFANTRY.infantryCount();
		
		return (int)Math.ceil((double)count / maxInfantryCount);
	}
	
	@Override
	public int overlapCount(Roll r) {
		Symbol[] infantrySymbols = {
				Symbol.THREE_INFANTRY,
				Symbol.TWO_INFANTRY,
				Symbol.ONE_INFANTRY,
		};
		int remaining = count;
		int overlap = 0;
		
		for(Symbol s : infantrySymbols) {
			int numSymbols = r.symbolCount(s);
			
			for(int i = 0; i < numSymbols; i++) {
				remaining -= s.infantryCount();
				overlap++;
				
				if(remaining <= 0) {
					return overlap;
				}
			}
		}
		
		return 0;
	}
	
	@Override
	public String toString() {
		return Integer.toString(count);
	}
}
