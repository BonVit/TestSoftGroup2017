/**
 * A class that finds fibonacci number.
 */
public class FibonacciNumber {
    /**
     * Find fibonacci number with serial number. Returns -1 when n < 0.
     * @param n serial number.
     * @return fibonacci number.
     */
    public long fib(int n)
    {
        if(n < 0)
            return -1;
        if(n == 0 || n == 1)
            return 1;
        return fib(n - 1) + fib(n - 2);
    }
}
