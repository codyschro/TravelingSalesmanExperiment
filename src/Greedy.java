import java.util.*;

public class Greedy {

    public static Path TravelingSalesman(CostMatrix costMatrix)
    {
        //Initialize new path, find nodes that remain to get to
        Path path = new Path();
        ArrayList<Integer> remainingNodes = new ArrayList<>();
        for(int i = 1; i < costMatrix.numOfNodes; i++) {
            remainingNodes.add(i);
        }
        //find node with smallest cost
        int currentNode = 0;
        path.nodes.add(currentNode);
        for( int i = 1; i < costMatrix.numOfNodes; i++) {
            int nextIndex = 0;
            int nextNode = remainingNodes.get(nextIndex);
            for(int j = 1; j < remainingNodes.size(); j++) {
                if(costMatrix.matrix[currentNode][remainingNodes.get(j)] < costMatrix.matrix[currentNode][nextNode]) {
                    nextIndex = j;
                    nextNode = remainingNodes.get(j);
                }
            }
            path.nodes.add(nextNode);
            path.cost += costMatrix.matrix[currentNode][nextNode];
            remainingNodes.remove(nextIndex);
            currentNode = nextNode;
        }
        path.nodes.add(0);
        path.cost += costMatrix.matrix[currentNode][0];
        return path;
    }
}
