package expressivo;

/**
 * An immutable data type representing an expression
 * that is the product of two expressions.
 */
public class Multiply implements Expression {
    private final Expression left;
    private final Expression right;
    
    // Abstraction function
    //   represents the product of the expressions this.left and this.right
    // Representation invariant
    //   both left and right are non-null
    // Safety from rep exposure
    //   all fields are private and final
    
    /**
     * Make a Multiply.
     * @param left the left expression
     * @param right the right expression
     */
    public Multiply(Expression left, Expression right) {
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
        return "(" + left.toString() + " * " + right.toString() + ")";
    }
    
    @Override 
    public boolean equals(Object thatObject) {
        if (!(thatObject instanceof Multiply)) return false;
        Multiply that = (Multiply) thatObject;
        return this.left.equals(that.left) && this.right.equals(that.right);
    }
    
    @Override 
    public int hashCode() {
        return left.hashCode() + right.hashCode();
    }

    @Override
    public Expression differentiate(String var) {
        // The derivative of a product is the sum of the product of the derivative of the left expression and the right expression and the product of the left expression and the derivative of the right expression
        return new Add(new Multiply(left.differentiate(var), right), new Multiply(left, right.differentiate(var)));
    }
    
}
