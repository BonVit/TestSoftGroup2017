import org.junit.Test;

import static org.junit.Assert.*;

public class TesterTest {

    @Test
    public void runTest()
    {
        Tester tester = new Tester();

        Tester.RunnableTask runnableTask = new Tester.RunnableTask() {
            @Override
            public void onExecute() {
                FibonacciNumber fn = new FibonacciNumber();
                fn.fib(10);
            }
        };

        try {
            TestResult testResult = tester.runTest(runnableTask, 5);
            assertEquals(5, testResult.getExecutionCount());

            testResult = tester.runTest(runnableTask, 0);
            assertEquals(0, testResult.getExecutionCount());

            testResult = tester.runTest(runnableTask, 13);
            assertEquals(13, testResult.getExecutionCount());

            testResult = tester.runTest(runnableTask, -13);
            assertEquals(0, testResult.getExecutionCount());

        } catch (Exception e){}
    }
}