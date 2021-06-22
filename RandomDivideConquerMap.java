import edu.princeton.cs.algs4.*;

public class RandomDivideConquerMap {
    static int pause_time = 300; // for the visual animation
    public static char[][] generate(int square) {
        return generate(square, square);
    }

    public static char[][] generate(int w, int h) {
        // gera um labirinto h x w, levando em conta que as paredes tem grossura
        int realH = 2*h + 1;
        int realW = 2*w + 1;
        
        char[][] map = new char[realW][realH];
        for (int y = 0; y < realH; y++) {
            for (int x = 0; x < realW; x++) {
                map[x][y] = ' ';
            }
        }
        for (int y = 0; y < realH; y++) {
            map[0][y]       = '#';
            map[realW-1][y] = '#';
        }
        for (int x = 0; x < realW; x++) {
            map[x][0]       = '#';
            map[x][realH-1] = '#';
        }
        for (int y = 2; y < realH; y+=2) {
            for (int x = 2; x < realW; x+=2) {
                map[x][y] = '#';
            }
        }
        divideConquer(map, 0, realW-1, 0, realH-1);
        return map;
    }

    private static void divideConquer(char[][] map, int xi, int xf, 
                                                    int yi, int yf) {
        // escolhe um ponto aleatorio, cria 4 paredes ortogonais a partir do
        // ponto, e escolhe 3 aleatorias para abrir uma passagem aleatoria
        // lembre-se da relacao realH = 2*h + 1
        if (xf - xi <= 2 || yf - yi <= 2) return;
        int xOrigin = StdRandom.uniform(xi + 2, xf);
        int yOrigin = StdRandom.uniform(yi + 2, yf);
        // cuidado, não é todo endereço que é válido para ter um muro
        xOrigin = xOrigin - xOrigin%2; 
        yOrigin = yOrigin - yOrigin%2;
        for (int x = xi; x < xf; x++) {
            map[x][yOrigin] = '#';
        }
        for (int y = yi; y < yf; y++) {
            map[xOrigin][y] = '#';
        }
        // escolher 3 para abrir parede é o mesmo que escolher um para não abrir
        int keepWall = StdRandom.uniform(4);
        if (keepWall != 0) { // caso direita
            int open = StdRandom.uniform(xOrigin, xf);
            open = open + (open+1)%2;
            map[open][yOrigin] = ' ';
        }
        if (keepWall != 1) { // caso cima
            int open = StdRandom.uniform(yOrigin, yf);
            open = open + (open+1)%2;
            map[xOrigin][open] = ' ';
        }
        if (keepWall != 2) { // caso esquerda
            int open = StdRandom.uniform(xi, xOrigin);
            open = open + (open+1)%2;
            map[open][yOrigin] = ' ';
        }
        if (keepWall != 3) { // caso cima
            int open = StdRandom.uniform(yi, yOrigin);
            open = open + (open+1)%2;
            map[xOrigin][open] = ' ';
        }
        // chamamos recursivamente nas 4 salas formadas
        divideConquer(map, xi, xOrigin, yi, yOrigin);
        divideConquer(map, xOrigin, xf, yi, yOrigin);
        divideConquer(map, xOrigin, xf, yOrigin, yf);
        divideConquer(map, xi, xOrigin, yOrigin, yf);
    }
    
    public static char[][] generateVisual(int square) {
        return generateVisual(square, square);
    }

    public static char[][] generateVisual(int w, int h) {
        
        // gera um labirinto h x w, levando em conta que as paredes tem grossura
        int realH = 2*h + 1;
        int realW = 2*w + 1;
        
        char[][] map = new char[realW][realH];
        for (int y = 0; y < realH; y++) {
            for (int x = 0; x < realW; x++) {
                map[x][y] = ' ';
            }
        }
        for (int y = 0; y < realH; y++) {
            map[0][y]       = '#';
            map[realW-1][y] = '#';
        }
        for (int x = 0; x < realW; x++) {
            map[x][0]       = '#';
            map[x][realH-1] = '#';
        }
        for (int y = 2; y < realH; y+=2) {
            for (int x = 2; x < realW; x+=2) {
                map[x][y] = '#';
            }
        }
        StdDraw.setCanvasSize(10*realW,10*realH);
        StdDraw.setXscale(0, realW);
        StdDraw.setYscale(0, realH); // needed to draw the same way we see in the .txt
        StdDraw.enableDoubleBuffering(); // to draw faster
        MapDrawer.initializedDrawMap(map);
        StdDraw.pause(pause_time);
        divideConquerVisual(map, 0, realW - 1, 0, realH - 1);
        return map;
    }

    private static void divideConquerVisual(char[][] map, int xi, int xf, 
                                                    int yi, int yf) {
        // escolhe um ponto aleatorio, cria 4 paredes ortogonais a partir do
        // ponto, e escolhe 3 aleatorias para abrir uma passagem aleatoria
        // lembre-se da relacao realH = 2*h + 1
        if (xf - xi <= 2 || yf - yi <= 2) return;
        int xOrigin = StdRandom.uniform(xi + 2, xf);
        int yOrigin = StdRandom.uniform(yi + 2, yf);
        // cuidado, não é todo endereço que é válido para ter um muro
        xOrigin = xOrigin - xOrigin%2; 
        yOrigin = yOrigin - yOrigin%2;
        for (int x = xi; x < xf; x++) {
            map[x][yOrigin] = '#';
        }
        for (int y = yi; y < yf; y++) {
            map[xOrigin][y] = '#';
        }
        // escolher 3 para abrir parede é o mesmo que escolher um para não abrir
        int keepWall = StdRandom.uniform(4);
        if (keepWall != 0) { // caso direita
            int open = StdRandom.uniform(xOrigin, xf);
            open = open + (open+1)%2;
            map[open][yOrigin] = ' ';
        }
        if (keepWall != 1) { // caso cima
            int open = StdRandom.uniform(yOrigin, yf);
            open = open + (open+1)%2;
            map[xOrigin][open] = ' ';
        }
        if (keepWall != 2) { // caso esquerda
            int open = StdRandom.uniform(xi, xOrigin);
            open = open + (open+1)%2;
            map[open][yOrigin] = ' ';
        }
        if (keepWall != 3) { // caso cima
            int open = StdRandom.uniform(yi, yOrigin);
            open = open + (open+1)%2;
            map[xOrigin][open] = ' ';
        }
        
        MapDrawer.initializedDrawMap(map);
        StdDraw.pause(pause_time);
        // chamamos recursivamente nas 4 salas formadas
        divideConquerVisual(map, xi, xOrigin, yi, yOrigin);
        divideConquerVisual(map, xOrigin, xf, yi, yOrigin);
        divideConquerVisual(map, xOrigin, xf, yOrigin, yf);
        divideConquerVisual(map, xi, xOrigin, yOrigin, yf);
    }
    
    public static void printMapToStdOut(char[][] map) {
        StdOut.println(map.length);
        StdOut.println(map[0].length);
        for (int y = map[0].length-1; y >= 0; y--) {
            for (int x = 0; x < map.length; x++) {
                if (map[x][y] != '#') System.out.print(".");
                else                  System.out.print("#");
                
            }
            System.out.println();
        }
    }
    
    public static void main(String[] args) {
        int w = Integer.parseInt(args[0]);
        int h = Integer.parseInt(args[1]);
        if (args.length <= 2) {
            char[][] map = generate(w, h);
            w = 2*w + 1;
            h = 2*h + 1;
            StdDraw.setCanvasSize(10*w,10*h);
            StdDraw.setXscale(0, w);
            StdDraw.setYscale(0, h); // needed to draw the same way we see in the .txt
            StdDraw.enableDoubleBuffering(); // to draw faster
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    if (map[x][y] == '#') StdDraw.setPenColor(StdDraw.BLACK);
                    else                  StdDraw.setPenColor(StdDraw.WHITE);
                    StdDraw.filledSquare(x+.5, y+.5, .5);
                }
            }
            StdDraw.show();
            printMapToStdOut(map);
        } else {
            char[][] map = generateVisual(w, h);
            MapDrawer.initializedDrawMap(map);
            printMapToStdOut(map);
        }
    }
}
