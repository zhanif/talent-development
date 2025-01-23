class Recursive {

    int func (int n)  {
        int result;
        if (n == 1)
            return 1;
        result = func (n - 1);
        return result;
    }

}

public class Recursion  {
    public static void main(String args[])  {
        Recursive obj = new Recursive() ;
        System.out.print(obj.func(5));
    }
}
