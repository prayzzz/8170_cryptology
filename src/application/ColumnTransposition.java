package application;

import java.util.Random;

/**
 * Created by Patrick on 18.12.2014.
 */
public class ColumnTransposition
{
    public String[] Transpose(String plainText, Integer blockLength)
    {
        Integer[] sigma = new Integer[blockLength];
        for (Integer i = 0; i < blockLength; i++)
        {
            sigma[i] = i;
        }

        ShuffleArray(sigma);

        String[] plainTextBlocks = splitString(plainText, blockLength);

        ShuffleChars(sigma, plainTextBlocks);

        return plainTextBlocks;
    }

    private void ShuffleChars(Integer[] sigma, String[] plainTextBlocks)
    {
        for (int i = 0; i < plainTextBlocks.length; i++)
        {
            String plainTextBlock = plainTextBlocks[i];
            StringBuilder block = new StringBuilder(plainTextBlock);

            for (int j = 0; j < block.length(); j++)
            {
                block.setCharAt(sigma[j], plainTextBlock.charAt(j));
            }

            plainTextBlocks[i] = block.toString();
        }
    }

    private String[] splitString(String text, Integer blockLength)
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

        Random r = new Random();
        String puffer = "";
        for (Integer i = 0; i < blockLength - blockRest; i++)
        {
            puffer += Character.toChars(48 + r.nextInt(47))[0];
        }

        splittedString[splittedString.length - 1] = text.substring(blockCount * blockLength) + puffer;

        return splittedString;
    }

    // Fisher-Yates-Shuffle
    private void ShuffleArray(Integer[] array)
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
}
