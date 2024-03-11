import org.junit.Test;
import static org.junit.Assert.*;

public class OperacionesTest {

    @Test
    public void testEvaluateExpression_Addition() {
        OperacionesAritmeticas operaciones = new OperacionesAritmeticas();
        String expression = "(+ 3 4)";
        int result = operaciones.evaluateExpression(expression);
        assertEquals(7, result);
    }

    @Test
    public void testEvaluateExpression_Subtraction() {
        OperacionesAritmeticas operaciones = new OperacionesAritmeticas();
        String expression = "(- 8 3)";
        int result = operaciones.evaluateExpression(expression);
        assertEquals(5, result);
    }

    @Test
    public void testEvaluateExpression_Multiplication() {
        OperacionesAritmeticas operaciones = new OperacionesAritmeticas();
        String expression = "(* 2 5)";
        int result = operaciones.evaluateExpression(expression);
        assertEquals(10, result);
    }

    @Test
    public void testEvaluateExpression_Division() {
        OperacionesAritmeticas operaciones = new OperacionesAritmeticas();
        String expression = "(/ 10 2)";
        int result = operaciones.evaluateExpression(expression);
        assertEquals(5, result);
    }

    @Test
    public void testEvaluateExpression_ComplexExpression() {
        OperacionesAritmeticas operaciones = new OperacionesAritmeticas();
        String expression = "(+ (* 3 2) (- 5 2))";
        int result = operaciones.evaluateExpression(expression);
        assertEquals(9, result);
    }
}
