import java.io.*;
import java.util.*;

public class Main {
    static int[][] map, new_map;
    static boolean[][] visited;
    static Queue<Integer> newNum;
    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};
    static class Node {
        int x, y, d, value;
        public Node(int x, int y, int d, int value) {
            this.x = x;
            this.y = y;
            this.d = d;
            this.value = value;
        }
    }
    static class One {
        int x, y;
        public One(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine());
        int K = stoi(st.nextToken());
        int M = stoi(st.nextToken());
        map = new int[5][5];
        for(int i = 0; i < 5; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < 5; j++) {
                map[i][j] = stoi(st.nextToken());
            }
        }
        newNum = new LinkedList<>();
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < M; i++) {
            newNum.add(stoi(st.nextToken()));
        }
        for(int i = 0; i < K; i++) {
            int result = find();
            if(result != 0) sb.append(result + " ");
            else break;
        }
        System.out.println(sb.toString().trim());
    }

    static int find() {
        Node max = new Node(0, 0, 0, 0);
        for(int j = 1; j <= 3; j++) {
            for(int i = 1; i <= 3; i++) {
                for(int d = 90; d <= 270; d += 90) {
                    // System.out.println("Now[" + i + ", " + j + ", " + d + "]");
                    rotate(i, j, d / 90);
                    // System.out.println("After Rotate");
                    // for(int a = 0; a < 5; a++) {
                    //     for(int b = 0; b < 5; b++) {
                    //         System.out.print(new_map[a][b] + " ");
                    //     }
                    //     System.out.println();
                    // }
                    int n = search();
                    // System.out.println("Value : " + n);
                    if(n > max.value || (n == max.value && d < max.d)) {
                        max = new Node(i, j, d, n);
                        // System.out.println("Update With : [" + i + ", " + j + ", " + d + "]");
                    }
                }
            }
        }
        return final_count(max);
    }

    static int search() {
        int sum = 0;
        visited = new boolean[5][5];
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                if(!visited[i][j]) {
                    int count = BFS(i, j);
                    if(count >= 3) sum += count;
                }
            }
        }
        return sum;
    }

    static void rotate(int x, int y, int t) {
        new_map = new int[5][5];
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                new_map[i][j] = map[i][j];
            }
        }
        for(int tc = 0; tc < t; tc++) {
            int[][] temp = new int[3][3];
            for(int i = 0; i < 3; i++) {
                for(int j = 0; j < 3; j++) {
                    temp[i][j] = new_map[x - 1 + i][y - 1 + j];
                }
            }
            for(int i = 0; i < 3; i++) {
                for(int j = 0; j < 3; j++) {
                    new_map[x - 1 + j][y + 1 - i] = temp[i][j];
                }
            }
        }
    }

    static int BFS(int x, int y) {
        int nowValue = 0;
        boolean[][] now_visited = new boolean[5][5];
        Queue<One> que = new LinkedList<One>();
        que.add(new One(x, y));
        now_visited[x][y] = true;
        while(!que.isEmpty()) {
            One node = que.poll();
            nowValue++;
            for(int i = 0; i < 4; i++) {
                int nx = node.x + dx[i];
                int ny = node.y + dy[i];
                if(nx < 0 || ny < 0 || nx >= 5 || ny >= 5 || new_map[nx][ny] != new_map[x][y] || visited[nx][ny] || now_visited[nx][ny]) continue;
                que.add(new One(nx, ny));
                now_visited[nx][ny] = true;
            }
        }
        if(nowValue >= 3) {
            for(int i = 0; i < 5; i++) {
                for(int j = 0; j < 5; j++) {
                    if(now_visited[i][j]) visited[i][j] = true;
                }
            }
        }
        return nowValue;
    }

    static int final_count(Node node) {
        int value = 0;
        rotate(node.x, node.y, node.d / 90);
        while(true) {
            int now_value = search();
            if(now_value == 0) break;
            else value += now_value;
            for(int j = 0; j < 5; j++) {
                for(int i = 4; i >= 0; i--) {
                    if(visited[i][j] && !newNum.isEmpty()) {
                        new_map[i][j] = newNum.poll();
                    }
                }
            }
        }
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                map[i][j] = new_map[i][j];
            }
        }
        return value;
    }

    static int stoi(String S) {
        return Integer.parseInt(S);
    }
}