import edu.princeton.cs.algs4.*;
import java.util.LinkedList;
// vamos fazer um parser de labirinto retangular em .txt
// Notamos que nem toda casa é útil para se explorar, então
// vamos fazer um objeto que só representa as casas com
// mudanças de caminho. Po exemplo, se temos um corredor grande
// que começa em (1,1) e só em (1, 100) temos uma oportunidade
// para poder virar, não precisamos representar as casa (1, 2), (1, 3), etc.

// vamos considerar uma ordenação das casas de acordo com a coordenada x. Em caso de
// empate, usamos a y.

public class MapToGraph {
    
    //private MapNode start;
    //private MapNode end;
    
    // guardar os endereços ordenados pelo x primeiro
    private AVL<AdressXfirst, MapNode> graphXfirst = new AVL<>();
    
    // guardar os endereços ordenados pelo y primeiro
    private AVL<AdressYfirst, MapNode> graphYfirst = new AVL<>();
    
    public MapToGraph(char[][] map) { 
        //start = new MapNode(1,1);           // lembre-se que o muro exterior ocupa espaço
        //end = new MapNode(map[0].length-2, map.length-2);
        makeGraph(map);
    }
    
    public MapToGraph(char[][] map, int startX, int startY, int endX, int endY) {
        //start = new MapNode(startX,startY);
        //end = new MapNode(endX, endY);
        makeGraph(map);
    }
    
    public MapNode getNode(int x, int y) {
        return graphXfirst.get(new AdressXfirst(x,y));
    }
    /* 
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
     */
    private class AdressXfirst implements Comparable<AdressXfirst> { 
    // representaçao dos endereços com a comparação feita com o x primeiro
        final int x, y; // guarda a coordenada da casa que representa: (x, y) = map[x][y]
        public AdressXfirst(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public int compareTo(AdressXfirst that) {
            if (this.x == that.x) return this.y - that.y;
            else                  return this.x - that.x;
        }
    }
    
    private class AdressYfirst implements Comparable<AdressYfirst> {
    // representaçao dos endereços com a comparação feita com o y primeiro
        final int x, y; // guarda a coordenada da casa que representa: (x, y) = map[x][y]
        public AdressYfirst(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public int compareTo(AdressYfirst that) {
            if (this.y == that.y) return this.x - that.x;
            else                  return this.y - that.y;
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
                b.upLength = -deltaY;
                a.downLength = -deltaY;
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
                b.rightLength = -deltaX;
                a.leftLength = -deltaX;
            }
        }
    }
    
    private void makeGraph(char[][] map) {
        
        for (int i = 1; i < map.length-1; i = i+2) { // lembre-se que o muro ocupa espaço
            for (int j = 1; j < map[0].length-1; j = j+2) {
                if (hasTurn(i, j, map)) {
                    MapNode node = new MapNode(i,j);
                    AdressXfirst ijXfirst = new AdressXfirst(i,j);
                    AdressYfirst ijYfirst = new AdressYfirst(i,j);
                    graphXfirst.put(ijXfirst, node);
                    graphYfirst.put(ijYfirst, node);
                    connect(node, map);
                }
            }
        } 
    }
    
    private void connect(MapNode node, char[][] map) {
        // como exeploramos o mapa sempre de i = 1 e j = 1 e de forma crescente, só precisamos
        // se preocupar com as conexões com nós de endereço menor.
        int x = node.x;
        int y = node.y;
        assert (!(map[x][y] == '#'));
        if (!(map[x-1][y] == '#')) addEdge(node, graphYfirst.floor(new AdressYfirst(x-1, y)));
        if (!(map[x][y-1] == '#')) addEdge(node, graphXfirst.floor(new AdressXfirst(x, y-1)));
    }
    
    private boolean hasTurn(int i, int j, char[][] map) {
        // se o espaço é um corredor #.# na vertical ou na horizontal, então não tem curva
        boolean hasUpWall    = map[i][j+1] == '#';
        boolean hasDownWall  = map[i][j-1] == '#';
        boolean hasLeftWall  = map[i-1][j] == '#';
        boolean hasRightWall = map[i+1][j] == '#';
        
        // caso horizontal
        if ( hasUpWall && hasDownWall && !hasLeftWall && !hasRightWall )
            return false;
        
        // caso vertical
        if ( !hasUpWall && !hasDownWall && hasLeftWall && hasRightWall )
            return false;
        
        return true;
    }
    
    public Iterable<MapNode> nodes() {
        return graphXfirst.vals();
    }

    public static void printMap(char[][] map) {
        int h = map[0].length;
        int w = map.length;
        for (int y = h-1; y >= 0; y--) { // we need to draw the same way we see the .txt
            for (int x = 0; x < w; x++) {
                StdOut.print(map[x][y]);
            }
            StdOut.println();
        }
    }
    
    public static void main(String[] args) {
        In in = new In(args[0]);
        int w = in.readInt();
        int h = in.readInt();
        char[][] map = new char[w][h];
        StdDraw.setCanvasSize(10*w,10*h);
        StdDraw.setXscale(0,w);
        StdDraw.setYscale(0,h);
        StdDraw.enableDoubleBuffering(); // to draw faster
        
        for (int y = h-1; y >= 0; y--) { // we need to draw the same way we see the .txt
            String s = in.readString();
            for (int x = 0; x < w; x++) {
                if (s.charAt(x) == '#') {
                    StdDraw.setPenColor(StdDraw.BLACK);
                    map[x][y] = '#';
                }
                else {
                    StdDraw.setPenColor(StdDraw.WHITE);
                    map[x][y] = '.';
                }
                StdDraw.filledSquare(x+.5, y+.5, .5);
            }
        }
        printMap(map);
        StdDraw.show();
        StdDraw.pause(300);
        MapToGraph g = new MapToGraph(map);
        
        for (MapNode node : g.nodes()) {
            //StdOut.println(node.x + " " + node.y);
            StdDraw.setPenColor(StdDraw.GREEN);
            StdDraw.filledCircle(node.x + .5, node.y + .5, .2);
            StdDraw.setPenColor(StdDraw.RED);
            if (node.down != null) {
                StdDraw.line(node.x + .5, node.y + .5, node.down.x + 0.5, node.down.y + 0.5);
            }
            if (node.left != null) {
                StdDraw.line(node.x + .5, node.y + .5, node.left.x + 0.5, node.left.y + 0.5);
            }
            if (node.up != null) {
                StdDraw.line(node.x + .6, node.y + .5, node.up.x + 0.6, node.up.y + 0.5);
            }
            if (node.right != null) {
                StdDraw.line(node.x + .5, node.y + .6, node.right.x + 0.5, node.right.y + 0.6);
            }
        }
        StdDraw.show();
    }
}