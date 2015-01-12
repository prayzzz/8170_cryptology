package application;

/**
 * Created by Patrick on 12.01.2015.
 */
public class MathHelper
{
    public static Integer factorial(Integer n)
    {
        return n == 0 ? 1 : n * factorial(n - 1);
    }
}
