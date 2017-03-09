import org.junit.Test;

import static org.junit.Assert.*;

public class FibonacciNumberTest {
    @Test
    public void fib() throws Exception {
        FibonacciNumber fibonacciNumber = new FibonacciNumber();

        assertEquals(-1, fibonacciNumber.fib(-1));
        assertEquals(1, fibonacciNumber.fib(0));
        assertEquals(1, fibonacciNumber.fib(1));
        assertEquals(8, fibonacciNumber.fib(5));
        assertEquals(55, fibonacciNumber.fib(9));
    }

}