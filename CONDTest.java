import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;

public class CONDTest {
    private COND cond;

    @BeforeEach
    void setUp() {
        cond = new COND();
    }

    @Test
    void testEvaluaMayor() {
        assertEquals("válido", cond.evaluateCond(">", 5, 3));
    }

    @Test
    void testEvaluaIgual() {
        assertEquals("válido", cond.evaluateCond("=", 4, 4));
    }

    @Test
    void testEValuaMenor() {
        assertEquals("válido", cond.evaluateCond("<", 2, 3));
    }

    @Test
    void testEvaluaOperadorInvalido() {
        assertThrows(IllegalArgumentException.class, () -> cond.evaluateCond("$", 2, 3));
    }


    @Test
    void testEvaluaCondFormatoINvalido() {
        assertThrows(IllegalArgumentException.class, () -> cond.evaluateCond(new String[]{"= x y z"}, new HashMap<>()));
    }

    @Test
    void testEvaluaCondValorInvalido() {
        assertThrows(IllegalArgumentException.class, () -> cond.evaluateCond(new String[]{"( = x y )"}, new HashMap<>()));
    }
}
