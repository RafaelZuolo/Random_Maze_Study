import edu.princeton.cs.algs4.*;
// geramos um labirinto aleatorio pelo método da busca em
// profundidade aleatória. Temos uma opção de animação para ver
// a exploração acontecendo.
// necessário ter o pacote do algs4 do Sedgewick.
//
// compilação: javac-algs4 RandomExploreMaze.java
// execução:   java-algs4 RandomAccessFile [int] [int] [double] [String] [String]
// onde args[0] é a largura (width), args[1] é a altura(height) e
// args[2] é a probabilidade de cada bloco ser eliminado para deixar o labirinto
// imperfeito.
// args[3] é opcional, nome do arquivo para salvar o labirinto perfeito.
// args[4] é opcional, nome do arquivo para salvar o labirinto imperfeito.
public class RandomExploreMaze {
    static final int up = 0;
    static final int right = 1;
    static final int down = 2;
    static final int left = 3;
    static final int PAUSE_TIME = 10;

    public static char[][] generate(int w, int h) {
        int realH = 2*h + 1;
        int realW = 2*w + 1;
        int[][] visited = new int[w][h];
        char[][] map = new char[realW][realH];
        for (int x = 0; x < realW; x++) // all map is block
            for (int y = 0; y < realH; y++)
                map[x][y] = '#';
        return recursiveExplore(map, visited);
    }

    static char[][] recursiveExplore(char[][] map, int[][] visited) {
        Stack<int[]> stack = new Stack<>();
        int[] start = new int[]{0,0};
        stack.push(start);
        explore(map, visited, stack);
        return map;
    }

    static void explore(char[][] map, int[][] visited, Stack<int[]> stack) {
        if (stack.isEmpty()) return;
        int[] now = stack.peek();
    
        visited[now[0]][now[1]] = 1;
        map[2*now[0]+1][2*now[1]+1] = '.';
        while (!isDeadEnd(visited, now[0], now[1])) {
            int dir;
            do {
                dir = StdRandom.uniform(4);
            } while (!isValid(visited, now[0], now[1], dir));

            int[] next;
            if (dir == up) {
                map[2*now[0]][2*now[1]+1] = '.';
                next = new int[]{now[0] -1, now[1]};
            } else if (dir == right) {
                map[2*now[0]+1][2*now[1]+2] = '.';
                next = new int[]{now[0] , now[1]+1};
            } else if (dir == down) {
                map[2*now[0]+2][2*now[1]+1] = '.';
                next = new int[]{now[0] +1, now[1]};
            } else {
                map[2*now[0]+1][2*now[1]] = '.';
                next = new int[]{now[0] , now[1]-1};
            }
            stack.push(next);
            explore(map, visited, stack);
        }
        stack.pop();
        return;
    }

    static boolean isValid(int[][] adress, int x, int y, int dir) {
        // return true if we can go from adress [x][y] to the direction dir
        if      (dir == up && x > 0) return adress[x-1][y] == 0;
        else if (dir == right && y < adress[0].length - 1) return adress[x][y+1] == 0;
        else if (dir == down && x < adress.length - 1) return adress[x+1][y] == 0;
        else if (dir == left && y > 0) return adress[x][y-1] == 0;
        else return false; 
    }

    static boolean isDeadEnd(int[][] adress, int x, int y) {
        // return true if all directions from (x,y) are visited
        if (!isValid(adress, x, y, up) && !isValid(adress, x, y, right) 
            && !isValid(adress, x, y, down) && !isValid(adress, x, y, left) ) {
            return true;
        }
        else
            return false;
    }

    public static void printMap(char[][] map) {
        StdDraw.clear();
        for (int j = map[0].length-1; j >= 0; j--) {
            for (int i = 0; i < map.length; i++) {
                if (map[i][j] != '#') {
                    System.out.print(" ");
                    StdDraw.setPenColor(StdDraw.WHITE);
                }
                else {
                    System.out.print("#");
                    StdDraw.setPenColor(StdDraw.BLACK);
                }
                StdDraw.filledSquare(i+.5, j+.5, .5);
            }
            System.out.println();
        }
        StdDraw.show();
    }
    
    // aqui implementamos uma pilha para a busca em profundidade.
    static class Node<K> {
        Node<K> next = null;
        K key;
        Node(K key) {
            this.key = key;
        }

        Node(K key, Node<K> next) {
            this.key = key;
            this.next = next;
        }
    }
    static class Stack<K> {
        Node<K> top = null;
        //Stack<K>(){}

        void push(K key) {
            Node<K> node = new Node<K>(key);
            node.next = this.top;
            this.top = node;
        }

        K pop() {
            K key = this.top.key;
            this.top = this.top.next;
            return key;
        }

        boolean isEmpty() {
            return top == null;
        }

        K peek() {
            return top.key;
        }
    }

    public static char[][] generateVisual(int w, int h) {
        int realH = 2*h + 1;
        int realW = 2*w + 1;
        int[][] visited = new int[w][h];
        char[][] map = new char[realW][realH];
        StdDraw.setCanvasSize(10*realW, 10*realH);
        StdDraw.setXscale(0, realW);
        StdDraw.setYscale(0, realH);
        StdDraw.enableDoubleBuffering();
        for (int i = 0; i < realW; i++) // all map is block
            for (int j = 0; j < realH; j++) {
                map[i][j] = '#';
                StdDraw.filledSquare(i+.5, j+.5, .5);
            }
        StdDraw.show();
        StdDraw.pause(PAUSE_TIME);
        return recursiveExploreVisual(map, visited);
    }

    static char[][] recursiveExploreVisual(char[][] map, int[][] visited) {
        Stack<int[]> stack = new Stack<>();
        int[] start = new int[]{0,0};
        stack.push(start);
        exploreVisual(map, visited, stack);
        StdDraw.show();
        return map;
    }

    static void exploreVisual(char[][] map, int[][] visited, Stack<int[]> stack) {
        while (!stack.isEmpty()) {
            StdDraw.show();
            StdDraw.pause(PAUSE_TIME);
            if (stack.isEmpty()) return;
            int[] now = stack.peek();
        
            visited[now[0]][now[1]] = 1;
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.filledSquare(2*now[0]+1.5, 2*now[1]+1.5, .5);
            map[2*now[0]+1][2*now[1]+1] = '.';
            if (!isDeadEnd(visited, now[0], now[1])) {
                int dir;
                do {
                    dir = StdRandom.uniform(4);
                } while (!isValid(visited, now[0], now[1], dir));

                int[] next;
                if (dir == up) {
                    map[2*now[0]][2*now[1]+1] = '.';
                    StdDraw.filledSquare(2*now[0]+.5, 2*now[1]+1.5, .5);
                    next = new int[]{now[0] -1, now[1]};
                } else if (dir == right) {
                    map[2*now[0]+1][2*now[1]+2] = '.';
                    StdDraw.filledSquare(2*now[0]+1.5, 2*now[1]+2.5, .5);
                    next = new int[]{now[0] , now[1]+1};
                } else if (dir == down) {
                    map[2*now[0]+2][2*now[1]+1] = '.';
                    StdDraw.filledSquare(2*now[0]+2.5, 2*now[1]+1.5, .5);
                    next = new int[]{now[0] +1, now[1]};
                } else {
                    map[2*now[0]+1][2*now[1]] = '.';
                    StdDraw.filledSquare(2*now[0]+1.5, 2*now[1]+.5, .5);
                    next = new int[]{now[0] , now[1]-1};
                }
                stack.push(next);
                StdDraw.show();
                StdDraw.pause(PAUSE_TIME);
                //exploreVisual(map, visited, stack);
            }
            else stack.pop();
        }
        return;
    }

    public static void imperfectIt(char[][] map, double rate) {
        // makes a perfect maze imperfect
        int w = map.length;
        int h = map[0].length;
        // we just need to roll a coin for the space right and up. Be careful with the last line
        for (int i = 1; i < w-1; i+=2) {
            for (int j = 1; j < h-1; j+=2) {
                assert map[i][j] != '#';
                if (i < w-2 &&  map[i+1][j] == '#')  
                    map[i+1][j] = StdRandom.uniform() < rate ? '.' : '#';
                if (j < h-2 && map[i][j+1] == '#')  
                    map[i][j+1] = StdRandom.uniform() < rate ? '.' : '#';
            }
        }
    }

    public static void main(String[] args) {
        int w = Integer.parseInt(args[0]);
        int h = Integer.parseInt(args[1]);
        
        /* if want visualisation of the exploration, uncomment the generate visual and
             comment the 7 lines below it
           */
        char[][] map = generateVisual(w, h); 
         
        /* char[][] map = generate(w, h);
        int realW = map.length;
        int realH = map[0].length;
        StdDraw.setCanvasSize(10*realW, 10*realH);
        StdDraw.setXscale(0, realW);
        StdDraw.setYscale(0, realH);
        StdDraw.enableDoubleBuffering(); */

        w = map.length;
        h = map[0].length;
        printMap(map);
        StdDraw.pause(1000);
        if (args.length > 3) {
            Out out = new Out(args[3]);
            out.println(w);
            out.println(h);
            for (int j = h-1; j >= 0; j--) {
                for (int i = 0; i < w; i ++) {
                    out.print(map[i][j]);
                }
                out.println();
            }
        }
        if (args.length > 2) {
            imperfectIt(map, Double.parseDouble(args[2]));
            if (args.length > 4) {
                StdDraw.clear();
                Out out2 = new Out(args[4]);
                out2.println(map.length);
                out2.println(map[0].length);            
                for (int j = h-1; j >= 0; j--) {
                    for (int i = 0; i < w; i ++) {
                        out2.print(map[i][j]);
                    }
                    out2.println();
                }
            }
            printMap(map);
        }
    }
}
