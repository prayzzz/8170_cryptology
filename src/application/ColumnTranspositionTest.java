package application;

import org.junit.Test;

public class ColumnTranspositionTest
{

    @Test
    public void testTranspose() throws Exception
    {
        String text = "HallomeinNameistGeorg.";
        Integer blockLength = 3;

        String blocks = new ColumnTransposition().Transpose(text, blockLength);

        System.out.println(blocks);
    }
}