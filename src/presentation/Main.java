package presentation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

/*
Zu einem Geheimtext, der mit einer kolumnaren Transposition erzeugt worden
ist, soll mit Hilfe eines bekannten Worts die Blocklaenge bestimmt werden.
Der Geheimtext soll dazu in 10 Fuenfergruppen pro Zeile als Datei vorliegen.
Anschließend sollen Stellen bestimmt werden, an denen das bekannte Wort
im Klartext stehen koennte, und Folgerungen fuer den Schluessel abgeleitet werden
*/

public class Main extends Application
{
    private static BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        ProgramStage stage = ProgramStage.getInstance();
        stage.setTitle("cryptobob");
        stage.setWidth(600);
        stage.setHeight(400);

        initRootLayout();
        showMainView();
    }

    public void initRootLayout()
    {
        try
        {
            // Load root layout from fxml file.
            rootLayout = FXMLLoader.load(getClass().getResource("views/RootView.fxml"));

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);

            ProgramStage stage = ProgramStage.getInstance();
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void showMainView()
    {
        try
        {
            rootLayout.setCenter((GridPane)FXMLLoader.load(getClass().getResource("views/MainView.fxml")));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static BorderPane getLayout()
    {
        return rootLayout;
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
