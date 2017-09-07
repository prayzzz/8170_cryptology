package test;

import application.ColumnTransposition;
import org.junit.Test;

public class ColumnTranspositionTest
{

    @Test
    public void testTranspose() throws Exception
    {
        Integer blockLength = 4;
        //String text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.";
        String text = "Hallo, mein Name ist Wolff.";
        text = text.replaceAll(" ", "");

        String blocks = new ColumnTransposition().Transpose(text, blockLength);

        System.out.println(blocks);
    }
}