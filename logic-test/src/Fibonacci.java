import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Fibonacci {
    public static int[] generateFibonacci(int count) {
        int[] result = new int[count];
        for (int i = 0; i < count; i++) {
            if (i == 0) result[0] = 0;
            else if (i == 1) result[1] = 1;
            else result[i] = result[i - 1] + result[i - 2];
        }
        return result;
    }
    public static boolean isFibonacci(int num) {
        List<Integer> result = new ArrayList<>(); // dynamic sized

        int i = 0;
        int lastVal = 0;
        while (lastVal < num) {
            if (i == 0) result.add(0, 0);
            else if (i == 1) result.add(0, 1);
            else {
                int val = result.get(i - 1) + result.get(i - 2);
                result.add(i, val);
                lastVal = val;
            }
            i++;
        }
        return lastVal == num;
    }

    public static void main(String[] args) {
        int count = 10;

        // 0, 1, 1, 2, 3, 5, 8, 13, 21, 34, ...
        System.out.println(Arrays.toString(generateFibonacci(count)));

        int num = 34;
        System.out.println(isFibonacci(num) ? num + " adalah fibonacci" : num + " bukanlah fibonacci");
        num = 35;
        System.out.println(isFibonacci(num) ? num + " adalah fibonacci" : num + " bukanlah fibonacci");
        num = -1;
        System.out.println(isFibonacci(num) ? num + " adalah fibonacci" : num + " bukanlah fibonacci");
        num = 8;
        System.out.println(isFibonacci(num) ? num + " adalah fibonacci" : num + " bukanlah fibonacci");
    }
}
