package main.model;

public abstract class Line {
	public abstract int required();
	
	public abstract int overlapCount(Roll r);

	@Override
	public int hashCode() {
		return toString().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj.toString().equals(this.toString());
	}
}
