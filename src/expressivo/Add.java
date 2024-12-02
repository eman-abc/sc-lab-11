package expressivo;

/*
 * An immutable data type representing an expression
 * that is the sum of two expressions.
 */
public class Add implements Expression {
    private final Expression left;
    private final Expression right;
    
    // Abstraction function
    //   represents the sum of the expressions this.left and this.right
    // Representation invariant
    //   both left and right are non-null
    // Safety from rep exposure
    //   all fields are private and final
    
    /**
     * Make an Add.
     * @param left the left expression
     * @param right the right expression
     */
    public Add(Expression left, Expression right) {
        this.left = left;
        this.right = right;
        checkRep();
    }
    
    /**
     * Check that the representation invariant is satisfied
     * @throws AssertionError if the representation invariant is violated
     */
    private void checkRep() {
        assert left != null;
        assert right != null;
    }

    @Override 
    public String toString() {
        return "(" + left.toString() + " + " + right.toString() + ")";
    }
    
    @Override 
    public boolean equals(Object thatObject) {
        if (!(thatObject instanceof Add)) return false;
        Add that = (Add) thatObject;
        return this.left.equals(that.left) && this.right.equals(that.right);
    }
    
    @Override 
    public int hashCode() {
        return left.hashCode() + right.hashCode();
    }

    @Override
    public Expression differentiate(String var) {
        // The derivative of a sum is the sum of the derivatives
        return new Add(left.differentiate(var), right.differentiate(var));
    }
    
   
}
