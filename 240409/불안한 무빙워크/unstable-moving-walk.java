import java.io.*;
import java.util.*;

public class Main {
    static class Node {
        int stability;
        boolean occupied;
    
        public Node(int stability, boolean occupied) {
            this.stability = stability;
            this.occupied = occupied;
        }
    }
    public static final int MAX_N = 100;
    public static int n, k;
    public static Node[] u = new Node[MAX_N];
    public static Node[] d = new Node[MAX_N];
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < n; i++) {
            int stability = Integer.parseInt(st.nextToken());
            u[i] = new Node(stability, false);
        }
        for(int i = 0; i < n; i++) {
            int stability = Integer.parseInt(st.nextToken());
            d[i] = new Node(stability, false);
        }
        
        int answer = 0;
        while(!done()) {
            simulate();
            answer++;
        }
        
        System.out.print(answer);
    }

    public static void simulate() {
        shift();
        moveAll();
        add();
        boolean occupied = u[n - 1].occupied;
        if(occupied)
            move(n - 1);
    }
    
    public static void shift() {
        Node temp = u[n - 1];
        for(int i = n - 1; i >= 1; i--)
            u[i] = u[i - 1];
        u[0] = d[n - 1];
        
        for(int i = n - 1; i >= 1; i--)
            d[i] = d[i - 1];
        d[0] = temp;
    }
    
    public static void moveAll() {
        for(int i = n - 1; i >= 0; i--) {
            if(u[i].occupied && canGo(i + 1))
                move(i);
        }
    }
    
    public static void add() {
        if(u[0].stability > 0 && !u[0].occupied)
            u[0] = new Node(u[0].stability - 1, true);
    }
    
    public static boolean canGo(int idx) {
        if(idx == n)
            return true;
        return u[idx].stability > 0 && !u[idx].occupied; 
    }
    
    public static void move(int idx) {
        int currStability = u[idx].stability;
        u[idx] = new Node(currStability, false);
        if(idx + 1 < n) {
            u[idx + 1] = new Node(u[idx + 1].stability - 1, true);
        }
    }
    
    public static boolean done() {
        int unstableCnt = 0;
        for(int i = 0; i < n; i++) {
            if(u[i].stability == 0) unstableCnt++;
        }
        for(int i = 0; i < n; i++) {
            if(d[i].stability == 0) unstableCnt++;
        }
        
        return unstableCnt >= k;
    }
}