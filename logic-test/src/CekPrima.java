public class CekPrima {
    public static boolean checkPrime(int number) {
        int countDiff = 0;

        System.out.println("Memerika apakah " + number + " termasuk bilangan prima atau bukan...");
        if (number <= 1) return false;
        for(int i = 1; i <= number; i++) {
            if (number % i == 0) {
                countDiff++;
                System.out.println("Faktor ditemukan: " + i);
            }
        }

        return countDiff == 2;
    }

    public static void main(String[] args) {
        int num = 47;

        if (checkPrime(num)) System.out.println(num + " adalah bilangan prima");
        else System.out.println(num + " bukanlah bilangan prima");
    }
}
