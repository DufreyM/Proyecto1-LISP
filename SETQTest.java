import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class SETQTest {
    private SETQ setq;

    @BeforeEach
    void setUp() {
        setq = new SETQ();
    }

    @Test
    void testProcesoSetqInputValido() {
        setq.processSetq("(setq x 5)");
        Map<String, Integer> variables = setq.getVariables();
        assertEquals(5, variables.get("x"));
    }

    @Test
    void testProcesoSetqFormatoInvalido() {
        assertThrows(IllegalArgumentException.class, () -> setq.processSetq("(setq x"));
    }

    @Test
    void testProcesoSetqValorInvalido() {
        assertThrows(NumberFormatException.class, () -> setq.processSetq("(setq x abc)"));
    }

    @Test
    void testProcesoSetqMultiplesValores() {
        assertThrows(IllegalArgumentException.class, () -> setq.processSetq("(setq x 5 y 6)"));
    }
}
