package main.model;

public enum Symbol {
	ARCHERY("A"),
	CAVALRY("C"),
	DAIMYO("D"),
	ONE_INFANTRY("1"),
	TWO_INFANTRY("2"),
	THREE_INFANTRY("3");
	
	private String shortName;
	
	Symbol(String shortName) {
        this.shortName = shortName;
    }
	
	public String shortName() {
		return this.shortName;
	}
	
	public int infantryCount() {
		switch(this) {
		case ONE_INFANTRY:
			return 1;
		case TWO_INFANTRY:
			return 2;
		case THREE_INFANTRY:
			return 3;
		default:
			return 0;
		}
	}
}
