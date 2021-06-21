import edu.princeton.cs.algs4.*;
// vamos fazer um parser de labirinto retangular em .txt
// Notamos que nem toda casa é útil para se explorar, então
// vamos fazer um objeto que só representa as casas com
// mudanças de caminho. Po exemplo, se temos um corredor grande
// que começa em (1,1) e só em (1, 100) temos uma oportunidade
// para poder virar, não precisamos representar as casa (1, 2), (1, 3), etc.

// vamos considerar uma ordenação das casas de acordo com a coordenada x. Em caso de
// empate, usamos a y.
public class MapToGraph {
    
    private MapNode start;
    private MapNode end;
    private AVL<MapNode, MapNode> graph;
    
    public MapToGraph(char[][] map) { 
        graph = new AVL<MapNode, MapNode>();
        //start = new MapNode(1,1);           // lembre-se que o muro exterior ocupa espaço
        //end = new MapNode(map[0].length-2, map.length-2);
        makeGraph(map, graph);
    }
    
    public MapToGraph(char[][] map, int startX, int startY, int endX, int endY) {
        graph = new AVL<MapNode, MapNode>();
        //start = new MapNode(startX,startY);
        //end = new MapNode(endX, endY);
        makeGraph(map, graph);
    }
    
    public void setStart(int x, int y) {

    }
    
    public void setEnd(int x, int y) {

    }
    
    public int getStartX(int x, int y) {
        return start.x;
    }
    
    public int getEndX(int x, int y) {
        return end.x;
    }
    
    public int getStartY(int x, int y) {
        return start.y;
    }
    
    public int getEndY(int x, int y) {
        return end.y;
    }
    
    private class MapNode implements Comparable<MapNode> {
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
    }
    
    private void addEdge(MapNode a, MapNode b) {
        if (a.equals(b)) return;
        int deltaX = b.x - a.x;
        int deltaY = b.y - a.y;
        if (deltaX == 0) {
            if (deltaY > 0) { // b acima de a
                a.up = b;
                b.down = a;
                a.upLength = deltaY;
                b.downLength = deltaY;
            } else { // a acima de b
                b.up = a;
                a.down = b;
                b.upLength = deltaY;
                a.downLength = deltaY;
            }
        } else {
            if (deltaX > 0) { // b esta na direita de a
                a.right = b;
                b.left = a;
                a.rightLength = deltaX;
                b.leftLength = deltaX;
            } else { // a esta na direita de b
                b.right = a;
                a.left = b;
                b.rightLength = deltaX;
                a.leftLength = deltaX;
            }
        }
    }
    
    private void makeGraph(char[][] map, AVL<MapNode, MapNode> graph) {
        MapNode node = new MapNode(1,1); // lembre-se que o muro ocupa espaço
        
    }
}