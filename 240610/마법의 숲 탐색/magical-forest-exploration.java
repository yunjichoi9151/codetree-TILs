import java.io.*;
import java.util.*;

public class Main {
    static int[][] map, exit;
    static int R, C, answer;
    static class Fairy {
        int x, y;
        public Fairy(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    static class Golem {
        int idx, x, y, dir;
        public Golem(int idx, int x, int y, int dir) {
            this.idx = idx;
            this.x = x;
            this.y = y;
            this.dir = dir;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        R = stoi(st.nextToken()) + 3;
        C = stoi(st.nextToken());
        int K = stoi(st.nextToken());
        map = new int[R][C];
        exit = new int[R][C];
        answer = 0;
        for(int tc = 1; tc <= K; tc++) {
            st = new StringTokenizer(br.readLine());
            int y = stoi(st.nextToken()) - 1;
            int dir = stoi(st.nextToken());
            Golem g = new Golem(tc, 2, y, dir);
            GolemMove(g);
        }
        System.out.println(answer);
    }

    static void GolemMove(Golem g) {
        Queue<Golem> que = new ArrayDeque<>();
        que.add(g);
        Golem res = null;
        while(!que.isEmpty()) {
            Golem now = que.poll();
            res = now;
            if(moveDown(now)) {
                que.offer(new Golem(now.idx, now.x + 1, now.y, now.dir));
            } else if(moveLeft(now)) {
                int nDir = now.dir > 0 ? now.dir - 1 : 3;
                que.offer(new Golem(now.idx, now.x + 1, now.y - 1, nDir));
            } else if(moveRight(now)) {
                int nDir = now.dir < 3 ? now.dir + 1 : 0;
                que.offer(new Golem(now.idx, now.x + 1, now.y + 1, nDir));
            }
        }
        if(res != null) {
            if(isAllIn(res)) {
                position(res);
                FairyMove(res);
            } else {
                map = new int[R][C];
                exit = new int[R][C];
            }
        }
    }

    static void FairyMove(Golem g) {
        int[] dx = {-2, -1, 0, -1};
        int[] dy = {0, 1, 0, -1};
        int nx = g.x + dx[g.dir];
        int ny = g.y + dy[g.dir];
        exit[nx][ny] = g.idx;
        Fairy f = new Fairy(nx, ny);
        int maxDown = f.x;
        Queue<Fairy> que = new ArrayDeque<>();
        boolean[][] visited = new boolean[R][C];
        que.offer(f);
        visited[f.x][f.y] = true;
        int[] mx = {0, 0, 1, -1};
        int[] my = {1, -1, 0, 0};
        while(!que.isEmpty()) {
            Fairy now = que.poll();
            maxDown = Math.max(now.x, maxDown);
            for(int i = 0; i < 4; i++) {
                int newX = now.x + mx[i];
                int newY = now.y + my[i];
                if(isInside(newX, newY) && !visited[newX][newY]) {
                    if(exit[now.x][now.y] != 0) {
                        if(map[newX][newY] != 0) {
                            visited[newX][newY] = true;
                            que.add(new Fairy(newX, newY));
                        }
                    } else {
                        if(map[newX][newY] != 0 && (map[newX][newY] == map[now.x][now.y])) {
                            visited[newX][newY] = true;
                            que.add(new Fairy(newX, newY));
                        }
                    }
                }
            }
        }
        answer += maxDown - 2;
    }

    static boolean moveDown(Golem g) {
        int[] dx = {0, 1, 0};
        int[] dy = {-1, 0, 1};
        for(int i = 0; i < 3; i++) {
            int nx = g.x + dx[i];
            int ny = g.y + dy[i];
            if(!isInside(nx, ny) || map[nx][ny] != 0) {
                return false;
            }
        }
        return true;
    }

    static boolean moveLeft(Golem g) {
        int[] dx = {-2, -1, 0, 0, 1};
        int[] dy = {-1, -2, -2, -1, -1};
        for(int i = 0; i < 5; i++) {
            int nx = g.x + dx[i];
            int ny = g.y + dy[i];
            if(!isInside(nx, ny) || map[nx][ny] != 0) {
                return false;
            }
        }
        return true;
    }

    static boolean moveRight(Golem g) {
        int[] dx = {-2, -1, 0, 0, 1};
        int[] dy = {1, 2, 2, 1, 1};
        for(int i = 0; i < 5; i++) {
            int nx = g.x + dx[i];
            int ny = g.y + dy[i];
            if(!isInside(nx, ny) || map[nx][ny] != 0) {
                return false;
            }
        }
        return true;
    }

    static boolean isAllIn(Golem g) {
        int[] dx = {-2, -1, -1, -1, 0};
        for(int i = 0; i < 5; i++) {
            int nx = g.x + dx[i];
            if(nx < 3)
                return false;
        }
        return true;
    }

    static void position(Golem g) {
        int[] dx = {-2, -1, -1, -1, 0};
        int[] dy = {0, -1, 0, 1, 0};
        for(int i = 0; i < 5; i++) {
            int nx = g.x + dx[i];
            int ny = g.y + dy[i];
            map[nx][ny] = g.idx;
        }
    }

    static boolean isInside(int x, int y) {
        return !(x < 0 || y < 0 || x >= R || y >= C);
    }

    static int stoi(String s) {
        return Integer.parseInt(s);
    }
}