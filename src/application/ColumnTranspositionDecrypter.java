package application;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Patrick on 07.01.2015.
 */
public class ColumnTranspositionDecrypter
{
    public static Map<Integer, String> Decrypt(String cipherText, String knownWord)
    {
        Map<Integer, String> results = new ConcurrentHashMap<Integer, String>();

        Integer maxBlockLength = cipherText.length();

        for (int blockLength = 1; blockLength <= maxBlockLength; blockLength++)
        {
            if (maxBlockLength % blockLength != 0)
            {
                continue;
            }

            String[] cipherTextBlocks = new String[maxBlockLength / blockLength];
            for (int i = 0; i < cipherTextBlocks.length; i++)
            {
                cipherTextBlocks[i] = "";
            }

            for (int i = 0; i < cipherText.length(); i++)
            {
                Integer column = i % (maxBlockLength / blockLength);
                char c = cipherText.charAt(i);
                cipherTextBlocks[column] += c;
            }

            Integer maxKnownWordBlockRange = ((knownWord.length() - 2) / blockLength) + 2;
            Map<Integer, Character>[] usedChars = new Map[cipherTextBlocks.length];

            for (int i = 0; i < cipherTextBlocks.length; i++)
            {
                usedChars[i] = new ConcurrentHashMap<Integer, Character>();
            }


            for (int currentBlock = 0; currentBlock < cipherTextBlocks.length ; currentBlock++)
            {
                Boolean result = asd(cipherTextBlocks, currentBlock, knownWord, 0, 1, maxKnownWordBlockRange, usedChars);
                System.out.println(result);

                //// Do something if true
            }

        }

        return results;
    }

    private static Boolean asd(String[] cipherTextBlocks, Integer currentBlock, String knownWord, Integer currentKnownWordCharPosition,
                               Integer currentKnownWordBlockRange, Integer maxKnownWordBlockRange, Map<Integer, Character>[] usedChars)
    {
        if (currentKnownWordCharPosition == knownWord.length())
        {
            return true;
        }

        Character currentChar = knownWord.charAt(currentKnownWordCharPosition);

        for (int i = 0; i < cipherTextBlocks[currentBlock].length(); i++)
        {
            if (usedChars[currentBlock].containsKey(i))
            {
                continue;
            }

            if (cipherTextBlocks[currentBlock].charAt(i) == currentChar)
            {
                usedChars[currentBlock].put(i, currentChar);
                Boolean result = asd(cipherTextBlocks, currentBlock, knownWord, currentKnownWordCharPosition + 1, currentKnownWordBlockRange, maxKnownWordBlockRange, usedChars);

                if (result)
                {
                    return true;
                }

                if (currentKnownWordBlockRange < maxKnownWordBlockRange && currentBlock + 1 < cipherTextBlocks.length)
                {
                    result = asd(cipherTextBlocks, currentBlock + 1, knownWord, currentKnownWordCharPosition + 1, currentKnownWordBlockRange + 1, maxKnownWordBlockRange, usedChars);

                    if (result)
                    {
                        return true;
                    }
                }

                usedChars[currentBlock].remove(i);
            }
        }

        return false;
    }
}
