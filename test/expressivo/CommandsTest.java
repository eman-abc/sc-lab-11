/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for the static methods of Commands.
 */
public class CommandsTest {

    // Testing strategy
    //   Tests for number
    //     number = 0, number = 1, number > 1
    //   Tests for variable
    //      var equals variable, var not equals variable
    //   Tests for add expressions
    //     either is number, either is variable, either is sub-expression
    //   Tests for multiply expressions
    //     either is number, either is variable, either is sub-expression
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    
    // TODO tests for Commands.differentiate() and Commands.simplify()
    @Test
    public void testDifferentiateNumber() {
        assertEquals("0.0", Commands.differentiate("0", "x"));
        assertEquals("0.0", Commands.differentiate("1", "x"));
        assertEquals("0.0", Commands.differentiate("1.5", "x"));
    }

    @Test
    public void testDifferentiateVariable() {
        assertEquals("1.0", Commands.differentiate("x", "x"));
        assertEquals("0.0", Commands.differentiate("x", "y"));
    }

    @Test
    public void testDifferentiateAdd() {
        assertEquals("(1.0 + 0.0)", Commands.differentiate("x + 1", "x"));
        assertEquals("(0.0 + 1.0)", Commands.differentiate("1 + x", "x"));
        assertEquals("(1.0 + 1.0)", Commands.differentiate("x + x", "x"));
        assertEquals("(0.0 + 0.0)", Commands.differentiate("x + x", "y"));
    }

    @Test
    public void testDifferentiateMultiply() {
        assertEquals("1.0", Commands.differentiate("x * 1", "x"));
        assertEquals("1.0", Commands.differentiate("1 * x", "x"));
        assertEquals("((1.0 * x) + (x * 1.0))", Commands.differentiate("x * x", "x"));
        assertEquals("((0.0 * x) + (x * 0.0))", Commands.differentiate("x * x", "y"));
    }
}
