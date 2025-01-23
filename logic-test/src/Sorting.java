public class Sorting {
    public static void sort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                if (arr[j] > arr[i]) {
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }

        for(int num : arr) System.out.print(num + " ");
    }

    public static void main(String[] args) {
        int[] arr = {20, 15, 43, 60, 17};

        sort(arr);
    }
}
