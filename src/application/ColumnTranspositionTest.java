package application;

import org.junit.Test;

import static org.junit.Assert.*;

public class ColumnTranspositionTest
{

    @Test
    public void testTranspose() throws Exception
    {
        String text = "Hallo mein Name ist Georg.";
        Integer blockLength = 5;

        String[] blocks = new ColumnTransposition().Transpose(text, blockLength);

        for (int i = 0; i < blocks.length; i++)
        {
            System.out.println(blocks[i]);
        }
    }
}