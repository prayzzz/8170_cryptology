package program;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.converter.NumberStringConverter;

public class Controller
{
    private MainModel model;

    public TextArea plainTextTextArea;

    public TextArea cipherTextTextArea;

    public TextField plainTextBlockLength;

    public Text cipherTextBlockLength;

    public Controller()
    {
        model = new MainModel();
    }

    @FXML
    private void initialize()
    {
        Bindings.bindBidirectional(plainTextTextArea.textProperty(), model.plainTextProperty());
        Bindings.bindBidirectional(cipherTextTextArea.textProperty(), model.cipherTextProperty());
        Bindings.bindBidirectional(plainTextBlockLength.textProperty(), model.blockLengthProperty(), new NumberStringConverter());
        cipherTextBlockLength.textProperty().bind(model.calculatedBlockLengthProperty().asString());
    }
}
