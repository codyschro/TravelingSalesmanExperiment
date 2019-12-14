import java.awt.*;
import java.util.*;

public class CostMatrix {

    //attributes
    int numOfNodes;
    Point[] nodes;
    public double[][] matrix;
    Path circularCorrect;

    //set attributes
    public CostMatrix(int numOfNodes){
        this.numOfNodes = numOfNodes;
        matrix = new double [numOfNodes][numOfNodes];
    }

    //get random integer
    public static int randomInt(int min, int max){
        return (int)(min + Math.random() * (max - min));
    }

    //create and get values for random matrix
    public void createRandomMatrix(int maxCost){
        for(int i = 0; i < numOfNodes; i++) {
            for (int j = 1 + i; j < numOfNodes; j++) {
                double random = randomInt(1, maxCost);
                matrix[i][j] = random;
                matrix[j][i] = random;
            }
        }
    }

    //find distances
    private void distanceBetweenNodes(){
        for(int i = 0; i < numOfNodes; i++) {
            for (int j = 1 + i; j < numOfNodes; j++) {
                double distance = nodes[i].distance(nodes[j]);
                matrix[i][j] = Math.floor(100 * distance) / 100;
                matrix[j][i] = Math.floor(100 * distance) / 100;
            }
        }
    }

    //print out matrix
    public void printMatrix(){
        System.out.println();
        System.out.print("   ");
        for(int i = 0; i < numOfNodes; i++) {
            System.out.format("%8d", i);
        }
        System.out.println();
        System.out.println();
        for (int i = 0; i < numOfNodes; i++) {
            System.out.print(i + "   ");
            for(int j = 0; j < numOfNodes; j++) {
                System.out.format(" %6.2f ", matrix[i][j]);
            }
            System.out.println();
        }
    }

    public void createEuclideanMatrix(int maxCoordinate){

        //create random points to be stored in array
        nodes = new Point[numOfNodes];
        for(int i = 0; i < numOfNodes; i++){
            nodes[i] = new Point(randomInt(0, maxCoordinate), randomInt(0, maxCoordinate));
        }
        //get distance
        distanceBetweenNodes();
    }

    public void createCircularMatrix(int radius){
        //get correct angle, then use sin and cos to get coordinates
        double angle = 360 / numOfNodes;
        double current = 0;
        ArrayList<Node> sortedNodes = new ArrayList<>();
        for(int i = 0; i < numOfNodes; i++){
            double radian = current * Math.PI / 180;
            double x = Math.cos(radian) * radius;
            double y = Math.sin(radian) * radius;
            sortedNodes.add(new Node((int) Math.round(x), (int)Math.round(y), i));
            current += angle;
        }

        //randomize order and store
        Collections.shuffle(sortedNodes);
        nodes = new Point[numOfNodes];
        circularCorrect = new Path(numOfNodes);
        for(int i = 0; i < numOfNodes; i++){
            nodes[i] = sortedNodes.get(i).getPoint();
            int num = sortedNodes.get(i).getNum();
            circularCorrect.nodes.set(num, i);
        }
        distanceBetweenNodes();
        circularCorrect.rotate();
        circularCorrect.getCost(matrix);
    }
}

