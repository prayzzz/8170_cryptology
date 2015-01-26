package test;

import application.ColumnTranspositionDecrypter;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class ColumnTranspositionDecrypterTest
{

    @Test
    public void testDecrypt() throws Exception
    {
        //String cipherText = "rpdra,seainiDLmulieocuice.esosmcetdsgtRoimottntrpilS";
        String cipherText = "a,neWflmNio.leaslEHoimtf";
        String knowWord = "Wolff";

        Map<Integer, List<String>> result = ColumnTranspositionDecrypter.Decrypt(cipherText, knowWord);

        for (Map.Entry<Integer, List<String>> entry : result.entrySet())
        {
            System.out.println(entry.getKey());

            for (String s : entry.getValue())
            {
                System.out.println("\t " + s);
            }
        }
    }
}