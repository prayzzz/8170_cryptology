package presentation.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import presentation.Main;
import presentation.models.EncryptModel;

import java.io.IOException;

public class MainController
{
   public void exit(ActionEvent actionEvent)
    {
        System.exit(0);
    }

    public void OpenEncryptView(ActionEvent actionEvent)
    {
        try
        {
            Main.getLayout().setCenter((GridPane) FXMLLoader.load(getClass().getResource("/presentation/views/EncryptView.fxml")));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void OpenDecryptView(ActionEvent actionEvent)
    {
        try
        {
            Main.getLayout().setCenter((GridPane) FXMLLoader.load(getClass().getResource("/presentation/views/DecryptView.fxml")));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
