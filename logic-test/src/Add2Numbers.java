public class Add2Numbers {
    static class List {
        int value;
        List next;

        List() {}
        List(int value, List next) {
            this.value = value;
            this.next = next;
        }
        List(int value) {
            this.value = value;
        }

        void print() {
            List ptr = this;
            while (true) {
                System.out.print(ptr.value + " ");
                if (ptr.next != null) ptr = ptr.next;
                else break;
            }
            System.out.println();
        }
        int size() {
            List ptr = this;
            int elementSize = 0;
            while (true) {
                elementSize++;
                if (ptr.next != null) ptr = ptr.next;
                else break;
            }
            return elementSize;
        }
//        List reverse() {
//            List prev = null;
//            List cur = this;
//            List next = this.next;
//            while (cur != null) {
//                cur.next = prev;
//                prev = cur;
//                cur = next;
//                next = next != null ? next.next : null;
//            }
//            return prev;
//        }
        void normalize() {
            List retVal = this;
            while (retVal != null) {
                int tempValue = retVal.value;
                if (tempValue > 9) {
                    retVal.value = tempValue % 10;
                    if (retVal.next != null) retVal.next.value += tempValue / 10;
                    else {
                        List temp = new List(tempValue / 10);
                        retVal.next = temp;
                    }
                }
                retVal = retVal.next;
            }
        }
    }
    public static void sumList(List a, List b) {
        System.out.print("A: ");
        a.print();

        int sizeA = a.size();
        int sizeB = b.size();
        int maxLoop = Math.max(sizeA, sizeB);
        System.out.print("B: ");
        b.print();
        System.out.println("------------------[+]");

        boolean mustNormalize = false;
        List result = null;
        List ptrA = a;
        List ptrB = b;
        List ptrResult = result;
        for (int i = 0; i < maxLoop; i++) {
            int safeA = ptrA != null? ptrA.value : 0;
            int safeB = ptrB != null ? ptrB.value : 0;
            int sum = safeA + safeB;
            if (sum > 9) mustNormalize = true;

            if (ptrResult == null) {
                result = new List(sum);
                ptrResult = result;
            }
            else {
                ptrResult.next = new List(sum);
                ptrResult = ptrResult.next;
            }

            ptrA = ptrA != null && ptrA.next != null ? ptrA.next : null;
            ptrB = ptrB != null && ptrB.next != null ? ptrB.next : null;
        }

        if (mustNormalize) result.normalize();
        result.print();

    }

    public static void main(String[] args) {
        // 2 -> 4 -> 3
        // 5 -> 6 -> 4
        List l1 = new List(2, new List(4, new List(3)));
        List l2 = new List(5, new List(6, new List(4)));
        sumList(l1, l2);

        System.out.println("\n\n");
        // 9_999_999
        //     9_999
        List l3 = new List(9, new List(9, new List(9, new List(9, new List(9, new List(9, new List(9)))))));
        List l4 = new List(9, new List(9, new List(9, new List(9))));
        sumList(l3, l4);
    }
}
