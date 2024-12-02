package expressivo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Console interface to the expression system.
 */
public class Main {

    private static final String DIFFERENTIATE_PREFIX = "!d/d";
    private static final String SIMPLIFY_PREFIX = "!simplify";
    private static final String VARIABLE = "[A-Za-z]+";
    private static final String DIFFERENTIATE = DIFFERENTIATE_PREFIX + "(" + VARIABLE + ") *";
    private static final String ASSIGNMENT = "(" + VARIABLE + ") *= *([^ ]+)";
    private static final String SIMPLIFY = SIMPLIFY_PREFIX + "( +" + ASSIGNMENT + ")* *";
    
    /**
     * Read expression and command inputs from the console and output results.
     * An empty input terminates the program.
     * @param args unused
     * @throws IOException if there is an error reading the input
     */
    public static void main(String[] args) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        Optional<String> currentExpression = Optional.empty();  // Holds the current expression
        
        while (true) {
            System.out.print("> ");
            final String input = in.readLine();
            
            if (input.isEmpty()) {
                System.out.println("Exiting the program. Goodbye!");
                return; // exits the program
            }
            
            try {
                final String output;
                
                // Check if input starts with differentiate prefix
                if (input.startsWith(DIFFERENTIATE_PREFIX)) {
                    final String variable = parseDifferentiate(input);
                    if (!currentExpression.isPresent()) {
                        System.out.println("You must first enter a valid expression before using this command.");
                        continue;
                    }
                    // Differentiate the current expression
                    output = Commands.differentiate(currentExpression.get(), variable);
                    currentExpression = Optional.of(output); // Update the current expression with the differentiated result
                    System.out.println("Differentiation successful.");
                } 
                // Check if input starts with simplify prefix
                else if (input.startsWith(SIMPLIFY_PREFIX)) {
                    final Map<String, Double> environment = parseSimplify(input);
                    if (!currentExpression.isPresent()) {
                        System.out.println("You must first enter a valid expression before using this command.");
                        continue;
                    }
                    // Simplify the current expression
                    output = Commands.simplify(currentExpression.get(), environment);
                    currentExpression = Optional.of(output); // Update the current expression
                    System.out.println("Simplification successful.");
                } 
                // Regular expression input (new expression)
                else {
                    final Expression expression = Expression.parse(input);
                    output = expression.toString();
                    currentExpression = Optional.of(output); // Store the newly parsed expression
                    System.out.println("Expression parsed successfully.");
                }
                
                System.out.println("Current expression: " + currentExpression.get());
            } catch (NoSuchElementException nse) {
                // currentExpression was empty
                System.out.println("Error: You must enter an expression before using this command.");
            } catch (RuntimeException re) {
                System.out.println("Error: " + re.getClass().getName() + ": " + re.getMessage());
            }
        }
    }

    // Parses the variable to differentiate the expression with respect to
    private static String parseDifferentiate(final String input) {
        final Matcher commandMatcher = Pattern.compile(DIFFERENTIATE).matcher(input);
        if (!commandMatcher.matches()) {
            throw new CommandSyntaxException("Usage: !d/d <variable> - Please provide a variable (e.g., !d/d x)");
        }
        return commandMatcher.group(1); // Return the variable name to differentiate with
    }

    // Parses the simplify command and builds a map of variables and their values
    private static Map<String, Double> parseSimplify(final String input) {
        final Matcher commandMatcher = Pattern.compile(SIMPLIFY).matcher(input);
        if (!commandMatcher.matches()) {
            throw new CommandSyntaxException("Usage: !simplify var1=val1 var2=val2 ... - Please provide valid variables and values.");
        }
        
        final Map<String, Double> environment = new HashMap<>();
        final Matcher argumentMatcher = Pattern.compile(ASSIGNMENT).matcher(input);
        while (argumentMatcher.find()) {
            final String variable = argumentMatcher.group(1);
            final double value = Double.valueOf(argumentMatcher.group(2));
            environment.put(variable, value);
        }

        return environment;
    }

    public static class CommandSyntaxException extends RuntimeException {
        private static final long serialVersionUID = 1;
        public CommandSyntaxException(String message) {
            super(message);
        }
    }
}
