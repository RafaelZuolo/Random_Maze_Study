import edu.princeton.cs.algs4.*;

public class AVL<Key extends Comparable<Key>, Value> {
    private Node root;             
    
    private class Node {
        private final Key key;       
        private Value val;
        private int height = 1;
        private Node left, right;  
        
        public Node(Key key, Value val) {
            this.key = key;
            this.val = val;
        }
        
        public String toString() {
            return key.toString();
        }
    }
    
    public AVL() {}
    
    public boolean isEmpty() {
        return root == null;
    }
   
    /* Retorna o valor correspondente a uma dada chave */
    public Value get(Key key){
        return get(root, key);
    }
    private Value get(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        else              return x.val;
    }
    
    /* retorna TRUE se a chave key contém um valor na tabela de símbolos */
    public boolean contains(Key key) {
        return get(key) != null;
    }
    
    /* Imprime os elementos da tabela de símbolo por ordem de chaves */
    public void inOrder() {
        inOrder(root);
    }
    
    private void inOrder(Node x) {
        if (x == null) return; 
        inOrder(x.left); 
        StdOut.print(x.key + " "); 
        inOrder(x.right); 
    }
    
    public void inOrderNicer() {
        inOrderNicer(root, 0);
        //StdOut.println();
    }
    
    private void inOrderNicer(Node x, int h) { 
        if (x == null) return; 
        StringBuffer s = new StringBuffer();
        for (int i = 0; i < h; i++) s.append("     |");     
        inOrderNicer(x.right,  h + 1);
        StdOut.println(s.toString() + x.key + " " + x.height); 
        inOrderNicer(x.left, h + 1); 
    }
    
/**************************************************************************************************/
/*                                                                                                */
/*             Metodos para manutencao da propriedade AVL                                         */
/*                                                                                                */
/**************************************************************************************************/
    
    /* retorna a diferenca de altura entre a subarvore esquerda e direita */
    private int diff(Node x) {
        if (x == null) return 0;
        return height(x.left) - height(x.right);
    }
    
    /* Retorna a altura de um noh, cuidando do caso null */
    private int height(Node x) {
        if (x==null) return 0;
        else         return x.height;
    }
    /*Gira o noh x para  a esquerda e retorna a nova raíz*/
    private Node leftRotate(Node x) {
        Node y = x.right;
        x.right = y.left;
        y.left = x;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        return y;
    }
    
    /*Gira o noh x para  a esquerda e retorna a nova raíz*/
    private Node rightRotate(Node x) {
        Node y = x.left;
        x.left = y.right;
        y.right = x;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        return y;
    }
    /* Faz o zig-zag que termina com o x na esquerda e retorna a nova raiz*/
    private Node leftDoubleRotate(Node x) {
        x.right = rightRotate(x.right);
        x = leftRotate(x);
        return x;
    }
    
    /* Faz o zig-zag que termina com o x na direita e retorna a nova raiz*/
    private Node rightDoubleRotate(Node x) {
        x.left = leftRotate(x.left);
        x = rightRotate(x);
        return x;
    }
    
    /* estuda o diff de um noh e decide como deve ser feita a correcao. Retorna a nova raiz */
    private Node fix(Node x) {
        // caso balanceado
        if (Math.abs(diff(x)) <= 1) return x;
        
        // caso que um leftRotate resolve: diff(x) == -2 e diff(x.right) == -1 
        if (diff(x) == -2 && diff(x.right) <= 0)     
            return leftRotate(x);
        
        // caso que um leftDoubleRotate resolve: diff(x) == -2 e diff (x.right) == 1
        if (diff(x) == -2 && diff(x.right) >= 0)     
            return leftDoubleRotate(x);
        
        // caso que um rightRotate resolve: diff(x) == 2 e diff(x.right) == 1 
        if (diff(x) == 2 && diff(x.left) >= 0)     
            return rightRotate(x);
        
        // caso que um rightDoubleRotate resolve: diff(x) == 2 e diff(x.right) == - 
        if (diff(x) == 2 && diff(x.left) <= -1)     
            return rightDoubleRotate(x);
        System.out.println("ALERT ALERT ALERT ALERT ALERT ALERT ALERT ALERT you have big problems");
        return x;
    }

/**************************************************************************************************/
/*             Metodos normais de ABB com a manutencao de AVL                                     */
/**************************************************************************************************/

    /* Insercao */
    public Node put(Key key, Value val) {
        root = put(root, key, val);
        return root;
    }    
    private Node put(Node x, Key key, Value val) {
        if (x == null) return new Node(key, val);
        // insercao normal
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = put(x.left, key, val);
        } else if (cmp > 0) {
            x.right = put(x.right, key, val);
        } else {
            x.val = val;
        }
        // acerto das alturas
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        x = fix(x);
        return x;
    }
    
    /* retorna o valor da menor chave */
    public Value min() {
        return min(root).val;
    }
    private Node min(Node x) {
        if (x.left == null) return x;
        else                return min(x.left);
    } 
    
    /* Remove elemento com menor chave */
    public Value removeMin() {
        Value min = min();
        root = removeMin(root);
        return min;
    }
    /* Remove elemento com menor chave na arvore e retorna a nova raiz */
    private Node removeMin(Node x) {
        if (x.left == null) return x.right;
        // caso nao for o noh minimo, chamamos recursivamente e consertamos a altura
        x.left = removeMin(x.left);
        x.height = Math.max(height(x.left),height(x.right)) + 1;
        x = fix(x);
        return x;
    }
     
    /* Remove o nó com chave "key" e retorna o valor correspondente a essa chave 
    
 ******** OBS: O seu algoritmo DEVE ser recursivo ********
    */
    public Value remove(Key key) {
        Value val = get(key);
        root = remove(root, key);
        return val;
    }
    /* remove o noh com chave key e retorna a nova raiz */
    private Node remove(Node x, Key key) {
        if      (x == null) return null;
        if      (key.compareTo(x.key) < 0) x.left  = remove(x.left,  key);
        else if (key.compareTo(x.key) > 0) x.right = remove(x.right, key);
        else {
            if (x.left  == null) return x.right;
            if (x.right == null) return x.left;
            // caso chato: precisamos encontrar o sucessor de x e trocar eles
            Node y = x;
            x = min(x.right); // x eh o sucessor
            x.right = removeMin(y.right); // acerta o filho direito do sucessor ao mesmo tempo que remove ele da subarvore direita
            // observe que o removeMin já corrige a propriedade AVL de t.right
            x.left = y.left;  // acerta o filho esquerdo do sucessor
        }
        // agora basta acertar a altura de x, consertar a propriedade AVL e retornamos ele
        x.height = Math.max(height(x.left),height(x.right)) + 1;
        x = fix(x);
        return x;
    }


    
    /* Remove todos os nós da árvore */
    public void clean(){
        clean(root);
        root = null;
    }
    private void clean(Node x){
        if (x == null) return;
        clean(x.left);
        x.left = null;
        clean(x.right);
        x.right = null;
    }


    public boolean isAVL() {
        return isAVL(root);
    }
    
    private boolean isAVL(Node x) {
        if (x == null) return true;
        //StdOut.println(x + " " + (diff(x)));
        if (Math.abs(diff(x)) > 1) return false;
        return isAVL(x.left) && isAVL(x.right);
    }
/***************************************************************************/
/*               Inicio dos métodos das simulações                                     */
/***************************************************************************/
    /* retorna o tempo de insercao de n pares chave valor com valores em [n]*/
    public static long treeTest(int n, String type, AVL<Integer,Integer> st) {
        boolean crescente = type.equals("crescente");
        int[] permutacao = new int[n];
        if (type.equals("aleatorio")) permutacao = StdRandom.permutation(n);
        else {
            for (int i = 0; i < n; i++) {
                if (crescente) permutacao[i] = i;
                else           permutacao[i] = n-i-1;
            }
        }
        long t0 = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            st.put(permutacao[i], i);
        }
        long t1 = System.currentTimeMillis();
        return t1 - t0;
    }
    public int treeHeight() {
        if (root ==null) return 0;
        return root.height;
    }
    
    public static void main(String[] args) {
	/* faça as simulações */
        //AVL<Integer, Integer> st = new AVL<>();
        /***************************************************************************/
        /*               Inicio das simulações                                     */
        /***************************************************************************/
        //int N = Integer.parseInt(args[0]);
        /*
        int N = 1000000;
        StdOut.printf("\n\nInicio da simulação:\n\n");
        StdOut.printf("    n    |     tipo    |  tempo  |  altura  \n");
        for (int i = 100; i <= N; i*=10) {
            long t = treeTest(i, "crescente", st);
            StdOut.printf(" %7d | %11s | %6d  | %6d \n",i, "crescente", t, st.treeHeight());
            if (!st.isAVL()) {StdOut.println("YOU HAVE BIG PROBLEMS"); break;}
            st.clean();
            t = treeTest(i, "decrescente", st);
            StdOut.printf(" %7d | %11s | %6d  | %6d \n",i, "decrescente", t, st.treeHeight());
            if (!st.isAVL()) {StdOut.println("YOU HAVE BIG PROBLEMS"); break;}
            st.clean();
            t = treeTest(i, "aleatorio", st);
            StdOut.printf(" %7d | %11s | %6d  | %6d \n",i, "aleatorio", t, st.treeHeight());
            if (!st.isAVL()) {StdOut.println("YOU HAVE BIG PROBLEMS"); break;}
            st.clean();
        }
        StdOut.printf("\n\nInicio da simulação de médias de 10 execuções:\n\n");
        StdOut.printf("    n    |     tipo    |  tempo  |  altura  \n");
        for (int i = 100; i <= N; i*=10) {
            long t=0;
            int height=0;
                for (int j = 0; j < 10; j++) {
                t += treeTest(i, "crescente", st);
                height += st.treeHeight();
                if (!st.isAVL()) {StdOut.println("YOU HAVE BIG PROBLEMS"); break;}
                st.clean();
            }
            StdOut.printf(" %7d | %11s | %6.3g  | %6.3g \n",i, "crescente", t/10., height/10.);
        }
        for (int i = 100; i <= N; i*=10) {
            long t=0;
            int height=0;
                for (int j = 0; j < 10; j++) {
                t += treeTest(i, "decrescente", st);
                height += st.treeHeight();
                if (!st.isAVL()) {StdOut.println("YOU HAVE BIG PROBLEMS"); break;}
                st.clean();
            }
            StdOut.printf(" %7d | %11s | %6.3g  | %6.3g \n",i, "decrescente", t/10., height/10.);
        }
        for (int i = 100; i <= N; i*=10) {
            long t=0;
            int height=0;
                for (int j = 0; j < 10; j++) {
                t += treeTest(i, "aleatorio", st);
                height += st.treeHeight();
                if (!st.isAVL()) {StdOut.println("YOU HAVE BIG PROBLEMS"); break;}
                st.clean();
            }
            StdOut.printf(" %7d | %11s | %6.3g  | %8.3f \n",i, "aleatorio", t/10., height/10.);
        }
        StdOut.printf("\n\nInicio da simulação de médias mas dobrando :\n\n");
        StdOut.printf("    n    |     tipo    |  tempo  |  altura  \n");
        for (int i = 100; i <= N; i*=2) {
            long t=0;
            int height=0;
                for (int j = 0; j < 10; j++) {
                t += treeTest(i, "aleatorio", st);
                height += st.treeHeight();
                if (!st.isAVL()) {StdOut.println("YOU HAVE BIG PROBLEMS"); break;}
                st.clean();
            }
            StdOut.printf(" %7d | %11s | %6.3g  | %8.3f \n",i, "aleatorio", t/10., height/10.);
        }*/
        AVL<String, String> st = new AVL<>();
        In in = new In(args[0]);
        while(!in.isEmpty()) {
            String s = in.readString();
            st.put(s, s);
            if (!st.isAVL()) {
                StdOut.println("ALERT ALERT ALERT ALERT ALERT ALERT error inserting");
                st.inOrderNicer();
            }
        }
        StdOut.println(st.isAVL());
        st.inOrderNicer();
        do {
            String s;
            StdOut.print("\nDigite palavra a ser removida: ");
            s = StdIn.readString();
            if (s.equals("-")) break;
            StdOut.println(st.remove(s));
            if (!st.isAVL()) {StdOut.println("NOT VALID"); return;}
            else {StdOut.println("OK, type smth:"); StdIn.readString();}
            st.inOrderNicer();
        } while (true);
        st.clean();
        StdOut.println(st.isAVL());
        st.inOrderNicer();
        while(!st.isEmpty()) {
            StdOut.println("\nO removeMin eh: " + st.removeMin());
            StdOut.println("Ainda é AVL? " + st.isAVL());
            st.inOrderNicer();
        }
    }
}
