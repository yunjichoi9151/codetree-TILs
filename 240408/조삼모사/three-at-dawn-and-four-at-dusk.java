import java.io.*;
import java.util.*;

public class Main {
    static int[][] arr;
    static boolean[] visited;
    static int answer = Integer.MAX_VALUE;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        arr = new int[n][n];
        int[] num = new int[n];
        for(int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int j = 0; j < n; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
            num[i] = i;
        }
        visited = new boolean[n];
        combination(num, visited, 0, n, n / 2);
        System.out.println(answer);
    }

    static void combination(int[] num, boolean[] visited, int start, int n, int r) {
        if(r == 0) {
            result(num, visited, n);
            return;
        }
        for(int i = start; i < n; i++) {
            visited[i] = true;
            combination(num, visited, i + 1, n, r - 1);
            visited[i] = false;
        }
    }

    static void result(int[] num, boolean[] visited, int n) {
        int sum1 = 0, sum2 = 0;
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(visited[i] && visited[j]) sum1 += arr[i][j];
                else if(!visited[i] && !visited[j]) sum2 += arr[i][j];
            }
        }
        answer = Math.min(answer, Math.abs(sum1 - sum2));
    }
}