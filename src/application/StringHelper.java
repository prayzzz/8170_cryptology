package application;

/**
 * Created by Patrick on 07.01.2015.
 */
public class StringHelper
{
    public static String[] splitString(String text, Integer blockLength)
    {
        Integer blockCount = text.length() / blockLength;
        Integer blockRest = text.length() % blockLength;

        String[] splittedString = blockRest == 0 ? new String[blockCount] : new String[blockCount + 1];

        for (Integer i = 0; i < blockCount; i++)
        {
            Integer position = i * blockLength;
            splittedString[i] = text.substring(position, position + blockLength);
        }

        if (blockRest == 0)
        {
            return splittedString;
        }

        splittedString[splittedString.length - 1] = text.substring(blockCount * blockLength);

        return splittedString;
    }
}
