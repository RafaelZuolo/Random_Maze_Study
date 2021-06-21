public class RandomDivideConquerMap {
    
    public static char[][] generate(int square) {
        return generate(square, square);
    }

    public static char[][] generate(int h, int w) {
        // gera um labirinto h x w, levando em conta que as paredes tem grossura
        int realH = 2*h + 1;
        int realW = 2*w + 1;
        StdDraw.setCanvasSize(realW, realH);
        StdDraw.setScale(realW, realH);
        StdDraw.enableDoubleBuffering();
        char[][] map = new char[realH][realW];
        divideConquer(map, 0, h, 0, w);
        return map;
    }

    private static void divideConquer(char[][] map, int xi, int xf, 
                                                    int yi, int yf) {
        // escolhe um ponto aleatorio, cria 4 paredes ortogonais a partir do
        // ponto, e escolhe 3 aleatorias para abrir uma passagem aleatoria
        // lembre-se da relacao realH = 2*h + 1
        if (xf - xi <= 2 || yf - yi <= 2) return;
        int xOrigin = StdRandom.uniform(xi+1, xf-1);
        int yOrigin = StdRandom.uniform(yi+1, yf - 1);

    }
}
