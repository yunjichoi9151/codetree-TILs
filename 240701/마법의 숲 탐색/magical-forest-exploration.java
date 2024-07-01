import java.io.*;
import java.util.*;

public class Main {
    static class Golem {
        int idx, x, y, out;
        public Golem(int idx, int x, int y, int out) {
            this.idx = idx;
            this.x = x;
            this.y = y;
            this.out = out;
        }
    }
    static class Angel {
        int x, y;
        public Angel(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, 1, 0, -1};
    static int R, C, answer;
    static int[][] map, exit;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        R = stoi(st.nextToken()) + 3;
        C = stoi(st.nextToken());
        int K = stoi(st.nextToken());
        answer = 0;
        map = new int[R][C];
        exit = new int[R][C];
        for(int tc = 1; tc <= K; tc++) {
            st = new StringTokenizer(br.readLine());
            int c = stoi(st.nextToken()) - 1;
            int d = stoi(st.nextToken());
            moveGolem(tc, c, d);
        }
        System.out.println(answer);
    }

    static void moveGolem(int idx, int y, int dir) {
        int[] tx = {-1, 0, 0, 0, 1};
        int[] ty = {0, -1, 0, 1, 0};
        int now_x = 1;
        while(true) {
            if(moveDown(now_x, y)) {
                now_x++;
            } else if(moveLeft(now_x, y)) {
                now_x++;
                y--;
                dir = (dir + 3) % 4;
            }
            else if(moveRight(now_x, y)) {
                now_x++;
                y++;
                dir = (dir + 1) % 4;
            } else {
                if(y == 0 || y == C || now_x <= 3) {
                    map = new int[R][C];
                    exit = new int[R][C];
                } else {
                    for(int i = 0; i < 5; i++) {
                        int nx = now_x + tx[i];
                        int ny = y + ty[i];
                        map[nx][ny] = idx;
                    }
                    exit[now_x + dx[dir]][y + dy[dir]] = idx;
                    moveAngel(idx, now_x + dx[dir], y + dy[dir], dir);
                }
                break;
            }
        }
    }

    static void moveAngel(int idx, int x, int y, int dir) {
        Queue<Angel> que = new LinkedList<>();
        boolean[][] visited = new boolean[R][C];
        que.add(new Angel(x, y));
        visited[x][y] = true;
        int max = 0;
        while(!que.isEmpty()) {
            Angel node = que.poll();
            max = Math.max(max, node.x);
            for(int i = 0; i < 4; i++) {
                int nx = node.x + dx[i];
                int ny = node.y + dy[i];
                if(nx < 3 || ny < 0 || nx >= R || ny >= C || visited[nx][ny]) continue;
                if((exit[node.x][node.y] != 0 && map[nx][ny] != 0) || map[nx][ny] == map[node.x][node.y]) {
                    visited[nx][ny] = true;
                    que.add(new Angel(nx, ny));
                }
            }
        }
        answer += max - 2;
    }

    static boolean moveDown(int x, int y) {
        int[] tx = {1, 2, 1};
        int[] ty = {-1, 0, 1};
        for(int i = 0; i < 3; i++) {
            int nx = x + tx[i];
            int ny = y + ty[i];
            if(nx >= R || map[nx][ny] != 0) return false;
        }
        return true;
    }

    static boolean moveLeft(int x, int y) {
        int[] tx = {-1, 0, 1, 1, 2};
        int[] ty = {-1, -2, -2, -1, -1};
        for(int i = 0; i < 5; i++) {
            int nx = x + tx[i];
            int ny = y + ty[i];
            if(nx >= R || ny < 0 || map[nx][ny] != 0) return false;
        }
        return true;
    }

    static boolean moveRight(int x, int y) {
        int[] tx = {-1, 0, 1, 1, 2};
        int[] ty = {1, 2, 2, 1, 1};
        for(int i = 0; i < 5; i++) {
            int nx = x + tx[i];
            int ny = y + ty[i];
            if(nx >= R || ny >= C || map[nx][ny] != 0) return false;
        }
        return true;
    }

    static int stoi(String s) {
        return Integer.parseInt(s);
    }
}