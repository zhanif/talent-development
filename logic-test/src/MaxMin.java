public class MaxMin {
    static void getMinMax(int[] arr) {
        int[] allSumValues = new int[arr.length];

        for (int i = 0; i < arr.length; i++) {
            int sum = 0;
            for (int j = 0; j < arr.length; j++) {
                if (i == j) continue;
                sum += arr[j];
            }
            allSumValues[i] = sum;
        }

        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int val : allSumValues) {
            max = val > max ? val : max;
            min = val < min ? val : min;
            System.out.print(val + " ");
        }
        System.out.println("\n\nMin: " + min);
        System.out.println("Max: " + max);
    }

    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5};

        getMinMax(array);
    }
}
