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
import javafx.stage.FileChooser;
import javafx.util.converter.NumberStringConverter;
import presentation.Main;
import presentation.ProgramStage;
import presentation.models.EncryptModel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

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
        encryptButton.disableProperty().bind(blockLengthTextField.textProperty().isEmpty());
        saveButton.disableProperty().bind(model.cypherTextProperty().isEmpty());

        blockLengthTextField.setText("");
    }

    public void OpenMainView(ActionEvent actionEvent)
    {
        try
        {
            Main.getLayout().setCenter((GridPane) FXMLLoader.load(getClass().getResource("/presentation/views/MainView.fxml")));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void Save(ActionEvent actionEvent)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Ciphertext");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Textdokument", "*.txt")
        );
        File file = fileChooser.showSaveDialog(ProgramStage.getInstance());
        if (file != null)
        {
            try
            {
                Files.write(file.toPath(), model.getCypherText().getBytes());
            } catch (IOException ex)
            {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void Encrypt(ActionEvent actionEvent)
    {
        if (this.model.getRemoveWhitespaces())
        {
            this.model.setPlainText(this.model.getPlainText().replaceAll(" ", ""));
            this.model.setPlainText(this.model.getPlainText().replaceAll("\n", ""));
        }

        String[] cipherTextBlocks = ColumnTransposition.Transpose(this.model.getPlainText(), this.model.getBlockLength());

        StringBuilder cipherText = new StringBuilder();
        for (int i = 0; i < cipherTextBlocks.length; i++)
        {
            cipherText.append(cipherTextBlocks[i]);

            if ((i + 1) % 10 == 0 || i == (cipherTextBlocks.length - 1))
            {
                cipherText.append('\n');
            } else
            {
                cipherText.append('|');
            }
        }

        this.model.setCypherText(cipherText.toString());

        this.model.setPlainText("");
        Bindings.unbindBidirectional(textArea.textProperty(), model.plainTextProperty());
        Bindings.bindBidirectional(textArea.textProperty(), model.cypherTextProperty());
        textArea.setEditable(false);
    }
}
