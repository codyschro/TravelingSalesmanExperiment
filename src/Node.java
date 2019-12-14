import java.awt.*;

public class Node extends Point {

    int num;

    public Node(int x, int y, int num){
        super(x,y);
        this.num = num;
    }

    public int getNum(){
        return num;
    }

    public Point getPoint(){
        return super.getLocation();
    }
}

