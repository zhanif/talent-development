public class Palindrome {
    public static boolean isPalindrom(String str) {
        for (int i = 0; i < str.length() / 2; i++) {
            if (str.charAt(i) != str.charAt(str.length() - 1 - i)) return false;
        }
        return true;
    }

    public static void result(String val) {
        System.out.println(isPalindrom(val)
                ? val + " is palindrom"
                : val + " is not palindrome");
    }

    public static void main(String[] args) {
        result("malam");
        result("12321");
        result("makan");
        result("12345");
    }
}
