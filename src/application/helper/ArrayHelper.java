package application.helper;

import java.util.Random;

/**
 * Created by Patrick on 12.01.2015.
 */
public class ArrayHelper
{
    public static void print(Object[] array)
    {
        for (int i = 0; i < array.length; i++)
        {
            System.out.println(i + ":\t" + array[i].toString());
        }
    }

    // Fisher-Yates-Shuffle
    public static void shuffle(Integer[] array)
    {
        int index;
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--)
        {
            index = random.nextInt(i + 1);
            if (index != i)
            {
                array[index] ^= array[i];
                array[i] ^= array[index];
                array[index] ^= array[i];
            }
        }
    }

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
