package presentation.models;

import javafx.beans.property.*;

/**
 * Created by Patrick on 18.12.2014.
 */
public class EncryptModel
{
    private StringProperty plainText = new SimpleStringProperty();

    private StringProperty cypherText = new SimpleStringProperty();

    private IntegerProperty blockLength = new SimpleIntegerProperty();

    private BooleanProperty removeWhitespaces = new SimpleBooleanProperty();

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

    public boolean getRemoveWhitespaces()
    {
        return removeWhitespaces.get();
    }

    public BooleanProperty removeWhitespacesProperty()
    {
        return removeWhitespaces;
    }

    public void setRemoveWhitespaces(boolean removeWhitespaces)
    {
        this.removeWhitespaces.set(removeWhitespaces);
    }

    public String getCypherText()
    {
        return cypherText.get();
    }

    public StringProperty cypherTextProperty()
    {
        return cypherText;
    }

    public void setCypherText(String cypherText)
    {
        this.cypherText.set(cypherText);
    }
}
