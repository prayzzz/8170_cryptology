package presentation.controller;

import application.ColumnTransposition;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.util.converter.NumberStringConverter;
import presentation.Main;
import presentation.models.EncryptModel;

import java.io.IOException;

/**
 * Created by Patrick on 05.01.2015.
 */
public class EncryptController
{
    public TextArea textArea;

    public CheckBox removeWhitespacesCheckBox;

    public TextField blockLengthTextField;

    public Button saveButton;

    public Button encryptButton;

    private EncryptModel model;

    public EncryptController()
    {
        model = new EncryptModel();
    }

    @FXML
    private void initialize()
    {
        Bindings.bindBidirectional(textArea.textProperty(), model.plainTextProperty());
        Bindings.bindBidirectional(blockLengthTextField.textProperty(), model.blockLengthProperty(), new NumberStringConverter());
        Bindings.bindBidirectional(removeWhitespacesCheckBox.selectedProperty(), model.removeWhitespacesProperty());

        encryptButton.disableProperty().bind(model.plainTextProperty().isEmpty());
        saveButton.disableProperty().bind(model.cypherTextProperty().isEmpty());
    }

    public void OpenMainView(ActionEvent actionEvent)
    {        try
    {
        Main.getLayout().setCenter((GridPane) FXMLLoader.load(getClass().getResource("views/MainView.fxml")));
    }
    catch (IOException e)
    {
        e.printStackTrace();
    }
    }

    public void Save(ActionEvent actionEvent)
    {
    }

    public void Encrypt(ActionEvent actionEvent)
    {
        if (this.model.getRemoveWhitespaces())
        {
            this.model.setPlainText(this.model.getPlainText().replaceAll(" ", ""));
        }

        String[] cipherTextBlocks = ColumnTransposition.Transpose(this.model.getPlainText(), this.model.getBlockLength());

        StringBuilder cipherText = new StringBuilder();
        for (int i = 0; i < cipherTextBlocks.length; i++)
        {
            cipherText.append(cipherTextBlocks[i]);

            if ((i + 1) % 10 == 0)
            {
                cipherText.append('\n');
            }
            else
            {
                cipherText.append('|');
            }
        }

        this.model.setCypherText(cipherText.toString());

        this.model.setPlainText(null);
        textArea.textProperty().bind(model.cypherTextProperty());
        textArea.setEditable(false);
    }
}
