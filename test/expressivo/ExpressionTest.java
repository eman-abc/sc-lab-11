/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for the Expression abstract data type.
 */
public class ExpressionTest {

    // Testing strategy
    //   Tests for number expressions
    //     number = 0, number = 1, number > 1
    //   Tests for variable expressions
    //     variable has single char, variable has multiple chars
    //   Tests for add expressions
    //     either is number, either is variable, either is sub-expression
    //   Tests for multiply expressions
    //     either is number, either is variable, either is sub-expression
    
    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    // Tests for number expressions
    @Test
    public void testNumberExpression() {
        // number = 0
        Expression e = new Number(0);
        assertEquals("0.0", e.toString());
        assertEquals(0, e.hashCode());
        assertTrue(e.equals(new Number(0)));
        
        // number = 1
        e = new Number(1);
        assertEquals("1.0", e.toString());
        assertEquals(Double.hashCode(1), e.hashCode());
        assertTrue(e.equals(new Number(1)));
        
        // number > 1
        e = new Number(1.5);
        assertEquals("1.5", e.toString());
        assertEquals(Double.hashCode(1.5), e.hashCode());
        assertTrue(e.equals(new Number(1.5)));
    }

    // Tests for variable expressions
    @Test
    public void testVariableExpression() {
        // variable has single char
        Expression e = new Variable("x");
        assertEquals("x", e.toString());
        assertEquals("x".hashCode(), e.hashCode());
        assertTrue(e.equals(new Variable("x")));
        
        // variable has multiple chars
        e = new Variable("var");
        assertEquals("var", e.toString());
        assertEquals("var".hashCode(), e.hashCode());
        assertTrue(e.equals(new Variable("var")));
    }

    // Tests for add expressions
    @Test
    public void testAddExpression() {
        // either is number
        Expression e = new Add(new Number(1), new Number(2));
        assertEquals("(1.0 + 2.0)", e.toString());
        assertEquals(new Add(new Number(1), new Number(2)).hashCode(), e.hashCode());
        assertTrue(e.equals(new Add(new Number(1), new Number(2))));
        
        // either is variable
        e = new Add(new Number(1), new Variable("x"));
        assertEquals("(1.0 + x)", e.toString());
        assertEquals(new Add(new Number(1), new Variable("x")).hashCode(), e.hashCode());
        assertTrue(e.equals(new Add(new Number(1), new Variable("x"))));

        // either is sub-expression
        e = new Add(new Number(1), new Add(new Number(2), new Number(3)));
        assertEquals("(1.0 + (2.0 + 3.0))", e.toString());
        assertEquals(new Add(new Number(1), new Add(new Number(2), new Number(3))).hashCode(), e.hashCode());
        assertTrue(e.equals(new Add(new Number(1), new Add(new Number(2), new Number(3)))));
    }

    // Tests for multiply expressions
    @Test
    public void testMultiplyExpression() {
        // either is number
        Expression e = new Multiply(new Number(1), new Number(2));
        assertEquals("(1.0 * 2.0)", e.toString());
        assertEquals(new Multiply(new Number(1), new Number(2)).hashCode(), e.hashCode());
        assertTrue(e.equals(new Multiply(new Number(1), new Number(2))));
        
        // either is variable
        e = new Multiply(new Number(1), new Variable("x"));
        assertEquals("(1.0 * x)", e.toString());
        assertEquals(new Multiply(new Number(1), new Variable("x")).hashCode(), e.hashCode());
        assertTrue(e.equals(new Multiply(new Number(1), new Variable("x"))));
        
        // either is sub-expression
        e = new Multiply(new Number(1), new Multiply(new Number(2), new Number(3)));
        assertEquals("(1.0 * (2.0 * 3.0))", e.toString());
        assertEquals(new Multiply(new Number(1), new Multiply(new Number(2), new Number(3))).hashCode(), e.hashCode());
        assertTrue(e.equals(new Multiply(new Number(1), new Multiply(new Number(2), new Number(3)))));
    }

    @Test 
    public void testParseNumber() {
        Expression e = Expression.parse("1.0");
        assertEquals(new Number(1), e);

        e = Expression.parse("0.0");
        assertEquals(new Number(0), e);

        e = Expression.parse("1.5");
        assertEquals(new Number(1.5), e);   
    }

    @Test
    public void testParseVariable() {
        Expression e = Expression.parse("x");
        assertEquals(new Variable("x"), e);

        e = Expression.parse("var");
        assertEquals(new Variable("var"), e);
    }

    @Test
    public void testParseAdd() {
        Expression e = Expression.parse("1.0 + 2.0");
        assertEquals(new Add(new Number(1), new Number(2)), e);
    }

    @Test
    public void testParseMultiply() {
        Expression e = Expression.parse("3.0 * 2.0");
        assertEquals(new Multiply(new Number(3), new Number(2)), e);

        e = Expression.parse("1.0 * 2.0");
        assertEquals(new Number(2), e);

        e = Expression.parse("0 * x");
        assertEquals(new Number(0), e);
    }

    @Test
    public void testParseCombination() {
        // Without Parenthesis
        Expression e = Expression.parse("1.0 + 2.0 * x");
        assertEquals(new Add(new Number(1), new Multiply(new Number(2), new Variable("x"))), e);

        // With Parenthesis
        e = Expression.parse("(1.0 + 2.0) * x");
        assertEquals(new Multiply(new Add(new Number(1), new Number(2)), new Variable("x")), e);
    }
    
    
    
}

