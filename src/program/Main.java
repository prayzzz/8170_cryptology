package program;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
Zu einem Geheimtext, der mit einer kolumnaren Transposition erzeugt worden
ist, soll mit Hilfe eines bekannten Worts die Blocklaenge bestimmt werden.
Der Geheimtext soll dazu in 10 Fuenfergruppen pro Zeile als Datei vorliegen.
Anschließend sollen Stellen bestimmt werden, an denen das bekannte Wort
im Klartext stehen koennte, und Folgerungen fuer den Schluessel abgeleitet werden
*/

public class Main extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("CryptoBob");
        primaryStage.setScene(new Scene(root, 500, 400));
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
