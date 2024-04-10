import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
	static class Knight {
		int r;
		int c;
		int h;
		int w;
		int k;
		int damage;

		Knight() {

		}

		Knight(String[] str) {
			r = stoi(str[0]) - 1;
			c = stoi(str[1]) - 1;
			w = stoi(str[2]);
			h = stoi(str[3]);
			k = stoi(str[4]);
		}
	}

	static Knight[] knights;
	static String[] inputs;
	static int L, N, Q;
	static int[][] map, knightMap;

	final static int BLANK = 0;
	final static int TRAP = 1;
	final static int WALL = 2;

	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		inputs = in.readLine().split(" ");
		L = Integer.parseInt(inputs[0]);
		N = Integer.parseInt(inputs[1]);
		Q = Integer.parseInt(inputs[2]);

		map = new int[L][L];
		knightMap = new int[L][L];
		for (int i = 0; i < L; ++i) {
			inputs = in.readLine().split(" ");
			for (int j = 0; j < L; ++j) {
				map[i][j] = stoi(inputs[j]);
			}
		}
		knights = new Knight[N + 1];
		knights[0] = new Knight();
		for (int i = 1; i <= N; ++i) {
			knights[i] = new Knight(in.readLine().split(" "));
			for (int x = 0; x < knights[i].w; ++x) {
				for (int y = 0; y < knights[i].h; ++y) {
					knightMap[x + knights[i].r][y + knights[i].c] = i;
				}
			}
		}

		for (int i = 0; i < Q; ++i) {
			inputs = in.readLine().split(" ");
			int selected = stoi(inputs[0]);
			int dir = stoi(inputs[1]);
			simulation(selected, dir);
		}

		int sum = 0;
		for (Knight iter : knights)
			if (iter.k > iter.damage)
				sum += iter.damage;

		System.out.println(sum);
	}

	// select 번호의 기사가 dir 방향으로 이동한다.
	static int[] dx = {-1, 0, 1, 0};
	static int[] dy = {0, 1, 0, -1};

	private static void simulation(int selected, int dir) {
		// 해당 기사는 죽었음.
		if (knights[selected].damage >= knights[selected].k)
			return;

		if (!canMove(selected, dir))
			return;

		move(selected, dir, true);

	}

	private static void move(int selected, int dir, boolean isHero) {
		boolean flag = false;
		int baseX = knights[selected].r;
		int baseY = knights[selected].c;
		for (int i = 0; i < knights[selected].w; ++i) {
			for (int j = 0; j < knights[selected].h; ++j) {
				int x = knights[selected].r + i;
				int y = knights[selected].c + j;
				int nextX = x + dx[dir];
				int nextY = y + dy[dir];

				// 범위 밖은 이동 불가
				if (!isInRange(nextX, nextY))
					return;

				// 다음이 벽이다. 이동불가
				if (map[nextX][nextY] == WALL)
					return;

				int nearByPlayer = knightMap[nextX][nextY];

				// 이동한 칸에 다른 기사가 있는 경우
				if (nearByPlayer > 0 && nearByPlayer != selected) {
					move(nearByPlayer, dir, false);
				}

				if (map[nextX][nextY] == TRAP && !isHero)
					knights[selected].damage++;

				// 현재칸 비우기 및 최초 1회 기준점 옮기기
				knightMap[x][y] = 0;
				if (!flag) {
					flag = true;
					baseX = baseX + dx[dir];
					baseY = baseY + dy[dir];
				}
			}
		}
		knights[selected].r = baseX;
		knights[selected].c = baseY;

		for (int i = 0; i < knights[selected].w; ++i) {
			for (int j = 0; j < knights[selected].h; ++j) {
				int x = knights[selected].r + i;
				int y = knights[selected].c + j;
				if (knights[selected].k > knights[selected].damage)
					knightMap[x][y] = selected;
				else
					knightMap[x][y] = 0;
			}
		}
	}

	public static boolean canMove(int selected, int dir) {
		boolean flag = true;
		for (int i = 0; i < knights[selected].w; ++i) {
			for (int j = 0; j < knights[selected].h; ++j) {
				int nextX = knights[selected].r + i + dx[dir];
				int nextY = knights[selected].c + j + dy[dir];

				// 범위 밖은 이동 불가
				if (!isInRange(nextX, nextY))
					return false;

				// 다음이 벽이다. 이동불가
				if (map[nextX][nextY] == WALL)
					return false;

				int nearByPlayer = knightMap[nextX][nextY];

				// 이동한 칸에 다른 기사가 있는 경우
				if (nearByPlayer > 0 && nearByPlayer != selected) {
					// 다음 칸의 플레이어가 이동 불가능이다.
					if (!canMove(nearByPlayer, dir))
						return false;
				}
			}
		}
		return flag;
	}

	public static boolean isInRange(int x, int y) {
		if (0 <= x && x < L && 0 <= y && y < L)
			return true;
		return false;
	}

	public static int stoi(String s) {
		return Integer.parseInt(s);
	}
}