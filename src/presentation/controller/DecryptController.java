package presentation.controller;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.util.converter.NumberStringConverter;
import presentation.Main;
import presentation.ProgramStage;
import presentation.models.DecryptModel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Created by Patrick on 05.01.2015.
 */
public class DecryptController
{
    public TextArea textArea;
    public TextField knownWordTextField;
    public Button decryptButton;
    private DecryptModel model;

    public DecryptController()
    {
        model = new DecryptModel();
    }

    @FXML
    private void initialize()
    {
        Bindings.bindBidirectional(textArea.textProperty(), model.cypherTextProperty());
        Bindings.bindBidirectional(knownWordTextField.textProperty(), model.knownWordProperty());


        decryptButton.disableProperty().bind(model.knownWordProperty().isEmpty().or(model.cypherTextProperty().isEmpty()));
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

    public void Load(ActionEvent actionEvent)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Ciphertext");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Textdokument", "*.txt")
        );
        File file = fileChooser.showOpenDialog(ProgramStage.getInstance());
        if (file != null)
        {
            try
            {
                model.setCypherText(new String(Files.readAllBytes(file.toPath())));
            } catch (IOException ex)
            {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void Decrypt(ActionEvent actionEvent)
    {

    }
}
