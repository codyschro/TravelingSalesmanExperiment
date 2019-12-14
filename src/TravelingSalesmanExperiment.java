import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class TravelingSalesmanExperiment {
    static ThreadMXBean bean = ManagementFactory.getThreadMXBean();


    /* define constants */
    static int MAXINPUTSIZE = 13;
    static int MININPUTSIZE = 4;
    static int numberOfTrials = 20;
    static String ResultsFolderPath = "/home/codyschroeder/Results/"; // pathname to results folder
    static FileWriter resultsFile;
    static PrintWriter resultsWriter;

    public static void main(String[] args) throws IOException {
        // run the whole experiment at least twice, and expect to throw away the data from the earlier runs, before java has fully optimized
        System.out.println("Running first full experiment...");
        runFullExperiment("TSPSQR-Exp1-ThrowAway.txt");
        System.out.println("Running second full experiment...");
        runFullExperiment("TSPSQR-Exp2.txt");
        System.out.println("Running third full experiment...");
        runFullExperiment("TSPSQR-Exp3.txt");
    }

    static void runFullExperiment(String resultsFileName) throws IOException {


        try {
            resultsFile = new FileWriter(ResultsFolderPath + resultsFileName);
            resultsWriter = new PrintWriter(resultsFile);

        } catch (Exception e) {
            System.out.println("*****!!!!!  Had a problem opening the results file " + ResultsFolderPath + resultsFileName);
            return; // not very foolproof... but we do expect to be able to create/open the file...
        }

        ThreadCpuStopWatch BatchStopwatch = new ThreadCpuStopWatch(); // for timing an entire set of trials
        ThreadCpuStopWatch TrialStopwatch = new ThreadCpuStopWatch(); // for timing an individual trial

        resultsWriter.println("#    N(inputSize)             SQRAvg"); // # marks a comment in gnuplot data
        resultsWriter.flush();

        //double previousTime = -1;
        //double doublingRatio = 0;

        /* for each size of input we want to test: in this case starting small and doubling the size each time */
        for (int inputSize = MININPUTSIZE; inputSize <= MAXINPUTSIZE; inputSize += 1) {
            // progress message...
            System.out.println("Running test for input size " + inputSize + " ... ");
            /* repeat for desired number of trials (for a specific size of input)... */
            //long batchElapsedTime = 0;
            double SQR = 0;
            double SQRAvg = 0;
            CostMatrix costMatrix = GenerateRandomEuclideanCostMatrix(inputSize, 100);
            System.out.print("    Running trial batch...");
            /* force garbage collection before each batch of trials run so it is not included in the time */
            System.gc();
            // instead of timing each individual trial, we will time the entire set of trials (for a given input size)
            // and divide by the number of trials -- this reduces the impact of the amount of time it takes to call the
            // stopwatch methods themselves
            //BatchStopwatch.start(); // comment this line if timing trials individually

            // run the tirals
            for (long trial = 0; trial < numberOfTrials; trial++) {

                /* force garbage collection before each trial run so it is not included in the time */
                System.gc();
                //CostMatrix costMatrix = GenerateRandomCostMatrix(inputSize, 100);
               Path optimal = BruteForce.TravelingSalesman(costMatrix);
               double optimalCost = optimal.cost;
               Path heuristic = Greedy.TravelingSalesman(costMatrix);
               double heuristicCost = heuristic.cost;
               SQR += heuristicCost / optimalCost;

                //TrialStopwatch.start(); // *** uncomment this line if timing trials individually
                /* run the function we're testing on the trial input */

                //Path path = Greedy.TravelingSalesman(costMatrix);

                //batchElapsedTime = batchElapsedTime + TrialStopwatch.elapsedTime(); // *** uncomment this line if timing trials individually
            }

            SQRAvg = SQR / numberOfTrials;
            //batchElapsedTime = BatchStopwatch.elapsedTime(); // *** comment this line if timing trials individually
            //double averageTimePerTrialInBatch = (double) batchElapsedTime / (double) numberOfTrials; // calculate the average time per trial in this batch

            //calculate doubling ratio
            //doublingRatio = averageTimePerTrialInBatch / previousTime;
            //previousTime = averageTimePerTrialInBatch;

            /* print data for this size of input */
            resultsWriter.printf("%12d  %15.2f \n", inputSize, SQRAvg); // might as well make the columns look nice
            resultsWriter.flush();
            System.out.println(" ....done.");

        }
    }

    public static CostMatrix GenerateRandomCostMatrix(int nodes, int maxCost )
    {
        CostMatrix matrix = new CostMatrix(nodes);
        matrix.createRandomMatrix(maxCost);
        return matrix;
    }

    public static CostMatrix GenerateRandomEuclideanCostMatrix(int nodes, int maxCoordinate)
    {
        CostMatrix matrix = new CostMatrix(nodes);
        matrix.createEuclideanMatrix(maxCoordinate);
        return matrix;
    }


}

