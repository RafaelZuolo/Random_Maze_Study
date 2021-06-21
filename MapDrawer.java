import edu.princeton.cs.algs4.*;
// we read two integers, height and width, the size os the maze
// and then we read the maze. If we read an '#', then print a block.
// else print a free space
public class MapDrawer {
    public static void main(String[] args) {
        In in = new In(args[0]);
        int h = in.readInt();
        int w = in.readInt();
        StdDraw.setCanvasSize(10*w,10*h);
        StdDraw.setXscale(0,h);
        StdDraw.setYscale(w,0); // needed to draw the same way we see in the .txt
        StdDraw.enableDoubleBuffering(); // to draw faster
        for (int i = 0; i < h; i++) {
            String s = in.readString();
            for (int j = 0; j < w; j++) {
                if (s.charAt(j) == '#') StdDraw.setPenColor(StdDraw.BLACK);
                else                    StdDraw.setPenColor(StdDraw.WHITE);
                StdDraw.filledSquare(j+.5, i+.5, .5);
            }
        }
        StdDraw.show();
    }
}
