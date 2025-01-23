public class AddTwoNumbers {

    static class ListNode {
        Integer val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
        int size() {
            int elementSize = 1;
            if (next != null) {
                ListNode ptr = next;
                while (ptr != null) {
                    elementSize++;
                    ptr = ptr.next;
                }
            }
            return elementSize;
        }
        void print() {
            int[] result = new int[this.size()];
            int idx = 0;
            result[idx] = val;

            if (next != null) {
                ListNode ptr = next;
                while (ptr != null) {
                    idx++;
                    result[idx] = ptr.val;
                    ptr = ptr.next;
                }
            }
            this.reverseArrayPrint(result);
        }
        private void reverseArrayPrint(int[] arr) {
            for (int i = arr.length - 1; i >= 0; i--) System.out.print(arr[i] + " ");
        }
        void normalize() {
            if (next != null) {
                ListNode ptr = next;
                while (ptr != null) {
                    String strVal = ptr.val.toString();
                    if (strVal.length() > 1) {
                        int lastValue = Integer.parseInt(String.valueOf(strVal.charAt(strVal.length() - 1)));
                        int addedVal = Integer.parseInt(strVal.substring(0, strVal.length() - 1));
                        ptr.val = lastValue;
                        if (ptr.next != null) ptr.next.val = ptr.next.val + addedVal;
                        else {
                            ListNode next = new ListNode(addedVal, ptr);
                            ptr.next = next;
                        }
                    }
                    System.out.println(strVal);

                    // continue
                    ptr = ptr.next;
                }
            }
        }
    }

    public static void sumList(ListNode list1, ListNode list2) {
        ListNode result = null;
        ListNode a, b;
        int maxLoop = 0;
        if (list1.size() >= list2.size()) {
            a = list1;
            b = list2;
            maxLoop = list1.size();
        }
        else {
            a = list2;
            b = list1;
            maxLoop = list2.size();
        }

        System.out.print("List pertama: ");
        list1.print();

        System.out.print("\nList kedua: ");
        list2.print();

        System.out.println("\n----------------------------------------[+]");

//        Input: l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
//        Output: [8,9,9,9,0,0,0,1]
        for (int i = 0; i < maxLoop; i++) {
            Integer safeA = a != null? a.val : 0;
            Integer safeB = b != null? b.val : 0;

            ListNode temp = new ListNode(safeA + safeB);
            if (result != null) {
                result.next = temp;
            }
            else result = temp;

            a = a != null ? a.next: null;
            b = b != null ? b.next: null;
        }

        System.out.print("Hasil: ");
        result.print();
    }
    public static void main(String[] args) {
        // Contoh 1:
        System.out.println("Contoh 1: ");
        ListNode list1 = new ListNode(2);
        list1 = new ListNode(4, list1);
        list1 = new ListNode(3, list1);

        ListNode list2 = new ListNode(5);
        list2 = new ListNode(6, list2);
        list2 = new ListNode(4, list2);
        sumList(list1, list2);

        // Contoh 2:
//        System.out.println("\n\nContoh 2:");
//        ListNode list3 = new ListNode(9);
//        list3 = new ListNode(9, list3);
//        list3 = new ListNode(9, list3);
//        list3 = new ListNode(9, list3);
//        list3 = new ListNode(9, list3);
//        list3 = new ListNode(9, list3);
//        list3 = new ListNode(9, list3);
//
//        ListNode list4 = new ListNode(9);
//        list4 = new ListNode(9, list4);
//        list4 = new ListNode(9, list4);
//        list4 = new ListNode(9, list4);
//        sumList(list3, list4);
    }
}
