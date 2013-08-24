package util;

public class Entry<X, Y> {

    protected X firstValue;
    protected Y secondValue;

    public Entry(X firstValue, Y secondValue) {
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
