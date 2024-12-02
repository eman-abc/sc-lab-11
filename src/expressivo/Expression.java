/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

import expressivo.parser.ExpressionLexer;
import expressivo.parser.ExpressionListener;
import expressivo.parser.ExpressionParser;

/**
 * An immutable data type representing a polynomial expression of:
 *   + and *
 *   nonnegative integers and floating-point numbers
 *   variables (case-sensitive nonempty strings of letters)
 * 
 * <p>PS3 instructions: this is a required ADT interface.
 * You MUST NOT change its name or package or the names or type signatures of existing methods.
 * You may, however, add additional methods, or strengthen the specs of existing methods.
 * Declare concrete variants of Expression in their own Java source files.
 */
public interface Expression {
    
    /**
     * Datatype definition:
     * 1. NumberExpression: Represents a nonnegative integer or floating-point constant.
     *    - Fields: double value
     * 
     * 2. VariableExpression: Represents a variable (case-sensitive, nonempty string of letters).
     *    - Fields: String name
     * 
     * 3. AddExpression: Represents the addition of two sub-expressions.
     *    - Fields: Expression left, Expression right
     * 
     * 4. MultiplyExpression: Represents the multiplication of two sub-expressions.
     *    - Fields: Expression left, Expression right
     * 
     * Recursive structure:
     * - An Expression can be composed of other Expressions through addition or multiplication,
     *   allowing for deeply nested expressions (e.g., (2 * x) + (3 * (x + 4))).
     */

    
    /**
     * Parse an expression.
     * @param input expression to parse, as defined in the PS3 handout.
     * @return expression AST for the input
     * @throws IllegalArgumentException if the expression is invalid
     */
    public static Expression parse(String input) {
        try {
            // Create a character stream from the input
            CharStream stream = new ANTLRInputStream(input);
            
            // Create a lexer that processes the character stream
            ExpressionLexer lexer = new ExpressionLexer(stream);
            lexer.reportErrorsAsExceptions();

            // Create a token stream from the lexer
            TokenStream tokenStream = new CommonTokenStream(lexer); 
            
            // Create a parser that processes the token stream
            ExpressionParser parser = new ExpressionParser(tokenStream);
            parser.reportErrorsAsExceptions();

            // Generate parse tree
            ParseTree tree = parser.root();

            // Walk the tree to create an expression
            BuildExpr builder = new BuildExpr();
            ParseTreeWalker walker = new ParseTreeWalker();

            // Walk the tree, and get the expression
            walker.walk(builder, tree);
            return builder.getExpression();
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid expression: " + input, e);
        }
    }
    
    /**
     * @return a parsable representation of this expression, such that
     * for all e:Expression, e.equals(Expression.parse(e.toString())).
     */
    @Override 
    public String toString();

    /**
     * @param thatObject any object
     * @return true if and only if this and thatObject are structurally-equal
     * Expressions, as defined in the PS3 handout.
     */
    @Override
    public boolean equals(Object thatObject);
    
    /**
     * @return hash code value consistent with the equals() definition of structural
     * equality, such that for all e1,e2:Expression,
     *     e1.equals(e2) implies e1.hashCode() == e2.hashCode()
     */
    @Override
    public int hashCode();
    
        /**
     * Strips unnecessary zeroes 
     * @param left
     * @param right
     * @return an Expression representing left + right, returning only one of the inputs
     * if one of them happens to be zero
     */
    public static Expression sum(Expression left, Expression right){
        Number zero = new Number(0);
        if(left.equals(zero)){
            return right;
        }
        if(right.equals(zero)){
            return left;
        }
        return new Add(left,right);
    }

    /**
     * Strips unnecessary ones and zeroes would just become zero 
     * @param left
     * @param right
     * @return an Expression representing left * right, returning 
     * a representation of zero if one of the inputs is zero, returning only the other 
     * input if one of the inputs represents one
     */
    public static Expression times(Expression left, Expression right){
        Number zero = new Number(0);
        Number one = new Number(1);
        if (left.equals(zero)||right.equals(zero)) {
            return new Number(0);
        }
        if (left.equals(one)) {
            return right;
        }
        if (right.equals(one)) {
            return left;
        }
        return new Multiply(left, right);
    }

    /**
     * Differentiates the expression with respect to the variable var
     * @param var the variable with respect to which the expression is to be differentiated
     * @return an Expression object representing the differentiated form
     */
    public Expression differentiate(String var);
}