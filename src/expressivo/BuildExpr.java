package expressivo;

import java.util.List;
import java.util.Stack;

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

class BuildExpr implements ExpressionListener {
    private Stack<Expression> stack = new Stack<>();
    /**
     * Returns the epxression constructed by this listener object
     * @return Expression for the parse tree
     */
    public Expression getExpression(){
        return stack.get(0);
    }
    
    @Override public void enterRoot(ExpressionParser.RootContext context) {
    }
    
    @Override public void exitRoot(ExpressionParser.RootContext context) {
    }

    @Override public void enterSum(ExpressionParser.SumContext context) {
    }
    
    @Override public void exitSum(ExpressionParser.SumContext context) {
        // matched the product ('+' product)* rule
        List<ExpressionParser.ProductContext> terms = context.product();
        assert stack.size() >= terms.size();
        // the pattern must have at least 1 child 
        // pop the last child
        assert terms.size() > 0;
        Expression sum = stack.pop();
        // pop the older children, one by one, and add them on
        for (int i = 1; i < terms.size(); i++) {
            sum = Expression.sum(stack.pop(), sum);
        }
        // the result is this subtree's Expression
        stack.push(sum);
    }
    
    @Override public void enterProduct(ExpressionParser.ProductContext context) {
    }
    
    @Override public void exitProduct(ExpressionParser.ProductContext context) {
        // matched the product ('+' product)* rule
        List<ExpressionParser.PrimitiveContext> factors = context.primitive();
        assert stack.size() >= factors.size();
        // the pattern must have at least 1 child 
        // pop the last child
        assert factors.size() > 0;
        Expression product = stack.pop();
        // pop the older children, one by one, and multiply them on
        for (int i = 1; i < factors.size(); i++){
            product = Expression.times(stack.pop(), product);
        }
        // the result is this subtree's Expression
        stack.push(product);
    }
    
    @Override public void enterPrimitive(ExpressionParser.PrimitiveContext context) {
    }
    
    @Override public void exitPrimitive(ExpressionParser.PrimitiveContext context) {
        if (context.NUMBER() != null){
            // matched the number alternative
            double n = Double.valueOf(context.NUMBER().getText());
            Expression number = new Number(n);
            stack.push(number);
        } else if(context.VARIABLE() != null){
            // matched the VAR alternative
            String v = context.VARIABLE().getText();
            Expression var = new Variable(v);
            stack.push(var);
        }
    }
    
    @Override public void visitTerminal(TerminalNode terminal) {
    }
    
    @Override public void enterEveryRule(ParserRuleContext context) { 
    }
    
    @Override public void exitEveryRule(ParserRuleContext context) {
    }
    
    @Override public void visitErrorNode(ErrorNode node) { 
    }       
}