import java.util.HashMap;
import java.util.Map;

/**
 * A class for multithreaded perfomance testing of specific task.
 */
public class Tester {

    /**
     * Implement this interface and @Override onExecute with your testing task.
     */
    public interface RunnableTask {
        public void onExecute();
    }

    /**
     * Start testing.
     * @param task task for testing.
     * @param count number of tests.
     * @return testing result.
     * @throws InterruptedException
     * @see TestResult
     */
    public TestResult runTest(final RunnableTask task, final int count) throws InterruptedException {
        Map<Thread, Long> threadPool = new HashMap<>();

        for (int i = 0; i < count; i++) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    long executionTime = System.nanoTime();
                    task.onExecute();
                    executionTime = System.nanoTime() - executionTime;

                    threadPool.put(this, executionTime);
                }
            };
            threadPool.put(thread, null);
        }

        for(Thread thread : threadPool.keySet())
            thread.start();

        long[] executionTime = new long [threadPool.size()];
        int i = 0;
        for(Thread thread : threadPool.keySet())
        {
            thread.join();
            executionTime[i] = threadPool.get(thread);
            i++;
        }

        return TestResult.newInstance(executionTime);
    }
}
