import edu.princeton.cs.algs4.*;
import java.util.LinkedList;
public class MapNode implements Comparable<MapNode> {
// representaçao dos endereços com a comparação feita com o x primeiro    
    MapNode up, down, left, right;
    int upLength, downLength, leftLength, rightLength;
    final int x, y; // guarda a coordenada da casa que representa: (x, y) = map[x][y]
    
    public MapNode(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public int compareTo(MapNode that) {
        if (this.x == that.x) return this.y - that.y;
        else                  return this.x - that.x;
    }  
    
    public boolean equals(MapNode that) {
        return this.x == that.x && this.y == that.y;
    }
    
    public Iterable<MapNode> adjacentTo(MapNode node) {
        LinkedList<MapNode> q = new LinkedList<MapNode>();
        if (node.up     != null) q.offer(node.up);
        if (node.down   != null) q.offer(node.down);
        if (node.left   != null) q.offer(node.left);
        if (node.right  != null) q.offer(node.right);
        return q;
    }
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}