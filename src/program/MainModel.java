package program;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Patrick on 18.12.2014.
 */
public class MainModel
{
    private StringProperty plainText = new SimpleStringProperty();

    private IntegerProperty blockLength = new SimpleIntegerProperty(0);

    private StringProperty cipherText = new SimpleStringProperty();

    private IntegerProperty calculatedBlockLength = new SimpleIntegerProperty(0);

    public String getPlainText()
    {
        return plainText.get();
    }

    public StringProperty plainTextProperty()
    {
        return plainText;
    }

    public void setPlainText(String plainText)
    {
        this.plainText.set(plainText);
    }

    public int getBlockLength()
    {
        return blockLength.get();
    }

    public IntegerProperty blockLengthProperty()
    {
        return blockLength;
    }

    public void setBlockLength(int blockLength)
    {
        this.blockLength.set(blockLength);
    }

    public String getCipherText()
    {
        return cipherText.get();
    }

    public StringProperty cipherTextProperty()
    {
        return cipherText;
    }

    public void setCipherText(String cipherText)
    {
        this.cipherText.set(cipherText);
    }

    public int getCalculatedBlockLength()
    {
        return calculatedBlockLength.get();
    }

    public IntegerProperty calculatedBlockLengthProperty()
    {
        return calculatedBlockLength;
    }

    public void setCalculatedBlockLength(int calculatedBlockLength)
    {
        this.calculatedBlockLength.set(calculatedBlockLength);
    }
}
