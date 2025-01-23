public class IndicesOfTwoNums {
    static void getIndicesOf2Nums(int[] arr, int target) {
        System.out.println("Output:");
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                if (i == j) continue;

                if (arr[i] + arr[j] == target) {
                    System.out.printf("[%d, %d] -- at index: %d, %d \n", arr[i], arr[j], i, j);
                    return;
                }
            }
        }
    }

    public static void main(String[] args) {
        getIndicesOf2Nums(new int[]{2, 7, 11, 15}, 9);
        getIndicesOf2Nums(new int[]{3, 2, 4}, 6);
        getIndicesOf2Nums(new int[]{3, 3}, 6);
    }
}
