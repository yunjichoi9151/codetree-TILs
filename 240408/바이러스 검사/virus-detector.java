import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] arr = new int[n];
        int[] canDo = new int[2];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }
        st = new StringTokenizer(br.readLine());
        canDo[0] = Integer.parseInt(st.nextToken());
        canDo[1] = Integer.parseInt(st.nextToken());
        long answer = 0;
        for(int i = 0; i < n; i++) {
            if(arr[i] > canDo[0]) {
                arr[i] -= canDo[0];
                answer += arr[i] / canDo[1] + (arr[i] % canDo[1] == 0 ? 1 : 2);
            } else {
                answer++;
            }
        }
        System.out.println(answer);
    }
}