import edu.princeton.cs.algs4.*;
import java.util.PriorityQueue;
/*
    Vamos fazer uma implementação do algoritmo A* para encontrar um caminho mais curto nos
    nosso labirintos. Vamos usar a representação por grafos para acelerar o processo.
*/
public class MapPathFinding {
    AVL<MapNode, Integer> distanceSet = new AVL<MapNode, Integer>();
    AVL<MapNode, MapNode> prevNode = new AVL<MapNode, MapNode>();
    PriorityQueue<DistNode> distancePriority = new PriorityQueue<>();
    MapToGraph nodeSet;
    MapNode start;
    MapNode end;
    
    //Queue<MapNode> nodeQueue;
    private class DistNode implements Comparable<DistNode> {
        // uma estrutura para guardar MapNode na fila de prioridade com comparaçao com
        // sua distancia até o start
        public MapNode val;
        public int dist;
        DistNode(int dist, MapNode val) {
            this.val = val;
            this.dist = dist;
        }
        public int compareTo(DistNode that) {
            return this.dist - that.dist;
        }
        public boolean equals(DistNode that) {
            return compareTo(that) == 0;
        }
    }
    
    public MapPathFinding(char[][] map, int xi, int yi, 
                                                       int xf, int yf) {
        nodeSet = new MapToGraph(map);
        start = nodeSet.getNode(xi, yi);
        end = nodeSet.getNode(xf, yf);
        if (start == null) {
            StdOut.println("Bad start node.");
        }
        if (end == null) {
            StdOut.println("Bad end node.");
        }
        for (MapNode node : nodeSet.nodes()) {
            distanceSet.put(node, Integer.MAX_VALUE);
        }
        Search();
    }
    
    private void Search() {
        MapNode current = start;
        distanceSet.put(start, 0);
        prevNode.put(start, null);
        while (!current.equals(end)) {
            seeNeighbours(current);
            current = distancePriority.poll().val;
        }
    }
    
    private void seeNeighbours(MapNode current) { // see Neighbours and add then to the distanceSet
        int currentLength = distanceSet.get(current);
        if (current.up != null && currentLength + current.upLength < distanceSet.get(current.up)) {
            distanceSet.put(current.up, currentLength + current.upLength);
            prevNode.put(current.up, current);
            distancePriority.offer(new DistNode(currentLength + current.upLength, current.up));
        }
        if (current.left != null && currentLength + current.leftLength < distanceSet.get(current.left)) {
            distanceSet.put(current.left, currentLength + current.leftLength);
            prevNode.put(current.left, current);
            distancePriority.offer(new DistNode(currentLength + current.upLength, current.left));
        }
        if (current.down != null && currentLength + current.downLength < distanceSet.get(current.down)) {
            distanceSet.put(current.down, currentLength + current.downLength);
            prevNode.put(current.down, current);
            distancePriority.offer(new DistNode(currentLength + current.upLength, current.down));
        }
        if (current.right!= null && currentLength + current.rightLength < distanceSet.get(current.right)) {
            distanceSet.put(current.right, currentLength + current.rightLength);
            prevNode.put(current.right, current);
            distancePriority.offer(new DistNode(currentLength + current.upLength, current.right));
        }
    }
    
    public Iterable<MapNode> path() {
        Stack<MapNode> stack = new Stack<>();
        MapNode current = end;
        stack.push(this.end);
        while (current != start) {
            current = prevNode.get(current);
            stack.push(current);
        }
        return stack;
    }
    
    public static void main(String[] args) {
        //char[][] map = MapDrawer.getCharFromStdIn();
        char[][] map;
        if (args.length > 0)
            map = RandomDivideConquerMap.generate(40, 30);
        else 
            map = RandomExploreMaze.generate(40, 30);
        MapPathFinding mpf = new MapPathFinding(map, 1, 1, map.length-2, map[0].length-2);
        MapDrawer.drawMap(map);
        StdDraw.setPenColor(StdDraw.RED);
        int xi = 1;
        int yi = 1;
        for (MapNode node : mpf.path()) {
            StdDraw.line(xi+.5,yi+.5,node.x+.5, node.y+.5);
            xi = node.x;
            yi = node.y;
            StdDraw.show();
            StdDraw.pause(300);
        }
    }
}