package application;

import java.util.Random;

/**
 * Created by Patrick on 18.12.2014.
 */
public class ColumnTransposition
{
    public static String Transpose(String plainText, Integer blockLength)
    {
        Integer[] sigma = new Integer[blockLength];
        for (Integer i = 0; i < blockLength; i++)
        {
            sigma[i] = i;
        }

        ArrayHelper.shuffle(sigma);
        ArrayHelper.print(sigma);

        String filledPlainText = fillString(plainText, blockLength);

        String[] plainTextBlocks = StringHelper.splitString(filledPlainText, blockLength);
        plainTextBlocks = StringHelper.ShuffleStringBlocks(sigma, plainTextBlocks);

        return ReadColumnWise(blockLength, plainTextBlocks);
    }

    private static String fillString(String text, int blockLength)
    {
        int textModulo = text.length() % blockLength;
        int charsToFill = textModulo == 0 ? 0 : blockLength - textModulo;

        StringBuilder filledText = new StringBuilder(text);

        Random r = new Random();
        for (Integer i = 0; i < charsToFill; i++)
        {
            filledText.append(Character.toChars(65 + r.nextInt(25))[0]);
        }

        return filledText.toString();
    }

    private static String ReadColumnWise(Integer blockLength, String[] plainTextBlocks)
    {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < blockLength; i++)
        {
            for (int j = 0; j < plainTextBlocks.length; j++)
            {
                text.append(plainTextBlocks[j].charAt(i));
            }
        }

        return text.toString();
    }
}
