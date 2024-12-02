package expressivo;

/**
 * An immutable data type representing an expression
 * that only contains a variable
 */
public class Variable implements Expression {
    private final String variable;
    
    // Abstraction function
    //   represents the variable this.variable
    // Representation invariant
    //   should not contain spaces, should not be empty, should not be null
    // Safety from rep exposure
    //   all fields are private and final
    
    /**
     * Make a Variable.
     * @param variable the variable to represent
     */
    public Variable(String variable) {
        this.variable = variable;
        checkRep();
    }
    
    /**
     * Check that the representation invariant is satisfied
     * @throws AssertionError if the representation invariant is violated
     */
    private void checkRep() {
        assert variable != null;
        assert !variable.isEmpty();
        assert !variable.contains(" ");
    }

    @Override 
    public String toString() {
        return variable;
    }
    
    @Override 
    public boolean equals(Object thatObject) {
        if (!(thatObject instanceof Variable)) return false;
        Variable that = (Variable) thatObject;
        return this.variable.equals(that.variable);
    }
    
    @Override 
    public int hashCode() {
        return variable.hashCode();
    }

    @Override
    public Expression differentiate(String var) {
        // The derivative of a variable is one if the variable is the same as the variable with respect to which the expression is to be differentiated
        if (variable.equals(var)) {
            return new Number(1);
        }
        // The derivative of a variable is zero if the variable is different from the variable with respect to which the expression is to be differentiated
        return new Number(0);
    }
}
