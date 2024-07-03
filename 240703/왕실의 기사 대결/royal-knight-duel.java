import java.io.*;
import java.util.*;

public class Main {
    static class Knight {
        int idx, x, y, h, w, k, damage;
        public Knight(int idx, int x, int y, int h, int w, int k, int damage) {
            this.idx = idx;
            this.x = x;
            this.y = y;
            this.h = h;
            this.w = w;
            this.k = k;
            this.damage = damage;
        }
    }
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, 1, 0, -1};
    static int L, N;
    static int[][] map, mapNow;
    static boolean[] visited;
    static Knight[] knights;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        L = stoi(st.nextToken());
        N = stoi(st.nextToken());
        int Q = stoi(st.nextToken());
        map = new int[L][L];
        mapNow = new int[L][L];
        for(int i = 0; i < L; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < L; j++) {
                map[i][j] = stoi(st.nextToken());
            }
        }
        knights = new Knight[N + 1];
        for(int t = 1; t <= N; t++) {
            st = new StringTokenizer(br.readLine());
            Knight newKnight = new Knight(t, stoi(st.nextToken()) - 1, stoi(st.nextToken()) - 1, stoi(st.nextToken()), stoi(st.nextToken()), stoi(st.nextToken()), 0);
            knights[t] = newKnight;
            for(int i = newKnight.x; i < newKnight.x + newKnight.h; i++) {
                for(int j = newKnight.y; j < newKnight.y + newKnight.w; j++) {
                    mapNow[i][j] = newKnight.idx;
                }
            }
        }
        int answer = 0;
        for(int t = 0; t < Q; t++) {
            st = new StringTokenizer(br.readLine());
            int idx = stoi(st.nextToken());
            int dir = stoi(st.nextToken());
            if(canMove(idx, dir)) {
                move(idx, dir);
            }
        }
        for(int t = 1; t <= N; t++) {
            if(knights[t] != null) answer += knights[t].damage;
        }
        System.out.println(answer);
    }

    static boolean canMove(int idx, int dir) {
        Queue<Knight> que = new LinkedList<>();
        visited = new boolean[N + 1];
        que.add(knights[idx]);
        visited[idx] = true;
        while(!que.isEmpty()) {
            Knight node = que.poll();
            if(dir % 2 == 0) {
                int nx = (dir == 0) ? (node.x - 1) : (node.x + node.h);
                for(int j = node.y; j < node.y + node.w; j++) {
                    if(nx < 0 || nx >= L || map[nx][j] == 2) {
                        return false;
                    } else if(visited[mapNow[nx][j]]) {
                        continue;
                    } else if(mapNow[nx][j] != 0 && knights[mapNow[nx][j]] != null) {
                        que.add(knights[mapNow[nx][j]]);
                        visited[mapNow[nx][j]] = true;
                    }
                }
            } else {
                int ny = (dir == 3) ? (node.y - 1) : (node.y + node.w);
                for(int i = node.x; i < node.x + node.h; i++) {
                    if(ny < 0 || ny >= L || map[i][ny] == 2) {
                        return false;
                    } else if(visited[mapNow[i][ny]]) {
                        continue;
                    } else if(mapNow[i][ny] != 0 && knights[mapNow[i][ny]] != null) {
                        que.add(knights[mapNow[i][ny]]);
                        visited[mapNow[i][ny]] = true;
                    }
                }
            }
        }
        return true;
    }

    static void move(int idx, int dir) {
        int[][] tmpMap = new int[L][L];
        for(int i = 0; i < L; i++) {
            for(int j = 0; j < L; j++) {
                int nowIdx = mapNow[i][j];
                if(visited[nowIdx]) {
                    tmpMap[i + dx[dir]][j + dy[dir]] = nowIdx;
                    if(map[i + dx[dir]][j + dy[dir]] == 1 && nowIdx != idx) {
                        knights[nowIdx].k--;
                        knights[nowIdx].damage++;
                        if(knights[nowIdx].k <= 0) {
                            knights[nowIdx] = null;
                        }
                    }
                } else if(mapNow[i][j] != 0) {
                    tmpMap[i][j] = mapNow[i][j];
                }
            }
        }
        updateMap(tmpMap);
    }

    static void updateMap(int[][] tmpMap) {
        for(int i = 0; i < L; i++) {
            for(int j = 0; j < L; j++) {
                if(tmpMap[i][j] != 0 && knights[tmpMap[i][j]] != null) {
                    mapNow[i][j] = tmpMap[i][j];
                }
            }
        }
    }

    static int stoi(String S) {
        return Integer.parseInt(S);
    }
}