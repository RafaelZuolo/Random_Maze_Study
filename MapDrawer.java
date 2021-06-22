import edu.princeton.cs.algs4.*;
// we read two integers, height and width, the size os the maze
// and then we read the maze. If we read an '#', then print a block.
// else print a free space
public class MapDrawer {
    
    public static void drawMap(String inAdress) {
        In in = new In(inAdress);
        int w = in.readInt();
        int h = in.readInt();
        StdDraw.setCanvasSize(10*w,10*h);
        StdDraw.setXscale(0, w);
        StdDraw.setYscale(0, h); // needed to draw the same way we see in the .txt
        StdDraw.enableDoubleBuffering(); // to draw faster
        for (int y = h-1; y >=0; y--) {
            String s = in.readString();
            for (int x = 0; x < w; x++) {
                if (s.charAt(x) == '#') StdDraw.setPenColor(StdDraw.BLACK);
                else                    StdDraw.setPenColor(StdDraw.WHITE);
                StdDraw.filledSquare(x+.5, y+.5, .5);
            }
        }
        StdDraw.show();
    }
    
    public static void drawMap(char[][] map) { // need to initialize StdDraw
        int w = map.length;
        int h = map[0].length;
        StdDraw.setCanvasSize(10*w,10*h);
        StdDraw.setXscale(0, w);
        StdDraw.setYscale(0, h); // needed to draw the same way we see in the .txt
        StdDraw.enableDoubleBuffering(); // to draw faster
        for (int y = h-1; y >=0; y--) {
            for (int x = 0; x < w; x++) {
                if (map[x][y] == '#') StdDraw.setPenColor(StdDraw.BLACK);
                else                  StdDraw.setPenColor(StdDraw.WHITE);
                StdDraw.filledSquare(x+.5, y+.5, .5);
            }
        }
        StdDraw.show();
    }
    
    public static void initializedDrawMap(char[][] map) { // StdDraw is already initialized
        StdDraw.clear();
        int w = map.length;
        int h = map[0].length;
        for (int y = h-1; y >=0; y--) {
            for (int x = 0; x < w; x++) {
                if (map[x][y] == '#') StdDraw.setPenColor(StdDraw.BLACK);
                else                  StdDraw.setPenColor(StdDraw.WHITE);
                StdDraw.filledSquare(x+.5, y+.5, .5);
            }
        }
        StdDraw.show();
    }
    
    public static void main(String[] args) {
        /* In in = new In(args[0]);
        int w = in.readInt();
        int h = in.readInt();
        StdDraw.setCanvasSize(10*w,10*h);
        StdDraw.setXscale(0, w);
        StdDraw.setYscale(0, h); // needed to draw the same way we see in the .txt
        StdDraw.enableDoubleBuffering(); // to draw faster
        for (int y = h-1; y >=0; y--) {
            String s = in.readString();
            for (int x = 0; x < w; x++) {
                if (s.charAt(x) == '#') StdDraw.setPenColor(StdDraw.BLACK);
                else                    StdDraw.setPenColor(StdDraw.WHITE);
                StdDraw.filledSquare(x+.5, y+.5, .5);
            }
        }
        StdDraw.show(); */
        drawMap(args[0]);
    }
}
