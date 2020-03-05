package ua.com.kl.cmathtutor.mo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class MathTest {
    @BeforeAll
    static void loadLibrary() {
	System.out.println(System.getProperty("java.library.path"));
	System.loadLibrary("Math");
    }

    @ParameterizedTest
    @CsvSource(value = { "0,1", "1,1", "2,2", "3,6", "4,24", "5,120", "10,3628800" })
    final void testFact(int argument, int fact) {
	assertEquals(fact, Math.fact(argument));
    }

}
