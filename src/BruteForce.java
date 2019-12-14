import java.util.*;

public class BruteForce {

    public static Path TravelingSalesman(CostMatrix costMatrix){

        //initialize path
        int[] path = new int[costMatrix.numOfNodes + 1];
        for(int i = 0; i < costMatrix.numOfNodes; i++){
            path[i] = i;
        }

        path[costMatrix.numOfNodes] = 0;
        int[] shortest = path.clone();
        double cost = getPathCost(path, costMatrix);

        //Heap algorithm, always starts and ends at 0
        //implemented from here: https://www.geeksforgeeks.org/heaps-algorithm-for-generating-permutations/
        int[] indexes = new int[costMatrix.numOfNodes];
        Arrays.fill(indexes, 1);
        int i = 0;
        while(i < costMatrix.numOfNodes){
            if(indexes[i] < i){
                swap(path, i % 2 != 0 ? 1: indexes[i], i);
                if (getPathCost(path, costMatrix) < cost) {
                    shortest = path.clone();
                    cost = getPathCost(path, costMatrix);
                }
                indexes[i]++;
                i = 0;
            }
            else{
                indexes[i] = 1;
                i++;
            }
        }

        //return new path
        ArrayList<Integer> list = new ArrayList<>();
        for(int j = 0; j < shortest.length; j++) {
            list.add(shortest[j]);
        }
        return new Path(list, cost);
    }

    //swap helper function
    private static void swap(int[] x, int a, int b) {
        int tmp = x[a];
        x[a] = x[b];
        x[b] = tmp;
    }

    //find path cost
    private static double getPathCost(int[] path, CostMatrix costMatrix) {
        double cost = 0;
        for ( int i = 0; i < path.length - 1; i++)
        {
            cost += costMatrix.matrix[path[i]][path[i + 1]];
        }
        return cost;
    }



}

