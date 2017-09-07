package presentation.models;

import javafx.beans.property.*;

/**
 * Created by Patrick on 18.12.2014.
 */
public class DecryptModel
{
    private StringProperty knownWord = new SimpleStringProperty();

    private StringProperty cypherText = new SimpleStringProperty();

    public String getKnownWord()
    {
        return knownWord.get();
    }

    public StringProperty knownWordProperty()
    {
        return knownWord;
    }

    public void setKnownWord(String knownWord)
    {
        this.knownWord.set(knownWord);
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
