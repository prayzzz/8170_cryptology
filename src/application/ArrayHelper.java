package application;

/**
 * Created by Patrick on 12.01.2015.
 */
public class ArrayHelper
{
    public static Integer[] unrank(Integer[] openNumber, Integer r)
    {
        Integer n = openNumber.length;

        while (n > 0)
        {
            swap(openNumber, n - 1, r % n);
            r = Math.floorDiv(r, n);
            n = n - 1;
        }

        return openNumber;
    }

    private static void swap(Integer[] openNumber, int oldPosition, int newPosition)
    {
        Integer i = openNumber[oldPosition];
        openNumber[oldPosition] = openNumber[newPosition];
        openNumber[newPosition] = i;
    }
}
