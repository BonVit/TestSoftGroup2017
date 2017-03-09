/**
 * A class for storing testing result.
 * @see Tester
 */
public class TestResult {
    private final int mExecutionCount;
    private final long mExecutionTime;
    private final double mExecutionAverageTime;
    private final long mExecutionMinTime;
    private final long mExecutionMaxTime;

    /**
     * @param mExecutionCount number of tests.
     * @param mExecutionTime total testing time in ns.
     * @param mExecutionAverageTime average testing time in ns.
     * @param mExecutionMinTime minimal testing time in ns.
     * @param mExecutionMaxTime maximal testing time in ns.
     */
    public TestResult(int mExecutionCount, long mExecutionTime, double mExecutionAverageTime, long mExecutionMinTime, long mExecutionMaxTime) {
        this.mExecutionCount = mExecutionCount;
        this.mExecutionTime = mExecutionTime;
        this.mExecutionAverageTime = mExecutionAverageTime;
        this.mExecutionMinTime = mExecutionMinTime;
        this.mExecutionMaxTime = mExecutionMaxTime;
    }

    /**
     * Test result builder.
     * @param executionTime execution time of each test in ns.
     * @return new instance of TestResult.
     */
    public static TestResult newInstance(long[] executionTime)
    {
        int mExecutionCount = executionTime.length;
        long mExecutionTime = 0;
        double mExecutionAverageTime = 0;
        long mExecutionMinTime = Long.MAX_VALUE;
        long mExecutionMaxTime = Long.MIN_VALUE;

        for(int i = 0; i < executionTime.length; i++)
        {
            mExecutionTime += executionTime[i];
            if(mExecutionMinTime > executionTime[i])
                mExecutionMinTime = executionTime[i];
            if(mExecutionMaxTime < executionTime[i])
                mExecutionMaxTime = executionTime[i];
        }
        mExecutionAverageTime = mExecutionTime / (1.0 * executionTime.length);

        return new TestResult(mExecutionCount, mExecutionTime, mExecutionAverageTime, mExecutionMinTime, mExecutionMaxTime);
    }

    /**
     * @return number of tests.
     */
    public int getExecutionCount() {
        return mExecutionCount;
    }

    /**
     * @return total testing time in ns.
     */
    public long getExecutionTime() {
        return mExecutionTime;
    }

    /**
     * @return average testing time in ns.
     */
    public double getExecutionAverageTime() {
        return mExecutionAverageTime;
    }

    /**
     * @return minimal testing time in ns.
     */
    public long getExecutionMinTime() {
        return mExecutionMinTime;
    }

    /**
     * @return mExecutionMaxTime maximal testing time in ns.
     */
    public long getExecutionMaxTime() {
        return mExecutionMaxTime;
    }
}
