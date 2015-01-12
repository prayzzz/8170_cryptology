package application;

import org.junit.Test;

public class ColumnTranspositionDecrypterTest
{

    @Test
    public void testDecrypt() throws Exception
    {
        String cipherText = "lmnmseg>aoiaiGg2HleNeto.";
        String knowWord = "Geogg";

        ColumnTranspositionDecrypter.Decrypt(cipherText, knowWord);
    }
}