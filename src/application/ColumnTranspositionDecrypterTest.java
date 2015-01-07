package application;

import org.junit.Test;

import static org.junit.Assert.*;

public class ColumnTranspositionDecrypterTest
{

    @Test
    public void testDecrypt() throws Exception
    {
        String cipherText = "lmnmseg>aoiaiGr2HleNeto.";
        String knowWord = "Georg";

        ColumnTranspositionDecrypter.Decrypt(cipherText, knowWord);
    }
}