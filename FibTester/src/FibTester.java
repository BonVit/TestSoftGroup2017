/**
 * A class for testing perfomance of finding fibonacci number.
 */
public class FibTester {
    /**
     * @param args supposed to be an array of 2 integers.
     * <br> First - serial number of fibonacci number. Second - number of tests.
     */
    public static void main(String[] args)
    {
        args = new String [] {"10", "5"};
        if(args.length < 2)
            throw new IllegalArgumentException("Wrong arguments!");

        int fibNumber = 0;
        int threadCount = 0;

        try {
            fibNumber = Integer.valueOf(args[0]);
            threadCount = Integer.valueOf(args[1]);

            if(fibNumber <= 0 || threadCount <= 0)
                throw new IllegalArgumentException("Wrong values!");
        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Wrong arguments type!");
        }

        final int f = fibNumber;
        Tester tester = new Tester();
        Tester.RunnableTask runnableTask = new Tester.RunnableTask() {
            @Override
            public void onExecute() {
                FibonacciNumber fn = new FibonacciNumber();
                fn.fib(f);
            }
        };
        try {
            TestResult testResult = tester.runTest(runnableTask, threadCount);
            System.out.println("Performed times: " + testResult.getExecutionCount() + "\nTotal time: " + testResult.getExecutionTime() +
                    " ns\nAverage time: " + testResult.getExecutionAverageTime() + " ns\nMin time: " + testResult.getExecutionMinTime() +
                    " ns\nMax time: " + testResult.getExecutionMaxTime() + " ns");
        } catch (Exception e){}
    }
}
