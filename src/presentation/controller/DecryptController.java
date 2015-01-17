package presentation.controller;

import application.ColumnTranspositionDecrypter;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import presentation.Main;
import presentation.ProgramStage;
import presentation.models.DecryptModel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

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
        this.model = new DecryptModel();
    }

    @FXML
    private void initialize()
    {
        Bindings.bindBidirectional(textArea.textProperty(), model.cypherTextProperty());
        Bindings.bindBidirectional(knownWordTextField.textProperty(), model.knownWordProperty());

        decryptButton.disableProperty().bind(model.knownWordProperty().length().lessThan(2).or(model.cypherTextProperty().isEmpty()));

        // Demo purpose
        try
        {
            model.setCypherText(new String(Files.readAllBytes(new File("C:\\Users\\Patrick\\Desktop\\asd.txt").toPath())));
            model.setKnownWord("scelerisque");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void OpenMainView(ActionEvent actionEvent)
    {
        try
        {
            Main.getLayout().setCenter(FXMLLoader.load(getClass().getResource("/presentation/views/MainView.fxml")));
        }
        catch (IOException e)
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
            }
            catch (IOException ex)
            {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void Decrypt(ActionEvent actionEvent)
    {
        String text = this.model.getCypherText().replaceAll(" ", "");
        text = text.replaceAll("\n", "");
        text = text.replaceAll("\\|", "");

        Map<Integer, List<String>> results = ColumnTranspositionDecrypter.Decrypt(text, this.model.getKnownWord());

        for (Map.Entry<Integer, List<String>> entry : results.entrySet())
        {
            System.out.println("Blocklength: " + entry.getKey());

            for (String res : entry.getValue())
            {
                System.out.println("\t " + res);
            }
        }

        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/views/ResultView.fxml"));
            Main.getLayout().setCenter(loader.load());
            loader.<ResultController>getController().setResult(results);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
