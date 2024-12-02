package expressivo;

/*
 * An immutable data type representing an expression
 * that contains only a number as a double.
 */
public class Number implements Expression {
    private final double number;
    
    // Abstraction function
    //   represents the number this.number
    // Representation invariant
    //   number is finite, and non-negative
    // Safety from rep exposure
    //   all fields are private and final
    
    /**
     * Make a Number.
     * @param number the number to represent
     */
    public Number(double number) {
        this.number = number;
        checkRep();
    }

    /**
     * Check that the representation invariant is satisfied
     * @throws AssertionError if the representation invariant is violated
     */
    private void checkRep() {
        assert number >= 0;
        assert Double.isFinite(number);
    }

    @Override 
    public String toString() {
        return Double.toString(number);
    }
    
    @Override 
    public boolean equals(Object thatObject) {
        if (!(thatObject instanceof Number)) return false;
        Number that = (Number) thatObject;
        final double epsilon = 1e-10;
        return Math.abs(this.number - that.number) < epsilon;
    }
    
    @Override 
    public int hashCode() {
        return Double.hashCode(number);
    }

    @Override
    public Expression differentiate(String var) {
        // The derivative of a constant is zero
        return new Number(0);
    }
}
