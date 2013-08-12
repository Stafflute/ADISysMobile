package util;

public class Couple<X, Y> {

    protected X firstValue;
    protected Y secondValue;
    
    public Couple(X firstValue, Y secondValue) {
	this.firstValue = firstValue;
	this.secondValue = secondValue;
    }

    public X getFirstValue() {
	return firstValue;
    }
    
    public Y getSecondValue() {
	return secondValue;
    }
}
