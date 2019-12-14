import java.util.Collections;
import java.util.ArrayList;

public class Path {

    //initialize attributes
    public double cost;
    ArrayList<Integer> nodes;

    //constructors
    public Path()
    {
        nodes = new ArrayList<>();
    }

    public Path(int numOfNodes)
    {
        nodes = new ArrayList<>();
        for(Integer i = 0; i < numOfNodes; i++) {
            nodes.add(i);
        }
    }

    public Path(ArrayList<Integer> nodes, double cost) {
        this.cost = cost;
        this.nodes = nodes;

    }

    //find cost of specific path
    public double getCost(double[][]matrix) {
        double cost = 0;
        for (int i = 0; i < nodes.size() - 1; i++) {
            cost += matrix[nodes.get(i)][nodes.get(i + 1)];
        }
        this.cost = Math.floor(cost * 100) / 100;
        return this.cost;
    }

    //print out the selected path
    public void printPath() {
        System.out.print("|");
        for (int i = 0; i < nodes.size() - 1; i++) {
            System.out.print(nodes.get(i) + " -> ");
        }
        System.out.print("0");
        System.out.println("|   Cost is " + cost);
    }

    //  rotate path, begin and end at zero
    public void rotate()
    {
        int zeroPlace = nodes.indexOf(0);
        Collections.rotate(nodes, zeroPlace * (-1));
        nodes.add(0);
    }
}


