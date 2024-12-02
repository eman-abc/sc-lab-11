/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;

import java.util.Map;

/**
 * String-based commands provided by the expression system.
 * 
 * <p>PS3 instructions: this is a required class.
 * You MUST NOT change its name or package or the names or type signatures of existing methods.
 * You MUST NOT add fields, constructors, or instance methods.
 * You may, however, add additional static methods, or strengthen the specs of existing methods.
 */
public class Commands {
    
    /**
     * Differentiate an expression with respect to a variable.
     * @param expression the expression to differentiate
     * @param variable the variable to differentiate by, a case-sensitive nonempty string of letters.
     * @return expression's derivative with respect to variable.  Must be a valid expression equal
     *         to the derivative, but doesn't need to be in simplest or canonical form.
     * @throws IllegalArgumentException if the expression or variable is invalid
     */
	// Differentiate an expression with respect to the given variable
    public static String differentiate(String expressionStr, String var) {
        // Parse the expression string into an Expression object
        Expression expression = Expression.parse(expressionStr);
        
        // Differentiate the expression
        Expression differentiated = expression.differentiate(var);
        
        // Return the differentiated expression as a string
        return differentiated.toString();
    }

    // Simplify command (not relevant to differentiation, but you might already have it)
    public static String simplify(String expressionStr, Map<String, Double> environment) {
        // You can implement simplification logic here
        // This example doesn't change the current expression; it's just a placeholder
        return expressionStr;
    }

    
}
